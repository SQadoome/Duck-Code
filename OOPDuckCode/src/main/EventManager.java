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
	private String chosenEvent = "";
	
	public EventManager() {
		super();
		dataManager = new DataManager();
		listener = new InputListener();
		events = dataManager.getEvents();
		int eventCount = (events.length + 3)/3; // because java rounds always to smallest (3.7 -> 3)
		Page[] pages = new Page[eventCount];
		
		for (int i = 0; i < pages.length; i++) {
			pages[i] = new Page(new StarStyle(new TextMarginar(TextMarginar.margins.beginning, 3), 128));
		}
		setPages(pages);
		//System.out.println(Arrays.toString(events));
	}
	
	private void generateEventsText(String[][] textData) {
		for (int i = 0; i < textData.length; i++) {
			int startAt = activePage*3;
			EventData event = events[startAt + i];
			String[] descriptionLines = event.getDescription().split(";");
			int innerSize = descriptionLines.length;
			textData[i] = new String[descriptionLines.length + 1]; // + 1 is for event "name"
			
			textData[i][0] = event.getName();
			for (int j = 1; j < innerSize + 1; j++) {
				//System.out.println("FUCKING J: " + j + " | FUCKING SIZE: " + textData[i].length);
				textData[i][j] = descriptionLines[j - 1];
			}
			//System.out.println("Finished i: " + i);
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
		commands = new String[size + 1]; // + 1 is for "back"
		functions = new Runnable[size + 1]; // + 1 is for "back"
		//System.out.println(Arrays.deepToString(textData));
		generateEventsText(textData);
		
		for (int i = 0; i < commands.length - 1; i++) {
			int index = i;
			commands[i] = textData[i][0].toLowerCase();
			functions[i] = () -> { viewEvent(textData[index][0]); };
		}
		commands[commands.length - 1] = "back";
		functions[functions.length - 1] = () -> { menu.begin(); return; };
		
		//System.out.println(Arrays.deepToString(textData));
		multiPrintActivePage(textData);
		
		listener.setMessage(AnsiValues.BOLD + "Enter command: " + AnsiValues.DEFAULT);
		listener.setCommands(commands);
		listener.setFunctions(functions);
		
		System.out.println(AnsiValues.BOLD + "Type event name to \"register\", \"back\" to go menu." + AnsiValues.DEFAULT);
		listener.listen();
		
	}
	
	public void viewEvent(String event) {
		
	}
	
}
