package inputControl;

import java.util.Scanner;

import prettyText.AnsiValues;

import java.util.HashMap;

public class InputListener {
	
	private static int activeListeners = 0;
	private static Scanner reader = new Scanner(System.in);
	
	private String message;
	private String invalidMessage;
	private HashMap<String, Integer> commands;
	private Runnable[] functions;
	private Runnable invalidInputFunction;
	
	// constructors
	
	public InputListener() {
		
	}
	
	
	public InputListener(String[] commands, Runnable[] functions) {
		setCommands(commands);
		this.functions = functions;
	}
	
	
	
	// --------
	public void setCommands(String[] commands) {
		this.commands = new HashMap<>();
		
		for (int i = 0; i < commands.length; i++) {
			this.commands.put(commands[i], i);
		}
	}
	
	
	public String[] getCommands() {
		String[] commandsArray = new String[commands.size()];
		int count = 0;
		for (HashMap.Entry<String, Integer> entry : commands.entrySet()) {
			commandsArray[count] = entry.getKey();
			count += 1;
		}
		return commandsArray;
	}
	
	public void setFunctions(Runnable[] functions) {
		this.functions = functions;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setInvalidMessage(String invalidMessage) {
		this.invalidMessage = invalidMessage;
	}
	
	public void setInvalidInputFunction(Runnable invalidInputFunction) {
		this.invalidInputFunction = invalidInputFunction;
	}
	
	// --------
	
	private void invalidInput(String input) {
		if (invalidMessage == null) {
			System.out.println(AnsiValues.RED + "Invalid input: " + AnsiValues.DEFAULT + input);
		} else {
			System.out.println(invalidMessage + input);
		}
		
		if (invalidInputFunction != null) {
			invalidInputFunction.run();
		}
		
	}
	
	public String listen() {
		if (activeListeners > 0) {
			System.err.println("Input listening oveflow!");
			return "";
		} else if (commands == null) {
			System.err.println("No speicifed commands!");
			return "";
		}
		
		activeListeners += 1;
		
		while (true) {
			if (message == null) {
				System.out.print("");
			} else {
				System.out.print(message);
			}
			String input = reader.nextLine();
			System.out.println();
		
			if (commands.containsKey(input.toLowerCase()) == true) {
				activeListeners -= 1;
				if (functions != null) {
					functions[commands.get(input.toLowerCase())].run();
				}
				return input;
			} else {
				invalidInput(input);
			}
		}
	}
	
	public String freeListen() {
		if (activeListeners > 0) {
			System.err.println("Input listening oveflow!");
			return "";
		}
		
		activeListeners += 1;
		System.out.print(message);
		String output = reader.nextLine();
		activeListeners -= 1;
		return output;
		
	}
	
}
