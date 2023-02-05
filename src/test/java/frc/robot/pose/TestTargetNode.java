package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
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

    @Test
    public void testGetPoseToTag() {
        TargetNode np = TargetNode.A2;

        assertEquals(np.getOffsetToTarget(), new Translation2d(0, 0));
    }    
    
}
