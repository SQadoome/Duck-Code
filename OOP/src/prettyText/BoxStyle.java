package prettyText;

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
	
}
