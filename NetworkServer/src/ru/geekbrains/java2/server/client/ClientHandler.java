package ru.geekbrains.java2.server.client;

import ru.geekbrains.java2.server.NetworkServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private NetworkServer networkServer;
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;

    private String nickname;

    public ClientHandler(NetworkServer networkServer, Socket socket){
        try {
            this.networkServer = networkServer;
            this.clientSocket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.nickname = "";
            new Thread(() -> {
                try {
                    authentication();
                    readMessages();
                } catch (IOException e) {
                    System.out.println("Connection with client " + nickname + " was closed!");
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        networkServer.unsubscribe(this);
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessages() throws IOException {
        while (true) {
            String message = in.readUTF();
            System.out.printf("From %s: %s%n", nickname, message);
            if ("/end".equals(message)) {
                return;
            }
            if(message.startsWith("/private ")) networkServer.sendPrivateMessage(message, nickname);
            else networkServer.broadcastMessage(nickname + ": " + message, this);
        }
    }

    private void authentication() throws IOException {
        while (true) {
            String message = in.readUTF();
            if (message.startsWith("/auth")) {
                String[] messageParts = message.split("\\s");
                String username = networkServer.getAuthService().getUsernameByLoginAndPassword(messageParts[1], messageParts[2]);
                if (username == null) {
                    System.out.println("Incorrect login or password!");
                    sendMessage("Incorrect login or password !");
                } else {
                    nickname = username;
                    networkServer.broadcastMessage(nickname + " join to chat!", this);
                    sendMessage("/auth " + nickname);
                    networkServer.subscribe(this);
                    break;
                }
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
    }

    public String getNickname(){
        return nickname;
    }
}
