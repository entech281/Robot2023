package frc.robot.filters;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class TestNoRotationFilter {

	protected NoRotationFilter filter = new NoRotationFilter();
	@Test
	public void testEnabledStopsRotation() {
		filter.enable(true);
		DriveInput di = new DriveInput(2,2,2);
		DriveInput newDi = filter.filter(di);
		
		assertEquals( 0, newDi.getRotation(),0.01);
	}
	
	@Test
	public void testNotEnabledDoesntStopRotation() {
		filter.enable(false);
		DriveInput di = new DriveInput(2,2,2);
		DriveInput newDi = filter.filter(di);
		assertEquals( 2, newDi.getRotation(),0.01);
	}	
}
