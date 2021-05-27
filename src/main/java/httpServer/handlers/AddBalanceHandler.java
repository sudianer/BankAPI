package httpServer.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.CardService;

import java.io.IOException;
import java.io.OutputStream;

public class AddBalanceHandler implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddBalanceHandler.class);

	CardService cardService;
	long cardId;
	long sum;

	public AddBalanceHandler(CardService cardService, long cardId, long sum){
		this.cardService = cardService;
		this.cardId = cardId;
		this.sum = sum;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LOGGER.info("Started");

		String response = "Id: " + cardId;
		response += " new Balance: " + cardService.addBalance(cardId, sum);

		OutputStream outputStream = exchange.getResponseBody();

		exchange.sendResponseHeaders(200, response.getBytes().length);

		outputStream.write(response.getBytes());
		outputStream.flush();
		outputStream.close();
		LOGGER.info("Done");
	}
}