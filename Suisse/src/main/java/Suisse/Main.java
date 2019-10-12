package Suisse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

import org.apache.commons.math3.util.Pair;

public class Main {

	public static void main(String[] args) throws Exception {

		EventParser eventParser = new JsonEventParser();
		
		try {
			Path file = Paths.get(ClassLoader.getSystemResource("logfile.txt").toURI());
			eventParser.parse(file);
		} catch (IOException | URISyntaxException e) {
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
