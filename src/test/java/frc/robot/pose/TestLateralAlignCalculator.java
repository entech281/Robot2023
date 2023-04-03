package frc.robot.pose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.util.Units;
import frc.robot.RobotConstants;

public class TestLateralAlignCalculator {

	protected LateralAlignCalculator lac = new LateralAlignCalculator();
	
	@Test
	public void testLowerLeftCorner() {
		LateralOffset lo = lac.findOffsetToNearestTarget(0,0);
		ScoringLocation selectedLoc = lo.getNearestLocation();
		assertEquals(8, selectedLoc.getSelectedTag().getId());
		assertEquals(TargetNode.A1.getNodeID(), selectedLoc.getSelectedNode().getNodeID());
		assertEquals(0.512, lo.getNearestLocation().computeAbsolutePose().getY(),RobotConstants.TEST.TOLERANCE_DISTANCE);
		assertEquals(0.512, lo.getLateralOffsetToLocationMeters(),RobotConstants.TEST.TOLERANCE_DISTANCE);
	}
	
	@Test
	public void testRightInFrontOfTag8GivesCenterLocation() {
		
		double TAG_8_X_IN = Units.inchesToMeters(40.45);
		double TAG_8_Y_IN = Units.inchesToMeters(42.19);		
		
		LateralOffset lo = lac.findOffsetToNearestTarget(TAG_8_X_IN,TAG_8_Y_IN);
		ScoringLocation selectedLoc = lo.getNearestLocation();
		assertEquals(8, selectedLoc.getSelectedTag().getId());
		assertEquals(TargetNode.A2.getNodeID(), selectedLoc.getSelectedNode().getNodeID());
		assertEquals(0.0, lo.getLateralOffsetToLocationMeters(),RobotConstants.TEST.TOLERANCE_DISTANCE);		
	}	
	
}
