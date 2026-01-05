package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	
	public EventPage(EventData event, Dimension size) {
		this.event = event;
		setPreferredSize(new Dimension(50, 50));
		setSize(size);
		setBackground(new Color(150, 150, 150, 255));
		setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120, 255)));
		setLayout(new AnchorLayout());
		setPage();
		updateButtonText();
	}
	
	private void setPage() {
		title = new JLabel(event.getName());
		description = new JLabel(" *" + event.getDescription());
		accessButton = new JButton("register");
		
		title.setPreferredSize(new Dimension(50, 10));
		description.setPreferredSize(new Dimension(50, 30));
		accessButton.setPreferredSize(new Dimension(20, 80));
		
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
		accessButton.setSize( (int) (getWidth()*0.2), (int) (getHeight()*0.15));
		
		accessButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					access();
				}
			}
		});
		
		add(title);
		add(description);
		add(accessButton);
	}
	
	public void updateEvent(EventData newEvent) {
		event = newEvent;
		title.setText(event.getName());
		description.setText(event.getDescription());
		
		title.setSize(title.getText().length()*32, 150);
		description.setSize(description.getText().length()*16, 150);
		updateButtonText();
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
	}

	private void unregister() {
		dataManager.removeParticiapnt(event, App.user);
	}
}
