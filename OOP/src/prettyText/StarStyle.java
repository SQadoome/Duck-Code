package prettyText;

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
	
}
