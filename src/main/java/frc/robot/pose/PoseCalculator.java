package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;

/**
 *
 * @author dcowden
 */
public class PoseCalculator {
    
    public RobotPose calculatePose ( DriveOutput ddo, VisionOutput vo, NavxOutput no, ArmOutput ao){
        RobotPose newPose = new RobotPose(new Pose2d());
        return newPose;
    }
}
