package main.server.servers;
import java.io.*;
import java.net.*;

import main.server.util.csv.ReadCsv;

public class Server4 {
public static void main(String[] args) {
	try{
        int port;
        try{
            port = Integer.parseInt(args[0]);
        }catch(Exception e){
            port = 4242;
        }
        ServerSocket ss = new ServerSocket(port);
			while (true) {
            // Accept the network connection from client
            Socket s = ss.accept();
            System.out.println("-Connection established-");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                 PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true)) {
                out.println("Connected to a dictionary.... ");
                boolean done = false;
                ReadCsv r = new ReadCsv();
                out.println("Enter the word you want to search for or type 'exit' to close connection:");
                while (!done) {
                    // Read data from client(request)
                    String word = in.readLine(); // reading
                    if (word == null || "Leave".equalsIgnoreCase(word.trim())) {
                    	out.println("I find it quite offensive, I shall pester you no more " );
                        done = true;
                    } 
                    else if("exit".equalsIgnoreCase(word.trim())){
                    	out.println("BYE!");
                    	done = true;
                    }
                    else {
                        String meaning = r.getMeaning(word);
                        System.out.println("Client asked: " + word);
                        // Write data to client(response)
                        out.println(meaning);
                        System.out.println("Sent Meaning");
                    }
                    out.println("Another Word Please");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            s.close();
            System.out.println("Connection Closed");
            ss.close();
        }
       } catch (IOException e) {
        System.err.println("Error: " + e.getMessage());
		}
	}
}
