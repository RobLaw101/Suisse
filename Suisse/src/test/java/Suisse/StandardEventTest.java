package Suisse;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StandardEventTest {
	StandardEvent event1 = null;
	StandardEvent event2 = null;
	
	@Before
	public void before () {
		event1 = new StandardEvent("foo", 7, "FINISHED");
		event2 = new StandardEvent("bar", 4, "STARTED");
	}

	@Test
	public void testCalcDuration() {
		
		long duration = event1.calculateDuration(event2);
		
		assertEquals(3, duration);
	}
	
	@Test
	public void testInEquality () {
		
		assertFalse (event1.equals(event2));
		
	}
	
	@Test
	public void testEquality () {
		
		StandardEvent event3 = new StandardEvent("foo", 7, "STARTED");
		
		assertFalse(event1.equals(event3));
		
	}

}
