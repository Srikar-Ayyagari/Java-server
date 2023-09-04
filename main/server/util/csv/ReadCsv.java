package main.server.util.csv;
import java.io.FileReader;  
import com.opencsv.CSVReader;  
import java.util.Scanner;
public class ReadCsv
{    
		public String getMeaning(String word)  
		{  
				CSVReader reader = null;  
				try  
				{  
						reader = new CSVReader(new FileReader("/home/dmacs/Untitled/OPTED-Dictionary.csv"));
						int c = 0;
						String [] nextLine;  
						while ((nextLine = reader.readNext()) != null)  
						{  
								if(nextLine[0].equalsIgnoreCase(word)){
									if(c==0){
										c++;
										return nextLine[3];
									}
								}
						} 
						if(c==0){
							return "Ask a valid word";
						}
				}  
				catch (Exception e)   
				{  
						e.printStackTrace();  
				} 
				return null;	 
		}  
}  
