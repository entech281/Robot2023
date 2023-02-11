package frc.robot.pose;

import edu.wpi.first.math.geometry.Translation2d;
import java.util.Objects;

/**
 *
 * @author aheitkamp
 */
public class TargetNode {

    public double absoluteNodeX;

    public static double A_ROW_X_IN = 8.5;
    public static double B_ROW_X_IN = 25.5;

    public static int LEFT_NODE_Y_OFSET_IN = 22;
    public static int MIDDLE_NODE_Y_OFSET_IN = 0;
    public static int RIGHT_NODE_Y_OFSET_IN = 22;
    
    public static TargetNode A1 = new TargetNode(LEFT_NODE_Y_OFSET_IN,A_ROW_X_IN,NodeID.A1);
    public static TargetNode A2 = new TargetNode(MIDDLE_NODE_Y_OFSET_IN,A_ROW_X_IN,NodeID.A2);
    public static TargetNode A3 = new TargetNode(RIGHT_NODE_Y_OFSET_IN,A_ROW_X_IN,NodeID.A3);
    public static TargetNode B1 = new TargetNode(LEFT_NODE_Y_OFSET_IN,B_ROW_X_IN,NodeID.B1);
    public static TargetNode B2 = new TargetNode(MIDDLE_NODE_Y_OFSET_IN,B_ROW_X_IN,NodeID.B2);
    public static TargetNode B3 = new TargetNode(RIGHT_NODE_Y_OFSET_IN,B_ROW_X_IN,NodeID.B3);
    public static TargetNode NONE = new TargetNode(0,0,NodeID.NONE);
    
    public static enum NodeID {
        A1,
        A2,
        A3,
        B1,
        B2,
        B3,
        NONE
    }    
    
    private final Translation2d offsetToTarget;
    private final NodeID id;

    protected TargetNode(double horizontalOffsetInches, double distanceOffsetInches, NodeID ID) {
        offsetToTarget = new Translation2d(horizontalOffsetInches,distanceOffsetInches);
        id = ID;
    }

    public NodeID getNodeID() { 
        return id; 
    }    
    
    public Translation2d getOffsetToTarget() {
        return offsetToTarget; 
    }


    public boolean isThirdRow(){
        return (id == NodeID.A1 || id == NodeID.A2 || id == NodeID.A3);
    }
    public boolean is2ndRow(){
        return (id == NodeID.B1 || id == NodeID.B2 || id == NodeID.B3);
    }    
    public boolean isTargetSelected(){
        return id != NodeID.NONE;
    }
    public boolean noTargetSelected(){
        return id == NodeID.NONE;
    }
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TargetNode)) {
            return false;
        }
        TargetNode nodePose = (TargetNode) o;
        return Objects.equals(offsetToTarget, nodePose.offsetToTarget) && Objects.equals(id, nodePose.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offsetToTarget, id);
    }    

    public double getXIn(){
        return offsetToTarget.getX();
    }
    
    public double getYIn(){
        return offsetToTarget.getY();
    }

}
