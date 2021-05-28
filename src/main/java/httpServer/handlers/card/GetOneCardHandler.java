package httpServer.handlers.card;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpServer.handlers.PARAMETERS;
import httpServer.handlers.ROUTING;
import httpServer.utils.HandlerUtils;
import httpServer.utils.ServiceContainer;
import model.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JSONparser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class GetOneCardHandler implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetOneCardHandler.class);

	Map<ROUTING, String> pathParameters;
	long cardId;
	long accountId;

	public GetOneCardHandler(Map<ROUTING, String> pathParameters, long accountId){
		this.cardId = HandlerUtils.parseInt(pathParameters.get(ROUTING.CARD_ID));
		pathParameters.remove(ROUTING.CARD_ID);
		this.pathParameters = pathParameters;
		this.accountId = accountId;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LOGGER.info("Started");

		OutputStream outputStream = exchange.getResponseBody();
		StringBuilder response = new StringBuilder();

		Map<String, String> paramsMap = HandlerUtils.queryToMap(exchange.getRequestURI().getQuery());
		long sum = HandlerUtils.parseInt(paramsMap.get(PARAMETERS.SUM.toString().toLowerCase()));

		Card card = ServiceContainer.getCardService().get(cardId);

		if(card == null){
			response.append(JSONparser.toJsonString("No card found with id: " + cardId));
			exchange.sendResponseHeaders(404, response.toString().getBytes().length);

			outputStream.write(response.toString().getBytes());
			outputStream.flush();
			outputStream.close();
		}

		assert card != null;
		if(card.getAccountId() != accountId){
			response.append(JSONparser.toJsonString("No such card found on given account with id: " + cardId));
			exchange.sendResponseHeaders(404, response.toString().getBytes().length);

			outputStream.write(response.toString().getBytes());
			outputStream.flush();
			outputStream.close();
		}

		if(!paramsMap.isEmpty()){
			new AddBalanceHandler(cardId, sum).handle(exchange);
		}

		response.append(JSONparser.toJsonString(card));

		exchange.sendResponseHeaders(200, response.toString().getBytes().length);

		outputStream.write(response.toString().getBytes());
		outputStream.flush();
		outputStream.close();
		LOGGER.info("Done");
	}
}
