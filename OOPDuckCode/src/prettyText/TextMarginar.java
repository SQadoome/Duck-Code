package prettyText;

public class TextMarginar {
	
	public static enum margins {
		beginning,
		centered,
		end
	}
	
	private margins margin;
	private int marginValue = 0;
	
	public TextMarginar() {
		setMarginType(margins.beginning);
		
	}
	
	public TextMarginar(margins margin) {
		setMarginType(margin);
	}
	
	public TextMarginar(margins margin, int marginValue) {
		setMarginType(margin);
		setMarginValue(marginValue);
	}
	
	public void setMarginValue(int marginValue) {
		if (marginValue < 0) {
			System.err.println("ERROR: cannot set margins to negative values.");
			return;
		} else {
			this.marginValue = marginValue;
		}
		
	}
	public void setMarginType(margins margin) {
		this.margin = margin;
	}
	
	public MarginData retrieveMargin(String text, int maxLength) {
		MarginData data = new MarginData(); // space and limit/boundary
		
		switch(margin) {
			case beginning:
				data.emptySpace = marginValue;
				data.limit = maxLength - (data.emptySpace + text.length());
				break;
			case centered:
				data.emptySpace = (maxLength)/10 + marginValue;
				data.limit = data.emptySpace; // before limiting emptySpace to atleast 1
				if (data.emptySpace == 0) {
					data.emptySpace = 1;
				}
				break;
			case end:
				break;
			default:
				break;	
				
		}
		
		return data;
	}
	
}
