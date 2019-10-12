package Suisse;

import java.util.Map;

import org.apache.commons.math3.util.Pair;

public interface EventDatabase {
	
	public void addStandardEventToDatabase(Map <Event, Pair <Long, Boolean>> events) throws Exception;
	
	public void addApplicationEventToDatabase(Map <ApplicationEvent, Pair <Long, Boolean>> events) throws Exception;

	void outputAllEventsToConsole() throws Exception;

}
