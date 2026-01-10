package main;

import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

public class Main {
	
	private static Scanner reader;
	
	private final static String DEFAULT_CODE = "\u001B[0m"; // reset the code as if we dont then every next System. will produce the previous color
	private final static String YELLOW_CODE = "\033[0;33m";
	private final static String GREEN_CODE = "\u001B[32m";
	private final static String CYAN_CODE = "\u001b[36m";
	
	private static String[] hints = {
		"----Command List----\n0 -> REGISTER\n1 -> LOGIN\n2 -> EXIT\n", // 0
		"---Login---\nType \"back\" to cancel\nUsername: ", // 1
		"---Login---\nType \"back\" to cancel\nPassword: ", // 2
		"\nUsername not found\n", // 3
		"\nInvalid password!\n", // 4
		"---Register---\nType \"back\" to cancel\nEnter username: ", // 5
		"---Register---\nType \"back\" to cancel\nEnter password: ", // 6
		"Command list:\n0 -> register for event\n1 -> signout\n2 -> exit\n", // 7
		"Password must be atleast 3 characters long", // 8
		"%s*Succesfully registered!%s\n\n", // 9
		"%s*Successfuly logged in. \nWelcome %s to Duck-Code. Have fun coding!%s\n\n" // 10
	};
	
	private static String user;
	
	public static void validateData() throws IOException {
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
			} else if (file.exists() == false) {
				file.createNewFile();
				if (file.getName().equals(dataFiles[1].getName()) == true) {
					generateDefaultEvents();
				}
			}
			
		}
				
	}
	
	public static String generateSalt(String username) {
		String salt = "";
		
		for (int i = 0; i < 3; i++) {
			salt += username.charAt(i);
		}
		
		return salt;
	}
	
	
	private static int getNextChar(int num_1, int num_2) {
		int xored = num_1^num_2;
		// leave the 126 char for data saving ||| username (char126) passwordhash (char126) salt
		return (xored % (125-33)) + 33;
	}

	public static String hash(String text) {
		int[] hashChars = new int[20];

		for (int i = 0; i < hashChars.length; i++) {
			hashChars[i] = 35;
			// '!' = 35
		}

		for (int cycle = 0; cycle < 100; cycle++) {
			for (int i = 0; i < text.length(); i++) {
				int outerAscii = (int) text.charAt(i);

				// left to right
				for (int j = 1; j < hashChars.length; j++) {
					int previous = hashChars[j - 1];
					hashChars[j] = getNextChar(previous*j*hashChars[j]*outerAscii + i, hashChars[j]*i*text.length() + j);
				}

				// right to left
				for (int j = hashChars.length - 2; j > -1; j--) {
					int next = hashChars[j + 1];
					hashChars[j] = getNextChar(next*j*hashChars[j]*outerAscii + i, hashChars[j]*i*text.length() + j);
				}

			}
		}
		char[] result = new char[hashChars.length];

		for (int i = 0; i < result.length; i++) {
			result[i] = (char) hashChars[i];
		}

		return new String(result);
	}
	
	
	public static String[] radixSort(String words[], int target) {
		String[][] temp = new String[26][words.length];
		int[] count = new int[26];
		int count2 = 0;
		
		for (int i = 0; i < words.length; i++) {
			if (words[i] == null) {
				break;
			}
			
			if (target > words[i].length() - 1) {
				String bucket = words[0];
				words[0] = words[i];
				words[i] = bucket;
				count2++;
				continue;
			}
			
			int atIndex = (Character.toLowerCase(words[i].charAt(target)) - '0') - 49;
			temp[atIndex][count[atIndex]] = words[i];
			count[atIndex] += 1;
			
		}
		
		for (int i = 0; i < 26; i++) {
			if (count[i] > 1) {
				temp[i] = radixSort(temp[i], target+1);
			}
			
			for (int j = 0; j < words.length; j++) {
				if (temp[i][j] == null) {
					break;
				} else {
					words[count2] = temp[i][j];
					count2++;
				}
				
				
			}
			
		}
		return words;
	}
	
	public static void main(String[] args) {
		reader = new Scanner(System.in);
		try {
			validateData();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		accountAccessLoop();
	}
	
	
	public static String getUserData(String typedUsername) {
		String data = "";
		
		// linear search to see if the username is registered
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader("Data/registered_users")); // might throw exception (file not found for example)
			
			while (true) {
				String user = reader.readLine();
				if (user == null) {
					break;
				}
				
				if (user.split("~")[0].equals(typedUsername) == true) {
				data = user;
				}
			}
			
			reader.close();
			return data;
		} catch (IOException e) {
			System.out.println(e);
			return data;
		}
		
	}
	
	public static boolean loginLoop() {
		while (true) {
			
			System.out.printf(hints[1]);
			String username = reader.nextLine();
			
			if (username.equals("back") == true) {
				return false;
			}
			if (username.isBlank() == true) {
				System.err.println("\nPlease enter a username");
				continue;
			}
			
			String data = getUserData(username);
			
			if (data.isBlank() == true) {
				System.err.println("User not found!");
				continue;
			}
			
			String[] formattedData = data.split("~");
			
			while (true) {
				System.out.printf(hints[2]);
				String password = reader.nextLine();
				if (password.equals("back") == true) {
					return false;
				}
				
				if (hash(password + formattedData[2]).equals(formattedData[1]) == true) {
					user = formattedData[0];
					System.out.printf("\n\n" + hints[10], "\u001B[32m", user, "\u001B[0m");
					appLoop();
					return true;
				} else {
					System.err.printf(hints[4]);
					continue;
				}
			}
		}
	}
	
	public static void registerLoop() {
		while (true) {
			System.out.printf(hints[5]);
			
			String username = reader.nextLine();
			if (username.toLowerCase().equals("back") == true) {
				return;
			} else if (username.length() < 3) {
				System.err.println("Username must be atleast 3 characters");
				continue;
			} else if (username.length() > 12) {
				System.err.println("Username must not exceed 12 characterss"); // without this the event slots will break
				continue;
			}
			boolean flag = false;
			
			for (int i = 0; i < username.length(); i++) {
				if (Character.isLetter(username.charAt(i)) == false) {
					System.err.println("\nUsername must ONLY contain letters.");
					flag = true;
					break;
					// cant put the coninue here because it will affect the for loop and not the function
				}
			}
			
			if (flag == true) {
				continue;
			}
			
			if (getUserData(username) == "") { // checks to see if the user is already registered
				
				while (true) {
					System.out.printf(hints[6]);
					String password = reader.nextLine();
					if (password.toLowerCase().equals("back") == true) {
						return;
					}
					
					if (password.length() < 3) {
						System.err.printf(hints[8]);
						continue;
					} else if (password.length() > 12) {
						System.err.println("\nPassword length must not exceed 12 characters!\n");
						continue;
					} else {
						registerUser(username , password);
						return;
					}
				}
			} else {
				System.err.println("\nUser already exists!\n");
				continue;
			}
		}
	}
	
	public static void accountAccessLoop() {
		String command = "-1";
		
		while (true) {
			System.out.printf(hints[0]);
			command = reader.nextLine();
			
			switch (command) {
				case "0":
					registerLoop();
					break;
				case"1":
					boolean success = loginLoop();
					if (success == true) {
						return;
					}
					break;
				case "2":
					System.out.println("Why did you leave us :(");
					System.exit(0);
				default:
					System.err.println("\nUnknown command: " + command + "\n");
					continue;
			}
			
		}
		
	}
	
	public static boolean registerUser(String username, String password) {
		
		try {
			FileWriter writer = new FileWriter("Data/registered_users", true); // create new file writer object with append mode on
			// append mode writes to the end instead of overwriting
			
			/*
			 the saving format is: (user);(user)
			 and the user is broken down to: (username):(password hash):(salt)
			 */
			String salt = generateSalt(username);
			writer.write(username + "~" + hash(password + salt) + "~" + salt + "\n");
			writer.close(); // close the writer
			return true;
			
		} catch (IOException e) {
			System.err.println("ERROR: Failed to save user | " + e.getMessage());
			return false;
		}
		
	}
	
	public static void appLoop() {
		System.out.printf("-----%s%s%s-----\n", GREEN_CODE, user, DEFAULT_CODE);
		
		String command = "-1";
		
		
		while (true) {
			System.out.printf(hints[7]);
			System.out.println();
			System.out.printf("%s* all rights reserved to duck-code.co%s\n", CYAN_CODE, DEFAULT_CODE);
			
			command = reader.nextLine();
			
			switch (command) {
				case "0":
					eventLoop(1);
					return;
				case "1":
					System.err.println("\nSigned out.\n");
					accountAccessLoop();
					return;
				case "2":
					System.out.println("why did you leave us :(");
					System.exit(0);
				default:
					System.err.println("\nInvalid command: " + command + "\n");
					continue;
					
			}
		}
	}
	
	public static void participateIn(String username, String event) {
		if (isParticipatingIn(username, event) == true) {
			System.err.println("Already participating");
			return;
		}
		
		try {
			int atPos = 0;
			Scanner reader = new Scanner(new File("Data/event_data"));
			String[] participants = null;
			
			// find event
			while (reader.hasNextLine() == true) {
				String line = reader.nextLine();
				String[] formatted = line.split(":");
				
				if (formatted[0].equals(event) == true) {
					atPos += formatted[0].length() + formatted[1].length() + 2;
					participants = formatted[2].split(",");
					break;
				} else {
					atPos += line.length() + 1;
				}
			}
			reader.close();
			
			// search for empty slot
			boolean flag = true; // if there is no space
			for (int i = 0; i < 16; i++) {
				if (participants[i].isBlank() == true) {
					flag = false;
					break;
				} else {
					atPos += 13;
				}
			}
			
			if (flag == true) {
				System.err.println("Full of participants");
				return;
			}
			
			RandomAccessFile overWriter = new RandomAccessFile("Data/event_data", "rw");
			
			overWriter.seek(atPos);
			overWriter.writeBytes(username + " ".repeat(12 - username.length()));
			
			overWriter.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		
		
	}
	
	public static void withdrawFrom(String username, String event) {
		if (isParticipatingIn(username, event) == false) {
			System.err.println("You are not participating in" + event);
			return;
		}
		
		try {
			int atPos = 0;
			Scanner reader = new Scanner(new File("Data/event_data"));
			String[] participants = null;
			
			// find event
			while (reader.hasNextLine() == true) {
				String line = reader.nextLine();
				String[] formatted = line.split(":");
				
				if (formatted[0].equals(event) == true) {
					atPos += formatted[0].length() + formatted[1].length() + 2;
					participants = formatted[2].split(",");
					break;
				} else {
					atPos += line.length() + 1;
				}
			}
			reader.close();
			
			// search for empty slot
			for (int i = 0; i < 16; i++) {
				if (participants[i].strip().equals(username) == true) {
					break;
				} else {
					atPos += 13;
				}
			}
			
			RandomAccessFile overWriter = new RandomAccessFile("Data/event_data", "rw");
			
			overWriter.seek(atPos);
			overWriter.writeBytes(" ".repeat(12));
			
			overWriter.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		
	}
	
	public static boolean isParticipatingIn(String username, String event) {
		
		String[] participants = getEventParticipants(event);
		
		for (int i = 0; i < participants.length; i++) {
			if (participants[i].strip().equals(username) == true) {
				return true;
			}
		}
	
		return false;
	}
	
	public static String[] appendToList(String[] list, String text) {
		String[] newList = new String[list.length + 1];
		
		for (int i = 0; i < list.length; i++) {
			newList[i] = list[i];
		}
		newList[list.length] = text;
		return newList;
	}
	
	public static String[][] getEvents() {
		try {
			Scanner reader = new Scanner(new File("Data/event_data"));
			String[] temp = new String[0];
			
			do {
				String raw = reader.nextLine();
				temp = appendToList(temp, raw);
			} while (reader.hasNextLine() == true);
			
			String[][] result = new String[temp.length][2];
			
			for (int i = 0; i < temp.length; i++) {
				String[] formatted = temp[i].split(":");
				result[i][0] = formatted[0];
				result[i][1] = formatted[1];
			}
			
			reader.close();
			return result;
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
		
		return null;
	}
	
	public static void eventLoop(int page) {
		String[][] events = getEvents();
		
		int totalPages = (events.length+3-1)/3;
		String[][] nextEvents;
		
		int startAt = (page-1)*3;
		int size = 0;
		
		for (int i = startAt; i < startAt + 3; i++) {
			if (i > events.length - 1) {
				break;
			}
			
			size += 1;
		}
		
		nextEvents = new String[size][2];
		for (int i = 0; i < size; i++) {
			nextEvents[i] = events[i + startAt];
		}
		
		System.out.println("\n---Events---\n");
		
		for (int i = 0; i < nextEvents.length; i++) {			
			String[] event = nextEvents[i];
			
			System.out.printf("%s*%s%s\n", YELLOW_CODE, event[0], DEFAULT_CODE);
			
			System.out.println(event[1]);
		}
		
		System.out.printf("-----%sPage:%s/%s%s-----\n", GREEN_CODE, page, totalPages, DEFAULT_CODE);
		
		String command = reader.nextLine();
		
		if (command.toLowerCase().equals("back") == true) {
			appLoop();
			return;
		}
		
		for (int i = 0; i < nextEvents.length; i++) {
			if (command.toLowerCase().equals(nextEvents[i][0].toLowerCase())) {
				participantsPage(nextEvents[i][0], page);
				return;
			}
		}
		
		try {
			int nextPage = Integer.parseInt(command);
			
			if (!(nextPage < 0 || nextPage > totalPages)) {
				page = nextPage;
			} else {
				System.err.println("Invalid command: " + command);
			}
			
		} catch (NumberFormatException e) {
			System.err.println("Invalid command: " + command);
		}
		
		eventLoop(page);
	}
	
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
	
	public static String[] getEventParticipants(String event) {
		String[] participants = {"0"};
		try {
			Scanner reader = new Scanner(new File("Data/event_data"));
			
			while (true) {
				
				String line = reader.nextLine();
				String[] formatted = line.split(":");
				
				if (formatted[0].equals(event) == true) {
					participants = formatted[2].split(",");
					break;
				}
				
			}
			
			reader.close();	
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
		
		return participants;
	}
	
	public static void generateDefaultEvents() {
		addEvent("Duck-Code major", new String[] {"the offical tournament"}, 16);
		addEvent("PPL", new String[] {"Programming pro league"}, 16);
		addEvent("Standoff 2 major", new String[] {"When will VP win?"}, 16);
		
		for (int i = 0; i < 8; i++) {
			addEvent("Test" + i, new String[] {"This is a test"}, 16);
		}
	}
	
	public static void addEvent(String title, String[] description, int slots) {
		
		String line = title + ":";
		
		for (int i = 0; i < description.length - 1; i++) {
			line += description[i] + ";";
		}
		line += description[description.length - 1] + ":";
		
		for (int i = 0; i < slots; i++) {
			line += " ".repeat(12) + ",";
		}
		line += "\n";
		
		FileWriter writer;
		try {
			
			writer = new FileWriter("Data/event_data", true);
			writer.write(line);
			writer.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	public static void participantsPage(String event, int page) {
		
		while (true) {
			showParticipants(event);
			String command = reader.nextLine().toLowerCase();
			
			switch (command) {
				case "back":
					eventLoop(page);
					return;
				case "register":
					participateIn(user, event);
					break;
				case "unregister":
					withdrawFrom(user, event);
					break;
				default:
					System.err.println("Invalid command: " + command);
			}
			
		}

	}
	
	public static void showParticipants(String event) {
		String[] participants = getEventParticipants(event);
		
		System.out.println("   -Participants-");
		String[] lines = new String[8];
		int lineCount = 0;
		
		// set empty slots
		for (int i = 0; i < 16; i +=2 ) {
			lines[lineCount] = (i+1) + "- " + "                   ";
			if (i + 1 < 10) {
				lines[lineCount] += " "; // 10-        11- (the extra digit offsets)
			}
			lines[lineCount] += (i + 2) + "- ";
			lineCount++;
		}
		
		lineCount = 0;
		
		// fill slots with particiapnts
		for (int i = 0; i < participants.length; i += 2) {
			lines[lineCount] = (i+1) + "- " + participants[i] + " ".repeat(8 + (12 - participants[i].length()));
			lines[lineCount] += (i + 2) + "- ";
			if (i + 1 < participants.length) { // make sure the second current participant actually exists
				lines[lineCount] += participants[i + 1];
			}	
			lineCount++;
		}
		
		for (int i = 0; i < lines.length; i++) {
			System.out.println(lines[i]);
		}
		
		System.out.println("register -> to participate | unregister -> to forfeit | back -> go back");
		
		
	}
	
}
