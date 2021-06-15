import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpServer;
import httpServer.config.Configuration;
import httpServer.config.ConfigurationManager;
import httpServer.handlers.*;
import util.DBconnection;
import services.AccountService;
import services.CardService;
import services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static String URL = "jdbc:h2:/Users/filatovoleg/Documents/bankDatabase/bank";
    private static String LOGIN = "admin";
    private static String PASSWORD = "admin";

    private static HttpServer server;

    private static int port = 8080;

    private static DBconnection dBconnection;

    public static void main(String[] args){
        try {
            dBconnection = new DBconnection(URL, LOGIN, PASSWORD);

            server = HttpServer.create();
            server.bind(new InetSocketAddress(port), 0);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        server.createContext("/",
                new RoutingHandler(
                    dBconnection.getUserService(),
                    dBconnection.getCardService(),
                    dBconnection.getAccountService())
                );

        server.start();
        LOGGER.info("server started on port: " + port);
    }
}
