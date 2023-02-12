package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

/**
 *
 * @author dcowden
 */
public class VisionFirstNavxAsBackupPoseCalculator implements PoseEstimator{
    
	@Override
	public Pose2d estimateRobotPose(VisionStatus vs, NavxStatus ns, DriveStatus ds) {
    	if ( vs.hasBestTarget() ) {
    		return estimatePoseFromCamera(vs);
    	}
    	else {
    		return estimatePoseFromNavx(ns);
    	}
	}
    
    private Pose2d estimatePoseFromCamera(VisionStatus vs) {
    	return vs.getBestAprilTagTarget().getOffsetFromCamera();
    }
    
    private Pose2d estimatePoseFromNavx(NavxStatus ns) {
    	return new Pose2d(0,0,Rotation2d.fromDegrees(ns.getYawAngleDegrees()));
    }


}
