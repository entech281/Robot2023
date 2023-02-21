package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;

public class AlignmentCalculator {

	public static final double NOT_FOUND = 999;
	
	public double calculateAngleToScoringLocation(ScoringLocation loc, Pose2d estimatedRobotPose) {
	   	TargetNode selectedNode = loc.getSelectedNode();
	   	double currentRobotAngle = estimatedRobotPose.getRotation().getDegrees();
        double robotToNodeX = selectedNode.getXMeters() + estimatedRobotPose.getX();
        double robotToNodeY = selectedNode.getYMeters() - estimatedRobotPose.getY();
        double nodeAngle = Math.toDegrees(Math.atan2(robotToNodeX,robotToNodeY));
		double totalTurnAngle = (90 - currentRobotAngle) + nodeAngle;
        return totalTurnAngle;
	}	

}
