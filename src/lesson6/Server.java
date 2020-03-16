package lesson6;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server {
    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream in;
    private DataOutputStream out;
    public static final String END_COMMAND = "/end";

    public Server(){
        openSockets();
        sendMessages();
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }

    public void openSockets(){
        try {
            serverSocket = new ServerSocket(8189);
            System.out.println("Server is running, waiting for connection...");
            clientSocket = serverSocket.accept();
            System.out.println("Client is connected.");

            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

        }catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                while (true) {
                    String message = in.readUTF();
                    System.out.println("From client: " + message);
                    if (message.equalsIgnoreCase(END_COMMAND)){
                        closeSockets();
                        System.exit(0);
                        break;
                    }

                }
            } catch (Exception e) {
                System.out.println("Connection has been closed!");
            }
        }).start();
    }

    public void sendMessages(){
        new Thread(() -> {
            String inputMessage;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input messages: ");
            while (true) {
                inputMessage = scanner.nextLine();
                if (!inputMessage.isBlank()) {
                    try {
                        out.writeUTF(inputMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error sending message.");
                    }
                }
                if(inputMessage.equalsIgnoreCase(END_COMMAND)){
                    closeSockets();
                    System.exit(0);
                    break;
                }
            }
            scanner.close();
        }).start();
    }

    public void closeSockets(){
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (clientSocket != null) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
