package frc.robot.pose;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author aheitkamp
 */
public class TestTargetNode {
    
    @Test
    public void testGetNodeId() {
        TargetNode np = TargetNode.A1;

        assertEquals(np.getNodeID(), TargetNode.NodeID.A1);
    }

    // @Test
    // public void testGetPoseToTag() {
    //     TargetNode np = TargetNode.A2;

    //     assertEquals(np.getPoseToTag(), new Pose2d(22, 8.5, new Rotation2d(0)));
    // }    
    
}
