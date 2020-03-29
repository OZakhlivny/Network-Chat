package ru.geekbrains.java2.server;

import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.server.auth.AuthService;
import ru.geekbrains.java2.server.auth.BaseAuthService;
import ru.geekbrains.java2.server.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetworkServer {
    private int port;
    private AuthService authService;
    private List<ClientHandler> clients;

    public NetworkServer(int port){
        this.port = port;
        try(ServerSocket serverSocket = new ServerSocket(this.port)){
            this.authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            clients = new CopyOnWriteArrayList<>();
            System.out.println("Server is running on port " + port);
            while(true){
                System.out.println("Waiting the client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client is connected.");
                new ClientHandler(this, clientSocket);
            }
        } catch (IOException e){
            System.out.println("Server error.");
            e.printStackTrace();
        } finally {
            if(authService != null) authService.stop();
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public /*synchronized*/ void broadcastMessage(Command message, ClientHandler owner) throws IOException {
        for (ClientHandler client : clients) {
            if(client != owner) {
                client.sendMessage(message);
            }
        }
    }

    public synchronized void sendPrivateMessage(String receiver, Command commandMessage) throws IOException {
        for (ClientHandler client : clients) {
            if(client.getNickname().equals(receiver)){
                client.sendMessage(commandMessage);
                break;
            }
        }
    }
    private List<String> getAllUserNames() {
        List<String> userNames = new LinkedList<>();
        for (ClientHandler clientHandler : clients) {
            userNames.add(clientHandler.getNickname());
        }
        return userNames;
    }

    public /*synchronized*/ void subscribe(ClientHandler clientHandler) throws IOException {
        clients.add(clientHandler);
        List<String> users = getAllUserNames();
        broadcastMessage(Command.updateUsersListCommand(users), null);
    }

    public /*synchronized*/ void unsubscribe(ClientHandler clientHandler) throws IOException {
        clients.remove(clientHandler);
        List<String> users = getAllUserNames();
        broadcastMessage(Command.updateUsersListCommand(users), null);
    }

    public boolean isNicknameBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(username)) return true;
        }
        return false;
    }
}
