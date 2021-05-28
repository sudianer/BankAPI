package httpServer.handlers.user;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpServer.handlers.ROUTING;
import httpServer.utils.ServiceContainer;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JSONparser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class GetAllUsersHandler implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetAllUsersHandler.class);

	Map<ROUTING, String> pathParameters;

	public GetAllUsersHandler(Map<ROUTING, String> pathParameters){
		pathParameters.remove(ROUTING.USERS);
		this.pathParameters = pathParameters;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		LOGGER.info("Started");

		OutputStream outputStream = exchange.getResponseBody();
		StringBuilder response = new StringBuilder();

		if(!pathParameters.isEmpty()){
			if(pathParameters.get(ROUTING.USER_ID).equalsIgnoreCase(ROUTING.NEWUSER.toString())){
				new PostNewUserHandler().handle(exchange);
			}
			new GetOneUserHandler(pathParameters).handle(exchange);
		}

		List<User> users = ServiceContainer.getUserService().getAll();

		if(users.isEmpty()){
			response.append(JSONparser.toJsonString("No users in database"));
			exchange.sendResponseHeaders(404, response.toString().getBytes().length);
		}

		if(!users.isEmpty()){
			response.append(JSONparser.toJsonString(ServiceContainer.getUserService().getAll()));
			exchange.sendResponseHeaders(200, response.toString().getBytes().length);
		}

		outputStream.write(response.toString().getBytes());
		outputStream.flush();
		outputStream.close();

		LOGGER.info("Done");
	}
}
