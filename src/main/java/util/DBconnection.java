package util;

import  java.sql.*;

public class DBconnection {


    private Connection connection;

    public DBconnection (String URL, String login, String password) throws SQLException {
        connection = DriverManager.getConnection(URL, login, password);
    }

    public Connection getConnection(){
        return connection;
    }

    public void disconnect() throws SQLException {
        connection.close();
    }
}
