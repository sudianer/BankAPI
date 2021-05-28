package httpServer.handlers.account;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpServer.utils.ServiceContainer;
import model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JSONparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class PostNewAccountHandler implements HttpHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostNewAccountHandler.class);

	long userID;

	public PostNewAccountHandler(long userID){
		this.userID = userID;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LOGGER.info("Started");

		InputStream inputStream = exchange.getRequestBody();
		OutputStream outputStream = exchange.getResponseBody();

		String response = "";
		Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

		Account account = JSONparser.fromJson(JSONparser.parse(scanner.nextLine()), Account.class);

		assert account != null;
		account = new Account.Builder(0)
				.withNumber(account.getNumber())
				.withCards(account.getCards())
				.withUserID(userID)
				.build();

		ServiceContainer.getAccountService().create(account);

		exchange.sendResponseHeaders(200, response.getBytes().length);

		outputStream.write(response.getBytes());
		outputStream.flush();
		outputStream.close();
		LOGGER.info("Done");
	}
}