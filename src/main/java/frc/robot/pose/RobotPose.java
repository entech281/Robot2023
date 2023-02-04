
package frc.robot.pose;

/**
 *
 * @author dcowden
 */
public class RobotPose {

    public RobotPose(NavxPose bodyPose, ArmPose armPose, VisionPose visionPose){
        this.bodyPose = bodyPose;
        this.armPose = armPose;
        this.visionPose = visionPose;
    }
  
    private NavxPose bodyPose;
    private ArmPose armPose;
    private VisionPose visionPose;

    public NavxPose getBodyPose(){
        return bodyPose;
    }

    public ArmPose getArmPose(){
        return armPose;
    }

    public VisionPose getVisionPose(){
        return visionPose;
    }

}
