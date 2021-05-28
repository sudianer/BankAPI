package httpServer.handlers;

import httpServer.handlers.user.GetAllUsersHandler;
import httpServer.utils.HandlerUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpServer.utils.ServiceContainer;
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

	public RoutingHandler(UserService userService, CardService cardService, AccountService accountService) {
		ServiceContainer.setUserService(userService);
		ServiceContainer.setCardService(cardService);
		ServiceContainer.setAccountService(accountService);
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LOGGER.info("Started");
		OutputStream outputStream = exchange.getResponseBody();

		Map<ROUTING, String> pathParameters = HandlerUtils.pathToMap(exchange.getRequestURI().getPath());
		String response = "";

		while(true) {

			if(pathParameters.containsKey(ROUTING.USERS)){
				new GetAllUsersHandler(pathParameters).handle(exchange);
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