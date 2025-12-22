package dataManipulators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class DataManager {
	
	private static DataReader reader = new DataReader();
	private static DataSaver saver = new DataSaver();
	
	public DataManager() {
		try {
			validateData();
		} catch (IOException e) {
			System.err.println("Error validating data.");
			e.printStackTrace();
		}
	}
	
	private void validateData() throws IOException {
		File registerationFolder = new File("Data");
		File[] dataFiles = {new File("Data/registered_users"), new File("Data/user_data")};
		
		if (registerationFolder.exists() == true) {
			// if the "folder" is a "file" then delete it and create a new folder
			if (registerationFolder.isDirectory() == false) {
				registerationFolder.delete();
				registerationFolder.mkdir();
			}
		} else {
			registerationFolder.mkdir();
		}
		
		for (File file : dataFiles) {
			
			if ((file.exists() == true && file.isFile() == false)) {
				file.delete();
				file.createNewFile();
			} else if (file.exists() == false) {
				file.createNewFile();
			}
			
		}
				
	}
	
	// ---------
	
	public UserData getUserRegisterationData(String username) {
		UserData reigsterationData = new UserData();
		String[] data = reader.readData(username, "Data/registered_users", ":");
		if (data != null) {
			reigsterationData.setUser(data[0], data[1], data[2]);
		}
		return reigsterationData;
	}
	
	public boolean doesUserExist(String username) {
		UserData data = getUserRegisterationData(username);
		
		if (data.isEmpty() == true) {
			return false;
		} else {
			return true;
		}
		
	}
	
	public void registerUser(String username, String passwordHash, String salt) {
		saver.appendData(username + ":" + passwordHash + ":" + salt, "Data/registered_users");
		
	}
	
	public EventData getEventData(String eventName) {
		EventData data = new EventData();
		
		String[] rawData = reader.readData(eventName, "Data/event_data", ":");
		data.setName(rawData[0]);
		data.setDescription(rawData[1]);
		
		return data;
	}
	
	public EventData[] getEvents() {
		String[] rawData = reader.getLines("Data/event_data");
		//System.out.println(Arrays.toString(rawData));
		EventData[] data = new EventData[rawData.length];
		for (int i = 0; i < rawData.length; i++) {
			String[] temp = rawData[i].split(":");
			EventData next = new EventData();
			next.setName(temp[0]);
			next.setDescription(temp[1]);
			next.setParticipants(temp[2].split(","));
			data[i] = next;
		}
		
		//System.out.println(Arrays.toString(data));
		return data;
	}
	
}

