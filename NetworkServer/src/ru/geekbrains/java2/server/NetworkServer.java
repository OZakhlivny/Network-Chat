package ru.geekbrains.java2.server;

import ru.geekbrains.java2.server.auth.AuthService;
import ru.geekbrains.java2.server.auth.BaseAuthService;
import ru.geekbrains.java2.server.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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

    public synchronized void broadcastMessage(String message, ClientHandler owner) throws IOException {
        for (ClientHandler client : clients) {
            if(client != owner) {
                client.sendMessage(message);
            }
        }
    }

    public synchronized void sendPrivateMessage(String message, String fromUser) throws IOException {
        String messageParts[] =  message.split("\\s", 3);
        for (ClientHandler client : clients) {
            if(client.getNickname().equals(messageParts[1])){
                client.sendMessage("Private from " + fromUser + ": " + messageParts[2]);
                break;
            }
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
