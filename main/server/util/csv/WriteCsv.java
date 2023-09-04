package main.server.util.csv;
import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class WriteCsv {
    private static String fileName = "/home/dmacs/text.csv";

    public static void writeToCsv(String name, String path, String text, File file) throws IOException {
        File tempFile = new File("/home/dmacs/temp.csv");

        FileWriter out = new FileWriter(tempFile,false); // overwrite mode
        System.out.println("Writing to file " + tempFile.getAbsolutePath());
        CSVWriter writer = new CSVWriter(out);
        CSVReader reader = new CSVReader(new FileReader(fileName));
        String[] nextLine;
        boolean found = false;
        text = text + "\n-EOF-\n";
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine[0].equals(name) && nextLine[1].equals(path)) {
                nextLine[2] = text; // Update the text
                found = true;
            }
            writer.writeNext(nextLine,false);
        } 
        reader.close();
        if (!found) {
            text = text + "\n-EOF-";
            String[] line = {name, path, text};
            writer.writeNext(line,false);
        }
        writer.close();

        Files.copy(tempFile.toPath(), new File(fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
        tempFile.delete();

        System.out.println("Written to CSV file");
    }
}
