package dao;

import model.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardDAO.class);

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public CardDAO(Connection conn) {
            connection = conn;
        }

    public Card get(long id){
        String sql = "Select * FROM Card WHERE Card.id = ?";

        Card card = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
                card = new Card.Builder(id)
                        .withAccountID(resultSet.getLong("AccountId"))
                        .withBalance(resultSet.getLong("Balance"))
                        .withNumber(resultSet.getString("Number"))
                        .build();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(card == null){
            LOGGER.info("No card with id: " + id);
            return null;
        }
        LOGGER.info("Card selected from db, id: " + card.getId());
        return card;
    }

    public List<Card> getAll(){
        String sql = "Select * FROM CARD";

        List<Card> cards = new ArrayList<>();

        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cards.add(new Card.Builder(resultSet.getLong("ID"))
                        .withAccountID(resultSet.getLong("AccountId"))
                        .withBalance(resultSet.getLong("Balance"))
                        .withNumber(resultSet.getString("Number"))
                        .build()
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        LOGGER.info("All cards selected from database");
        return cards;
    }

    public long create(Card card){
        String sql = "INSERT INTO CARD (ACCOUNTID, BALANCE, NUMBER) values ( ?, ?, ? )";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, card.getAccountId());
            preparedStatement.setLong(2, card.getBalance());
            preparedStatement.setString(3, card.getNumber());

            preparedStatement.executeUpdate();
            LOGGER.info("New card created");
        } catch (SQLException throwables) {
            LOGGER.error("", throwables);
        }
        return getMaxId();
    }

    public void delete(long id){
        String sql = "DELETE from CARD WHERE CARD.id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        LOGGER.info("Card deleted from database, id: " + id);
    }

    public void update(Card card){
        String sql = "UPDATE CARD Set ACCOUNTID = ?, BALANCE = ?, NUMBER = ?" +
                "WHERE CARD.ID = ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, card.getAccountId());
            preparedStatement.setLong(2, card.getBalance());
            preparedStatement.setString(3, card.getNumber());
            preparedStatement.setLong(4, card.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        LOGGER.info("Account updated, id: " + card.getId());
    }

    public List<Card> getByAccountID(long id){
        String sql = "Select * FROM ACCOUNT WHERE ACCOUNT.USERID = ?";

        List<Card> cards = new ArrayList<>();

        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cards.add(
                        new Card.Builder(resultSet.getLong("id"))
                                .withAccountID(resultSet.getLong("AccountID"))
                                .withNumber(resultSet.getString("Number"))
                                .withBalance(resultSet.getLong("Balance"))
                                .build()
                );
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        LOGGER.info("Accounts by userID selected from database, userID: " + id);
        return cards;
    }

    public long addBalance(long id, long balance){

        String sql = "UPDATE CARD SET CARD.BALANCE = ? WHERE CARD.ID = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, balance);
            preparedStatement.setLong(2, id);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return get(id).getBalance();
    }

    private long getMaxId(){
        String sql = "SELECT Id from CARD ORDER BY id DESC LIMIT 1";
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