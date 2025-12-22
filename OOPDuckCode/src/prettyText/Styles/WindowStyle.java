package prettyText.Styles;

import prettyText.TextMarginar;

public class WindowStyle extends Style{

	public WindowStyle() {
		super();
		startText = "-".repeat(lineLength + 2);
		bodyText = "";
		endText = "-".repeat(lineLength + 2);
	}
	
	public WindowStyle(int length) {
		super(length);
		startText = "-".repeat(length + 2);
		bodyText = "";
		endText = "-".repeat(length + 2);
		
	}
	
	public WindowStyle(TextMarginar marginar) {
		super(marginar);
	}
	
	public WindowStyle(TextMarginar marginar, int length) {
		super(marginar, length);
		startText = "-".repeat(length + 2);
		bodyText = "";
		endText = "-".repeat(length + 2);
	}
	
}
