package main;

import dataManipulators.DataManager;
import dataManipulators.UserData;
import inputControl.InputListener;
import prettyText.AnsiValues;
import prettyText.Page;
import prettyText.TextMarginar;
import prettyText.Styles.BoxStyle;
import java.util.ArrayList;

public class MainMenu extends Page {

	DataManager dataManager;
	UserData userData;
	App app;
	
	public MainMenu(UserData userData, App app) {
		super(new BoxStyle(60));
		dataManager = new DataManager();
		this.userData = userData;
		this.app = app;
	}
	
	public void begin() {
		
		String[][] text = {
				{"%sevents ->%s to go to events page.", AnsiValues.BOLD, AnsiValues.DEFAULT},
				{"%sprofile ->%s to view your profile.", AnsiValues.BOLD, AnsiValues.DEFAULT},
				{"%ssignout ->%s go back to account access.", AnsiValues.BOLD, AnsiValues.DEFAULT},
				{"%sdepart ->%s leave the application.", AnsiValues.BOLD, AnsiValues.DEFAULT}
		};
		
		String[] commands = {"events", "profile", "signout", "depart"};
		Runnable[] functions = {
			() -> viewEvents(),
			() -> profile(),
			() -> app.begin(),
			() -> { System.out.println(AnsiValues.BLUE + "You traitor... you left us :((((((" + AnsiValues.DEFAULT); System.exit(0); }
		};
		
		printParagraph(text);
		InputListener listener = new InputListener(commands, functions);
		listener.setMessage("Enter command: ");
		listener.listen();
		
	}
	

	public void viewEvents() {
		EventManager eventManager = new EventManager();
		eventManager.begin(this);
		
	}
	
	public void profile() {
		
	}
	
	public void signout() {
		
	}
	
	public void depart() {
		
	}
}

