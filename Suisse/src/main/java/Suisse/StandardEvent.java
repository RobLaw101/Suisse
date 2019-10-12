package Suisse;
public class StandardEvent implements Event {
    private String id;
    private long timeStamp;
    private State state;
    
    public StandardEvent(String id, long timeStamp, String stateString) {
    	this.id = id;
    	this.timeStamp = timeStamp;
    	if (stateString.equals("STARTED")) {
    		state = State.STARTED;
    	} else {
    		state = State.FINISHED;
    	}
    }

	@Override
	public String getId() {
		return id;
	}

	@Override
	public long getTimeStamp() {
		return timeStamp;
	}
	
	@Override
	public State getState() {
		return state;
	}

	@Override
	public long calculateDuration(Event event) {
		long duration;
		long otherTimeStamp = event.getTimeStamp();			
		if ( this.timeStamp > otherTimeStamp ) {
			duration = timeStamp - otherTimeStamp;
		} else {
			duration = otherTimeStamp - timeStamp;
		}
		return duration;
	}	

	@Override
	public String toString() {
		return "event : " + id + " state : " + state;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Event event = (Event) obj;
		return this.id.equals(event.getId()) && this.getState() == event.getState();
	}

	@Override
	public int compareTo(Event e) {
		if (this.equals(e)) return 0;
		else if (this.getId().equals(e.getId())) return this.getState().compareTo(e.getState());
		else return this.getId().compareTo(e.getId());
	}
}
