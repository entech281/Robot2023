package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

/**
 *
 * @author dcowden
 */
public class PoseCalculator {
    
    public RobotPose calculatePose ( DrivePose ddo, VisionPose vo, NavxPose no, ArmPose ao){
        RobotPose newPose = new RobotPose();

        newPose.setCalculatedPose(new Pose2d(0, 0, new Rotation2d(no.getYawAngleDegrees())));

        //lots of math needs to be added
        return newPose;
    }
}
