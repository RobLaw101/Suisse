package Suisse;

public class ApplicationEvent implements Event {
	private Event standardEvent;
	private String type;
	private String host;
	
	public ApplicationEvent (Event event, String type, String host) {
	        this.standardEvent = event;
	        this.type = type;
	        this.host = host;
	}

	@Override
	public String getId() {
		return standardEvent.getId();
	}

	@Override
	public long getTimeStamp() {
		return standardEvent.getTimeStamp();
	}
	
	@Override
	public State getState() {
		return this.standardEvent.getState();
	}
	
	@Override
	public String toString() {
		return "" + standardEvent + " " + type;
	}

	@Override
	public long calculateDuration(Event event) {
		return this.standardEvent.calculateDuration(event);
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.standardEvent.equals(obj);
	}

	@Override
	public int compareTo(Event e) {
		return this.standardEvent.compareTo(e);
	}
	
	public String getType() {
		return type;
	}
	
	public String getHost () {
		return host;
	}
}
