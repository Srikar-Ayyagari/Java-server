package main.server.util.csv;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchCsv {
    public String getTextByName(String name){
        CSVReader reader = null;
        try{
            reader = new CSVReader(new FileReader("/home/dmacs/text.csv"));
            String[] line;
            while((line = reader.readNext()) != null){
                if(line[0].equalsIgnoreCase(name)){
                    return line[2];
                }
            }
            return "File doesn't exist";
        }catch(IOException e){
            System.err.println("Exception caught: " + e.getMessage());
            return "-EOF-";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Exception caught: " + e.getMessage());
                }
            }
        }
    }
    public String getFileByString(String string){
        CSVReader reader = null;
        try{
            reader = new CSVReader(new FileReader("/home/dmacs/text.csv"));
            String text = "";
            String[] line;
            while((line = reader.readNext()) != null){
                try{text = line[2];
                }catch(ArrayIndexOutOfBoundsException e){}
                if(text.indexOf(string) != -1){
                    return line[0];
                }
            }
            return null;
        } catch(IOException e){
            System.err.println("Exception caught: " + e.getMessage());
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Exception caught: " + e.getMessage());
                }
            }
        }
    }
}
