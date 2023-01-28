package frc.robot.targeting;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.targeting.NodePoses.NodeID;

/**
 * 
 *
 * @author aheitkamp
 */
public class NodePose {
    private final Pose2d poseToTag;
    private final NodeID id;

    public NodePose(double x, double y, double zRadians, NodeID ID) {
        poseToTag = new Pose2d(x, y, new Rotation2d(zRadians));
        id = ID;
    }

    public NodeID getNodeID() { return id; }

    public Pose2d getPoseToTag() { return poseToTag; }


}
