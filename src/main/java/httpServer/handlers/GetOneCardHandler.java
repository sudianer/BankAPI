package httpServer.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpServer.utils.HandlerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.CardService;
import util.JSONparser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class GetOneCardHandler implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetOneCardHandler.class);

	CardService cardService;
	long cardId;

	public GetOneCardHandler(CardService cardService, long cardId){
		this.cardService = cardService;
		this.cardId = cardId;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LOGGER.info("Started");

		OutputStream outputStream = exchange.getResponseBody();
		StringBuilder response = new StringBuilder();


		Map<String, String> paramsMap = HandlerUtils.queryToMap(exchange.getRequestURI().getQuery());
		long sum = HandlerUtils.parseInt(paramsMap.get(PARAMETERS.SUM.toString().toLowerCase()));

		if(!paramsMap.isEmpty()){

			new AddBalanceHandler(cardService, cardId, sum).handle(exchange);

			exchange.sendResponseHeaders(200, response.toString().getBytes().length);

			outputStream.write(response.toString().getBytes());
			outputStream.flush();
			outputStream.close();
		}

		response.append(JSONparser.toJsonString(cardService.get(cardId)));

		exchange.sendResponseHeaders(200, response.toString().getBytes().length);

		outputStream.write(response.toString().getBytes());
		outputStream.flush();
		outputStream.close();
		LOGGER.info("Done");
	}
}
