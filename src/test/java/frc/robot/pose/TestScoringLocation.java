package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.RobotConstants.TEAM;
import frc.robot.pose.TargetNode.NodeID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

public class TestScoringLocation {
    
    @Test
    public void testPose2dMath() {
        //create tag and node position 
       Pose2d tag = new Pose2d(610.77, 42.19, Rotation2d.fromDegrees(180));
       Translation2d node = new Translation2d(25.5, -18.5);
       //calculate absolute node position
       Pose2d absoluteNodePosition = new Pose2d((tag.getTranslation().plus(node)), tag.getRotation());
       System.out.println("Absilute nod pos" + absoluteNodePosition);
       //testing against expected node position
       Pose2d expectedNodePosition = new Pose2d(636.27, 23.69, Rotation2d.fromDegrees(180));

        assertEquals(expectedNodePosition, absoluteNodePosition);
    }

    @Test
    public void testFirstAprilTag() {
        FieldAprilTag tag = new FieldAprilTag(610.77, 42.19, 180, 1, TEAM.RED);
        TargetNode node = new TargetNode(25.5, -18.5, NodeID.A3);
        //TargetNode B3;
        ScoringLocation testScoringLocation = new ScoringLocation(tag, node);
        Pose2d testAbsolutePose = testScoringLocation.computeAbsolutePose();
        System.out.println(testAbsolutePose.toString());
        Pose2d expectedNodePosition = new Pose2d(636.27, 23.69, Rotation2d.fromDegrees(180));

        assertEquals(expectedNodePosition, testAbsolutePose);
    }

}