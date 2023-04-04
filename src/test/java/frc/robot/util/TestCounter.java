package frc.robot.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class TestCounter {

	
	@Test
	public void testCounterCountsUp() {
		Counter c = new Counter(3);
		assertFalse ( c.atTarget());
		c.up();
		c.up();
		c.up();
		assertTrue ( c.atTarget());
		c.up();
		assertTrue ( c.atOrAboveTarget());
		
	}
}
