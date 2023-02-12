
package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;

/**
 * Value object that represents a target we've recognized via vision.
 * @author dcowden
 */
public class RecognizedAprilTagTarget {
    
    public RecognizedAprilTagTarget(Pose2d relativeCamera, AprilTagLocation tagLocation) {
        this.tagLocation = tagLocation;
        this.offsetFromCamera = relativeCamera;
    }
    
    Pose2d offsetFromCamera;
    Pose2d photonVisionEstimatedPose;
    
    private AprilTagLocation tagLocation;

    public AprilTagLocation getTagLocation() {
		return tagLocation;
	}

	public Pose2d getOffsetFromCamera() {
        return offsetFromCamera;
    }

    

}
