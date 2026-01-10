package main;

import java.util.Scanner;

public class CommandApplier {

	private String[] commands;
	private Scanner reader;
	private String message;
	
	public CommandApplier() {
		reader = new Scanner(System.in);
		message = "";
	}
	
	//---
	
	public void setCommands(String[] commands) {
		this.commands = commands;
	}
	
	public String[] getCommands() {
		return commands;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	//---
	
	public String gatherInput() {
		System.out.print(message);
		String input = reader.nextLine();
		System.out.println();
		return input;
	}
	
	public void listenForCommands() {
		System.out.print(message);
		
		String input = reader.nextLine();
		
		for (int i = 0; i < commands.length; i++) {
			if (input.equals(commands[i]) == true) {
				System.out.println();
				apply(input);
				return; // exit early
			}
		}
		
		invalidCommand(input);
	}
	
	public void apply(String command) {
		// override and add logic
	}
	
	public void invalidCommand(String command) {
		// overrdie and add logic
	}
	
}
