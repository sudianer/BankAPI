package httpServer.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.AccountService;
import util.JSONparser;

import java.io.IOException;
import java.io.OutputStream;

public class GetUserAccountsHandler implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetUserAccountsHandler.class);

	AccountService accountService;
	long userId;

	public GetUserAccountsHandler(AccountService accountService, long userId){
		this.accountService = accountService;
		this.userId = userId;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		LOGGER.info("Started");
		OutputStream outputStream = exchange.getResponseBody();

		StringBuilder response = new StringBuilder();

		response.append(JSONparser.toJsonString(accountService.getByUserID(userId)));

		exchange.sendResponseHeaders(200, response.toString().getBytes().length);

		outputStream.write(response.toString().getBytes());
		outputStream.flush();
		outputStream.close();

		LOGGER.info("Done");
	}
}