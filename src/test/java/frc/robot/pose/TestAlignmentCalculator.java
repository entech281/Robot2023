package frc.robot.pose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class TestAlignmentCalculator {

	protected AlignmentCalculator calculator = new AlignmentCalculator();
	public static final double TOLERANCE_DEGREES=1.0;
	@Test
	public void testAlignmentCalculatorAngle() {
		
		//if the center of the robot is at the corner of the field
		Pose2d robotPose = new Pose2d(0,0,Rotation2d.fromDegrees(0));
		
		//and we want to score here:
		ScoringLocation sloc = new ScoringLocation(AprilTagLocation.BLUE_LEFT,TargetNode.A3);
		
		//then the angle we should turn to is
		int I_HAVE_NO_IDEA_THE_REAL_ANSWER = 0;
		//assertEquals(I_HAVE_NO_IDEA_THE_REAL_ANSWER,calculator.calculateAngleToScoringLocation(sloc, robotPose),TOLERANCE_DEGREES );
		
	}
}