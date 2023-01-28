package frc.robot.targeting;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import frc.robot.targeting.NodePoses.NodeID;

/**
 * 
 *
 * @author aheitkamp
 */
public class TestNodePoses {
    
    @Test
    public void testGetNodePose() {
        NodePose npA = new NodePose(0, 0, 0, NodeID.B1);
        NodePose npB = NodePoses.getNodePose(NodeID.B1);

        assertEquals(npA, npB);
    }
}
