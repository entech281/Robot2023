package frc.robot.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestForwardSpeedLimitFilter {
	
	public static final double SMALL_DIFFERENCE = 0.001;
	
	@Test
	public void testNoLimitByDeault() {
		ForwardSpeedLimitFilter f = new ForwardSpeedLimitFilter();
		double ONE = 1.0;
		DriveInput in = new DriveInput(ONE,ONE,ONE,ONE);
		
		DriveInput filtered = f.filter(in);
		assertEquals(ONE, filtered.getForward(),SMALL_DIFFERENCE);
		assertEquals(ONE, filtered.getRight(),SMALL_DIFFERENCE);
		assertEquals(ONE, filtered.getRotation(),SMALL_DIFFERENCE);
		
	}
	
	@Test
	public void testForwardSpeedIsLimitedAndBadwardIsNot() {
		ForwardSpeedLimitFilter f = new ForwardSpeedLimitFilter();
		double ONE = 1.0;
		double HALFSPEED = 0.5;
		DriveInput in = new DriveInput(ONE,ONE,ONE,ONE);
		f.setMaxSpeedPercent(HALFSPEED);
		
		DriveInput filtered = f.filter(in);
		assertEquals(HALFSPEED, filtered.getForward(),SMALL_DIFFERENCE);
		assertEquals(ONE, filtered.getRight(),SMALL_DIFFERENCE);
		assertEquals(ONE, filtered.getRotation(),SMALL_DIFFERENCE);
		
		in = new DriveInput(-ONE,ONE,ONE,ONE);
		f.setMaxSpeedPercent(HALFSPEED);
		
		filtered = f.filter(in);
		assertEquals(-ONE, filtered.getForward(),SMALL_DIFFERENCE);
		assertEquals(ONE, filtered.getRight(),SMALL_DIFFERENCE);
		assertEquals(ONE, filtered.getRotation(),SMALL_DIFFERENCE);		
		
	}	
}
