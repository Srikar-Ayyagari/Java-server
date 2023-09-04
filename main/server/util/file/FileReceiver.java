package main.server.util.file;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import main.server.util.csv.WriteCsv;
import main.util.file.Files;
import main.server.util.tesseract.Tess;

public class FileReceiver {
    private static int UPLOAD_PORT = 4444;
    private static String filePath = "/home/dmacs/JavaProject/images";
    public static void receiveFile(String[] arg) throws IOException, ClassNotFoundException {
        try {
            UPLOAD_PORT = Integer.parseInt(arg[0]);
        } catch (ArrayIndexOutOfBoundsException a) {
            UPLOAD_PORT = 4444;
        }
        ServerSocket uploadServerSocket = new ServerSocket(UPLOAD_PORT);
        Socket uploadSocket = uploadServerSocket.accept();
        ObjectInputStream objectIn = new ObjectInputStream(uploadSocket.getInputStream());
        Object object = objectIn.readObject();
        if (object instanceof String) {
            // Discard the object and print an error message
            System.err.println("Error: Received unexpected String object.");
            objectIn.close();
            uploadSocket.close();
            uploadServerSocket.close();
            return;
        } else if (object instanceof Files) {
            // Save the Files object to disk
            Files file = (Files) object;
            if (file.init == 0) {
                objectIn.close();
                uploadSocket.close();
                uploadServerSocket.close();
                return;
            }
            System.out.println("File received successfully: " + file.getPath() + file.getName());
            file.writeToFile(filePath,file.getName());
            //System.out.println("File saved to " + filePath + "/" + file.getName());
            String text = writeToCsv(filePath, file.getName());
            //System.out.println(text);
            // Close the TCP connection
            objectIn.close();
            uploadSocket.close();
            uploadServerSocket.close();
        } else {
            // Discard the object and print an error message
            System.err.println("Error: Received unexpected object of type " + object.getClass().getName());
            objectIn.close();
            uploadSocket.close();
            uploadServerSocket.close();
            return;
        }
    }

    public static String writeToCsv(String path, String name) {
        String fullPath = path + "/" + name;
        System.out.println(fullPath);
        String text = Tess.convertToText(fullPath);
        //System.out.println(text);
        File file = new File(fullPath);
        try {
            WriteCsv.writeToCsv(name, path, text, file);
            //WriteToDb.writeToDb(name, path, text, file);
        } catch (IOException i) {
        }
        return text;
    }
}
