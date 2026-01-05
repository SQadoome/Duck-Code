package main;

import java.util.ArrayList;
import java.util.Arrays;

import dataManipulators.DataManager;
import dataManipulators.EventData;
import inputControl.InputListener;
import prettyText.AnsiValues;
import prettyText.Book;
import prettyText.Page;
import prettyText.TextMarginar;
import prettyText.Styles.BoxStyle;
import prettyText.Styles.StarStyle;

public class EventManager extends Book {
	
	private DataManager dataManager;
	private InputListener listener;
	private EventData[] events;
	
	public EventManager() {
		super();
		dataManager = new DataManager();
		listener = new InputListener();
		events = dataManager.getEvents();
		int pageCount = (events.length) / 3; // 3 events per page
		
		if (events.length % 3 > 0) {
			pageCount += 1;  // because java rounds always to smallest (0.7 -> 0)
		}
		
		Page[] pages = new Page[pageCount];
		
		for (int i = 0; i < pages.length; i++) {
			pages[i] = new Page(new StarStyle(64));
		}
		setPages(pages);
		//System.out.println(Arrays.toString(events));
	}
	
	private void generateEventsText(String[][] textData) {
		
		for (int i = 0; i < textData.length; i++) {
			//System.err.println("Good...");
			int startAt = activePage*3;
			EventData event = events[startAt + i];
			
			String[] descriptionLines = event.getDescription().split(";"); // get formated descritpipon
			textData[i] = new String[descriptionLines.length + 1]; // create an inner array for descritpion and title
			
			textData[i][0] = event.getName(); // set title
			
			for (int j = 0; j < descriptionLines.length; j++) {
				textData[i][j + 1] = descriptionLines[j];
				
			}
			
		}
		
	}
	
	public void begin(MainMenu menu) {
		String[][] textData;
		String[] commands;
		Runnable[] functions;
		
		int startAt = activePage*3;
		int size = 0;
		
		for (int i = 0; i < 3; i++) {
			if (startAt + i < events.length) {
				size += 1;
			} else {
				break;
			}
		}
		
		textData = new String[size][];
		commands = new String[size + (getPages().length) + 1]; // + 1 is for "back"
		functions = new Runnable[size + (getPages().length) + 1]; // + 1 is for "back"
		//System.out.println(Arrays.deepToString(textData));
		generateEventsText(textData);
		
		for (int i = 0; i < size; i++) {
			int index = i;
			commands[i] = textData[i][0].toLowerCase();
			functions[i] = () -> { viewEvent(menu, events[index + startAt]); };
		}
		
		for (int i = size; i < commands.length - 1 ; i++) {
			int nextPage = commands.length - i - 1;
			commands[i] = nextPage + "";
			functions[i] = () -> { switchPage(nextPage - 1); begin(menu); return; };
		}
		commands[commands.length - 1] = "back";
		functions[functions.length - 1] = () -> { menu.begin(); return; };
		
		//System.out.println(Arrays.deepToString(textData));
		
		// color the title
		
		//System.err.println(Arrays.deepToString(textData));
		multiPrintActivePage(textData);
		
		listener.setMessage(AnsiValues.BOLD + "Enter command: " + AnsiValues.DEFAULT);
		listener.setCommands(commands);
		listener.setFunctions(functions);
		
		System.out.println(AnsiValues.BOLD + "Type event name to view details, \"back\" to go menu." + AnsiValues.DEFAULT);
		listener.listen();
		
	}
	
	public void viewEvent(MainMenu menu, EventData event) {
		ParticipantsPage participantsPage = new ParticipantsPage(dataManager.getEventData(event.getName()).getParticipants());
		
		InputListener listener = new InputListener();
		String[] commands = {
				"back",
				"register",
				"unregister"
		};
		
		Runnable[] functions = {
			() -> { begin(menu); return; },
			() -> { registerForEvent(event); viewEvent(menu, event); },
			() -> { unregisterForEvent(event); viewEvent(menu, event); }
		};
		
		listener.setCommands(commands);
		listener.setFunctions(functions);
		listener.setMessage("Enter command: ");
		
		listener.listen();
		
	}
	
	public void registerForEvent(EventData event) {
		dataManager.addParticipant(event, App.user.username);
	}
	
	public void unregisterForEvent(EventData event) {
		dataManager.removeParticiapnt(event, App.user.username);
	}
	
}
