package dataManipulators;

public class Data {

	private String raw;
	private String[] formated;
	private int atByte; // theoritically, a data file can have characters which exceed the integer limit, then this is cooked.
	// but it is escential for random access file when add/removing data.
	
	public Data() {
		
	}
	
	public Data(String raw) {
		setRaw(raw);
	}

	// ------
	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public String[] getFormated() {
		return formated;
	}

	public void setFormated(String[] formated) {
		this.formated = formated;
	}

	public int getAtByte() {
		return atByte;
	}

	public void setAtByte(int atByte) {
		if (atByte < 0) {
			System.err.println("Warning: a negative value position was attempted while saving adata.");
		} else {
			this.atByte = atByte;
		}
	}
	
	
	
}
