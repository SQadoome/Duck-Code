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
	
	public void printParagraph(String title, String[] lines) {
		style.printStart();
		style.printLine(AnsiValues.YELLOW + title + AnsiValues.DEFAULT);
		style.printBody(lines);
		style.printEnd();
	}
	
	public void printParagraph(String[] lines) {
		style.printStart();
		style.printBody(lines);
		style.printEnd();
	}
	
	public void printParagraphs(String[][] paragraphs) {
		style.printStart();
		
		for (int i = 0; i < paragraphs.length; i++) {
			
			style.printLine(AnsiValues.YELLOW + paragraphs[i][0] + AnsiValues.DEFAULT); // title
			style.printBody(Arrays.copyOfRange(paragraphs[i], 1, paragraphs[i].length)); // body (end is not enclusive)
			style.printLine("");
		}
		
		style.printEnd();
	}
	
}
