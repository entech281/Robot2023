package frc.robot.pose;

/**
 *
 * @author dcowden
 */
public class PoseCalculator {
    
    public RobotPose calculatePose ( DrivePose ddo, VisionPose vo, NavxPose no, ArmPose ao){
        RobotPose newPose = new RobotPose(new NavxPose(0, null), new ArmPose(false, 0, 0), new VisionPose());
        //lots of math needs to be added
        return newPose;
    }
}
