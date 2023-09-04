package main.server.servers;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import main.server.util.csv.SearchCsv;

public class Server3 {
    public static void main(String[] args){
        ServerSocket ss = null;
        try{
            int port;
            try{
                port = Integer.parseInt(args[0]);
            }catch(Exception e){
                port = 4343;
            }
            ss = new ServerSocket(port);
            while (true) {
                // Accept the network connection from client
                Socket s = ss.accept();
                System.out.println("-Connection established-");
                try{
                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
                    boolean exit = false;
                    while(!exit){
                        String choice = in.readLine();
                        //System.out.println(choice);
                        if(choice == null){
                            break;
                        }
                        //String reply;
                        switch(choice){
                            case "1":
                                out.println("Enter file name");
                                String fileName = in.readLine();
                                SearchCsv searchCsv = new SearchCsv();
                                String fileText = searchCsv.getTextByName(fileName);
                                String[] lines = fileText.split("\\r?\\n");
                                //for (String line : lines) {
                                //System.out.println(line);
                                //}
                                int len = lines.length;
                                out.println(len);
                                for(int i=0;i<len;i++){
                                    out.println(lines[i]);
                                }
                                break;
                            case "2":
                                out.println("Enter search string");
                                String searchString = in.readLine();
                                SearchCsv searchCsv2 = new SearchCsv();
                                String fileName2 = searchCsv2.getFileByString(searchString);
                                if(fileName2 == null){
                                    out.println("No such file found");
                                    break;
                                }
                                else{
                                    System.out.println(fileName2);
                                    String name = fileName2.split("\n")[0];
                                    out.println(name);
                                    break;
                                }
                                //break;
                            //default:
                              //  out.println("Exiting program...");
                                //exit = true;
                        }
                        if(choice.equals("0")){
                            out.println("bye!");
                            break;
                        }
                    }
                    //System.out.println("Outside while");
                    break;
                }catch(IOException e){}
            }
              // ss.close();
            } catch(IOException e){
            System.err.println("Exception caught: " + e.getMessage());
        }
        finally{
            if(ss!= null){
                try{
                    ss.close();
                }catch(IOException e){}
            }
        }
    }    
}
