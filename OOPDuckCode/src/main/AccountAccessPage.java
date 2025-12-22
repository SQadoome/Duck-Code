package main;

import prettyText.*;
import prettyText.Styles.*;
import dataManipulators.DataManager;
import dataManipulators.PasswordValidator;
import dataManipulators.UserData;
import inputControl.*;


public class AccountAccessPage extends Page {
	
	DataManager dataManager;
	App app;
	
	// constructors
	public AccountAccessPage() {
		super();
		dataManager = new DataManager();
	}
	
	
	public AccountAccessPage(Style style) {
		super(style);
		dataManager = new DataManager();
	}
	// --------
	
	public void setApp(App app) {
		this.app = app;
	}
	
	public void begin() {
		
		String[] commands = {"0", "1", "2"};
		String[] text = {
				AnsiValues.BOLD + "-----" + AnsiValues.DEFAULT + AnsiValues.BLUE + "Duck*Code" + AnsiValues.DEFAULT + AnsiValues.BOLD + "-----\n" + AnsiValues.DEFAULT,
				AnsiValues.BOLD + " 0 -> " + AnsiValues.BLUE + "Register" + AnsiValues.DEFAULT,
				AnsiValues.BOLD + " 1 -> " + AnsiValues.GREEN + "Login" + AnsiValues.DEFAULT,
				AnsiValues.BOLD + " 2 -> " + AnsiValues.RED + "Depart" + AnsiValues.DEFAULT};
		Runnable[] functions = {
				() -> { System.out.println(AnsiValues.BLUE + "*Type back to go back." + AnsiValues.DEFAULT); register(); },
				() -> { System.out.println(AnsiValues.BLUE + "*Type back to go back." + AnsiValues.DEFAULT); login(); },
				() -> { System.out.println(AnsiValues.BOLD + AnsiValues.BLUE + "Why did you leave us :(" + AnsiValues.DEFAULT); System.exit(0); }
		};
		InputListener listener = new InputListener(commands, functions);
		listener.setMessage("Enter command: ");
		
		printParagraph(text);
		listener.listen();
	}
	
	private void register() {
		String[] text = {
				AnsiValues.BOLD + "Enter username: " + AnsiValues.DEFAULT,
				AnsiValues.BOLD + "Enter password: " + AnsiValues.DEFAULT
		};
		InputListener listener = new InputListener();
		listener.setMessage(text[0]);
		
		String username;
		String password;
		
		while(true) {
			String input = listener.freeListen();
			if (input.toLowerCase().equals("back") == true) {
				begin();
				return;
			}
			if (dataManager.doesUserExist(input) == false) {
				username = input;
				while(true) {
					listener.setMessage(text[1]);
					input = listener.freeListen();
					
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
		InputListener listener = new InputListener();
		
		listener.setMessage(AnsiValues.BOLD + "Enter username: " + AnsiValues.DEFAULT);
		while (true) {
			String username = listener.freeListen();
			
			if (username.equals("back") == true) {
				begin();
				return;
			}
			
			if (dataManager.doesUserExist(username) == true) {
				
				listener.setMessage(AnsiValues.BOLD + "Enter password: " + AnsiValues.DEFAULT);
				while (true) {
					
					String password = listener.freeListen();
					
					if (password.equals("back") == true) {
						begin();
						return;
					}
					
					UserData registerationData = dataManager.getUserRegisterationData(username);
					//System.out.println(registerationData.toString());
					
					if (PasswordValidator.hash(password + registerationData.hashSalt).equals(registerationData.passwordHash) == true) {
						System.out.println(AnsiValues.GREEN + "Successfully logged in.\nWelcome " + username + " to Duck-Code!" + AnsiValues.DEFAULT);
						UserData userData = new UserData();
						userData.setUser(registerationData.username, registerationData.passwordHash, registerationData.hashSalt);
						app.startMainMenu(userData);
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


