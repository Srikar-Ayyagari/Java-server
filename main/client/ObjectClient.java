package main.client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import main.client.gui.file.*;
import main.client.gui.dict.*;
public class ObjectClient {
    private static String SERVER_HOSTNAME = "localhost";
    private static int TCP_PORT = 3333;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        Socket clientSocket = new Socket();
    
        // Connect to TCP server socket
        try{
            try {
                System.out.println("Taking server to be at " + args[0]);
                SERVER_HOSTNAME = args[0];
                //clientSocket = new Socket(SERVER_HOSTNAME, TCP_PORT);
                clientSocket.connect(new InetSocketAddress(SERVER_HOSTNAME, TCP_PORT), 5000);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Taking server to be localhost");
                SERVER_HOSTNAME = "localhost";
    //            clientSocket = new Socket(SERVER_HOSTNAME, TCP_PORT);
                clientSocket.connect(new InetSocketAddress(SERVER_HOSTNAME, TCP_PORT), 5000);
            } 
        }catch (SocketTimeoutException | SocketException | UnknownHostException e) {
            System.out.println("Server is not at the specified host.");
            while (!exit) {
                clientSocket = new Socket();
                System.out.println("Would you like to enter the IP address (Y/N)");
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                    System.out.print("Enter IP: ");
                    String ip = sc.nextLine();
                    SERVER_HOSTNAME = ip;
                    try {
                        //clientSocket = new Socket(SERVER_HOSTNAME, TCP_PORT);
                        clientSocket.connect(new InetSocketAddress(SERVER_HOSTNAME, TCP_PORT), 5000);
                        exit = true;
                    } catch (SocketException | SocketTimeoutException ex) {
                        System.out.println("Could not connect to the server at " + SERVER_HOSTNAME + ex.getMessage());
                    }catch(UnknownHostException u){
                        System.out.println("You entered a wrong IP address");
                    }
                } else {
                    System.out.println("OK, bye!");
                    return;
                }
            }
        }
    
        exit = false;
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
        while (!exit) {
            
            String line = in.readLine();
            while (!line.equalsIgnoreCase("EOF")) {
                System.out.println(line);
                line = in.readLine();
            }
            // Send choice to server
            String choice = sc.nextLine();
            out.println(choice);
            String[] arg;
            String port = in.readLine();
            if(port == null){
                System.out.println("Sorry! Server has closed the connection");
                break;
            }
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e){

            }
            // Handle user choice
            switch (choice) {
                case "1":
                    System.out.println("Word to search for:");
                    arg = new String[]{SERVER_HOSTNAME,port};
                    ClientGUIDict.main(arg);
                    // Handle file download
                    break;
                case "2":
                    System.out.println("Read DataBase");
                    arg = new String[]{SERVER_HOSTNAME,port};
                    ClientGUISearch.main(arg);
                    // Handle file upload
                    break;
                case "3":
                    System.out.println("Uploading object...");
                    arg = new String[]{SERVER_HOSTNAME,port};
                    ClientGUIFile.main(arg);
                    // Create a new TCP connection to send the object
                    System.out.println("Waiting for Server to respond....");
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
           // System.out.println("End of Switch");

        }   
            // Close the TCP connection to the server
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("Connection closed");
    }
}

