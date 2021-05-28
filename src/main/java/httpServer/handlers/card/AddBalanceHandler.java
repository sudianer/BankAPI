package httpServer.handlers.card;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpServer.utils.ServiceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JSONparser;

import java.io.IOException;
import java.io.OutputStream;

public class AddBalanceHandler implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddBalanceHandler.class);

	long cardId;
	long sum;

	public AddBalanceHandler(long cardId, long sum){
		this.cardId = cardId;
		this.sum = sum;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LOGGER.info("Started");

		long newBalance = ServiceContainer.getCardService().addBalance(cardId, sum);
		String response = "Card id: " + cardId + "newBalance is: " + newBalance;
		response = JSONparser.toJsonString(response);

		OutputStream outputStream = exchange.getResponseBody();

		exchange.sendResponseHeaders(200, response.getBytes().length);

		outputStream.write(response.getBytes());
		outputStream.flush();
		outputStream.close();
		LOGGER.info("Done");
	}
}