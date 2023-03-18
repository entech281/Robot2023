package frc.robot.pose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

public class TestLateralAlignCalculator {

	double TOLERANCE = 0.01;

	protected LateralAlignmentOffsetCalculator lac = new LateralAlignmentOffsetCalculator();
	
	@Test
	public void testLowerLeftCorner() {
		
		Pose2d bottmLeftCornerFacingBack = new Pose2d(0,0, Rotation2d.fromDegrees(180));
		
		LateralOffset lo = lac.getNearestScoringLocation(bottmLeftCornerFacingBack);
//		assertEquals(TargetNode.A1, lo.getNearestLocation());
		assertEquals(0.51, lo.getLateralOffsetToLocationMeters(), TOLERANCE);		
	}
	
	@Test
	public void testRightInFrontOfTag8GivesCenterLocation() {
		
		double TAG_8_X_IN = Units.inchesToMeters(40.45);
		double TAG_8_Y_IN = Units.inchesToMeters(42.19);
		Pose2d directlyInFrontOfTag8 = new Pose2d(TAG_8_X_IN,TAG_8_Y_IN, Rotation2d.fromDegrees(180));
		
		LateralOffset lo = lac.getNearestScoringLocation(directlyInFrontOfTag8);
//		assertEquals(TargetNode.A2, lo.getNearestLocation());
		assertEquals(0.0, lo.getLateralOffsetToLocationMeters(), TOLERANCE);		
	}

	@Test
	public void testToTheLeftOfTag3() {
		Pose2d toTheLeftOfTag3 = new Pose2d(15.29, 5, Rotation2d.fromDegrees(0));
		LateralOffset lo = lac.getNearestScoringLocation(toTheLeftOfTag3);
		System.out.print(lo.getNearestLocation().getSelectedNode().getNodeID());
		assertEquals(0.511302, lo.getLateralOffsetToLocationMeters(), TOLERANCE);
	}
}
