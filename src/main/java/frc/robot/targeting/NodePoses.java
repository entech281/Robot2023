package frc.robot.targeting;

/**
 * 
 *
 * @author aheitkamp
 */
public final class NodePoses {
    public static enum NodeID {
        A1,
        A2,
        A3,
        B1,
        B2,
        B3
    }

    public static NodePose getNodePose(NodeID nodeID) {
        switch (nodeID) {
            case A1: return new NodePose(0, 0, 0, NodeID.A1);
            case A2: return new NodePose(0, 0, 0, NodeID.A2);
            case A3: return new NodePose(0, 0, 0, NodeID.A3);
            case B1: return new NodePose(0, 0, 0, NodeID.B1);
            case B2: return new NodePose(0, 0, 0, NodeID.B2);
            case B3: return new NodePose(0, 0, 0, NodeID.B3);
            default: return null;
        }
    }
}
