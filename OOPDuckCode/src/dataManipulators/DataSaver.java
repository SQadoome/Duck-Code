package dataManipulators;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class DataSaver {

	public BufferedWriter generateWriter(String path, boolean appendMode) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path, appendMode));
			return writer;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void appendData(String dataLine, String filePath) {
		BufferedWriter writer = generateWriter(filePath, true);
		
		try {
			writer.write(dataLine);
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void overriteData(String data, String filePath, int atPos) {
		
	}
	
}
