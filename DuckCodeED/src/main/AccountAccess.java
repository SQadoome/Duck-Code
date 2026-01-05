package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import dataManipulators.DataManager;
import dataManipulators.PasswordValidator;
import dataManipulators.UserData;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import layouts.AnchorLayout;

public class AccountAccess extends JFrame {
	
	// Screen size
	private final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	private JPanel box;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton registerButton;
	private JButton newUserButton;
	private JButton backButton;
	private JLabel usernameMessage = new JLabel();
	private JLabel passwordMessage = new JLabel();
	private DataManager dataManager = new DataManager();
	
	public AccountAccess() {
		setPage();
	}
	
	public void setPage() {
		setSize((int) (SCREEN_SIZE.width*0.6), (int) (SCREEN_SIZE.height*0.7));
		setLayout(new AnchorLayout());
		setBackground(new Color(10, 10, 10, 255));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		box = new JPanel();
		box.setPreferredSize(new Dimension(50, 50));
		box.setSize( (int) (getWidth()*.5), (int) (getHeight()*.3));
		box.setLayout(new AnchorLayout());
		box.setBackground(new Color(200, 200, 200, 255));
		box.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220, 230), 3));
		add(box);
		
		JLabel title = new JLabel("*Duck - Code");
		title.setForeground(new Color(100, 100, 255, 255));
		title.setFont(new Font(title.getFont().getName(), Font.BOLD, 18));
		title.setSize( (int) (getWidth()*.2), (int) (getHeight()*.1));
		title.setPreferredSize(new Dimension(55, 30));
		add(title);
		
		createFields();
		createButtons();
		setVisible(true);
	}
	
	private void createFields() {
		usernameField = new JTextField();
		usernameField.setSize( (int) (box.getWidth()*.7), (int) (box.getHeight()*0.1));
		usernameField.setPreferredSize(new Dimension(60, 20));
		box.add(usernameField);
		
		passwordField = new JPasswordField();
		passwordField.setSize( (int) (box.getWidth()*.7), (int) (box.getHeight()*0.1));
		passwordField.setPreferredSize(new Dimension(60, 80));
		box.add(passwordField);
		
		JLabel label1 = new JLabel("Username: ");
		JLabel label2 = new JLabel("Password: ");
		
		label1.setSize( (int) (box.getWidth()*0.25), (int) (box.getHeight()*.2));
		label2.setSize( (int) (box.getWidth()*.25), (int) (box.getHeight()*.2));
		
		label1.setPreferredSize(new Dimension(20, 20));
		label2.setPreferredSize(new Dimension(20, 80));
		
		box.add(label1);
		box.add(label2);
		
		usernameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == e.VK_ENTER) {
					passwordField.requestFocusInWindow();
				}
			}
		});
		
	}
	
	private void createButtons() {
		
		usernameMessage.setSize(600, 15);
		usernameMessage.setForeground(new Color(255, 0, 0, 255));
		usernameMessage.setPreferredSize(new Dimension(
				usernameField.getPreferredSize().width + 40,
				usernameField.getPreferredSize().height + 10));
		box.add(usernameMessage);
		
		passwordMessage.setSize(600, 15);
		passwordMessage.setForeground(new Color(255, 0, 0, 255));
		passwordMessage.setPreferredSize(new Dimension(
				passwordField.getPreferredSize().width + 40,
				passwordField.getPreferredSize().width + 10));
		box.add(passwordMessage);
		
		loginButton = new JButton("Login");
		newUserButton = new JButton("New user?");
		registerButton = new JButton("Register");
		backButton = new JButton("Back");
		
		loginButton.setSize( (int) (getWidth()*.1), (int) (getHeight()*.05));
		newUserButton.setSize( (int) (getWidth()*.15), (int) (getHeight()*.05));
		registerButton.setSize( (int) (getWidth()*.15), (int) (getHeight()*.05));
		backButton.setSize( (int) (getWidth()*.15), (int) (getHeight()*.05));
		
		loginButton.setPreferredSize(new Dimension(30, 70));
		newUserButton.setPreferredSize(new Dimension(70, 70));
		registerButton.setPreferredSize(new Dimension(50, 70));
		backButton.setPreferredSize(new Dimension(70, 70));
		
		registerButton.setVisible(false);
		backButton.setVisible(false);
		
		newUserButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					switchPage(1);
				}
			}
		});
		
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					errorfiyUsername("");
					errorfiyPassword("");
					login();
				}
			}
		});
		
		registerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					errorfiyUsername("");
					errorfiyPassword("");
					register();
				}
			}
		});
		
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					switchPage(0);
				}
			}
		});
		add(loginButton);
		add(newUserButton);
		add(registerButton);
		add(backButton);
		
		
	}
	
	private void errorfiyUsername(String message) {
		usernameMessage.setText(message);
	}
	
	private void errorfiyPassword(String message) {
		passwordMessage.setText(message);
	}
	
	private void switchPage(int page) {
		errorfiyUsername("");
		errorfiyPassword("");
		System.err.println(page);
		switch (page) {
			case 0:
				usernameField.setText("");
				passwordField.setText("");
				usernameField.requestFocusInWindow();

				loginButton.setVisible(true);
				newUserButton.setVisible(true);
				registerButton.setVisible(false);
				backButton.setVisible(false);
				break;
			case 1:
				usernameField.setText("");
				passwordField.setText("");
				usernameField.requestFocusInWindow();
				
				loginButton.setVisible(false);
				newUserButton.setVisible(false);
				registerButton.setVisible(true);
				backButton.setVisible(true);
				break;
				
		}
		
	}
	
	private void register() {
		String username = usernameField.getText();
		
		if (username.length() > 12) {
			errorfiyUsername("Username must not exceed 12 characters");
			return;
		} else if (username.length() < 3) {
			errorfiyUsername("Username must be atleast 3 characters");
			return;
		}
		
		for (int i = 0; i < username.length(); i++) {
			if (Character.isLetter(username.charAt(i)) == false) {
				errorfiyUsername("Username must only be characters");
				return;
			}
		}
		
		if (dataManager.doesUserExist(username) == true) {
			errorfiyUsername("USER ALREADY EXISTS!");
			return;
		}
		
		String password = new String(passwordField.getPassword());
		if (password.length() < 5) {
			errorfiyPassword("Password must atleast be 5 characters");
			return;
		} else if (password.length() > 15) {
			errorfiyPassword("Password must not exceed 15 characters");
			return;
		}
		System.err.println("Successfuly registered user: " + username + " | " + password);
		String salt = PasswordValidator.generateSalt();
		dataManager.registerUser(username, PasswordValidator.hash(password + salt), salt);
		switchPage(0);
	}
	
	private void login() {
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());
		
		if (username.isBlank() == true) {
			errorfiyUsername("Please enter a username first");
			return;
		}
		
		if (dataManager.doesUserExist(username) == false) {
			errorfiyUsername("USER DOESNOT EXIST!");
			return;
		}
		
		if (password.isBlank() == true) {
			errorfiyPassword("Please enter a password first");
			return;
		}
		
		UserData data = dataManager.getUserRegisterationData(username);
		System.err.println("Tried to log user in: " + username + " | " + password + " | " + data.hashSalt);
		
		if (PasswordValidator.hash(password + data.hashSalt).equals(data.passwordHash) == true) {
			Main.beginApp(username);
			dispose();
		} else {
			errorfiyPassword("WRONG PASSWORD!");
			return;
		}
	}
	
}
