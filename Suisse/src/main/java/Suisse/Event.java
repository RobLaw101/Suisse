package Suisse;

public interface Event extends Comparable <Event> {
	enum State {
		STARTED, FINISHED
	}

	public String getId ();
	
	public long getTimeStamp ();
	
	public State getState();
	
	public long calculateDuration(Event event);
}