package Suisse;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.TreeSet;

public interface EventParser {
	
	public static final String ID = "id";
	public static final String STATE = "state";
	public static final String TIMESTAMP = "timestamp";
	public static final String TYPE = "type";
	public static final String HOST = "host";
	
	public void parse(BufferedReader reader) throws IOException;
	
	public TreeSet <Event> getStandardEvents();
	
	public TreeSet <ApplicationEvent> getApplicationEvents();

}
