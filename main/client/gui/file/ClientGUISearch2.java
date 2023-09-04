/*package main.client.gui.file;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ClientGUISearch2 extends JFrame implements ActionListener{
    private static String SERVER_ADDRESS = "localhost";
    private final static int SERVER_PORT = 4343;

    private JFrame frame;
    private JLabel label;
    private JTextField textField;
    private JTextArea textArea;
    private JButton getBtn, searchBtn, exitBtn;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Clientfile client;  
    public ClientGUISearch2(String server) {
        SERVER_ADDRESS = server;
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
        client = new Clientfile(server, textArea);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.sendRequest("0",null);
                System.out.println("Frame closed");
                try{socket.close();}
                catch(IOException i){}
            }
        });

        // Create the socket connection
      /*  try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        } */
 /*   }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == getBtn){
            String fileName = textField.getText();
            client.sendRequest("1",fileName);
        }
        else if(e.getSource() == searchBtn){
            String searchString = textField.getText();
            client.sendRequest("2", searchString);
        }
        else if(e.getSource() == exitBtn){
            client.sendRequest("0",null);
            try{socket.close();}
            catch(IOException i){}
            frame.dispose();
        }
    }


    public static void main(String[] args) {
 //       SwingUtilities.invokeLater(new Runnable() {
  //          public void run() {
                new ClientGUISearch2(args[0]);
  //          }
  //      });
    }
}
*/