package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
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
        Transform2d tf = new Transform2d(selectedNode.getOffsetToTarget(),Rotation2d.fromDegrees(0));
        Pose2d absoluteNodePosition = tagPose.transformBy(tf);
        return absoluteNodePosition;
    }
    
}   