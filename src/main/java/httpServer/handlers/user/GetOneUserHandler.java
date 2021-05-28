package httpServer.handlers.user;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpServer.handlers.ROUTING;
import httpServer.handlers.account.GetUserAccountsHandler;
import httpServer.utils.HandlerUtils;
import httpServer.utils.ServiceContainer;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JSONparser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class GetOneUserHandler implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetOneUserHandler.class);

	long userId;
	Map<ROUTING, String> pathParameters;

	public GetOneUserHandler(Map<ROUTING, String> pathParameters){
		this.userId = HandlerUtils.parseInt(pathParameters.get(ROUTING.USER_ID));

		pathParameters.remove(ROUTING.USER_ID);
		this.pathParameters = pathParameters;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		LOGGER.info("Started");

		OutputStream outputStream = exchange.getResponseBody();

		StringBuilder response = new StringBuilder();

		User user = ServiceContainer.getUserService().get(userId);

		if(user == null){
			LOGGER.info("no such user");
			response.append(JSONparser.toJsonString("No such user"));
			exchange.sendResponseHeaders(404, response.toString().getBytes().length);

			outputStream.write(response.toString().getBytes());
			outputStream.flush();
			outputStream.close();
		}

		if(!pathParameters.isEmpty()){
			new GetUserAccountsHandler(pathParameters, userId).handle(exchange);
		}

		response.append(JSONparser.toJsonString(user));
		exchange.sendResponseHeaders(200, response.toString().getBytes().length);

		outputStream.write(response.toString().getBytes());
		outputStream.flush();
		outputStream.close();

		LOGGER.info("Done");
	}
}