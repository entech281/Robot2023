package frc.robot.pose;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;

public class LateralAlignCalculator {

	/**
	 * the 
	 * @param robotPose
	 * @return lateraloffset to the nearest scoring location.  
	 * IMPORTANT NOTE: a positive offset should be to the robots left, from the robots view looking forward
	 * 
	 * the selected targetNode should be the one in the back ( a1, a2, or a3 )
	 */
	public LateralOffset findOffsetToNearestTarget(double x, double y ) {
		ScoringLocation closest = null;
		double closestSoFar = 999;	
		
		double FIELD_MIDPOINT_X_METERS = 8.26;
		List<AprilTagLocation> aprilTags = new ArrayList<>();
		if ( x > FIELD_MIDPOINT_X_METERS) {
			aprilTags.add(AprilTagLocation.RED_LEFT);
			aprilTags.add(AprilTagLocation.RED_MIDDLE);
			aprilTags.add(AprilTagLocation.RED_RIGHT);
			aprilTags.add(AprilTagLocation.BLUE_LOADING);
		}
		else {
			aprilTags.add(AprilTagLocation.BLUE_LEFT);
			aprilTags.add(AprilTagLocation.BLUE_MIDDLE);
			aprilTags.add(AprilTagLocation.BLUE_RIGHT);
			aprilTags.add(AprilTagLocation.RED_LOADING);			
		}
		
		List<TargetNode> targetNodes = List.of(
			TargetNode.A1,
			TargetNode.A2,
			TargetNode.A3
		);
		
		for ( AprilTagLocation at: aprilTags) {
			for ( TargetNode tn: targetNodes) {
				ScoringLocation s = new ScoringLocation(at,tn);
				double dist = yDistanceBetween(y, s);
				if ( dist < closestSoFar ) {
					closest = s;
					closestSoFar = dist;
				}
			}
		}
		return new LateralOffset(closestSoFar, closest) ;
	}
	
	
	private static double yDistanceBetween (double  y, ScoringLocation scoringLocation) {
		Pose2d scoringLocationPose = scoringLocation.computeAbsolutePose();
		return Math.abs(y - scoringLocationPose.getY());
		
	}	
}
