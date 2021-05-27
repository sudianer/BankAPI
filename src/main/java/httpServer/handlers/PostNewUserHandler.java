package httpServer.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.UserService;
import util.JSONparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class PostNewUserHandler implements HttpHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostNewUserHandler.class);

	UserService userService;

	public PostNewUserHandler(UserService userService){
		this.userService = userService;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		LOGGER.info("Started");
		InputStream inputStream = exchange.getRequestBody();
		OutputStream outputStream = exchange.getResponseBody();

		String response = "";
		Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

		User user = JSONparser.fromJson(JSONparser.parse(scanner.nextLine()), User.class);

		userService.create(user);

		exchange.sendResponseHeaders(200, response.getBytes().length);

		outputStream.write(response.getBytes());
		outputStream.flush();
		outputStream.close();
		LOGGER.info("Done");
	}
}