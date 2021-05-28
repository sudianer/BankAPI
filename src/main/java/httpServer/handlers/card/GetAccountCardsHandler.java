package httpServer.handlers.card;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpServer.handlers.ROUTING;
import httpServer.handlers.user.GetAllUsersHandler;
import httpServer.utils.ServiceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JSONparser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class GetAccountCardsHandler implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetAllUsersHandler.class);

	Map<ROUTING, String > pathParameters;
	long accountId;

	public GetAccountCardsHandler(Map<ROUTING, String> pathParameters, long accountId){
		pathParameters.remove(ROUTING.CARDS);
		this.pathParameters = pathParameters;
		this.accountId = accountId;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LOGGER.info("Started");

		OutputStream outputStream = exchange.getResponseBody();

		StringBuilder response = new StringBuilder();

		if(!pathParameters.isEmpty()){
			if(pathParameters.get(ROUTING.CARD_ID).equalsIgnoreCase(ROUTING.NEWCARD.toString())){
				new PostNewCardHandler(accountId).handle(exchange);
			}
			new GetOneCardHandler(pathParameters, accountId).handle(exchange);
		}

		response.append(JSONparser.toJsonString(ServiceContainer.getCardService().getByAccountId(accountId)));

		exchange.sendResponseHeaders(200, response.toString().getBytes().length);

		outputStream.write(response.toString().getBytes());
		outputStream.flush();
		outputStream.close();
		LOGGER.info("Done");
	}
}
