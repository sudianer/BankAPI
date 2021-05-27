package httpServer.handlers;

import httpServer.utils.HandlerUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.AccountService;
import services.CardService;
import services.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class RoutingHandler implements HttpHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoutingHandler.class);

	private final UserService userService;
	private final CardService cardService;
	private final AccountService accountService;

	public RoutingHandler(UserService userService, CardService cardService, AccountService accountService){
		this.userService = userService;
		this.cardService = cardService;
		this.accountService = accountService;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LOGGER.info("Started");
		OutputStream outputStream = exchange.getResponseBody();

		Map<ROUTING, String> pathParameters = HandlerUtils.pathToMap(exchange.getRequestURI().getPath());

		String response = "";

		while(true) {
			if(pathParameters.containsKey(ROUTING.NEWUSER)){
				new PostNewUserHandler(userService).handle(exchange);
				break;
			}

			if(pathParameters.containsKey(ROUTING.CARD_ID)){
				new GetOneCardHandler(cardService, HandlerUtils.parseInt(pathParameters.get(ROUTING.CARD_ID)))
						.handle(exchange);
				break;
			}

			if(pathParameters.containsKey(ROUTING.NEWCARD)){
				new PostNewCardHandler(cardService, HandlerUtils.parseInt(pathParameters.get(ROUTING.ACCOUNT_ID)))
						.handle(exchange);
				break;
			}

			if(pathParameters.containsKey(ROUTING.CARDS)){
				new GetAccountCardsHandler(cardService, HandlerUtils.parseInt(pathParameters.get(ROUTING.ACCOUNT_ID)))
						.handle(exchange);
				break;
			}

			if(pathParameters.containsKey(ROUTING.NEWACCOUNT)){
				new PostNewAccountHandler(accountService, HandlerUtils.parseInt(pathParameters.get(ROUTING.USER_ID)))
						.handle(exchange);
				break;
			}

			if(pathParameters.containsKey(ROUTING.ACCOUNT_ID)){
				new GetOneAccountHandler(accountService, HandlerUtils.parseInt(pathParameters.get(ROUTING.ACCOUNT_ID)))
						.handle(exchange);
				break;
			}

			if(pathParameters.containsKey(ROUTING.ACCOUNTS)) {
				new GetUserAccountsHandler(accountService, HandlerUtils.parseInt(pathParameters.get(ROUTING.USER_ID)))
						.handle(exchange);
				break;
			}

			if(pathParameters.containsKey(ROUTING.USER_ID)){
				new GetOneUserHandler(userService, HandlerUtils.parseInt(pathParameters.get(ROUTING.USER_ID)))
						.handle(exchange);
				break;
			}

			if(pathParameters.containsKey(ROUTING.USERS)){
				new GetAllUsersHandler(userService).handle(exchange);
				break;
			}

			if(pathParameters.isEmpty()){
				response = "Bad URL";
				exchange.sendResponseHeaders(400, response.getBytes().length);
				outputStream.write(response.getBytes());
				outputStream.flush();
				outputStream.close();
			}
		}

		exchange.sendResponseHeaders(200, response.getBytes().length);
		outputStream.write(response.getBytes());
		outputStream.flush();
		outputStream.close();
		LOGGER.info("Done");
	}
}