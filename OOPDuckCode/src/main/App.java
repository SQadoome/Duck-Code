package main;

import dataManipulators.UserData;

public class App {

	AccountAccessPage accountAccess;
	MainMenu mainMenu;
	public App() {
		accountAccess = new AccountAccessPage();
		
	}
	
	public void begin() {
		accountAccess.setApp(this);
		accountAccess.begin();
	}
	
	public void startMainMenu(UserData userData) {
		mainMenu = new MainMenu(userData, this);
		mainMenu.begin();
	}
	
}