package frc.robot.targeting;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import java.util.Objects;

import frc.robot.targeting.NodePoses.NodeID;

/**
 * 
 *
 * @author aheitkamp
 */
public class NodePose {
    private final Pose2d poseToTag;
    private final NodeID id;

    public NodePose(double horizontalOffsetInches, double verticalOffsetInches, double Radians, NodeID ID) {
        poseToTag = new Pose2d(horizontalOffsetInches, verticalOffsetInches, new Rotation2d(Radians));
        id = ID;
    }

    public NodeID getNodeID() { return id; }

    public Pose2d getPoseToTag() { return poseToTag; }

    public double getHorizontalOffsetInches() { return poseToTag.getX(); }

    public double getVerticalOffsetInches() { return poseToTag.getY(); }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NodePose)) {
            return false;
        }
        NodePose nodePose = (NodePose) o;
        return Objects.equals(poseToTag, nodePose.poseToTag) && Objects.equals(id, nodePose.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poseToTag, id);
    }

}
