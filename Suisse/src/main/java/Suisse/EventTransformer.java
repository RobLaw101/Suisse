package Suisse;

import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.math3.util.Pair;

public interface EventTransformer <T extends Event> {
	public TreeMap <T, Pair<Long, Boolean>> transform (TreeSet <T> events);
}
