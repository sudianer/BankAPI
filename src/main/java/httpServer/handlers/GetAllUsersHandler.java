package httpServer.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.UserService;
import util.JSONparser;

import java.io.IOException;
import java.io.OutputStream;

public class GetAllUsersHandler implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetAllUsersHandler.class);

	UserService userService;

	public GetAllUsersHandler(UserService userService){
		this.userService = userService;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		LOGGER.info("Started");
		OutputStream outputStream = exchange.getResponseBody();

		StringBuilder response = new StringBuilder();

		response.append(JSONparser.toJsonString(userService.getAll()));

		exchange.sendResponseHeaders(200, response.toString().getBytes().length);

		outputStream.write(response.toString().getBytes());
		outputStream.flush();
		outputStream.close();

		LOGGER.info("Done");
	}
}
