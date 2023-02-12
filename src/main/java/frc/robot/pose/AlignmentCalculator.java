package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;

public class AlignmentCalculator {

	public double calculateAngleToScoringLocation(ScoringLocation loc, Pose2d estimatedRobotPose) {
	   	TargetNode selectedNode = loc.getSelectedNode();
        double robotToNodeX = selectedNode.getXIn() + estimatedRobotPose.getX();
        double robotToNodeY = selectedNode.getYIn() - estimatedRobotPose.getY();
        double turnAngle = Math.toDegrees(Math.atan2(robotToNodeY,robotToNodeX)) + 180;            
        return turnAngle;
	}	

}
