package main.server.util.tesseract;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import main.util.file.Files;

public class Tess {
	public static String convertToText(String filePath)
	{
		Tesseract tesseract = new Tesseract();
		try {

			tesseract.setDatapath("./tesseract-main/tessdata");

			// the path of your tess data folder
			// inside the extracted file
			//System.out.println(filePath);
			String text
				= tesseract.doOCR(new File(filePath));

			// path of your image file
			return text;
		}
		catch (TesseractException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String convertFileToText(Files file){
		String name = file.getPath()+file.getName();
		return convertToText(name);
	}
}

