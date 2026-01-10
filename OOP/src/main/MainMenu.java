package main;

import dataManipulators.DataManager;
import dataManipulators.PasswordValidator;
import prettyText.AnsiValues;
import prettyText.BoxStyle;
import prettyText.Page;

import java.util.ArrayList;

public class MainMenu extends CommandApplier {

	private DataManager dataManager;
	private App app;
	private boolean adminMode = false;
	private Page page;
	
	public MainMenu(App app) {
		super();
		dataManager = new DataManager();
		this.app = app;
		page = new Page(new BoxStyle(60));
	}
	
	@Override
	public void apply(String command) {
		switch(command) {
			case "add":
				addEvent();
				break;
			case "events":
				viewEvents();
				break;
			case "signout":
				app.begin();
				break;
			case "depart":
				System.out.println(AnsiValues.BLUE + "You traitor... you left us :((((((" + AnsiValues.DEFAULT);
				System.exit(0);
			case "010":
				tryAdmin();
		}
	}
	
	@Override
	public void invalidCommand(String command) {
		System.out.println(AnsiValues.RED + AnsiValues.BOLD + "Invalid command: " + command + AnsiValues.DEFAULT);
		begin();
	}
	
	
	public void begin() {
		String[] text;
		String[] commands = new String[4];
		
		if (adminMode == true) {
			text = new String[4];
			text[3] = AnsiValues.BOLD + "add ->" + AnsiValues.DEFAULT + " Add event.";
			commands[3] = "add";
		} else {
			text = new String[3];
			commands[3] = "010";
		}
		
		text[0] = AnsiValues.GREEN + AnsiValues.BOLD + "events ->" + AnsiValues.DEFAULT + " to go to events page.";
		text[1] = AnsiValues.YELLOW + AnsiValues.BOLD + "signout ->" + AnsiValues.DEFAULT + " go back to account access.";
		text[2] = AnsiValues.RED + AnsiValues.BOLD + "depart ->" + AnsiValues.DEFAULT + " leave the application.";
		
		commands[0] = "events";
		commands[1] = "signout";
		commands[2] = "depart";
		
		setCommands(commands);
		setMessage(AnsiValues.BOLD + "Enter command: " + AnsiValues.DEFAULT);
		
		page.printParagraph(text);
		listenForCommands();
		
	}
	

	public void viewEvents() {
		EventManager eventManager = new EventManager(this);
		eventManager.begin();
	}
	
	private void tryAdmin() {
		setMessage(AnsiValues.RED + AnsiValues.BOLD + "Key? " + AnsiValues.DEFAULT);
		
		String key = gatherInput();
		if (PasswordValidator.hash(key + "fS1?3ra_)").equals("(OL_2'X{N#$kN/0O^Cle") == true) {
			System.out.println(AnsiValues.RED + "Admin mode enabled" + AnsiValues.DEFAULT);
			adminMode = true;
			begin();
		} else {
			System.out.println(AnsiValues.RED + AnsiValues.BOLD + "Nice try. :0" + AnsiValues.DEFAULT);
			begin();
		}
	}
	
	private void addEvent() {
		setMessage(AnsiValues.RED + "Enter title: " + AnsiValues.DEFAULT);
		
		String title = gatherInput();
		ArrayList<String> lines = new ArrayList<>();
		
		setMessage(AnsiValues.RED + "Enter a description line (leave blank if done): " + AnsiValues.DEFAULT);
		while (true) {
			String description = gatherInput();
			
			if (description.equals("") == false) {
				lines.add(description);
			} else {
				break;
			}
		}
		
		String[] description = new String[lines.size()];
		for (int i = 0; i < description.length; i++) {
			description[i] = lines.get(i);
		}
		
		dataManager.addEvent(title, description, 16);
		begin();
	}
}

