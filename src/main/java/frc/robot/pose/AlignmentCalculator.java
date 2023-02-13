package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;

public class AlignmentCalculator {

	public static final double NOT_FOUND = 999;
	
	public double calculateAngleToScoringLocation(ScoringLocation loc, Pose2d estimatedRobotPose) {
	   	TargetNode selectedNode = loc.getSelectedNode();
        double robotToNodeX = selectedNode.getXIn() + estimatedRobotPose.getX();
        double robotToNodeY = selectedNode.getYIn() - estimatedRobotPose.getY();
        double turnAngle = Math.toDegrees(Math.atan2(robotToNodeY,robotToNodeX)) + 180;            
        return turnAngle;
	}	
	
	public double calculateAngleToScoringLocation ( VisionStatus vs, TargetNode targetNode, Pose2d estimatedRobotPose) {    	
    	RecognizedAprilTagTarget rat = vs.getBestAprilTagTarget();
    	if ( rat == null ) {
    		return NOT_FOUND;
    	}
    	ScoringLocation s = new ScoringLocation(rat.getTagLocation(),targetNode);
    	return calculateAngleToScoringLocation(s,estimatedRobotPose);
	}

}
