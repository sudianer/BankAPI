package util;

import dao.AccountDAO;
import dao.CardDAO;
import dao.UserDAO;
import repository.AccountRepository;
import repository.CardRepository;
import repository.UserRepository;

import  java.sql.*;

public class DBconnection {

    private Connection connection;

    UserDAO userDAO;
    AccountDAO accountDAO;
    CardDAO cardDAO;

    UserRepository userRepository;
    AccountRepository accountRepository;
    CardRepository cardRepository;

    public DBconnection (String URL, String login, String password) throws SQLException {
        connection = DriverManager.getConnection(URL, login, password);
        userDAO = new UserDAO(connection);
        accountDAO = new AccountDAO(connection);
        cardDAO = new CardDAO(connection);

        userRepository = new UserRepository(this.getUserDAO());
        accountRepository = new AccountRepository(this.getAccountDAO());
        cardRepository = new CardRepository(this.getCardDAO());
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public CardRepository getCardRepository() {
        return cardRepository;
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
}
