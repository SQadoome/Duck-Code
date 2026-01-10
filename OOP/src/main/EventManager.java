package main;

import dataManipulators.DataManager;
import dataManipulators.EventData;
import prettyText.AnsiValues;
import prettyText.Book;
import prettyText.Page;
import prettyText.StarStyle;

public class EventManager extends CommandApplier {
	
	private enum states
	{
		EVENTS,
		EVENT
	}
	
	private states state;
	private DataManager dataManager;
	private EventData[] events;
	private Book book;
	private MainMenu menu;
	private EventData chosenEvent;
	
	public EventManager(MainMenu menu) {
		super();
		state = states.EVENTS;
		dataManager = new DataManager();
		book = new Book();
		events = dataManager.getEvents();
		int pageCount = (events.length) / 3; // 3 events per page
		
		if (events.length % 3 > 0) {
			pageCount += 1;  // because java rounds always to smallest (0.7 -> 0)
		}
		
		Page[] pages = new Page[pageCount];
		
		for (int i = 0; i < pages.length; i++) {
			pages[i] = new Page(new StarStyle(64));
		}
		book.setPages(pages);
		this.menu = menu;
	}
	
	private void generateEventsText(String[][] textData) {
		
		for (int i = 0; i < textData.length; i++) {
			
			int startAt = book.activePage*3;
			EventData event = events[startAt + i];
			
			String[] descriptionLines = event.getDescription().split(";"); // get formated descritpipon
			textData[i] = new String[descriptionLines.length + 1]; // create an inner array for descritpion and title
			
			textData[i][0] = event.getName(); // set title
			
			for (int j = 0; j < descriptionLines.length; j++) {
				textData[i][j + 1] = descriptionLines[j];
				
			}
			
		}
		
	}
	
	public void begin() {

		String[][] textData;
		String[] commands;

		int startAt = book.activePage * 3;
		int size = 0;

		for (int i = 0; i < 3; i++) {
			if (startAt + i < events.length) {
				size += 1;
			} else {
				break;
			}
		}

		textData = new String[size][];
		commands = new String[size + book.getPages().length + 1]; // the 3 events themselves and the page numbers and back

		generateEventsText(textData);

		for (int i = 0; i < size; i++) {
			commands[i] = textData[i][0].toLowerCase();
		}

		for (int i = size; i < commands.length - 1; i++) {
			int nextPage = commands.length - i - 1;
			commands[i] = nextPage + "";
		}
		commands[commands.length - 1] = "back";

		book.printActivePage(textData);

		setMessage(AnsiValues.BOLD + "Enter command: " + AnsiValues.DEFAULT);
		setCommands(commands);

		System.out.println(AnsiValues.BOLD + "Type event name to view details, \"back\" to go menu." + AnsiValues.DEFAULT);
		listenForCommands();
	}
	
	public void viewEvent() {
		ParticipantsPage participantsPage = new ParticipantsPage(dataManager.getEventData(chosenEvent.getName()).getParticipants());
		state = states.EVENT;
		
		String[] commands = {
				"back",
				"register",
				"unregister"
		};

		setCommands(commands);
		setMessage("Enter command: ");
		listenForCommands();
		
	}
	
	@Override
	public void apply(String command) {
		switch (command) {
		case "back":
			
			if (state == states.EVENTS) {
				menu.begin();
			} else if (state == states.EVENT) {
				state = states.EVENTS;
				begin();
			}
			
			break;
		case "register":
			registerForEvent();
			viewEvent();
			break;
		case "unregister":
			unregisterForEvent();
			viewEvent();
			break;
		default:
			for (int i = 0; i < getCommands().length; i++) {
				if (getCommands()[i].equals(command) == true) {

					// is event?
					for (int j = 0; j < events.length; j++) {
						if (events[j].getName().toLowerCase().equals(command) == true) {
							chosenEvent = events[j];
							viewEvent();
							return;
						}
					}
					
					book.switchPage(Integer.parseInt(getCommands()[i]) - 1);
					begin();
					return;
				}
			}
		}
	}
	
	@Override
	public void invalidCommand(String command) {
		try {
			Integer.parseInt(command);
			System.out.println(AnsiValues.BOLD + AnsiValues.RED + "Please choose an available page! " + command + AnsiValues.DEFAULT);
			
		} catch(NumberFormatException e) {
			System.out.println(AnsiValues.BOLD + AnsiValues.RED + "Invalid command: " + command + AnsiValues.DEFAULT);
		}
		begin();
	}
	
	public void registerForEvent() {
		dataManager.addParticipant(chosenEvent, App.user.username);
	}
	
	public void unregisterForEvent() {
		dataManager.removeParticiapnt(chosenEvent, App.user.username);
	}
	
}
