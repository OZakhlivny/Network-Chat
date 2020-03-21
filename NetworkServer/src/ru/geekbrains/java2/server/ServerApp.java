package ru.geekbrains.java2.server;

public class ServerApp {

    private static final int DEFAULT_PORT = 8189;

    public static void main(String[] args) {
        int port = getServerPort(args);
        new NetworkServer(port);
    }

    private static int getServerPort(String[] args){
        int port = DEFAULT_PORT;
        if(args.length == 1){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Incorrect port format, using port by default.");
                e.printStackTrace();
            }
        }
        return port;
    }
}
