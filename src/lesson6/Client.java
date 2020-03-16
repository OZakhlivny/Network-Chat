package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    public static final String END_COMMAND = "/end";

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;


    public Client(){
        try {
            openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMessages();
    }

    public void openConnection() throws IOException {
        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        new Thread(() -> {
            try {
                while (true) {
                    String strFromServer = in.readUTF();
                    System.out.println("From sever: " + strFromServer);
                    if (strFromServer.equalsIgnoreCase(END_COMMAND)) {
                        closeConnection();
                        System.exit(0);
                        break;
                    }

                }
            } catch (Exception e) {
                System.out.println("Connection has been closed!");
            }
        }).start();
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessages() {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input messages: ");
            while (true) {
                String inputMessage = scanner.nextLine();
                if (!inputMessage.isBlank()) {
                    try {
                        out.writeUTF(inputMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error sending message.");
                    }
                }
                if(inputMessage.equalsIgnoreCase(END_COMMAND)){
                    closeConnection();
                    System.exit(0);
                    break;
                }
            }
            scanner.close();
        }).start();
    }

    public static void main(String[] args) {
        new Client();
    }
}
