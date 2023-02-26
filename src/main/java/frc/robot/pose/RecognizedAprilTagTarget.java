
package frc.robot.pose;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Transform3d;

/**
 * Value object that represents a target we've recognized via vision.
 * @author dcowden
 */
public class RecognizedAprilTagTarget {
    
    public RecognizedAprilTagTarget(Transform3d cameraToTarget, AprilTagLocation tagLocation, PhotonTrackedTarget pt) {
        this.tagLocation = tagLocation;
        this.cameraToTarget = cameraToTarget;
        this.photonTarget = pt;
    }

	private AprilTagLocation tagLocation;
    private Transform3d cameraToTarget; 
    private PhotonTrackedTarget photonTarget;
    
    public PhotonTrackedTarget getPhotonTarget() {
		return photonTarget;
	}

	public Transform3d getCameraToTargetTransform() {
		return cameraToTarget;
	}

    public AprilTagLocation getTagLocation() {
		return tagLocation;
	}

	@Override
	public String toString() {
		return "RecognizedAprilTagTarget [tagLocation=" + tagLocation + ", cameraToTarget=" + cameraToTarget + "]";
	}

}
