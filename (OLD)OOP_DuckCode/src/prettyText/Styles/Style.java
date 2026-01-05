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
	
	// constructors
	public Style() {
		lineLength = 40;
	}

	public Style(int length) {
		lineLength = length;
	}
	
	//-------------
	
	public String getFilteredText(String stuffing) {
		String filtered = ""; // filtered from ansi values
		// useful for getting the "real" length of a string
		// the ansis i used all start with '\' and end with 'm'
		
		boolean flag = false;
		
		for (int i = 0; i < stuffing.length(); i++) {
			
			if (flag == true) {
				if (stuffing.charAt(i) == 'm') {
					flag = false;
				}
				continue; // as long as 'm' is not encountered this will continue
			}
			
			if (stuffing.charAt(i) - '0' == -21) { // -21 is the int value of '\' as direct comparison failed
				flag = true; // found an ansi
				continue;
			}
			
			filtered += stuffing.charAt(i);
		}
		return filtered;
	}
	
	public void printStart() {
		System.out.println(startText);
	}
	
	public void printEnd() {
		System.out.println(endText);
	}
	
	public void printBody(String[] lines) {
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].length() > lineLength - 1) {
				System.err.println("WARNING: text is longer than specified line length");
				return;
			}
			
			printLine(lines[i]);
		}
		
	}

	public void printLine(String text) {
		int realLength = getFilteredText(text).length();
		System.out.println(bodyText + " " + text + " ".repeat(lineLength - realLength - 1) + bodyText);
	}
	
}
