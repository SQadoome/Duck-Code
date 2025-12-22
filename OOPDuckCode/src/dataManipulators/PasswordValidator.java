package dataManipulators;

import java.util.Random;

import prettyText.AnsiValues;

public class PasswordValidator {
	
	
	public static String hash(String text) {
		char[] hashChars = {'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'}; // 20 char sized array
		String availableChars = "./ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 64 characters used for the hash
		
		/*
			simple custom-made hashing algorithm it is based on multiplying the ascii values and using mod to get the corresponding char
		*/
		
		for (int i = 0; i < text.length(); i++) {
			int ascii = text.charAt(i) - '0';
			
			for (int charIndex = 0; charIndex < hashChars.length; charIndex++) {
				int innerAscii = hashChars[charIndex] - '0';
				int hashedChar = ((ascii*innerAscii*(charIndex + 1)) % (availableChars.length()));
				if (hashedChar < 0) {
					hashedChar *= -1;
				}
				
				hashChars[charIndex] = availableChars.charAt(hashedChar);
				
			}
		}
		
		return new String(hashChars);
	
	}
	
	public static String generateSalt() {
		String availableChars = "./ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		String salt = "";
		Random ran = new Random();
		for (int i = 0; i < 5; i++) {
			salt += availableChars.charAt(ran.nextInt(availableChars.length()));
		}
		return salt;
	}
	
	public static boolean validPassword(String password, String passwordHash) {
		if (password.length() > 12) {
			System.err.println("\nPassword must not exceed 12 characters.");
			return false;
		} else if (password.length() < 5) {
			System.err.println("\nPassword must be atleast 5 characters long.");
			return false;
		}
		
		if (hash(password).equals(passwordHash) == false) {
			System.err.println("\nInvalid password!");
			return false;
		}
		
		return true;
	}
	public static boolean validPassword(String password) {
		if (password.length() > 12) {
			System.out.println(AnsiValues.RED + "\nPassword must not exceed 12 characters." + AnsiValues.DEFAULT);
			return false;
		} else if (password.length() < 5) {
			System.out.println(AnsiValues.RED + "\nPassword must be atleast 5 characters long." + AnsiValues.DEFAULT);
			return false;
		}
		
		return true;
	}
	
}
