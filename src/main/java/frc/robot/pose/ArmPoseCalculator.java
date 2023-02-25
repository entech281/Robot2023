package frc.robot.pose;

import frc.robot.RobotConstants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;

public class ArmPoseCalculator {
    
    public ArmPoseResult getDeploymentStatus(Pose2d estimatedPose, ScoringLocation target) {
		Translation2d start = estimatedPose.getTranslation();
		Translation2d end = target.computeAbsolutePose().getTranslation();
        ArmPoseResult armPose = new ArmPoseResult();
        armPose.setTargetDistance(end.getDistance(start));
        armPose.setCanDeploy(canDeploy(armPose.targetDistance));
		return armPose;
	}

    private boolean canDeploy(double targetDistance){
        boolean tooFar = targetDistance > RobotConstants.ARM.MAX_EXTENSION_METERS;
        boolean tooClose = targetDistance < RobotConstants.ARM.MIN_EXTENSION_METERS;
        boolean canDeploy = !tooFar && !tooClose;
        return canDeploy;
    }

}
