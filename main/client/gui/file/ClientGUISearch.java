package main.client.gui.file;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ClientGUISearch extends JFrame implements ActionListener{
    private static String SERVER_ADDRESS = "localhost";
    private static int SERVER_PORT = 4343;

    private JFrame frame;
    private JLabel label;
    private JTextField textField;
    private JTextArea textArea;
    private JButton getBtn, searchBtn, exitBtn;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientGUISearch(String server, String port) {
        SERVER_ADDRESS = server;
        SERVER_PORT = Integer.parseInt(port);
        frame = new JFrame("Search File");
        label = new JLabel("Enter search string or file name:");
        textField = new JTextField(20);
        textArea = new JTextArea(10, 30);
        getBtn = new JButton("Get Text");
        searchBtn = new JButton("Search File");
        exitBtn = new JButton("Exit");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(label);
        panel.add(textField);
        panel.add(getBtn);
        panel.add(searchBtn);
        panel.add(exitBtn);

        textArea.setEditable(false);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);

        // Add action listeners for the buttons
        getBtn.addActionListener(this);
        searchBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sendRequest("0",null);
                System.out.println("Frame closed");
                try{socket.close();}
                catch(IOException i){}
            }
        });

        // Create the socket connection
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == getBtn){
            String fileName = textField.getText();
            sendRequest("1",fileName);
        }
        else if(e.getSource() == searchBtn){
            String searchString = textField.getText();
            sendRequest("2", searchString);
        }
        else if(e.getSource() == exitBtn){
            sendRequest("0",null);
            try{socket.close();}
            catch(IOException i){}
            frame.dispose();
        }
    }

    private void sendRequest(String type,String request) {
        try {
            String response;
            if(request == null){
                out.println(type);
                //response = in.readLine();
                return;
            }
            // Send the request to the server
            out.println(type);
            response = in.readLine();
            //System.out.println(response);
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
                        /*if(response == null){
                            break;
                        }*/
                        textArea.append(response);
                    }
                    break;
                case "0":
                    
                    //dispose();
                default:
                    break;
           }
            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
                new ClientGUISearch(args[0],args[1]);
    }
}
