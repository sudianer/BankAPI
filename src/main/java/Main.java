import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpServer;
import dao.CardDAO;
import httpServer.config.Configuration;
import httpServer.config.ConfigurationManager;
import httpServer.handlers.*;
import repository.AccountRepository;
import repository.CardRepository;
import repository.UserRepository;
import dao.AccountDAO;
import dao.UserDAO;
import services.AccountService;
import services.CardService;
import services.UserService;
import util.DBconnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static String URL = "jdbc:h2:/Users/filatovoleg/Documents/bankDatabase/bank";
    private static String LOGIN = "admin";
    private static String PASSWORD = "admin";

    private final static String CONFIG_PATH = "src/main/resources/httpConfig.json";
    private static Configuration configuration;
    private static HttpServer server;

    private static DBconnection connection;

    static File json = new File("/Users/filatovoleg/Documents/JDBC_week_1/JSonFiles/test.json");
    static File outputJson = new File("/Users/filatovoleg/Documents/JDBC_week_1/JSonFiles/output.json");

    static UserDAO userDAO;
    static AccountDAO accountDAO;
    static CardDAO cardDAO;

    public static void main(String[] args){
        try {
            connection = new DBconnection(URL, LOGIN, PASSWORD);
            userDAO = new UserDAO(connection.getConnection());
            accountDAO = new AccountDAO(connection.getConnection());
            cardDAO = new CardDAO(connection.getConnection());

            ConfigurationManager.getInstance().loadConfigurationFile(CONFIG_PATH);
            configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

            server = HttpServer.create();
            server.bind(new InetSocketAddress(configuration.getPort()), 0);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        UserRepository userRepository = new UserRepository(userDAO);
        AccountRepository accountRepository = new AccountRepository(accountDAO);
        CardRepository cardRepository = new CardRepository(cardDAO);

        // /User выводит список всех пользователей
        // /User/{id} выводит одного пользователя
        server.createContext("/",
                new RoutingHandler(new UserService(userRepository),
                new CardService(cardRepository),
                new AccountService(accountRepository)));

        server.start();
        LOGGER.info("server started on port: " + configuration.getPort());
    }
}
