package frc.robot.targeting;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import frc.robot.targeting.NodePoses.NodeID;


/**
 * 
 *
 * @author aheitkamp
 */
public class TestNodePose {
    
    @Test
    public void testGetNodeId() {
        NodePose np = new NodePose(0, 0, 0, NodeID.A1);

        assertEquals(np.getNodeID(), NodeID.A1);
    }

    @Test
    public void testGetPoseToTag() {
        NodePose np = new NodePose(0, 0, 0, NodeID.A2);

        assertEquals(np.getPoseToTag(), new Pose2d(0, 0, new Rotation2d(0)));
    }
}
