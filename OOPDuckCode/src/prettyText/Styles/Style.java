package prettyText.Styles;

import java.util.Arrays;

import prettyText.AnsiValues;
import prettyText.MarginData;
import prettyText.TextMarginar;
import prettyText.TextMarginar.margins;

public class Style {
	
	// vars
	public String startText = "";
	public String bodyText = "";
	public String endText = "";
	
	public int lineLength;
	public TextMarginar marginar;
	
	// constructors
	public Style() {
		lineLength = 40;
		marginar = new TextMarginar(TextMarginar.margins.centered);
	}

	public Style(int length) {
		marginar = new TextMarginar(TextMarginar.margins.beginning);
		lineLength = length;
	}
	
	public Style(TextMarginar marginar) {
		this.marginar = marginar;
	}
	
	public Style(TextMarginar marginar, int length) {
		this.marginar = marginar;
		lineLength = length;
	}
	
	//-------------
	
	private String getCleanText(String text) {
		String updatedText = "";
		
		boolean flag = false;
		for (int i = 0; i < text.length(); i++) {
			if (flag == true) {
				if (text.charAt(i) == 'm') {
					flag = false;
				}
				continue;
			}
			
			if (text.charAt(i) == '\\') {
				flag = true;
			} else {
				updatedText = updatedText + text.charAt(i);
			}
		}
		
		return updatedText;
	}
	
	public void printStart() {
		System.out.println(startText);
	}
	
	public void printBody(String stuffing, int textLength) {
		MarginData marginData = marginar.retrieveMargin(lineLength);
		
		String editedText;
		int endsAt = 0;
		
		if (textLength + marginData.emptySpace > lineLength) {
			endsAt = lineLength - 1 - marginData.emptySpace - marginData.limit; // the -1 comes from "-"
			editedText = stuffing.substring(0, endsAt) + "-" ;
			
			printLine(marginData.emptySpace, editedText, marginData.limit);		
		
		} else {
			printLine(marginData.emptySpace, stuffing, lineLength - (textLength+marginData.emptySpace));
			return;
		}
		
		printBody(stuffing.substring(endsAt), textLength-(endsAt));
	}
	
	public void printBody(String stuffing, int textLength, String[] args) {
		MarginData marginData = marginar.retrieveMargin(lineLength);
		
		String editedText;
		int endsAt = 0;
		
		if (textLength + marginData.emptySpace > lineLength) {
			endsAt = lineLength - 1 - marginData.emptySpace - marginData.limit; // the -1 comes from "-"
			editedText = stuffing.substring(0, endsAt) + "-" ;
			
			printLine(marginData.emptySpace, editedText, marginData.limit, args);		
		
		} else {
			printLine(marginData.emptySpace, stuffing, lineLength - (textLength+marginData.emptySpace), args);
			return;
		}
		
		
		printBody(stuffing.substring(endsAt), textLength-(endsAt));
	}
	
	public void printTitle(String title) {
		MarginData marginData = marginar.retrieveMargin(lineLength);
		int realLength = title.replaceAll("\\u001b\\[[;\\d]*m", "").length();
		
		int emptyCount = marginData.emptySpace - 1;
		if (emptyCount <= 0) {
			emptyCount = 0;
		}
		
		System.out.print(bodyText + " ".repeat(emptyCount));
		System.out.print(AnsiValues.BOLD + title);
		System.out.println(AnsiValues.DEFAULT + " ".repeat(lineLength - (emptyCount+realLength)) + bodyText);
	}
	
	private void printLine(int startSpace, String stuffing, int endSpace) {
		System.out.print(bodyText + " ".repeat(startSpace)); // body start
		System.out.print(stuffing); // stuffing
		System.out.println(" ".repeat(endSpace) + bodyText); // body end
	}
	
	private void printLine(int startSpace, String stuffing, int endSpace, String[] args) {
		System.out.print(bodyText + " ".repeat(startSpace)); // body start
		System.out.printf(stuffing, args); // stuffing
		System.out.println(" ".repeat(endSpace) + bodyText); // body end
	}
	
	public void printEnd() {
		System.out.println(endText);
	}
	
}
