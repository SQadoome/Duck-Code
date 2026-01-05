package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
		accessButton.setPreferredSize(new Dimension(20, 80));
		accessButton.setSize( (int) (getWidth()*0.2), (int) (getHeight()*0.15));
		accessButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					access();
				}
			}
		});
		
		showParticipants = new JButton("participants");
		showParticipants.setSize( (int) (getWidth()*0.2), (int) (getHeight()*0.15));
		showParticipants.setPreferredSize(new Dimension(50, 80));
		showParticipants.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					showParticipants();
				}
			}
		});
		
		backButton = new JButton("back");
		backButton.setPreferredSize(new Dimension(15, 90));
		backButton.setSize( (int) (getWidth()*0.15), (int) (getHeight()*0.1));
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					secondCover.setVisible(false);
					mainCover.setVisible(true);
				}
			}
		});
		
		secondCover.add(backButton);
		mainCover.add(accessButton);
		mainCover.add(showParticipants);
	}
	
	private void setupLabels() {
		title = new JLabel(event.getName());
		description = new JLabel(" *" + event.getDescription());
		participants = new JLabel[8];
		
		title.setPreferredSize(new Dimension(50, 10));
		description.setPreferredSize(new Dimension(50, 30));
		
		title.setSize(title.getText().length()*32, 150);
		title.setFont(new Font(
				title.getFont().getName(),
				Font.BOLD,
				32));
		title.setForeground(new Color(255, 215, 0, 255));
		description.setSize(description.getText().length()*16, 150);
		description.setFont(new Font(
				description.getFont().getName(),
				Font.BOLD,
				16));
		
		
		int height = 5;
		for (int i = 0; i < participants.length; i++) {
			participants[i] = new JLabel();
			participants[i].setSize(getWidth(), 16);
			participants[i].setPreferredSize(new Dimension(50, height));
			participants[i].setFont(new Font(
					secondCover.getFont().getName(),
					Font.BOLD,
					16
			));
			participants[i].setForeground(new Color(255, 210, 0, 255));
			
			secondCover.add(participants[i]);
			height += 10;
			
		}
		
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
		
		mainCover.setBackground(new Color(150, 150, 150, 255));
		mainCover.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120, 255)));
		secondCover.setBackground(new Color(150, 150, 150, 255));
		secondCover.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120, 255)));
		
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
						result += (i*2 + 1) + "-" + " ".repeat(12) + " ".repeat(60);
					} else {
						result += (i*2 + 1) + "-" + raw[i][0] + " ".repeat(60);
					}
					
					
				} else {
					if (raw[i][1] == null) {
						result += (i*2 + 2) + "-" + " ".repeat(12);
					} else {
						result += (i*2 + 2) + "-" + raw[i][1];
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
		} else {
			register();
			accessButton.setText("unregister");
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
