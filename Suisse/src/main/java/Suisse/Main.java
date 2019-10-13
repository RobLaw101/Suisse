package Suisse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TreeMap;

import org.apache.commons.math3.util.Pair;

public class Main {

	public static void main(String[] args) throws Exception {

		EventParser eventParser = new JsonEventParser();
		
		try {
			InputStream in = Main.class.getClassLoader().getResourceAsStream("logfile.txt"); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			eventParser.parse(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EventDatabase database = new HSQLDatabase();
		
		EventTransformerForDB <Event> eventTransformer = new EventTransformerForDB <Event>();		
		TreeMap <Event, Pair <Long, Boolean>> events = eventTransformer.transform(eventParser.getStandardEvents());
		database.addStandardEventToDatabase(events);
		
		EventTransformerForDB <ApplicationEvent> appEventTransformer = new EventTransformerForDB <ApplicationEvent>();
		TreeMap <ApplicationEvent, Pair <Long, Boolean>>  appplicationEvents = 
				appEventTransformer.transform(eventParser.getApplicationEvents());
		database.addApplicationEventToDatabase(appplicationEvents);
		
		database.outputAllEventsToConsole();
	}

}
