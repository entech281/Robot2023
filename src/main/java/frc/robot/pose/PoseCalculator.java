package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;

/**
 *
 * @author dcowden
 */
public class PoseCalculator {
    
    public RobotPose calculatePose ( DrivePose ddo, VisionPose vo, NavxPose no, ArmPose ao){
        RobotPose newPose = new RobotPose(new NavxPose(0, null), new ArmPose(false, 0, 0));
        //lots of math needs to be added
        return newPose;
    }
}
