package main;


public class Main {

	public static void main(String[] args) {
		beginAccess();
	}
	
	public static void beginAccess() {
		new AccountAccess();
	}
	
	public static void beginApp(String user) {
		new App(user);
	}
	
}
