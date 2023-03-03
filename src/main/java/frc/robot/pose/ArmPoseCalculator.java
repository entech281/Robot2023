package frc.robot.pose;

import frc.robot.RobotConstants;
import frc.robot.subsystems.ElbowStatus;
import frc.robot.subsystems.ArmStatus;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import java.lang.Math;

public class ArmPoseCalculator {
    
    public ArmPoseResult getDeploymentStatus(Pose2d estimatedPose, ScoringLocation target, ElbowStatus elbowAngle, ArmStatus armStatus) {
        Translation2d start = estimatedPose.getTranslation();
        Translation2d end = target.computeAbsolutePose().getTranslation();
        ArmPoseResult armPose = new ArmPoseResult();
        armPose.setTargetDistance(end.getDistance(start));
        armPose.setCanDeploy(canDeploy(armPose.targetDistance, elbowAngle.getVerticalAngle()));
        return armPose;
	}

    private boolean canDeploy(double targetDistance, double elbowAngle){
        double maxHorizontalArmReach = RobotConstants.ARM.MAX_EXTENSION_METERS * Math.sin(Math.toRadians(elbowAngle));
        double minHorizontalArmReach = RobotConstants.ARM.MIN_EXTENSION_METERS * Math.sin(Math.toRadians(elbowAngle));
        boolean tooFar = targetDistance > maxHorizontalArmReach;
        boolean tooClose = targetDistance < minHorizontalArmReach;
        //System.out.println("targetDistance: " + targetDistance + ", maxHorizontalArmReach" + maxHorizontalArmReach);
        //System.out.println("Too Far: " + tooFar + ", Too Close:" + tooClose);    
        boolean canDeploy = !tooFar && !tooClose;
        return canDeploy;
    }

}
