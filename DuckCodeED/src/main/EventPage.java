package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventSetDescriptor;
import java.util.Arrays;

import javax.swing.*;

import dataManipulators.DataManager;
import dataManipulators.EventData;
import layouts.AnchorLayout;

public class EventPage extends JPanel {
	
	EventData event;
	DataManager dataManager = new DataManager();
	
	private JLabel title;
	private JLabel description;
	private JButton accessButton;
	private JButton showParticipants;
	private JPanel mainCover;
	
	private JPanel secondCover;
	private JLabel[] participants;
	private JButton backButton;
	
	public EventPage(EventData event, Dimension size) {
		this.event = event;		
		setPreferredSize(new Dimension(50, 50));
		setSize(size);
		setLayout(new AnchorLayout());
		setupPage();
		updateButtonText();
		updateEvent(event);
	}
	
	private void setupButtons() {
		accessButton = new JButton("register");
		accessButton.setPreferredSize(new Dimension(15, 90));
		accessButton.setSize( (int) (getWidth()*0.2), (int) (getHeight()*0.1));
		accessButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				access();
			}
		});
		
		showParticipants = new JButton("participants");
		showParticipants.setSize( (int) (getWidth()*0.2), (int) (getHeight()*0.1));
		showParticipants.setPreferredSize(new Dimension(85, 90));
		showParticipants.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showParticipants();
			}
		});
		
		backButton = new JButton("back");
		backButton.setPreferredSize(new Dimension(15, 90));
		backButton.setSize( (int) (getWidth()*0.15), (int) (getHeight()*0.1));
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				secondCover.setVisible(false);
				mainCover.setVisible(true);
			}
		});
		
		Styling.styleComponent(accessButton);
		Styling.styleComponent(showParticipants);
		Styling.styleComponent(backButton);
		
		if (dataManager.isParticipating(event, App.user) == true) {
			accessButton.setForeground(new Color(255, 0, 0, 255));
		}
		
		secondCover.add(backButton);
		mainCover.add(accessButton);
		mainCover.add(showParticipants);
	}
	
	private void setupLabels() {
		title = new JLabel("--" + event.getName() + "--");
		description = new JLabel(" *" + event.getDescription());
		participants = new JLabel[8];
		
		title.setPreferredSize(new Dimension(50, 10));
		description.setPreferredSize(new Dimension(50, 30));
		
		int height = 8;
		for (int i = 0; i < participants.length; i++) {
			participants[i] = new JLabel();
			participants[i].setSize(getWidth(), 24);
			participants[i].setPreferredSize(new Dimension(55, height));
			Styling.styleComponent(participants[i], 18);
			secondCover.add(participants[i]);
			height += 9;
			
		}
		
		Styling.styleComponent(description);
		Styling.styleComponent(title, 24);
		
		mainCover.add(title);
		mainCover.add(description);
	}
	
	private void setupPage() {
		mainCover = new JPanel();
		secondCover = new JPanel();
		
		mainCover.setSize(getWidth(), getHeight());
		mainCover.setPreferredSize(new Dimension(50, 50));
		secondCover.setSize(getWidth(), getHeight());
		secondCover.setPreferredSize(new Dimension(50, 50));
		
		mainCover.setLayout(new AnchorLayout());
		secondCover.setLayout(new AnchorLayout());
		
		mainCover.setBackground(new Color(200, 200, 200, 255));
		mainCover.setBorder(BorderFactory.createLineBorder(Styling.LIGHT_GREEN, 3));
		mainCover.setBackground(Styling.GRAY);
		
		secondCover.setBackground(new Color(200, 200, 200, 255));
		secondCover.setBorder(BorderFactory.createLineBorder(Styling.LIGHT_GREEN, 3));
		secondCover.setBackground(Styling.GRAY);
		
		add(mainCover);
		add(secondCover);
		secondCover.setVisible(false);
		setupLabels();
		setupButtons();
	}
	
	private String[] radixSort(String words[], int target) {
		String[][] temp = new String[26][words.length];
		int[] count = new int[26];
		int count2 = 0;
		
		for (int i = 0; i < words.length; i++) {
			if (words[i] == null) {
				break;
			}
			
			if (target > words[i].length() - 1) {
				String bucket = words[0];
				words[0] = words[i];
				words[i] = bucket;
				count2++;
				continue;
			}
			
			int atIndex = (Character.toLowerCase(words[i].charAt(target)) - '0') - 49;
			temp[atIndex][count[atIndex]] = words[i];
			count[atIndex] += 1;
			
		}
		
		for (int i = 0; i < 26; i++) {
			if (count[i] > 1) {
				temp[i] = radixSort(temp[i], target+1);
			}
			
			for (int j = 0; j < words.length; j++) {
				if (temp[i][j] == null) {
					break;
				} else {
					words[count2] = temp[i][j];
					count2++;
				}
				
				
			}
			
		}
		return words;
	}
	
	public void updateEvent(EventData newEvent) {
		event = newEvent;
		title.setText(event.getName());
		description.setText(event.getDescription());
		
		title.setSize(title.getText().length()*32, 150);
		description.setSize(description.getText().length()*16, 150);
		updateButtonText();
		event.setParticipants(radixSort(event.getParticipants(), 0));
		secondCover.setVisible(false);
		mainCover.setVisible(true);
	}
	
	
	private String[][] pairParticipants() {
		String[][] text = new String[8][2];
		
		for (int i = 0; i < text.length; i++) {
			if (i*2 < event.getParticipants().length) {
				
				String next = event.getParticipants()[i*2];
				text[i][0] = next + " ".repeat(12 - next.length());

			}
			if (1 + i*2 < event.getParticipants().length) {
				
				String next = event.getParticipants()[i*2 + 1];
				text[i][1] = next + " ".repeat(12 - next.length());	
				
			}
		}
		return text;
	}
	
	private void showParticipants() {
		
		String[][] raw = pairParticipants();
		String result = "";
		System.out.println(Arrays.deepToString(raw));
		
		for (int i = 0; i < raw.length; i++) {
			result = "";
			
			for (int j = 0; j < 2; j++) {
				if (j == 0) {
					
					if (raw[i][0] == null) {
						result += "*" + " ".repeat(60 + 24);
					} else {
						result += "*" + raw[i][0] + " ".repeat(60);
					}
					
					
				} else {
					if (raw[i][1] == null) {
						result +=  "*";
					} else {
						result += "*" + raw[i][1];
					}
				}
			}
			
			participants[i].setText(result);
		}
		
		mainCover.setVisible(false);
		secondCover.setVisible(true);
	}
	
	private void access() {
		if (dataManager.isParticipating(event, App.user) == true) {
			unregister();
			accessButton.setText("register");
			accessButton.setForeground(Styling.LIGHT_GREEN);
		} else {
			register();
			accessButton.setText("unregister");
			accessButton.setForeground(new Color(255, 0, 0, 255));
		}
		
	}
	
	private void updateButtonText() {
		if (dataManager.isParticipating(event, App.user) == true) {
			accessButton.setText("unregister");
		} else {
			accessButton.setText("register");
		}
	}
	
	private void register() {
		dataManager.addParticipant(event, App.user);
		updateEvent(dataManager.getEventData(event.getName()));
	}

	private void unregister() {
		dataManager.removeParticiapnt(event, App.user);
		updateEvent(dataManager.getEventData(event.getName()));
	}
}
