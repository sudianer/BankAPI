package httpServer.handlers.account;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpServer.handlers.ROUTING;
import httpServer.handlers.card.GetAccountCardsHandler;
import httpServer.utils.HandlerUtils;
import httpServer.utils.ServiceContainer;
import model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JSONparser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class GetOneAccountHandler  implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetOneAccountHandler.class);

	Map<ROUTING, String> pathParameters;
	long account_Id;
	long user_Id;

	public GetOneAccountHandler(Map<ROUTING, String> pathParameters, long userId){
		this.user_Id = userId;
		this.account_Id = HandlerUtils.parseInt(pathParameters.get(ROUTING.ACCOUNT_ID));
		pathParameters.remove(ROUTING.ACCOUNT_ID);
		this.pathParameters = pathParameters;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LOGGER.info("Started");

		OutputStream outputStream = exchange.getResponseBody();

		StringBuilder response = new StringBuilder();

		Account account = ServiceContainer.getAccountService().get(account_Id);

		if(account == null){

			response.append("No account found with id: ").append(account_Id);
			exchange.sendResponseHeaders(404, response.toString().getBytes().length);

			outputStream.write(response.toString().getBytes());
			outputStream.flush();
			outputStream.close();
		}

		if(account.getUserID() != user_Id){

			response.append(JSONparser.toJsonString("No account with given id found on active user"));
			exchange.sendResponseHeaders(404, response.toString().getBytes().length);

			outputStream.write(response.toString().getBytes());
			outputStream.flush();
			outputStream.close();
		}

		if(!pathParameters.isEmpty()){
			new GetAccountCardsHandler(pathParameters, account_Id).handle(exchange);
		}
		response.append(JSONparser.toJsonString(account));

		exchange.sendResponseHeaders(200, response.toString().getBytes().length);

		outputStream.write(response.toString().getBytes());
		outputStream.flush();
		outputStream.close();
		LOGGER.info("Done");
	}
}
