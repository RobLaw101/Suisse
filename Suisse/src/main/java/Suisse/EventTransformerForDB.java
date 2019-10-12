package Suisse;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.math3.util.Pair;

public class EventTransformerForDB <T extends Event> implements EventTransformer <T> {

	@Override
	public TreeMap <T, Pair<Long, Boolean>> transform(TreeSet <T> events) {
		TreeMap <T, Pair<Long, Boolean>> eventMapToDuration = new TreeMap <T, Pair <Long, Boolean>> ();
		Iterator <T> it = events.iterator();
		//As tree set is sorted so we call next() twice to move forward by 2 each time
		while (it.hasNext()) {
			T e1 = it.next();
			T e2 = it.next();
			long duration = e1.calculateDuration(e2);
			//we have the duration no need to store second event with same id in the map
			eventMapToDuration.put( e1, new Pair <Long, Boolean> (duration, duration > 4) );
		}
		
		return eventMapToDuration;
	}

}
