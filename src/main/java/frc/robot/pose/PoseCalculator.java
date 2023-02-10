package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;

/**
 *
 * @author dcowden
 */
public class PoseCalculator {
    
    public RobotPose calculatePose ( DriveStatus ds, VisionStatus vs, NavxStatus ns, ArmStatus as){
        RobotPose newPose = new RobotPose();
        Pose2d simplePose = new Pose2d( 0, 0, ns.getBasePose().getRotation());
        return newPose;
    }
}
