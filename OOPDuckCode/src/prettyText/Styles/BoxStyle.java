package prettyText.Styles;

import prettyText.TextMarginar;

public class BoxStyle extends Style {
	
	public BoxStyle() {
		super();
		startText = "-".repeat(lineLength + 2);
		bodyText = "|";
		endText = "-".repeat(lineLength + 2);
	}
	
	public BoxStyle(int length) {
		super(length);
		startText = "-".repeat(lineLength + 2);
		bodyText = "|";
		endText = "-".repeat(lineLength + 2);
		
	}
	
	public BoxStyle(TextMarginar marginar) {
		super(marginar);
	}
	
	public BoxStyle(TextMarginar marginar, int length) {
		super(marginar, length);
		startText = "-".repeat(length + 2);
		bodyText = "|";
		endText = "-".repeat(length + 2);
	}
	
}
