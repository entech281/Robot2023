package frc.robot.pose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

public class TestLateralAlignCalculator {

	protected LateralAlignCalculator lac = new LateralAlignCalculator();
	private double TOLERANCE = 0.01;
	
	@Test
	public void testLowerLeftCorner() {
		
		Pose2d bottmLeftCornerFacingBack = new Pose2d(0,0, Rotation2d.fromDegrees(180));
		
		LateralOffset lo = lac.findOffsetToNearestTarget(bottmLeftCornerFacingBack);
		ScoringLocation selectedLoc = lo.getNearestLocation();
		assertEquals(8, selectedLoc.getSelectedTag().getId());
		assertEquals(TargetNode.A1, selectedLoc.getSelectedNode().getNodeID());
		assertEquals(0.276, lo.getLateralOffsetToLocationMeters(),TOLERANCE);		
	}
	
	@Test
	public void testRightInFrontOfTag8GivesCenterLocation() {
		
		double TAG_8_X_IN = Units.inchesToMeters(40.45);
		double TAG_8_Y_IN = Units.inchesToMeters(42.19);
		Pose2d bottmLeftCornerFacingBack = new Pose2d(0,0, Rotation2d.fromDegrees(180));
		
		LateralOffset lo = lac.findOffsetToNearestTarget(bottmLeftCornerFacingBack);
		ScoringLocation selectedLoc = lo.getNearestLocation();
		assertEquals(8, selectedLoc.getSelectedTag().getId());
		assertEquals(TargetNode.A2, selectedLoc.getSelectedNode().getNodeID());
		assertEquals(0.0, lo.getLateralOffsetToLocationMeters(),TOLERANCE);		
	}	
	
}
