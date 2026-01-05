package inputControl;

import java.util.Scanner;
import prettyText.AnsiValues;


public class InputListener {
	
	private static int activeListeners = 0;
	private static Scanner reader = new Scanner(System.in);
	
	private String message;
	private String invalidMessage;
	private String[] commands;
	private ActionApplier applier;

	// constructors
	
	public InputListener() {
		
	}
	
	
	public InputListener(String[] commands) {
		setCommands(commands);
	}
	
	
	
	// --------
	public void setCommands(String[] commands) {
		String[] fomrattedCommands = new String[commands.length];

		for (int i = 0; i < commands.length; i++) {
			fomrattedCommands[i] = commands[i].toLowerCase();
		}
		this.commands = fomrattedCommands;
	}
	
	
	public String[] getCommands() {
		return commands;
	}

	public void setAction(ActionApplier applier) {
		this.applier = applier;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setInvalidMessage(String invalidMessage) {
		this.invalidMessage = invalidMessage;
	}
	
	// --------

	public boolean hasCommand(String command) {
		for (int i = 0; i < commands.length; i++) {
			if (commands[i].equals(command) == true) {
				return true;
			}
		}
		return false;
	}

	private void invalidInput(String input) {
		if (invalidMessage == null) {
			System.out.println(AnsiValues.RED + "Invalid input: " + AnsiValues.DEFAULT + input);
		} else {
			System.out.println(invalidMessage + input);
		}
	}
	
	public void listen() {
		if (activeListeners > 0) {
			System.err.println("Input listening oveflow!");
		} else if (commands == null) {
			System.err.println("No speicifed commands!");
		}
		
		activeListeners += 1;
		
		while (true) {
			if (message != null) {
				System.out.print(message);
			}

			String input = reader.nextLine();
			System.out.println();
		
			if (hasCommand(input.toLowerCase()) == true) {
				activeListeners -= 1;

				if (applier != null) {
					applier.applyInput(input.toLowerCase());
				}

			} else {
				invalidInput(input);
			}
		}
	}
	
	public String freeListen() {
		if (activeListeners > 0) {
			System.err.println("Input listening overflow!");
			return "";
		}
		
		activeListeners += 1;
		System.out.print(message);
		String output = reader.nextLine();
		activeListeners -= 1;
		return output;
	}
}
