package main;

import dataManipulators.DataManager;
import dataManipulators.PasswordValidator;
import inputControl.ActionApplier;
import inputControl.InputListener;
import prettyText.AnsiValues;
import prettyText.Page;
import prettyText.Styles.BoxStyle;
import java.util.ArrayList;

public class MainMenu extends Page {

	private DataManager dataManager;
	private App app;
	private boolean adminMode = false;
	
	public MainMenu(App app) {
		super(new BoxStyle(60));
		dataManager = new DataManager();
		this.app = app;
	}
	
	public void begin() {
		String[] text;
		String[] commands = new String[4];
		Runnable[] functions = new Runnable[4];
		
		if (adminMode == true) {
			text = new String[4];
			text[3] = AnsiValues.BOLD + "add ->" + AnsiValues.DEFAULT + " Add event.";
			commands[3] = "add";
			functions[3] = () -> addEvent();
		} else {
			text = new String[3];
			commands[3] = "010";
			functions[3] = () -> tryAdmin();
		}
		
		text[0] = AnsiValues.GREEN + AnsiValues.BOLD + "events ->" + AnsiValues.DEFAULT + " to go to events page.";
		text[1] = AnsiValues.YELLOW + AnsiValues.BOLD + "signout ->" + AnsiValues.DEFAULT + " go back to account access.";
		text[2] = AnsiValues.RED + AnsiValues.BOLD + "depart ->" + AnsiValues.DEFAULT + " leave the application.";
		
		commands[0] = "events";
		commands[1] = "signout";
		commands[2] = "depart";

		
		printParagraph(text);
		InputListener listener = new InputListener(commands);
		listener.setAction(new ActionApplier() {
			@Override
			public void applyInput(String input) {
				switch(input) {
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
		});

		listener.setMessage("Enter command: ");
		listener.listen();
		
	}
	

	public void viewEvents() {
		EventManager eventManager = new EventManager();
		eventManager.begin(this);
		
	}
	
	private void tryAdmin() {
		InputListener listener = new InputListener();
		listener.setMessage(AnsiValues.RED + AnsiValues.BOLD + "Key? " + AnsiValues.DEFAULT);
		String key = listener.freeListen();
		if (PasswordValidator.hash(key + "abcdef").equals("_vU@#r]do~%Dc.qlS>e0") == true) {
			System.out.println(AnsiValues.RED + "Admin mode enabled" + AnsiValues.DEFAULT);
			adminMode = true;
			begin();
		} else {
			System.out.println(AnsiValues.RED + AnsiValues.BOLD + "Nice try. :0" + AnsiValues.DEFAULT);
			begin();
		}
	}
	
	private void addEvent() {
		InputListener listener = new InputListener();
		listener.setMessage(AnsiValues.RED + "Enter title: " + AnsiValues.DEFAULT);
		String title = listener.freeListen();
		ArrayList<String> lines = new ArrayList<>();
		listener.setMessage(AnsiValues.RED + "Enter a description line (leave blank if done): " + AnsiValues.DEFAULT);
		while (true) {
			String description = listener.freeListen();
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

