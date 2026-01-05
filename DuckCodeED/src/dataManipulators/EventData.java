package dataManipulators;

public class EventData {
	
	private String name;
	private String description;
	private String[] participants;
	
	private boolean empty = true;
	
	public EventData() {
		name = ""; // avoid null
		description = "";
		participants = new String[] {""};
	}
	
	@Override
	public String toString() {
		
		return getName() + " | " + (getDescription());
	}
	
	// ----------
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		empty = false;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		empty = false;
	}
	public String[] getParticipants() {
		return participants;
	}
	public void setParticipants(String[] participants) {
		this.participants = participants;
		empty = false;
	}
	
	// -------
	
	public boolean isEmpty() {
		return empty;
	}
}
