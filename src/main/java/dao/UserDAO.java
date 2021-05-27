package dao;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);
    private final AccountDAO accountDAO = new AccountDAO(connection);

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public UserDAO(Connection conn)
    {
        connection = conn;
    }

    public User get(long id){

        String sql = "Select * FROM USER WHERE USER.id = ?";

        User user = null;

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new User.Builder(resultSet.getLong("id"))
                        .withFirstName(resultSet.getString("firstName"))
                        .withSecondName(resultSet.getString("secondName"))
                        .withAccountList(accountDAO.getByUserID(resultSet.getLong("id")))
                        .build();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assert user != null;
        LOGGER.info("User Selected from database, id: " + user.getId());
        return user;
    }

    public List<User> getAll(){
        List<User> result = new ArrayList<>();
        String sql = "Select * FROM USER";

        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                result.add(new User.Builder(resultSet.getInt("ID"))
                        .withFirstName(resultSet.getString("firstName"))
                        .withSecondName(resultSet.getString("secondName"))
                        .build()
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        LOGGER.info("All users selected from database");

        return result;
    }

    public long create(User user){
        String sql = "INSERT INTO USER (FIRSTNAME, SECONDNAME) values ( ?, ? )";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());

            preparedStatement.executeUpdate();
            LOGGER.info("new User created");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return getMaxId();
    }

    public void delete(long id){
        String sql = "DELETE from USER WHERE USER.id = ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();
            LOGGER.info("User Deleted, id: " + id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(User user){
        String sql = "UPDATE USER Set FIRSTNAME = ?, SECONDNAME = ?" +
                "WHERE USER.ID = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setLong(3, user.getId());

            preparedStatement.executeUpdate();
            LOGGER.info("User Updated, id: " + user.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private long getMaxId(){
        String sql = "SELECT Id from USER ORDER BY id DESC LIMIT 1";
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
