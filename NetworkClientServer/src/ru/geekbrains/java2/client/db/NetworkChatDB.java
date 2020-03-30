package ru.geekbrains.java2.client.db;

import java.sql.*;

public class NetworkChatDB {
    private static final String DB_URL = "jdbc:sqlite:C:\\SQLlite\\db\\NetworkChat.db";
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void connectToDB() throws SQLException {
        connection = null;
        connection = DriverManager.getConnection(DB_URL);
        statement = connection.createStatement();
    }

    public static String getNickname (String login, String password) throws SQLException{
        String nickname = null;
        resultSet = statement.executeQuery(String.format("SELECT nickname FROM users WHERE login=\"%s\" AND password=\"%s\";", login, password));
        if(resultSet.next()) nickname = resultSet.getString("nickname");
        return nickname;
    }

    public static void closeConnection() throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }

}
