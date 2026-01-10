package dataManipulators;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

class DataSaver {

	public BufferedWriter generateWriter(String path, boolean appendMode) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path, appendMode));
			return writer;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public RandomAccessFile generateOverWriter(String path) {
		try {
			
			RandomAccessFile overWriter = new RandomAccessFile(path, "rw");
			return overWriter;
		
		} catch (FileNotFoundException e) {
			System.err.println("Data file " + path + " not found!");
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	public void appendData(String dataLine, String filePath) {
		BufferedWriter writer = generateWriter(filePath, true);
		
		try {
			
			writer.write(dataLine);
			writer.close();
		
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void overriteData(String data, String filePath, int atPos) {
		RandomAccessFile overWriter = generateOverWriter(filePath);
		
		try {
			
			overWriter.seek(atPos);
			overWriter.writeBytes(data);
			
			overWriter.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
