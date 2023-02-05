package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;

/**
 *
 * @author dcowden
 */
public class ScoringLocation {

    public ScoringLocation ( FieldAprilTag tag, TargetNode node){
        this.selectedNode = node;
        this.selectedTag = tag;
    }
    
    /**
     * these methods are delegated to node.
     * @return 
     */
    public boolean is2ndRow(){
        return selectedNode.is2ndRow();
    }
    
    public boolean isThirdRow(){
        return selectedNode.isThirdRow();
    }
    
    public boolean isTargetSelected(){
        return selectedNode.isTargetSelected();
    }
    public boolean noTargetSelected(){
        return selectedNode.noTargetSelected();
    }
    public FieldAprilTag getSelectedTag() {
        return selectedTag;
    }

    public TargetNode getSelectedNode() {
        return selectedNode;
    }
    
    private final FieldAprilTag selectedTag;
    private final TargetNode selectedNode;

    public  Pose2d computeAbsolutePose( ){
        Transform2d offsetToTarget = new Transform2d( selectedNode.getOffsetToTarget(), Rotation2d.fromDegrees(0.0));
        return selectedTag.getPositionInches().plus(offsetToTarget);
    }    

    
}
