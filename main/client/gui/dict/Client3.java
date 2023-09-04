package main.client.gui.dict;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.*;

class Client3 {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private JTextArea textArea;
    private JTextArea history;

    public Client3(String server,String port, JTextArea textArea, JTextArea history) {
        // Connect to server
        try {
            int p = Integer.parseInt(port);
            socket = new Socket(server, p);
            System.out.println("Server for client3 is at "+server);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            textArea.append("Connected to server.\n");
            textArea.append(in.readLine());
            textArea.append(in.readLine());
            history.append("Your history of Searches : \n");
        } catch (IOException e) {
            textArea.append("Error connecting to server: " + e.getMessage() + "\n");
        }

        this.textArea = textArea;
        this.history = history;
    }
    public void writeMeaning(String word) throws IOException {
        out.println(word);
        history.append(word + "\n");
        String response = in.readLine();
        textArea.setText("");
        textArea.append(response + "\n\n\n");
        textArea.append(in.readLine() + "\n");
    }
    public void sendWord(String word) throws IOException{
        out.println(word);
    }
}
