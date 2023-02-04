
package frc.robot.pose;

/**
 *
 * @author dcowden
 */
public class RobotPose {

    public RobotPose(NavxPose bodyPose, ArmPose armPose){
        this.bodyPose = bodyPose;
        this.armPose = armPose;
    }
  
    private NavxPose bodyPose;
    private ArmPose armPose;

    public NavxPose getBodyPose(){
        return bodyPose;
    }

    public ArmPose getArmPose(){
        return armPose;
    }

}
