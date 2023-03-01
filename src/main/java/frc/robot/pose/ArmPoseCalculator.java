package frc.robot.pose;

import frc.robot.RobotConstants;
import frc.robot.subsystems.ElbowStatus;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import java.lang.Math;

public class ArmPoseCalculator {
    
    public ArmPoseResult getDeploymentStatus(Pose2d estimatedPose, ScoringLocation target, ElbowStatus elbowAngle) {
		Translation2d start = estimatedPose.getTranslation();
		Translation2d end = target.computeAbsolutePose().getTranslation();
        ArmPoseResult armPose = new ArmPoseResult();
        armPose.setTargetDistance(end.getDistance(start));
        armPose.setCanDeploy(canDeploy(armPose.targetDistance, elbowAngle.getVerticalAngle()));
		return armPose;
	}

    private boolean canDeploy(double targetDistance, double elbowAngle){
       //calculate max horizontal arm reach
        double maxHorizontalArmReach = RobotConstants.ARM.MAX_EXTENSION_METERS * Math.sin(elbowAngle);
       //calculate min horizontal arm reach
       double minHorizontalArmReach = RobotConstants.ARM.MIN_EXTENSION_METERS * Math.sin(elbowAngle);
        boolean tooFar = targetDistance > maxHorizontalArmReach;
        boolean tooClose = targetDistance < minHorizontalArmReach;
        
        boolean canDeploy = !tooFar && !tooClose;
        return canDeploy;
    }

}
