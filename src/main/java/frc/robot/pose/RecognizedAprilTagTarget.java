
package frc.robot.pose;

import edu.wpi.first.math.geometry.Transform3d;

/**
 * Value object that represents a target we've recognized via vision.
 * @author dcowden
 */
public class RecognizedAprilTagTarget {
    
    public RecognizedAprilTagTarget(Transform3d cameraToTarget, AprilTagLocation tagLocation) {
        this.tagLocation = tagLocation;
        this.cameraToTarget = cameraToTarget;
    }

	private AprilTagLocation tagLocation;
    private Transform3d cameraToTarget;
    
    
    public Transform3d getCameraToTargetTransform() {
		return cameraToTarget;
	}

    public AprilTagLocation getTagLocation() {
		return tagLocation;
	}

}
