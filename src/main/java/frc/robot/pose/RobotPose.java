
package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 *
 * @author dcowden
 */
public class RobotPose implements Sendable{

    public void setCalculatedPose(Pose2d calculatedPose) {
        this.calculatedPose = calculatedPose;
    }

    public void setBodyPose(NavxPose bodyPose) {
        this.bodyPose = bodyPose;
    }

    public void setArmPose(ArmPose armPose) {
        this.armPose = armPose;
    }

    public void setVisionPose(VisionPose visionPose) {
        this.visionPose = visionPose;
    }

  
    private Pose2d calculatedPose;
    private  NavxPose bodyPose;
    private  ArmPose armPose;
    private  VisionPose visionPose; 
    
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
