package frc.robot.pose;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public class TestScoringLocation {
    
    @Test
    public void testPose2dMath() {
        //create tag and node position 
       Pose2d tag = new Pose2d(Units.inchesToMeters(610.77), Units.inchesToMeters(42.19), Rotation2d.fromDegrees(180));
       Translation2d node = new Translation2d(Units.inchesToMeters(25.5), Units.inchesToMeters(-18.5));
       //calculate absolute node position
       Pose2d absoluteNodePosition = new Pose2d((tag.getTranslation().plus(node)), tag.getRotation());
       //testing against expected node position
       Pose2d expectedNodePosition = new Pose2d(Units.inchesToMeters(636.27), Units.inchesToMeters(23.69), Rotation2d.fromDegrees(180));

        assertEquals(expectedNodePosition, absoluteNodePosition);
    }

    @Test
    public void testRedMiddleAprilTag() {
    	AprilTagLocation tag = AprilTagLocation.RED_MIDDLE;
        TargetNode node = TargetNode.A2;
        ScoringLocation testScoringLocation = new ScoringLocation(tag, node);
        Pose2d testAbsolutePose = testScoringLocation.computeAbsolutePose();
        Pose2d expectedNodePosition = new Pose2d(Units.inchesToMeters(636.27), Units.inchesToMeters(108.19), Rotation2d.fromDegrees(180));
        //System.out.println(expectedNodePosition);
        //System.out.println(testAbsolutePose);

        assertEquals(expectedNodePosition, testAbsolutePose);
    }

    @Test
    public void testLeftblueTagA2() {
        
    	AprilTagLocation tag = AprilTagLocation.findFromTag(8);
        TargetNode node = TargetNode.A2;
        ScoringLocation testScoringLocation = new ScoringLocation(tag, node);
        Pose2d testAbsolutePose = testScoringLocation.computeAbsolutePose();
        Pose2d expectedNodePosition = new Pose2d(Units.inchesToMeters(14.95), Units.inchesToMeters(42.19), Rotation2d.fromDegrees(0));
        assertEquals(expectedNodePosition, testAbsolutePose);
    }

    @Test
    public void testLeftblueTagB3() {
        
    	AprilTagLocation tag = AprilTagLocation.findFromTag(8);
        TargetNode node = TargetNode.B3;
        ScoringLocation testScoringLocation = new ScoringLocation(tag, node);
        Pose2d testAbsolutePose = testScoringLocation.computeAbsolutePose();
        Pose2d expectedNodePosition = new Pose2d(Units.inchesToMeters(31.95), Units.inchesToMeters(64.19), Rotation2d.fromDegrees(0));
        assertEquals(expectedNodePosition, testAbsolutePose);
    }
    
    
}