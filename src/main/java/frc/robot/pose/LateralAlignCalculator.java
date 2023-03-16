package frc.robot.pose;

import java.lang.annotation.Target;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.RobotConstants;
import frc.robot.pose.TargetNode.NodeID;

public class LateralAlignCalculator {

	private VisionSubsystem visionSubsystem;
	private double lateralDistanceToNearestNodeMeters;
	private TargetNode nodeID;

	public LateralAlignCalculator( double lateralDistanceToNearestNodeMeters, TargetNode nodeID) {
        this.lateralDistanceToNearestNodeMeters = lateralDistanceToNearestNodeMeters;
		this.nodeID = nodeID;
    }

	/**
	 * the 
	 * @param robotPose
	 * @return lateraloffset to the nearest scoring location.  
	 * IMPORTANT NOTE: a positive offset should be to the robots left, from the robots view looking forward
	 * 
	 * the selected targetNode should be the one in the back ( a1, a2, or a3 )
	 */

	public LateralAlignCalculator getLateralOffsetToNodeAndNearestNode(Pose2d robotPose) {
		Translation2d t = robotPose.getTranslation();
		double robotCameraLateralOffset = visionSubsystem.getLateralOffset();
		double robotY = t.getY();
		if(robotY < robotCameraLateralOffset) {
			return new LateralAlignCalculator(getlateralDistanceToNearestNodeMeters(robotPose), getNearestNode(robotPose));
		} else if(robotY > robotCameraLateralOffset) {
			return new LateralAlignCalculator(getlateralDistanceToNearestNodeMeters(robotPose), getNearestNode(robotPose));
		} else {
			return new LateralAlignCalculator(getlateralDistanceToNearestNodeMeters(robotPose), getNearestNode(robotPose));
		}
	}

	public double getlateralDistanceToNearestNodeMeters(Pose2d robotPose) {
		Translation2d t = robotPose.getTranslation();
		double robotCameraLateralOffset = visionSubsystem.getLateralOffset();
		double robotY = t.getY();
		if(robotY < robotCameraLateralOffset) {
			double lateralDistanceToNearestNodeMeters = robotY + RobotConstants.ALIGNMENT.FIELD.METERS_TO_NODE_FROM_TAG;
			return lateralDistanceToNearestNodeMeters;
		} else if(robotY > robotCameraLateralOffset) {
			double lateralDistanceToNearestNodeMeters = robotY - RobotConstants.ALIGNMENT.FIELD.METERS_TO_NODE_FROM_TAG;
			return lateralDistanceToNearestNodeMeters;
		} else {
			double lateralDistanceToNearestNodeMeters = 0.0;
			return lateralDistanceToNearestNodeMeters;
		}
		
	}

	public TargetNode getNearestNode(Pose2d robotPose) {
		Translation2d t = robotPose.getTranslation();
		double robotCameraLateralOffset = visionSubsystem.getLateralOffset();
		double robotY = t.getY();
		if(robotY < robotCameraLateralOffset) {
			return TargetNode.A1;
		} else if(robotY > robotCameraLateralOffset) {
			return TargetNode.A3;
		} else {
			return TargetNode.A2;
		}
		
	}
}
