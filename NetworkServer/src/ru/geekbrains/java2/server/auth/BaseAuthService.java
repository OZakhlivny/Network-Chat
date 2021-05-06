package ru.geekbrains.java2.server.auth;

import ru.geekbrains.java2.client.db.NetworkChatDB;

import java.sql.SQLException;

public class BaseAuthService implements AuthService {

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        String result = null;
        try {
            NetworkChatDB.connectToDB();
            result = NetworkChatDB.getNickname(login, password);
            NetworkChatDB.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void start() {
        System.out.println("Authentication service is running...");
    }

    @Override
    public void stop() {
        System.out.println("Authentication service is stopped.");
    }
}
