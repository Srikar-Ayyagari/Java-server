package main.client.gui.file;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Clientfile {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private JTextArea textArea;
    public Clientfile(String server, JTextArea textArea) {
        // Connect to server
        try {
            socket = new Socket(server, 4242);
            System.out.println("Server for clientfile is at "+server);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            textArea.append("Connected to server.\n");
            textArea.append(in.readLine());
            textArea.append(in.readLine());
        } catch (IOException e) {
            textArea.append("Error connecting to server: " + e.getMessage() + "\n");
        }
        this.textArea = textArea;
    }
    public void sendRequest(String type,String request) {
        try {
            if(request == null){
                return;
            }
            String response;
            // Send the request to the server
            out.println(type);
            response = in.readLine();
            System.out.println(response);
            switch(type){
                case "1":
                    if(response.equalsIgnoreCase("Enter file name")){
                        out.println(request);
                        textArea.setText("");
                        response = in.readLine();
                        int len = Integer.parseInt(response);
                        for(int i=0;i<len;i++){
                            response = in.readLine();
                            textArea.append(response+"\n");
                        }
                    }
                    break;
                case "2":
                    if(response.equalsIgnoreCase("Enter search string")){
                        out.println(request + "\n");
                        textArea.setText("");
                        response = in.readLine();
                        if(response == null){
                            break;
                        }
                        textArea.append(response);
                    }
                    break;
                case "0":
                default:
                    break;
           }
            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
