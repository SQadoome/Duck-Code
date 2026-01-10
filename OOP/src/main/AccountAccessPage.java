package main;

import prettyText.*;
import dataManipulators.DataManager;
import dataManipulators.PasswordValidator;
import dataManipulators.UserData;


public class AccountAccessPage extends CommandApplier {
	
	DataManager dataManager;
	App app;
	private Page page;
	
	// constructors
	public AccountAccessPage() {
		super();
		dataManager = new DataManager();
		page = new Page(new FreeStyle());
	}
	// --------
	
	public void setApp(App app) {
		this.app = app;
	}
	
	@Override
	public void apply(String command) {
		switch (command) {
		case "0":
			System.out.println(AnsiValues.BLUE + "*Type back to go back." + AnsiValues.DEFAULT);
			register();
			break;
		case "1":
			System.out.println(AnsiValues.BLUE + "*Type back to go back." + AnsiValues.DEFAULT); login();
			break;
		case "2":
			System.out.println(AnsiValues.BOLD + AnsiValues.BLUE + "Why did you leave us :(" + AnsiValues.DEFAULT);
			System.exit(0);
			break;
		}
	}
	
	@Override
	public void invalidCommand(String command) {
		System.err.println("Invalid command:" + command);
		begin();
	}
	
	public void begin() {
		
		String[] commands = {"0", "1", "2"};
		String[] text = {
				AnsiValues.BOLD + "-----" + AnsiValues.DEFAULT + AnsiValues.BLUE + "Duck*Code" + AnsiValues.DEFAULT + AnsiValues.BOLD + "-----\n" + AnsiValues.DEFAULT,
				AnsiValues.BOLD + " 0 -> " + AnsiValues.BLUE + "Register" + AnsiValues.DEFAULT,
				AnsiValues.BOLD + " 1 -> " + AnsiValues.GREEN + "Login" + AnsiValues.DEFAULT,
				AnsiValues.BOLD + " 2 -> " + AnsiValues.RED + "Depart" + AnsiValues.DEFAULT
		};
		
		setCommands(commands);
		setMessage(AnsiValues.BOLD + "Enter command: " + AnsiValues.DEFAULT);
		
		page.printParagraph(text);
		listenForCommands();
	}
	
	private void register() {
		String[] text = {
				AnsiValues.BOLD + "Enter username: " + AnsiValues.DEFAULT,
				AnsiValues.BOLD + "Enter password: " + AnsiValues.DEFAULT
		};
		
		String username;
		String password;
		
		while(true) {
			setMessage(text[0]);
			
			String input = gatherInput();
			if (input.toLowerCase().equals("back") == true) {
				begin();
				return;
			}
			
			if (input.length() > 12) {
				System.out.println(AnsiValues.RED + "\nUser must not exceed 12 characters!" + AnsiValues.DEFAULT);
				continue;
			}
			if (input.length() < 1) {
				System.out.println(AnsiValues.RED + "\nUsername must ATLEAST contain 1 character!" + AnsiValues.DEFAULT);
				continue;
			}
			boolean flag = false;
			
			for (int i = 0; i < input.length(); i++) {
				if (Character.isLetter(input.charAt(i)) == false) {
					System.out.println(AnsiValues.RED + "\nUsername must ONLY contain letters." + AnsiValues.DEFAULT);
					flag = true;
					break;
				}
			}
			
			if (flag == true) {
				continue;
			}
			
			if (dataManager.doesUserExist(input) == false) {
				username = input;
				while(true) {
					setMessage(text[1]);
					input = gatherInput();
					
					if (input.toLowerCase().equals("back") == true) {
						begin();
						return;
					}
					password = input;
					if (PasswordValidator.validPassword(password) == true) {
						String salt = PasswordValidator.generateSalt();
						dataManager.registerUser(username, PasswordValidator.hash(password+salt), salt);
						System.out.println(AnsiValues.GREEN + "\nSuccessfuly registered." + AnsiValues.DEFAULT);
						begin();
						return;
					}	
				}
			} else {
				System.out.println(AnsiValues.RED + "\nUSER ALREADY EXISTS!" + AnsiValues.DEFAULT);
			}
		}
	}
	
	private void login() {
		String[] text = {
				AnsiValues.BOLD + "Enter username: " + AnsiValues.DEFAULT,
				AnsiValues.BOLD + "Enter password: " + AnsiValues.DEFAULT
		};
		
		setMessage(text[0]);
		
		while (true) {
			String username = gatherInput();
			
			if (username.equals("back") == true) {
				begin();
				return;
			}
			
			if (dataManager.doesUserExist(username) == true) {
				setMessage(text[1]);
				
				while (true) {
					
					String password = gatherInput();
					
					if (password.equals("back") == true) {
						begin();
						return;
					}
					
					UserData registerationData = dataManager.getUserRegisterationData(username);
					
					if (PasswordValidator.hash(password + registerationData.hashSalt).equals(registerationData.passwordHash) == true) {
						System.out.println(AnsiValues.GREEN + "Successfully logged in.\nWelcome " + username + " to Duck-Code!" + AnsiValues.DEFAULT);
						
						UserData userData = new UserData();
						userData.setUser(registerationData.username, registerationData.passwordHash, registerationData.hashSalt);
						
						App.user = userData;
						app.startMainMenu();
						return;
					} else {
						System.out.println(AnsiValues.RED + "Password is incorrect!" + AnsiValues.DEFAULT);
					}
					
				}
				
			} else {
				System.out.println(AnsiValues.RED + "User does not exist!" + AnsiValues.DEFAULT);
			}
			
		}
	}
	
}


