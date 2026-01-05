package dataManipulators;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import prettyText.AnsiValues;

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
		File[] dataFiles = {new File("Data/registered_users"), new File("Data/event_data")};
		
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
				if (file.getName().equals(dataFiles[1].getName()) == true) {
					generateDefaultEvents();
				}
				
			} else if (file.exists() == false) {
				file.createNewFile();
				if (file.getName().equals(dataFiles[1].getName()) == true) {
					generateDefaultEvents();
				}
			}
			
		}
		
	}
	
	// ---------
	
	private void generateDefaultEvents() {
		addEvent("Duck-Code major", new String[] {"the offical tournament"}, 16);
		addEvent("PPL", new String[] {"Programming pro league"}, 16);
		addEvent("Standoff 2 major", new String[] {"When will VP win?"}, 16);
		
		for (int i = 0; i < 8; i++) {
			addEvent("Test" + i, new String[] {"This is a test"}, 16);
		}
		
	}
	
	public UserData getUserRegisterationData(String username) {
		UserData reigsterationData = new UserData();
		Data data = reader.readData(username, "Data/registered_users", ":");
		if (data != null) {
			reigsterationData.setUser(data.getFormated()[0], data.getFormated()[1], data.getFormated()[2]);
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
		saver.appendData(username + ":" + passwordHash + ":" + salt + "\n", "Data/registered_users");
		
	}
	
	public EventData getEventData(String eventName) {
		EventData data = new EventData();
		Data retrievedData = reader.readData(eventName, "Data/event_data", ":");
		data.setName(retrievedData.getFormated()[0]);
		data.setDescription(retrievedData.getFormated()[1]);
		
		ArrayList<String> participants = new ArrayList<>();
		String[] raw = retrievedData.getFormated()[2].split(",");
		
		for (int i = 0; i < 16; i++) {
			String next = raw[i].strip();
			if (next.isBlank() == false) {
				participants.add(next);
			}
		}
		
		String[] participantsArr = new String[participants.size()];
		
		for (int i = 0; i < participants.size(); i++) {
			participantsArr[i] = participants.get(i);
		}
		
		
		data.setParticipants(participantsArr);
		return data;
	}
	
	public EventData[] getEvents() {
		String[] rawData = reader.getLines("Data/event_data");
		
		EventData[] data = new EventData[rawData.length];
		for (int i = 0; i < rawData.length; i++) {
			String[] temp = rawData[i].split(":");
			EventData next = new EventData();
			next.setName(temp[0]);
			next.setDescription(temp[1]);
			// for the sake of not killing the memory, if you want to get participants use getEventData instead.
			
			data[i] = next;
		}
		
		return data;
	}
	
	public boolean addParticipant(EventData event, String username) {
		if (isParticipating(event, username) == true) {
			System.out.println(AnsiValues.RED + AnsiValues.BOLD + "Already registered." + AnsiValues.DEFAULT);
			return false;
		}
		
		Data data = reader.readData(event.getName(), "Data/event_data", ":");
		int targetPos = data.getAtByte();
		
		targetPos += event.getName().length() + 1 + event.getDescription().length() + 1;
		int participantCount = getEventData(event.getName()).getParticipants().length;
		
		
		if (participantCount >= 16) {
			System.err.println(AnsiValues.RED + AnsiValues.BOLD + "Full of participants." + AnsiValues.DEFAULT);
			return false;
		}
		
		String[] slots = data.getFormated()[2].split(",");
		
		for (int i = 0; i < slots.length; i++) {
			if (slots[i].isBlank() == true) {
				break;
			} else {
				targetPos += 12 + 1;
			}
			
		}
		
		saver.overriteData(" ".repeat(12), "Data/event_data", targetPos);
		saver.overriteData(username, "Data/event_data", targetPos);
		System.out.println(AnsiValues.GREEN + "Successfully registered for event." + AnsiValues.DEFAULT);
		return true;
	}
	
	public void addEvent(String title, String[] description, int slots) {
		
		String line = title + ":";
		
		for (int i = 0; i < description.length - 1; i++) {
			line += description[i] + ";";
		}
		line += description[description.length - 1] + ":";
		
		for (int i = 0; i < slots; i++) {
			line += " ".repeat(12) + ",";
		}
		line += "\n";
		saver.appendData(line, "Data/event_data");
		
	}
	
	public void removeParticiapnt(EventData event, String username) {
		if (isParticipating(event, username) == false) {
			System.out.println(AnsiValues.RED + "You are already not participating." + AnsiValues.DEFAULT);
			return;
		}
		
		Data data = reader.readData(event.getName(), "Data/event_data", ":");
		int targetPos = data.getAtByte();
		
		targetPos += event.getName().length() + 1 + event.getDescription().length() + 1;
		
		String[] slots = data.getFormated()[2].split(",");
		
		for (int i = 0; i < slots.length; i++) {
			if (slots[i].strip().equals(username) == true) {
				break;
			} else {
				targetPos += 12 + 1;
			}
			
		}
		
		saver.overriteData(" ".repeat(12), "Data/event_data", targetPos);
		System.out.println(AnsiValues.GREEN + "Sucessfuly unregistered for the event." + AnsiValues.DEFAULT);
	}
	
	public boolean isParticipating(EventData event, String username) {
		
		String[] participants = getEventData(event.getName()).getParticipants();
		
		for (int i = 0; i < participants.length; i++) {
			if (username.equals(participants[i].strip()) == true) {
				return true;
			}
		}
		return false;
	}
	
}

