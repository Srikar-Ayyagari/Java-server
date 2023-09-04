package main.client.util;

import java.io.*;
import java.net.*;
import main.util.file.Files;
public class FileSender {

    public static void sendFile(Object files, String server, int port) {
        try {
            // Connect to server socket
            Socket socket = new Socket(server, port);

            // Send file data to server
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(files);

            // Close the output stream and socket
            oos.close();
            socket.close();

            System.out.println("File sent successfully to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendFile(File file, String server, int port) {
        try {
            // create a Files object with the file name and path
            Files f = new Files(file.getParent(), file.getName());

            // connect to the server
            Socket socket = new Socket(server, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            // send the Files object to the server
            oos.writeObject(f);

            // Close the output stream and socket
            oos.close();
            socket.close();

            System.out.println("File sent successfully to server.");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
