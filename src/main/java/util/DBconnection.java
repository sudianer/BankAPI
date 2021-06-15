package util;

import dao.AccountDAO;
import dao.CardDAO;
import dao.UserDAO;
import services.AccountService;
import services.CardService;
import services.UserService;

import  java.sql.*;

public class DBconnection {

    private Connection connection;

    UserDAO userDAO;
    AccountDAO accountDAO;
    CardDAO cardDAO;

    UserService userService;
    AccountService accountService;
    CardService cardService;

    public DBconnection (String URL, String login, String password) throws SQLException {
        connection = DriverManager.getConnection(URL, login, password);
        userDAO = new UserDAO(connection);
        accountDAO = new AccountDAO(connection);
        cardDAO = new CardDAO(connection);

        userService = new UserService(this.getUserDAO());
        accountService = new AccountService(this.getAccountDAO(), userService);
        cardService = new CardService(this.getCardDAO());
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public CardDAO getCardDAO() {
        return cardDAO;
    }

    public UserService getUserService() {
        return userService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public CardService getCardService() {
        return cardService;
    }
}
