package frc.robot.pose;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;

public class LateralAlignmentOffsetCalculator {

    public LateralAlignmentOffsetCalculator() {
    }

    public LateralOffset getNearestScoringLocation(Pose2d robotPose) {
        
        ScoringLocation closest = null;
		double closestSoFar = 999;
		
		List<AprilTagLocation> aprilTags = List.of( 
				AprilTagLocation.BLUE_LEFT,
				AprilTagLocation.BLUE_MIDDLE,
				AprilTagLocation.BLUE_RIGHT,
				AprilTagLocation.RED_LEFT,
				AprilTagLocation.RED_MIDDLE,
				AprilTagLocation.RED_RIGHT				
		);
		
		List<TargetNode> targetNodes = List.of(
			TargetNode.A1,
			TargetNode.A2,
			TargetNode.A3
		);
		
		for ( AprilTagLocation at: aprilTags) {
			for ( TargetNode tn: targetNodes) {
				ScoringLocation s = new ScoringLocation(at,tn);
				double dist = yDistanceBetween(robotPose, s);
				if ( dist < closestSoFar ) {
					closest = s;
					closestSoFar = dist;
				}
			}
		}
		return new LateralOffset(closestSoFar, closest);
	}
	
	    private static double yDistanceBetween ( Pose2d robotPose, ScoringLocation scoringLocation) {
		    Pose2d scoringLocationPose = scoringLocation.computeAbsolutePose();
		    return Math.abs(robotPose.getY() - scoringLocationPose.getY());
		
	    }
    }
    