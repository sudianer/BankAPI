package httpServer.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.CardService;
import util.JSONparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class PostNewCardHandler  implements HttpHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostNewCardHandler.class);

	CardService cardService;
	long accountId;

	public PostNewCardHandler(CardService cardService, long accountId){
		this.cardService = cardService;
		this.accountId = accountId;
	}


	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LOGGER.info("Started");
		InputStream inputStream = exchange.getRequestBody();
		OutputStream outputStream = exchange.getResponseBody();

		String response = "";
		Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

		Card card = JSONparser.fromJson(JSONparser.parse(scanner.nextLine()), Card.class);

		assert card != null;
		card = new Card.Builder(0)
				.withNumber(card.getNumber())
				.withAccountID(accountId)
				.withBalance(card.getBalance())
				.build();

		cardService.create(card);

		exchange.sendResponseHeaders(200, response.getBytes().length);

		outputStream.write(response.getBytes());
		outputStream.flush();
		outputStream.close();
		LOGGER.info("Done");
	}
}