package pck.rcserver.be.server;

import pck.rcserver.ServerGUI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {
    public static final int NUM_OF_THREAD = 32;
    public static int SERVER_PORT= 7777;
    public static LinkedHashMap<Integer, Client> clients = new LinkedHashMap<>();
    public static Thread thread;

    public static void main(String[] args) {
        create();
        start();
    }
    private static ServerSocket serverSocket = null;



    public static ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static boolean create() {
        if(serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println("Binding to port " + SERVER_PORT + ", please wait...");
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server started: " + serverSocket);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void start() {
        new Thread(() -> {
            try {
                System.out.println("Start and wait for clients ...");
                ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_THREAD);

                while(!serverSocket.isClosed()) {
                    try {
                        System.out.println("Listen to client's connection request");

                        Socket socket = serverSocket.accept();
                        Client client = new Client(socket);
                        clients.put(socket.getPort(), client);

                        ServerGUI.addNewClient(client);

                        // Handle request from client
                        Worker handler = new Worker(client);
                        executor.execute(handler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                serverSocket.close();
                executor.shutdown();
                executor.awaitTermination(10, TimeUnit.SECONDS); // wait for 10s in this case
                executor.shutdownNow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void stop() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                for (int key : clients.keySet()) {
                    clients.get(key).getSocket().close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
