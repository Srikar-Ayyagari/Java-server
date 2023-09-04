package main.client.gui.file;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import main.client.util.FileSender;
import main.client.util.*;

public class ClientGUIFile extends JFrame implements ActionListener {

    private JTextField tfServer, tfPort, tfFile;
    private JButton btnBrowse, btnSend;
    private JTextArea taMessages;
    private JFileChooser fileChooser;
    private File selectedFile;
    private static String server = "localhost";
    private static int port = 4444;
    private static String response = "";

    public ClientGUIFile() {
        super("ClientGUIFile");
        setLayout(new BorderLayout());

        // Panel to hold input fields and buttons
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setPreferredSize(new Dimension(500, 60));
        inputPanel.add(new JLabel("Server:"));
        tfServer = new JTextField(10);
        tfServer.setText(server);
        inputPanel.add(tfServer);
        inputPanel.add(new JLabel("Port:"));
        tfPort = new JTextField(4);
        tfPort.setText(Integer.toString(port));
        inputPanel.add(tfPort);
        inputPanel.add(new JLabel("File:"));
        tfFile = new JTextField(25);
        inputPanel.add(tfFile);
        btnBrowse = new JButton("Browse");
        btnBrowse.addActionListener(this);
        inputPanel.add(btnBrowse);
        btnSend = new JButton("Send");
        btnSend.addActionListener(this);
        inputPanel.add(btnSend);

        // Panel to hold message display
        taMessages = new JTextArea(50, 50);
        taMessages.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taMessages);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // File chooser dialog
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(".")); // set default directory
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Combo box for info option
        JPanel infoPanel = new JPanel(new FlowLayout());
        inputPanel.add(infoPanel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object o = "new Object()";
                FileSender.sendFile(o, server, port);
                if (response.equalsIgnoreCase("Yes")) {
                    String[] arg = new String[]{server, Integer.toString(port)};
                    //ClientGUIFileReceive.main(arg);
                }
                System.out.println("Frame closed");
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBrowse) {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                tfFile.setText(selectedFile.getAbsolutePath());
            }
        } else if (e.getSource() == btnSend) {
            server = tfServer.getText().trim();
            port = Integer.parseInt(tfPort.getText().trim());
            if (selectedFile != null) {
                // Send the file and get the response
                String text = FileSender.sendFile(selectedFile, server, port);
                JOptionPane.showMessageDialog(this, "File sent successfully to server.", "File Sent", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                taMessages.append("Please select a file to send.\n");
            }
        }
    }

    public static void main(String[] args) {
        if (args.length >= 2) {
        server = args[0];
        port = Integer.parseInt(args[1]);
        } else {
        server = "localhost";
        port = 4444;
        }
        ClientGUIFile gui = new ClientGUIFile();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(600, 400);
        gui.setVisible(true);
    }
}