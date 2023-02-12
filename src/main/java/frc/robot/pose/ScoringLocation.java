package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;

/**
 *
 * @author dcowden
 */
public class ScoringLocation {

    public ScoringLocation ( AprilTagLocation tag, TargetNode node){
        this.selectedNode = node;
        this.selectedTag = tag;
    }
    
    /**
     * these methods are delegated to node.
     * @return 
     */

    public boolean is2ndRow(){
        return (selectedNode.is2ndRow());
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

    public AprilTagLocation getSelectedTag() {
        return selectedTag;
    }

    public TargetNode getSelectedNode() {
        return selectedNode;
    }
    
    private final AprilTagLocation selectedTag;
    private final TargetNode selectedNode;

    public  Pose2d computeAbsolutePose( ){
        Pose2d tagPose = selectedTag.asPose2d();
        Translation2d nodeTranslation = selectedNode.getOffsetToTarget(); 
        //calaculates absoluteNodePosition correctly 
        Pose2d absoluteNodePosition = new Pose2d((tagPose.getTranslation().plus(nodeTranslation)), tagPose.getRotation());
        return absoluteNodePosition;
    }
    
}   