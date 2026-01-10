package dataManipulators;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class DataReader {
	
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
	public Data readData(String target, String filePath) {
		BufferedReader reader = generateReader(filePath);
		if (reader == null) {
			return null;
		}
		
		Data data = new Data();
		int byteCount = 0;
		String line;	
		while (true) {
			try {
				line = reader.readLine();
				if (line == null) {
					break;
				} else if (line.isBlank()) {
					continue;
				}
				
				if (line.equals(target) == true) {
					data.setRaw(line);
					data.setAtByte(byteCount);
					return data;
				}
				byteCount += line.length();
				
			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				break;
			}
		}
		
		return null;
	}
	
	// with splitting
	public Data readData(String target, String filePath, String splitSymbol) {
		BufferedReader reader = generateReader(filePath);
		if (reader == null) {
			return null;
		}
		Data data = new Data();
		int byteCount = 0;
		String line;	
		while (true) {
			try {
				line = reader.readLine();
				if (line == null) {
					reader.close();
					break;
				} else if (line.isBlank()) {
					continue;
				}
				String[] splited = line.split(splitSymbol);
				
				if (splited[0].equals(target) == true) {
					reader.close();
					data.setRaw(line);
					data.setFormated(splited);
					data.setAtByte(byteCount);
					return data;
				}
				byteCount += line.length() + 1;
				
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
				}  else if (line.isBlank()) {
					continue;
				}else {
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
