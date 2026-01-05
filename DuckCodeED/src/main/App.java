package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import dataManipulators.DataManager;
import dataManipulators.EventData;

import layouts.AnchorLayout;

public class App extends JFrame {
	
	public static String user;
	
	private int pageIndex = 0;
	private EventPage page;
	private final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private DataManager dataManager = new DataManager();
	private EventData[] events;
	
	private JButton switchLeft;
	private JButton switchRight;
	
	public App(String user) {
		this.user = user;
		events = dataManager.getEvents();
		setLayout(new AnchorLayout());
		setSize((int) (SCREEN_SIZE.width*0.6), (int) (SCREEN_SIZE.height*0.7));
		setLayout(new AnchorLayout());
		setBackground(new Color(10, 10, 10, 255));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBox();
	}
	
	public void exit() {
		dispose();
		Main.beginAccess();
	}
	
	private void setBox() {
		page = new EventPage(
				dataManager.getEventData(events[0].getName()),
				new Dimension( (int) (getWidth()*0.6), (int) (getHeight()*0.5)));
		add(page);
		
		switchRight = new JButton("-->");
		switchLeft = new JButton("<--");
		
		switchRight.setSize( (int) (getWidth()*0.15), (int) (getHeight()*.05));
		switchLeft.setSize( (int) (getWidth()*0.15), (int) (getHeight()*.05));
		
		switchRight.setPreferredSize(new Dimension(70, 80));
		switchLeft.setPreferredSize(new Dimension(30, 80));
		
		switchLeft.setVisible(false);
		
		switchRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					switchPage(1);
				}
			}
		});
		
		switchLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					switchPage(-1);
				}
			}
		});
		
		JButton signout = new JButton("signout");
		signout.setFont(new Font(
				signout.getFont().getName(),
				Font.BOLD,
				16));
		signout.setForeground(new Color(255, 0, 0, 255));
		signout.setPreferredSize(new Dimension(50, 80));
		signout.setSize( (int) (getWidth()*0.15), (int) (getHeight()*0.05));
		
		signout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				exit();
			}
		});
		
		add(signout);
		add(switchRight);
		add(switchLeft);
	}
	
	private void switchPage(int next) {
		pageIndex += next;
		
		if (pageIndex == 0) {
			switchLeft.setVisible(false);
		} else {
			switchLeft.setVisible(true);
		}
		
		if (pageIndex == events.length - 1) {
			switchRight.setVisible(false);
		} else {
			switchRight.setVisible(true);
		}
		
		page.updateEvent(dataManager.getEventData(events[pageIndex].getName()));
	}
	
}
