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
		List<ScoringLocation> scoringLocationsToTry = new ArrayList<>();
		
		if ( x > FIELD_MIDPOINT_X_METERS) {
			for ( AprilTagLocation at: List.of (AprilTagLocation.RED_LEFT,AprilTagLocation.RED_MIDDLE ,AprilTagLocation.RED_RIGHT) ) {
				for ( TargetNode tn: List.of (	TargetNode.A1,TargetNode.A2,TargetNode.A3 )) {
					scoringLocationsToTry.add(new ScoringLocation( at, tn));
				}
			}
			scoringLocationsToTry.add( new ScoringLocation(AprilTagLocation.BLUE_LOADING,TargetNode.LL ));
			scoringLocationsToTry.add( new ScoringLocation(AprilTagLocation.BLUE_LOADING,TargetNode.LR ));
		}
		else {
			for ( AprilTagLocation at: List.of (AprilTagLocation.BLUE_LEFT,AprilTagLocation.BLUE_MIDDLE ,AprilTagLocation.BLUE_RIGHT) ) {
				for ( TargetNode tn: List.of (	TargetNode.A1,TargetNode.A2,TargetNode.A3 )) {
					scoringLocationsToTry.add(new ScoringLocation( at, tn));
				}
			}
			scoringLocationsToTry.add( new ScoringLocation(AprilTagLocation.RED_LOADING,TargetNode.LL ));
			scoringLocationsToTry.add( new ScoringLocation(AprilTagLocation.RED_LOADING,TargetNode.LR ));		
		}
		
		for ( ScoringLocation s: scoringLocationsToTry) {
			double dist = yDistanceBetween(y, s);			
			if ( Math.abs(dist) < Math.abs(closestSoFar )) {
				closest = s;
				closestSoFar = dist;
			}
		}
		return new LateralOffset(closestSoFar, closest) ;
	}
	
	
	private static double yDistanceBetween (double  y, ScoringLocation scoringLocation) {
		Pose2d scoringLocationPose = scoringLocation.computeAbsolutePose();
		return scoringLocationPose.getY() - y;
		
	}	
}
