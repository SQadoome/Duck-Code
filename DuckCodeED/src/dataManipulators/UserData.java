package dataManipulators;

import java.util.HashMap;

public class UserData {

	public String username = "";
	public String passwordHash = "";
	public String hashSalt = "";
	private HashMap<String, Boolean> events;
	
	public UserData() {
		events = new HashMap<>();
	}
	
	@Override
	public String toString() {
		return "username: " + username + " | hash: " + passwordHash + " | salt: " + hashSalt + " \n" + events.toString();
	}
	
	public void setUser(String username, String passwordHash, String hashSalt) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.hashSalt = hashSalt;
	}
	
	public boolean isEmpty() {
		if (username.isBlank() == true || passwordHash.isBlank() == true) {
			return true;
		} else {
			return false;
		}
	}
	
}
