package dataManipulators;

import java.util.Random;


public class PasswordValidator {

	private static int getNextChar(int num_1, int num_2) {
		int xored = num_1^num_2;
		// leave the 126 char for data saving username (char126) passwordhash (char126) salt
		return (xored % (125-33)) + 33;
	}

	public static String hash(String text) {
		int[] hashChars = new int[20];

		for (int i = 0; i < hashChars.length; i++) {
			hashChars[i] = 35;
			// '!' = 35
		}


		for (int cycle = 0; cycle < 100; cycle++) {
			for (int i = 0; i < text.length(); i++) {
				int outerAscii = (int) text.charAt(i);

				for (int j = 1; j < hashChars.length; j++) {
					int previous = hashChars[j - 1];
					hashChars[j] = getNextChar(previous*j*hashChars[j]*outerAscii + i, hashChars[j]*i*text.length() + j);
				}

				for (int j = hashChars.length - 2; j > -1; j--) {
					int next = hashChars[j + 1];
					hashChars[j] = getNextChar(next*j*hashChars[j]*outerAscii + i, hashChars[j]*i*text.length() + j);
				}

			}
		}
		char[] result = new char[hashChars.length];

		for (int i = 0; i < result.length; i++) {
			result[i] = (char) hashChars[i];
		}

		return new String(result);
	
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
			System.out.println("\nPassword must not exceed 12 characters.");
			return false;
		} else if (password.length() < 5) {
			System.out.println("\nPassword must be atleast 5 characters long.");
			return false;
		}
		
		return true;
	}
	
}
