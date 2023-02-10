package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.RobotConstants.TEAM;
import frc.robot.pose.TargetNode.NodeID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

public class TestScoringLocation {
    
    @Test
    public void testFirstAprilTag() {
        FieldAprilTag tag = new FieldAprilTag(610.77, 42.19, 180, 1, TEAM.RED);
        TargetNode node = new TargetNode(-18.5, -25.5, 0, NodeID.A3);
        ScoringLocation testScoringLocation = new ScoringLocation(tag, node);
        Pose2d testAbsolutePose = testScoringLocation.computeAbsolutePose();
        System.out.println(testAbsolutePose.toString());

        assertTrue(false);
        //assertEquals(np.getNodeID(), TargetNode.NodeID.A1);
    }

}