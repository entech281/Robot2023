package frc.robot.pose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

public class TestLateralAlignCalculator {

	protected LateralAlignCalculator lac = new LateralAlignCalculator(0, null); //I don't know what to set the nodeID to
	private double TOLERANCE = 0.01;
	
	@Test
	public void testLowerLeftCorner() {
		
		Pose2d bottmLeftCornerFacingBack = new Pose2d(0,0, Rotation2d.fromDegrees(180));
		
		double TAG_8_X_IN = Units.inchesToMeters(40.45);
		double TAG_8_Y_IN = Units.inchesToMeters(42.19);
		
		LateralAlignCalculator lo = lac.getLateralOffsetToNodeAndNearestNode(bottmLeftCornerFacingBack);
		assertEquals(TargetNode.A2, lo.getNearestNode(bottmLeftCornerFacingBack));
		assertEquals(0.0, lo.getLateralOffsetToNodeAndNearestNode(bottmLeftCornerFacingBack));		
	}
	
	@Test
	public void testRightInFrontOfTag8GivesCenterLocation() {
		
		double TAG_8_X_IN = Units.inchesToMeters(40.45);
		double TAG_8_Y_IN = Units.inchesToMeters(42.19);
		Pose2d directlyInFrontOfTag8 = new Pose2d(TAG_8_X_IN,TAG_8_Y_IN, Rotation2d.fromDegrees(180));
		
		LateralAlignCalculator lo = lac.getLateralOffsetToNodeAndNearestNode(directlyInFrontOfTag8);
		assertEquals(TargetNode.A2, lo.getNearestNode(directlyInFrontOfTag8));
		assertEquals(0.0, lo.getLateralOffsetToNodeAndNearestNode(directlyInFrontOfTag8));		
	}	
	
}
