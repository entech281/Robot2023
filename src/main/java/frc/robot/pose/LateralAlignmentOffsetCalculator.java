package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;

public class LateralAlignmentOffsetCalculator {
    private final ScoringLocation target;

    public LateralAlignmentOffsetCalculator(Pose2d robotPose) {
        target = getNearestScoringLocaton(robotPose);
    }

    public double getLateralOffset(Pose2d robotPose) {
        double robotY = robotPose.getY();
        double nodeDistanceToTarget = target.getSelectedNode().getYMeters();
        double aprilTagYCoordinate = target.getSelectedTag().getYMeters();
        double lateralAlignmentOffset = (aprilTagYCoordinate + nodeDistanceToTarget) + robotY;
        return lateralAlignmentOffset;
    }

    public LateralOffset LateralOffsetToNearestScoringLocation(Pose2d robotPose) {
        return new LateralOffset(getLateralOffset(robotPose), getNearestScoringLocaton(robotPose));
    }
	
    private ScoringLocation getNearestScoringLocaton(Pose2d robotPose) {
        double robotY = robotPose.getY();
        double robotX = robotPose.getX();
        if (robotX < 285.16) {
            if (robotY < 75.19){
                if (robotY < 37.875) {
                    return new ScoringLocation(AprilTagLocation.BLUE_LEFT, TargetNode.A1);
                } else if (robotY > 48.125) {
                    return new ScoringLocation(AprilTagLocation.BLUE_LEFT, TargetNode.A3);
                } else {
                    return new ScoringLocation(AprilTagLocation.BLUE_LEFT, TargetNode.A2);
                }
            } else if(robotY > 141.19) {
                if (robotY < 178.875) {
                    return new ScoringLocation(AprilTagLocation.BLUE_RIGHT, TargetNode.A1);
                } else if (robotY > 189.125) {
                    return new ScoringLocation(AprilTagLocation.BLUE_RIGHT, TargetNode.A3);
                } else {
                    return new ScoringLocation(AprilTagLocation.BLUE_RIGHT, TargetNode.A2);
                }
            } else {
                if (robotY < 112.875) {
                    return new ScoringLocation(AprilTagLocation.BLUE_MIDDLE, TargetNode.A1);
                } else if (robotY > 123.125) {
                    return new ScoringLocation(AprilTagLocation.BLUE_MIDDLE, TargetNode.A3);
                } else {
                    return new ScoringLocation(AprilTagLocation.BLUE_MIDDLE, TargetNode.A2);
                }
            }
        } else {
            if (robotY < 75.19){
                if (robotY < 37.875) {
                    return new ScoringLocation(AprilTagLocation.BLUE_RIGHT, TargetNode.A2);
                } else if (robotY > 48.125) {
                    return new ScoringLocation(AprilTagLocation.BLUE_RIGHT, TargetNode.A3);
                } else {
                    return new ScoringLocation(AprilTagLocation.BLUE_RIGHT, TargetNode.A1);
                }
            } else if(robotY > 141.19) {
                if (robotY < 178.875) {
                    return new ScoringLocation(AprilTagLocation.BLUE_LEFT, TargetNode.A2);
                } else if (robotY > 189.125) {
                    return new ScoringLocation(AprilTagLocation.BLUE_LEFT, TargetNode.A3);
                } else {
                    return new ScoringLocation(AprilTagLocation.BLUE_LEFT, TargetNode.A1);
                }
            } else {
                if (robotY < 112.875) {
                    return new ScoringLocation(AprilTagLocation.BLUE_MIDDLE, TargetNode.A2);
                } else if (robotY > 123.125) {
                    return new ScoringLocation(AprilTagLocation.BLUE_MIDDLE, TargetNode.A3);
                } else {
                    return new ScoringLocation(AprilTagLocation.BLUE_MIDDLE, TargetNode.A1);
                }
            }
        }
    }
}
