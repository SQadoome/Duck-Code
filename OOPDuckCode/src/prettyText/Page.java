package prettyText;

import java.util.Arrays;

import prettyText.Styles.*;

public class Page {
	
	// vars
	public Style style;
	
	// constructors
	public Page() {
		setTyle(new FreeStyle());
	}
	
	public Page(Style style) {
		setTyle(style);
	}
	
	//--------
	public Style getStyle() {
		return style;
	}
	
	public void setTyle(Style newStyle) {
		style = newStyle;
	}
	//--------
	
	public void printParagraph(String[] lines) {
		if (style != null) {
			
			style.printStart();
			for (int i = 0; i < lines.length; i++) {
				style.printBody(lines[i], lines[i].length());
			}
			style.printEnd();
			
		} else {
			for (int i = 0; i < lines.length; i++) {
				System.out.printf(lines[i]);
			}
		}
	}
	
	
	public void printParagraph(String title, String[] lines) {
		if (style != null) {
			
			style.printStart();
			style.printTitle(title);
			for (int i = 0; i < lines.length; i++) {
				style.printBody(lines[i], lines[i].length());
			}
			style.printEnd();
			
		} else {
			for (int i = 0; i < lines.length; i++) {
				System.out.printf(lines[i]);
			}
		}
	}
	
	public void printParagraph(String[][] data) {
		if (style != null) {
			
			style.printStart();
			for (int i = 0; i < data.length; i++) {
				style.printBody(data[i][0], data[i][0].length(), Arrays.copyOfRange(data[i], 1, data[i].length));
			}
			style.printEnd();
			
		} else {
			for (int i = 0; i < data.length; i++) {
				System.out.printf(data[i][0]);
			}
		}
	}
	
	public void printParagraph(String title, String[][] data) {
		if (style != null) {
			
			style.printStart();
			style.printTitle(title);
			for (int i = 0; i < data.length; i++) {
				style.printBody(data[i][0], data[i][0].length(), Arrays.copyOfRange(data[i], 1, data[i].length - 1));
			}
			style.printEnd();
			
		} else {
			for (int i = 0; i < data.length; i++) {
				System.out.printf(data[i][0]);
			}
		}
	}
	
	public void printParagraphs(String[][] paragraphs) {
		if (style != null) {
			
			style.printStart();
			
			for (int index = 0; index < paragraphs.length; index++) {
				style.printTitle(paragraphs[index][0]);
				for (int line = 0; line < paragraphs[index].length; line++) {
					style.printBody(paragraphs[index][line], paragraphs[index][line].length());
				}
				style.printBody("", 0);
			}
			style.printEnd();
		}
		
	}
}
