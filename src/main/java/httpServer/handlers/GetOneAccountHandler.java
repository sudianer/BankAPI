package httpServer.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.AccountService;
import util.JSONparser;

import java.io.IOException;
import java.io.OutputStream;

public class GetOneAccountHandler  implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetOneAccountHandler.class);

	AccountService accountService;
	long account_Id;

	public GetOneAccountHandler(AccountService accountService, long account_Id){
		this.accountService = accountService;
		this.account_Id = account_Id;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LOGGER.info("Started");

		OutputStream outputStream = exchange.getResponseBody();

		StringBuilder response = new StringBuilder();

		response.append(JSONparser.toJsonString(accountService.get(account_Id)));

		exchange.sendResponseHeaders(200, response.toString().getBytes().length);

		outputStream.write(response.toString().getBytes());
		outputStream.flush();
		outputStream.close();
		LOGGER.info("Done");
	}
}
