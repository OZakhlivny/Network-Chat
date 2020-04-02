package ru.geekbrains.java2.client.db;

import java.sql.*;

public class NetworkChatDB {
    private static final String DB_URL = "jdbc:sqlite:NetworkClientServer/NetworkChat.db";
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

    public static String changeNickname(String oldNickname, String newNickname) throws SQLException {
        String errorString = "Error during changing the nickname!";
        resultSet = statement.executeQuery(String.format("SELECT nickname FROM users WHERE nickname=\"%s\";", newNickname));
        if(!resultSet.next()){
            if(statement.executeUpdate(String.format("UPDATE users SET nickname=\"%s\" WHERE nickname=\"%s\";", newNickname, oldNickname)) > 0)
                errorString = null;
        } else errorString = String.format("Nickname \"%s\" is busy!", newNickname);

        return errorString;
    }

    public static void closeConnection() throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }

    public static String getLogin (String nickname) throws SQLException{
        String login = null;
        resultSet = statement.executeQuery(String.format("SELECT login FROM users WHERE nickname=\"%s\";", nickname));
        if(resultSet.next()) login = resultSet.getString("login");
        return login;
    }

    public static boolean isRestrictedWord(String word) throws SQLException {
        resultSet = statement.executeQuery(String.format("SELECT id FROM restricted_words WHERE \"%s%%\" LIKE UPPER(TRIM(word))||\"%%\";", word.trim().toUpperCase()));
        return resultSet.next();
    }

}
