package main.client.gui.dict;
import java.awt.*;
import java.awt.event.*;    
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.*;

public class ClientGUIDict extends JFrame implements ActionListener, KeyListener {
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private JTextArea textArea;
    private JPanel panel;
    private JTextArea history;
    private Client3 client;

    public ClientGUIDict(String server, String port) {
        setTitle("Dictionary Client");  
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container container = getContentPane();
        // Create components
        label = new JLabel("Enter word to lookup:");
        label.setBounds(0, 0, 10, 10);
        textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(200, 20));
        textField.addKeyListener(this);
        button = new JButton("Get Meaning");
        button.setPreferredSize(new Dimension(200, 20));
        button.addActionListener(this);
        textArea = new JTextArea(10, 50);
        textArea.setEditable(false);
        history = new JTextArea(10, 50);
        history.setEditable(false);
        JPanel panel2 = new JPanel();

        // Add components to layout
        // JPanel panel = new JPanel(new GridLayout(4, 1));
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        container.add(panel);
        panel.add(label);
        panel.add(textField);
        panel.add(button);
        panel.add(new JScrollPane(textArea));
        panel.add(new JScrollPane(history));
        // setContentPane(panel);
        setVisible(true);

        // Connect to server
        client = new Client3(server,port,textArea, history);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try{client.sendWord("Exit");}
                catch(IOException a){}
                //System.out.println("Frame closed");
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            // Get word from text field
            String word = textField.getText();

            try {
                client.writeMeaning(word);
            } catch (IOException ex) {
                textArea.append("Error receiving response from server: " + ex.getMessage() + "\n");
            }
            textField.setText(word);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String word = textField.getText();

            try {
                client.writeMeaning(word);
            } catch (IOException ex) {
                textArea.append("Error receiving response from server: " + ex.getMessage() + "\n");
            }
            textField.setText("");
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        new ClientGUIDict(args[0],args[1]);
    }
}


