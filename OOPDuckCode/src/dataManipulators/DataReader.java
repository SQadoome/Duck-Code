package dataManipulators;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DataReader {
	
	public BufferedReader generateReader(String path) {
		try {
			FileReader fr = new FileReader(path);
			return new BufferedReader(fr);
			
		} catch (FileNotFoundException e) {
			System.err.println("Data file not found!");
			e.printStackTrace();
			return null;
		}
	}
	
	// without splitting
	public String readData(String target, String filePath) {
		BufferedReader reader = generateReader(filePath);
		if (reader == null) {
			return "";
		}
		
		String data = "";
		String line;	
		while (true) {
			try {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				
				if (line.equals(target) == true) {
					break;
				}
				
			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				break;
			}
		}
		
		return data;
	}
	
	// with splitting
	public String[] readData(String target, String filePath, String splitSymbol) {
		BufferedReader reader = generateReader(filePath);
		if (reader == null) {
			return null;
		}
		
		String line;	
		while (true) {
			try {
				line = reader.readLine();
				if (line == null) {
					reader.close();
					break;
				}
				String[] rawData = line.split(splitSymbol);
				
				if (rawData[0].equals(target) == true) {
					reader.close();
					return rawData;
				}
				
			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				break;
			}
		}
		
		return null;
	}
	
	public String[] getLines(String filePath) {
		ArrayList<String> lines = new ArrayList<>();
		BufferedReader reader = generateReader(filePath);
		String line;
		
		while (true) {
			try {
				line = reader.readLine();
				
				if (line == null) {
					break;
				} else {
					lines.add(line);
				}
				
			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
		String[] linesArr = new String[lines.size()];
		
		for (int i = 0; i < lines.size(); i++) {
			linesArr[i] = lines.get(i);
		}
		
		return linesArr;
	}
	
}
