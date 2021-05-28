package httpServer.handlers.account;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpServer.handlers.ROUTING;
import httpServer.utils.ServiceContainer;
import model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JSONparser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class GetUserAccountsHandler implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetUserAccountsHandler.class);

	Map<ROUTING, String> pathParameters;
	long userId;

	public GetUserAccountsHandler(Map<ROUTING, String> pathParameters, long userId){
		pathParameters.remove(ROUTING.ACCOUNTS);
		this.pathParameters = pathParameters;
		this.userId = userId;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		LOGGER.info("Started");

		OutputStream outputStream = exchange.getResponseBody();
		StringBuilder response = new StringBuilder();

		if(!pathParameters.isEmpty()){
			if(pathParameters.get(ROUTING.ACCOUNT_ID).equalsIgnoreCase(ROUTING.NEWACCOUNT.toString())){
				new PostNewAccountHandler(userId).handle(exchange);
			}
			new GetOneAccountHandler(pathParameters, userId).handle(exchange);
		}

		List<Account> accounts = ServiceContainer.getAccountService().getByUserID(userId);

		if(accounts.isEmpty()){
			response.append(JSONparser.toJsonString("current user has no active accounts"));
			exchange.sendResponseHeaders(404, response.toString().getBytes().length);
		}

		if(!accounts.isEmpty()) {
			response.append(JSONparser.toJsonString(accounts));
			exchange.sendResponseHeaders(200, response.toString().getBytes().length);
		}

		outputStream.write(response.toString().getBytes());
		outputStream.flush();
		outputStream.close();

		LOGGER.info("Done");
	}
}