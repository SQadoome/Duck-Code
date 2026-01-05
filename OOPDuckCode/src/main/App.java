package main;

import dataManipulators.UserData;

public class App {

	public static UserData user;
	 
	AccountAccessPage accountAccess;
	MainMenu mainMenu;
	
	public App() {
		accountAccess = new AccountAccessPage();
	}
	
	public void begin() {
		accountAccess.setApp(this);
		accountAccess.begin();
	}
	
	public void startMainMenu() {
		mainMenu = new MainMenu(this);
		mainMenu.begin();
	}
	
}