package frc.robot.pose;

import java.util.Objects;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

/**
 *
 * @author aheitkamp
 */
public class TargetNode {

    public double absoluteNodeX;

    public static double A_ROW_X_METERS = Units.inchesToMeters(-25.5);
    public static double B_ROW_X_METERS = Units.inchesToMeters(-8.5);

    public static double LEFT_NODE_Y_OFSET_METERS = Units.inchesToMeters(-22);
    public static double MIDDLE_NODE_Y_OFSET_METERS = 0;
    public static double RIGHT_NODE_Y_OFSET_METERS = Units.inchesToMeters(22);
    public static double LOADING_STATION_OFFSET = Units.inchesToMeters(29.5);
    
    public static TargetNode A1 = new TargetNode(A_ROW_X_METERS,LEFT_NODE_Y_OFSET_METERS,NodeID.A1);
    public static TargetNode A2 = new TargetNode(A_ROW_X_METERS,MIDDLE_NODE_Y_OFSET_METERS,NodeID.A2);
    public static TargetNode A3 = new TargetNode(A_ROW_X_METERS,RIGHT_NODE_Y_OFSET_METERS,NodeID.A3);
    public static TargetNode B1 = new TargetNode(B_ROW_X_METERS,LEFT_NODE_Y_OFSET_METERS,NodeID.B1);
    public static TargetNode B2 = new TargetNode(B_ROW_X_METERS,MIDDLE_NODE_Y_OFSET_METERS,NodeID.B2);
    public static TargetNode B3 = new TargetNode(B_ROW_X_METERS,RIGHT_NODE_Y_OFSET_METERS,NodeID.B3);
    public static TargetNode LR = new TargetNode(0,-LOADING_STATION_OFFSET,NodeID.LR);
    public static TargetNode LL = new TargetNode(0,LOADING_STATION_OFFSET,NodeID.LL);
    
    
    public static TargetNode NONE = new TargetNode(0,0,NodeID.NONE);
    
    public static enum NodeID {
        A1,
        A2,
        A3,
        B1,
        B2,
        B3,
        LL,
        LR,
        NONE
    }    
    
    private final Translation2d offsetToTarget;
    private final NodeID id;

    protected TargetNode(double xOffsetMeters, double yOffsetMeters, NodeID ID) {
        offsetToTarget = new Translation2d(xOffsetMeters,yOffsetMeters);
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
    public boolean isCube() {
    	return id == NodeID.A2 || id == NodeID.B2;
    }
    public boolean isLoading() {
    	return id == NodeID.LL || id == NodeID.LR;
    }
    public boolean isCone() {
    	return (!isCube() && !isLoading());
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

    public double getXMeters(){
        return offsetToTarget.getX();
    }
    
    public double getYMeters(){
        return offsetToTarget.getY();
    }

}
