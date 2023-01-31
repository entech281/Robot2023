package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import java.util.Objects;

/**
 *
 * @author aheitkamp
 */
public class TargetNode {
    
    public static TargetNode A1 = new TargetNode(0,0,0,NodeID.A1);
    public static TargetNode A2 = new TargetNode(0,0,0,NodeID.A2);
    public static TargetNode A3 = new TargetNode(0,0,0,NodeID.A3);
    public static TargetNode B1 = new TargetNode(0,0,0,NodeID.B1);
    public static TargetNode B2 = new TargetNode(0,0,0,NodeID.B2);
    public static TargetNode B3 = new TargetNode(0,0,0,NodeID.B3);
    public static TargetNode NONE = new TargetNode(0,0,0,NodeID.NONE);
    
    public static enum NodeID {
        A1,
        A2,
        A3,
        B1,
        B2,
        B3,
        NONE
    }    
    
    private final Pose2d poseToTag;
    private final NodeID id;

    protected TargetNode(double horizontalOffsetInches, double verticalOffsetInches, double Radians, NodeID ID) {
        poseToTag = new Pose2d(horizontalOffsetInches, verticalOffsetInches, new Rotation2d(Radians));
        id = ID;
    }

    public NodeID getNodeID() { 
        return id; 
    }    
    
    public Pose2d getPoseToTag() {
        return poseToTag; 
    }

    public double getHorizontalOffsetInches() {
        return poseToTag.getX(); 
    }

    public double getVerticalOffsetInches() {
        return poseToTag.getY(); 
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TargetNode)) {
            return false;
        }
        TargetNode nodePose = (TargetNode) o;
        return Objects.equals(poseToTag, nodePose.poseToTag) && Objects.equals(id, nodePose.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poseToTag, id);
    }    
    
}
