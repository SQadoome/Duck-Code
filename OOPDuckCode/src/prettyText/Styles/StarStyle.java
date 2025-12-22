package prettyText.Styles;

import prettyText.TextMarginar;

public class StarStyle extends Style{
	
	public StarStyle() {
		super();
		startText = "*-*".repeat((lineLength + 2)/3);
		bodyText = "*";
		endText = "*-*".repeat((lineLength + 2)/3);
	}
	
	public StarStyle(int length) {
		super(length);
		startText = "*-*".repeat((length + 2)/3);
		bodyText = "*";
		endText = "*-*".repeat((length + 2)/3);
		
	}
	
	public StarStyle(TextMarginar marginar) {
		super(marginar);
	}
	
	public StarStyle(TextMarginar marginar, int length) {
		super(marginar, length);
		startText = "*-*".repeat((length + 2)/3);
		bodyText = "*";
		endText = "*-*".repeat((length + 2)/3);
	}
	
}
