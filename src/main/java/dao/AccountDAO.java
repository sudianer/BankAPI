package dao;

import model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDAO.class);
    private final CardDAO cardDAO = new CardDAO(connection);

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public AccountDAO(Connection conn) {
        connection = conn;
    }

    public Account get(long id){
        String sql = "Select * FROM ACCOUNT WHERE ACCOUNT.id = ?";

        Account account = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                account = new Account.Builder(id)
                        .withUserID(resultSet.getLong("UserID"))
                        .withNumber(resultSet.getString("Number"))
                        .withCards(cardDAO.getByAccountID(id))
                        .build();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(account == null){
            LOGGER.info("No account with id: " + id);
            return null;
        }

        LOGGER.info("Account Selected from database, id: " + account.getId());
        return account;
    }

    public List<Account> getAll(){
        String sql = "Select * FROM ACCOUNT";

        List<Account> accounts = new ArrayList<>();

        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accounts.add(
                        new Account.Builder(resultSet.getLong("id"))
                        .withUserID(resultSet.getLong("UserID"))
                        .withNumber(resultSet.getString("Number"))
                        .withCards(cardDAO.getByAccountID(resultSet.getLong("id")))
                        .build()
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        LOGGER.info("All accounts selected from database");
        return accounts;
    }

    public long create(Account account){
        String sql = "INSERT INTO ACCOUNT (USERID, NUMBER) values ( ?, ? )";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, account.getUserID());
            preparedStatement.setString(2, account.getNumber());

            preparedStatement.executeUpdate();
            LOGGER.info("New account created, id: " + account.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return getMaxID();
    }

    public void delete(long id){
        String sql = "DELETE from ACCOUNT WHERE ACCOUNT.id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            LOGGER.info("Account deleted from database, id: " + id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Account account){
        String sql = "UPDATE ACCOUNT Set USERID = ?, NUMBER = ?" +
                "WHERE ACCOUNT.ID = ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, account.getUserID());
            preparedStatement.setString(2, account.getNumber());
            preparedStatement.setLong(3, account.getId());

            preparedStatement.executeUpdate();
            LOGGER.info("Account updated, id: " + account.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Account> getByUserID(long userID){
        LOGGER.info("Getting accounts by user_ID: " + userID);
        return getAll()
                .stream()
                .filter(account -> account.getUserID() == userID)
                .collect(Collectors.toList());
    }

    private long getMaxID(){
        String sql = "SELECT Id from ACCOUNT ORDER BY id DESC LIMIT 1";
        long maxId = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                maxId = resultSet.getLong("Id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return maxId;
    }
}