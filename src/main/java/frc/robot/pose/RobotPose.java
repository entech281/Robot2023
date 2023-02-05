
package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 *
 * @author dcowden
 */
public class RobotPose implements Sendable{

  
    private Pose2d calculatedPose;
    private FieldAprilTag selectedTag;
    private  NavxPose bodyPose;
    private  ArmPose armPose;
    private  VisionPose visionPose;

    public FieldAprilTag getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(FieldAprilTag selectedTag) {
        this.selectedTag = selectedTag;
    }    
    
    public Pose2d getCalculatedPose() {
        return calculatedPose;
    }
    
    public NavxPose getBodyPose(){
        return bodyPose;
    }

    public ArmPose getArmPose(){
        return armPose;
    }

    public VisionPose getVisionPose(){
        return visionPose;
    }

    @Override
    public void initSendable(SendableBuilder sb) {
        passSenableBuilderDownToSubOjbects(sb);
    }
    
    private void passSenableBuilderDownToSubOjbects( SendableBuilder sb ){
        armPose.initSendable(sb);
        visionPose.initSendable(sb);
        bodyPose.initSendable(sb);
    }

}
