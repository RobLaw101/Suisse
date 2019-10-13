package Suisse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonEventParser implements EventParser {
	private static final Logger logger = LoggerFactory.getLogger(JsonEventParser.class);
	//TreeSet which sorts on each insertion
	private final TreeSet <Event> standardEvents = new TreeSet <Event> ();
	private final TreeSet <ApplicationEvent> applicationEvents = new TreeSet <ApplicationEvent> ();

	private ApplicationEvent parseApplicationEvent(JSONObject jsonEvent) {
		Event event = parseStandardEvent(jsonEvent);
		String type = (String) jsonEvent.get(TYPE);
		String host = (String) jsonEvent.get(HOST);
		
		return new ApplicationEvent (event, type, host);		
	}

	private Event parseStandardEvent(JSONObject jsonEvent) {
		String id = (String) jsonEvent.get(ID);
		long timeStamp = (Long) jsonEvent.get(TIMESTAMP);	
		
		return new StandardEvent(id, timeStamp, ((String) jsonEvent.get(STATE)));
	}

	@Override
	public void parse(BufferedReader eventReader) throws IOException {
		Stream<String> lines = eventReader.lines();
		JSONParser eventParser = new JSONParser();
		lines.forEach(line -> {
			try {
				JSONObject jsonEvent = (JSONObject) eventParser.parse(line);
				if (jsonEvent.get(TYPE) == null) {
					logger.info("standard event found");
					standardEvents.add( parseStandardEvent(jsonEvent) );
				} else {
					logger.info("application event found");
					applicationEvents.add( parseApplicationEvent (jsonEvent) );
				}
			} catch (ParseException e) {
				lines.close();
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public TreeSet <Event> getStandardEvents () {
		
		return standardEvents;
	}
	
	@Override
	public TreeSet <ApplicationEvent> getApplicationEvents () {
		
		return applicationEvents;
	}
}
