package main.server;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Random;

import main.server.servers.Server3;
import main.server.servers.Server4;
import main.server.util.file.FileReceiver;


public class FinalServer {
    private static final int TCP_PORT = 3333;
    private static List<Integer> ports = new ArrayList<>();
    private static List<Socket> clientSockets = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        // Start TCP server socket
        ServerSocket tcpServerSocket = new ServerSocket(TCP_PORT);
        System.out.println("TCP server started on port " + TCP_PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                for (Socket clientSocket : clientSockets) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {

                    }
                }
            }
        }));
        
        while (true) {
            System.out.println("Waiting for connection");
            // Wait for a client to connect via TCP
            Socket clientSocket = tcpServerSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
            clientSockets.add(clientSocket);
            // Create a new thread to handle the client request
            Thread clientThread = new Thread(new ClientHandler(clientSocket));
            clientThread.start();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            int count = 0;
            boolean connected = true;
            try {
                // Send menu options to client
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out.println("Menu:\n1. Ask Meaning\n2. Read Database\n3. Upload object\n0. Exit\nEnter choice:\nEOF");
                while (connected && count < 3) {
                    // Receive choice from client
                    String choice = in.readLine();
                    int port;
                    Random r = new Random();
                    port = r.nextInt(5000) + 5000;
                    while(ports.contains(port)){
                        port = r.nextInt(5000) + 5000;
                    }
                    ports.add(port);
                    System.out.println("Giving the port " + port);
                    out.println(port);
                    String[] arg = new String[]{""+port};
                    // Handle client choice
                    switch (choice) {
                        case "1":
                            System.out.println("Client chose to ask meaning");
                            Server4.main(arg);
                            count = 0;
                            break;
                        case "2":
                            System.out.println("Client chose to read database");
                            count = 0;
                            Server3.main(arg);
                            break;
                        case "3":
                            System.out.println("Client chose to upload object");
                            count = 0;
                            // Handle object upload
                            // Create a new TCP connection to receive the object
                            //FileReceiver.receiveFile();
                            Thread backgroundThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        FileReceiver.receiveFile(arg);
                                    } catch (IOException | ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            backgroundThread.start();
                            break;
                        case "0":
                            out.println("Ok Bye!");
                            System.out.println("Closing connection with client");
                            connected = false;
                            break;
                        default:
                            System.out.println("Invalid choice");
                            count++;
                            if(count == 5){
                                out.println("That's 3 tries you got wrong in a row\nDon't waste my time anymore\nEOF");
                                connected = false;
                            }
                            break;
                    }
                    out.println("Enter another choice:\nEOF");
                }
                // Close the input stream for the current client request
                in.close();
                out.close();
            } catch (IOException e) {
                //e.printStackTrace();
            } finally {
                try {
                    // Close the TCP connection to the client
                    clientSocket.close();
                    System.out.println("Connection closed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
