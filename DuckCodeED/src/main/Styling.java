package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;

public class Styling {
	
	public static final Color LIGHT_GREEN = new Color(90, 255, 0, 255);
	public static final Color DARK_GREEN = new Color(0, 99, 18, 255);
	public static final Color GRAY = new Color(20, 20, 20, 255);	
	
	
	public static JPanel generateBackground(Component parent) {
		final JPanel BACKGROUND = new JPanel();
		BACKGROUND.setSize(parent.getWidth(), parent.getHeight());
		BACKGROUND.setPreferredSize(new Dimension(50, 50));
		BACKGROUND.setBackground(new Color(20, 20, 20, 255));
		return BACKGROUND;
	}
	
	public static void styleComponent(JPanel component) {
		component.setForeground(LIGHT_GREEN);
		component.setFont(new Font(
				component.getFont().getName(),
				Font.ITALIC,
				14));
		component.setBackground(GRAY);
		component.setBorder(BorderFactory.createLineBorder(LIGHT_GREEN, 3));
		
	}
	
	public static void styleComponent(JButton component) {
		component.setForeground(LIGHT_GREEN);
		component.setFont(new Font(
				component.getFont().getName(),
				Font.ITALIC,
				16));
		component.setBackground(GRAY);
		component.setBorder(BorderFactory.createLineBorder(LIGHT_GREEN, 3));
		
	}
	
	public static void styleComponent(JLabel component) {	
		component.setForeground(LIGHT_GREEN);
		component.setFont(new Font(
			component.getFont().getName(),
			Font.BOLD,
			14));
	}
	
	public static void styleComponent(JLabel component, int fontSize) {	
		component.setForeground(LIGHT_GREEN);
		component.setFont(new Font(
			component.getFont().getName(),
			Font.BOLD,
			fontSize));
	}
	
	public static void styleComponent(JTextField component) {	
		component.setForeground(LIGHT_GREEN);
		component.setFont(new Font(
			component.getFont().getName(),
			Font.BOLD,
			14));
		component.setBorder(BorderFactory.createLineBorder(LIGHT_GREEN, 3));
		component.setBackground(GRAY);
	}
	
	public static void styleComponent(JPasswordField component) {	
		component.setForeground(LIGHT_GREEN);
		component.setFont(new Font(
				component.getFont().getName(),
			Font.BOLD,
			14));
		component.setBorder(BorderFactory.createLineBorder(LIGHT_GREEN, 3));
		component.setBackground(GRAY);
	}
	
}
