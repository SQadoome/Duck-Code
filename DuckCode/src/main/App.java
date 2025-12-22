package main;

import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException; // for scanner


public class App {
	
	private final static String defaultCode = "\u001B[0m"; // reset the code as if we dont then every next System. will produce the previous color
	private final static String yellowCode = "\033[0;33m";
	private final static String greenCode = "\u001B[32m";
	private final static String cyanCode = "\u001b[36m";
	
	private static String[] hints = {
		"----Command List----\n0 -> REGISTER\n1 -> LOGIN\n2 -> EXIT\n", // 0
		"---Login---\nType \"back\" to cancel\nUsername: ", // 1
		"---Login---\nType \"back\" to cancel\nPassword: ", // 2
		"\nUsername not found\n", // 3
		"\nInvalid password!\n", // 4
		"---Register---\nType \"back\" to cancel\nEnter username: ", // 5
		"---Register---\nType \"back\" to cancel\nEnter password: ", // 6
		"Command list:\n0 -> register for event\n1 -> view profile\n2 -> sign out\n3 -> exit\n", // 7
		"Password must be atleast 3 characters long", // 8
		"%s*Succesfully registered!%s\n\n", // 9
		"%s*Successfuly logged in. \nWelcome %s to Duck-Code. Have fun coding!%s\n\n" // 10
	};
	
	private static String[] events = {
		"Duck-Code major:<World-Wide>:The Duck-Code major programming contest is the official competetion from the developers of duck code.",
		"Programming pro league:<Regional>:poawfhiosahgoasasgsagiahgua",
		"Event test 1:<PLACE>:DESCRIPTION",
		"Counter strike iem colonge major:<World-Wide>:May the best team win.",
		"Standoff 2 major:<Russian(probably)>:When will V.P win?",
		"Event test 2:<PLACE>:DESCRIPTION",
		"Event test 3:<PLACE>:DESCRIPTION",
		"Event test 4:<PLACE>:DESCRIPTION"
	};
	
	private static String user;
	
	public static String hash(String text) {
		String hashValue = "";
		char[] hashChars = {'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'}; // 20 char sized array
		String availableChars = "./ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 64 characters used for the hash
		
		/*
			simple custom-made hashing algorithm it is based on multiplying the ascii values and using mod to get the corresponding char
		*/
		
		
		for (int i = 0; i < text.length(); i++) {
			int ascii = text.charAt(i) - '0';
			
			for (int charIndex = 0; charIndex < hashChars.length; charIndex++) {
				int innerAscii = hashChars[charIndex] - '0';
				int hashedChar = ((ascii*innerAscii*(charIndex + 1)) % (availableChars.length()));
				if (hashedChar < 0) {
					hashedChar *= -1;
				}
				
				hashChars[charIndex] = availableChars.charAt(hashedChar);
				
			}
		}
		
		for (int i = 0; i < hashChars.length; i++) {
			hashValue += hashChars[i];
		}
		
		return hashValue;
	}
	
	
	public static void main(String[] args) {
		validateData();
		accountAccessLoop();
		
		
	}
	
	
	public static String getUserData(String typedUsername) {
		String data = "";
		validateData();
		
		// linear search to see if the username is registered
		FileReader reader;
		
		try {
			reader = new FileReader("Data/registered_users"); // might throw exception (file not found for example)
			String[] users = reader.readAllAsString().split(";");
			
			for (String user : users) {
				if (user.split(":")[0].equals(typedUsername) == true) {
					data = user;
					reader.close();
					break;
				}
			}
			reader.close();
			return data;
		} catch (IOException e) {
			System.out.println(e);
			return data;
		}
		
	}
	
	public static String getUserEventData() {
		String data = "";
		validateData();
		
		// linear search to get tp user
		FileReader reader;
		
		try {
			reader = new FileReader("Data/registered_users"); // might throw exception (file not found for example)
			String[] users = reader.readAllAsString().split(";");
			
			for (String user : users) {
				if (user.split(":")[0].equals(user) == true) {
					data = user;
					reader.close();
					break;
				}
			}
			reader.close();
			return data;
		} catch (IOException e) {
			System.out.println(e);
			return data;
		}
		
	}
	
	public static boolean loginLoop(Scanner reader) {
		while (true) {
			
			/*
			 double while loop is used as a technique for:
			 	first we see username and when it is successfull:
			 		we see password and if it is successfull then we exit the entire function with returning true
			 and if in any case "back" is entered then we exit the function immedialty and return false
			 */
			
			System.out.printf(hints[1]);
			String username = reader.next();
			if (username.equals("back") == true) {
				return false;
			}
			
			String data = getUserData(username);
			if (data == "") {
				System.err.printf(hints[3]);
				continue;
			} else {
				while (true) {
					System.out.printf(hints[2]);
					String password = reader.next();
					if (password.equals("back") == true) {
						return false;
					}
					
					if (hash(password).equals(data.split(":")[1]) == true) {
						user = data.split(":")[0];
						System.out.println();
						System.out.println();
						System.out.printf(hints[10], "\u001B[32m", user, "\u001B[0m");
						appLoop();
						return true;
					} else {
						System.err.printf(hints[4]);
						continue;
					}
				}
			}
		}
	}
	
	public static void registerLoop(Scanner reader) {
		while (true) {
			System.out.printf(hints[5]);
			
			String username = reader.next();
			if (username.toLowerCase().equals("back") == true) {
				return;
			}
			if (getUserData(username) == "") { // checks to see if the user is already registered
				
				/*
				 double while loop is used as a technique for:
				 	first we see username and when it is successfull:
				 		we see password and if it is successfull then we exit the entire function
				 and if in any case "back" is entered then we exit the function immedialty
				 */
				
				while (true) {
					System.out.printf(hints[6]);
					String password = reader.next();
					if (password.toLowerCase().equals("back") == true) {
						return;
					}
					
					if (password.length() < 3) {
						System.err.printf(hints[8]);
						continue;
					} else if (password.length() > 12) {
						System.out.println();
						System.err.println("Password length must not exceed 12 characters!");
						System.out.println();
						continue;
					} else {
						registerUser(username , password);
						System.out.printf(hints[9], greenCode, defaultCode);
						return;
					}
				}
			} else {
				System.out.println();
				System.err.println("User already exists!");
				System.out.println();
				continue;
			}
		}
	}
	
	public static void accountAccessLoop() {
		Scanner reader = new Scanner(System.in);
		int command = -1;
		
		while (true) {
			System.out.printf(hints[0]);
			try {
				command = reader.nextInt();
			} catch (InputMismatchException e) {
				// this exception is thrown when the reader.nextInt() doesnot work
				
				System.err.println("Please input a number as a command!");
				reader.nextLine(); // CLEAR THE WRONG INPUT AS THE INPUT WILL NOT CHANGE NEXT ITERATION!
				continue;
			}
			
			switch (command) {
				case 0:
					registerLoop(reader);
					break;
				case 1:
					boolean success = loginLoop(reader);
					if (success == true) {
						return;
					}
					break;
				case 2:
					System.out.println("Why did you leave us :(");
					System.exit(0);
				default:
					System.out.println();
					System.err.println("Unknown command: " + command);
					System.out.println();
					continue;
			}
			
		}
		
	}
	
	public static void validateData() {
		
		File dataFolder = new File("Data"); // file object on path "Data"
		File registerFile;
		File userData;
		
		// check either the folder doesnot exist OR it exists but it is not a folder
		if (dataFolder.exists() == false || (dataFolder.exists() == true && dataFolder.isDirectory() == false)) {
			dataFolder.delete(); // delete the unwatned clone/imposter
			dataFolder.mkdir(); // create folder
		}
		
		registerFile = new File("Data/registered_users");
		userData = new File("Data/user_data");
		
		// check if register data FILE does not exist OR it exists but it is not a FILE
		if (registerFile.exists() == false || (registerFile.exists() == true && registerFile.isFile() == false)) {
			try {
				registerFile.delete(); // delete the unwanted clone/imposter
				registerFile.createNewFile(); // create new user register FILE
			} catch (IOException e) {
				System.err.println("ERROR: Failed to retireve data.");
			}
		}
		// same as above but for user data
		if (userData.exists() == false || (userData.exists() == true && userData.isFile() == false)) {
			try {
				userData.delete(); // delete the unwanted clone/imposter
				userData.createNewFile(); // create new user register FILE
			} catch (IOException e) {
				System.err.println("ERROR: Failed to retireve data.");
			}
		}
		
	}
	
	public static boolean registerUser(String username, String password) {
		validateData(); // make sure the files are validated to avoid uneccessary exceptions
		
		try {
			FileWriter writer = new FileWriter("Data/registered_users", true); // create new file writer object with append mode on
			FileWriter writer2 = new FileWriter("Data/user_data", true); // for user data (like events)
			// append mode writes to the end instead of overwriting
			
			/*
			 the saving format is: (user);(user)
			 and the user is broken down to: (username):(password)
			 */
			
			writer.write(username + ":" + hash(password) + ";");
			writer2.write(username + ",");
			for (int i = 0; i < events.length; i++) {
				writer2.write("0:");
			}
			writer2.write(";"); // seperate users
			writer.close(); // close the writer
			writer2.close();
			return true;
			
		} catch (IOException e) {
			System.err.println("ERROR: Failed to save user");
			return false;
		}
		
	}
	
	public static void appLoop() {
		System.out.printf("-----%s%s%s-----\n", greenCode, user, defaultCode);
		
		Scanner reader = new Scanner(System.in);
		int command = -1;
		
		
		while (true) {
			System.out.printf(hints[7]);
			System.out.println();
			System.out.printf("%s* all rights reserved to duck-code.co%s\n", cyanCode, defaultCode);
			try {
				command = reader.nextInt();
				
			} catch (InputMismatchException e) {
				reader.nextLine(); // make reader go to next input and not stuck on the current
				System.out.println();
				System.err.println("Pls write a number as a command!");
				System.out.println();
				continue;
			}
			
			switch (command) {
				case 0:
					validateData();
					reader.nextLine(); // avoid scanner glitching on empty input
					eventLoop(reader, 1);
					break;
				case 1:
					reader.nextLine(); // avoid scanner glitching on empty input
					profileLoop(reader);
					break;
				case 2:
					System.err.println();
					System.err.println("Signed out.");
					System.err.println();
					accountAccessLoop();
					return;
				case 3:
					System.out.println("why did you leave us :(");
					System.exit(0);
					break;
				default:
					System.out.println();
					System.err.println("Invalid command: " + command);
					System.out.println();
					continue;
					
			}
		}
	}
	
	public static void profileLoop(Scanner reader) {
		while (true) {
			System.out.println("----" + user + "----");
			int[] registeredEvents = getEvents();
			System.out.printf("%scommands: \"back\" , \"(event_name)\" to no longer particapte.\"%s\n\n", cyanCode, defaultCode);
			
			System.out.printf("%s-Username: %s%s.\n", greenCode, defaultCode, user);
			System.out.printf("%s-Registered for:%s\n", yellowCode, defaultCode);
			boolean isRegistered = false;
			for (int i = 0; i < events.length; i++) {
				if (registeredEvents[i] == 1) {
					isRegistered = true;
					System.out.println(" *" + events[i].split(":")[0]);
				}
			}
			if (isRegistered == false) {
				System.out.println("NOTHING. noob!");
			}
			System.out.println();
			System.out.println("----Duck-Code----");
			String command = reader.nextLine();
			if (command.equals("back") == true) {
				return;
			}
			boolean success = false;
			for (int i = 0; i < events.length; i++) {
				if (command.toLowerCase().equals(events[i].split(":")[0].toLowerCase()) == true && registeredEvents[i] == 1) {
					editUserEvent(i, 0);
					success = true;
					break;
				}
			}
			if (success == false) {
				System.out.println();
				System.err.println("Unkown command: " + command);
				System.out.println();
			}
		}
	}
	
	public static void editUserEvent(int index, int particpating) {
		try {
			RandomAccessFile dataModifier = new RandomAccessFile("Data/user_data", "rw"); // "rw" -> read + write
			FileReader reader = new FileReader("Data/user_data");
			String entireData = reader.readAllAsString();
			reader.close();
			// SQadoome,0:0:;  (username_length + ,0:0:;)
			
			String[] users = entireData.split(";");
			int charIndex = 0;
			
			for (int i = 0; i < users.length; i++) {
				String[] currentUser = users[i].split(",");
				if (currentUser[0].equals(user) == true) {
					charIndex += currentUser[0].length() + 1 + 2*index;
					break;
				} else {
					charIndex += currentUser[0].length() + 6;
				}
			}
			
			dataModifier.seek(charIndex);
			dataModifier.write((particpating + "").getBytes());
			dataModifier.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: A DATA FILE IS MISSING!");
		} catch (IOException e) {
			System.err.println("ERROR: Unkown cause...");
		}
		
		
		
	}
	
	public static int[] getEvents() {
		int[] registeredEvents = new int[events.length]; // values will be 0 by default
		
		try {
			FileReader dataReader = new FileReader("Data/user_data");
			String[] users = dataReader.readAllAsString().split(";"); // this method might give IO exception (like reading from a closed reader)
			
			boolean userFound = false;
			
			for (int i = 0; i < users.length; i++) {
				String[] currentUser = users[i].split(",");
				//System.err.println("THE FUCKING USER: " + Arrays.toString(currentUser));
				if (currentUser[0].equals(user) == true) {
					userFound = true;
					String[] userEventData = currentUser[1].split(":"); //this variable gets what events the user registered for (0->no) (1->yes)
					
					for (int index = 0; index < userEventData.length; index++) {
						if (userEventData[index].equals("1") == true) {
							registeredEvents[index] = 1;
						}
					}
					break;
				}
			}
			dataReader.close();
			if (userFound == false) {
				System.err.println("ERROR: User not found...");
				return registeredEvents;
			}
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Failed to retrieve data.");
		} catch (IOException e) {
			System.err.println("ERROR: Unkown cause.");
		}
		return registeredEvents;
	}
	
	public static void eventLoop(Scanner reader, int page) {
		
		int[] registeredEvents = getEvents();
		int totalPages = (events.length+3-1)/3;
		
		String[] nextEvents = new String[3];
		
		for (int i = (page-1)*3; i < (page-1)*3 + 3; i++) {
			if (i >= events.length) { break; }
			
			nextEvents[i - (page-1)*3] = events[i];
		}
		
		System.out.println();
		System.out.println("---Events---");
		System.out.println();
		
		for (int i = 0; i < nextEvents.length; i++) {
			if (nextEvents[i] == null) { break; } // break instead of "continue" because if a null exists, then all after it is null.
			
			String[] event = nextEvents[i].split(":"); // split into 3 parts (name):(level):(description)
			
			System.out.printf("%s*%s%s", yellowCode, event[0], defaultCode);
			
			if (registeredEvents[i + (page-1)*3] == 1) {
				System.out.printf(" | %sRegistered.%s", greenCode, defaultCode);
			} else {
				System.out.printf(" | %sNot registered%s.", cyanCode, defaultCode);
			}
			
			System.out.println();
			System.out.println(" -" + event[1] + "-");
			System.out.println(" -" + event[2]);
			System.out.println();
		}
		
		System.out.printf("-----%sPage:%s/%s%s-----\n", greenCode, page, totalPages, defaultCode);
		
		while (true) {
			String command = reader.nextLine();
			if (command.equals("back") == true) {
				return;
			}
			boolean flag = true;
			
			for (int i = 0; i < nextEvents.length; i++) {
				if (nextEvents[i] == null) { break; }
				
				if (nextEvents[i].split(":")[0].toLowerCase().equals(command.toLowerCase()) == true) {
					if (registeredEvents[i + (page-1)*3] == 0) {
						System.out.println();
						System.out.printf("%sSuccessfully registered for: %s%s\n\n", greenCode, nextEvents[i].split(":")[0], defaultCode);
						editUserEvent((page-1)*3 + i, 1);
						return;
					} else {
						System.out.println();
						System.err.println("Already registered for: " + nextEvents[i].split(":")[0]);
						flag = false;
						break;
					}
				}
				
				for (int count = 0; count < totalPages; count++) {
					if (command.equals((i+1) + "") == true) {
						page = i+1;
						flag = false;
						break;
					}
				}
			}
			if (flag == true) {
				System.err.println("Unkown command: " + command);
			}
			eventLoop(reader, page); // repeat until success or "back"
			break;
			
		}
	}
	
}
