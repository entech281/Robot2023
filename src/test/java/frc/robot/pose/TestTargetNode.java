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
}
