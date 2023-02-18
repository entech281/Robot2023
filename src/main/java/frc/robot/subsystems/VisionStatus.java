package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.pose.RecognizedAprilTagTarget;

public class VisionStatus implements SubsystemStatus{

    private double latency;
    private RecognizedAprilTagTarget bestTarget;
    private final List<RecognizedAprilTagTarget> targets = new ArrayList<>();
    private Pose3d photonEstimatedPose;
    
    public boolean hasPhotonPose() {
    	return photonEstimatedPose != null;
    }
    public Pose3d getPhotonEstimatedPose() {
		return photonEstimatedPose;
	}

	public void setPhotonEstimatedPose(Pose3d photonEstimatedPose) {
		this.photonEstimatedPose = photonEstimatedPose;
	}

	public List<RecognizedAprilTagTarget> getRecognizedTargets() {
        return targets;
    }

    public void addRecognizedTarget( RecognizedAprilTagTarget targetToAdd) {
    	targets.add(targetToAdd);
    }

    public double getLatency() {
        return this.latency;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }

    public boolean hasTargets() {
        return !targets.isEmpty();
    }

    public RecognizedAprilTagTarget getBestAprilTagTarget() {
    	if ( this.bestTarget != null) {
    		return this.bestTarget;
    	}
    	else if (targets.size() > 0 ){
    		return targets.get(0);
    	}
    	else {
    		return null;
    	}
    }

    public void setBestTarget(RecognizedAprilTagTarget target) {
        this.bestTarget = target;
    }
    public boolean hasBestTarget() {
    	return this.bestTarget != null;
    }


	@Override
	public String toString() {
		return "VisionStatus [latency=" + latency + ", bestTarget=" + bestTarget + ", targets=" + targets
				+ ", photonEstimatedPose=" + photonEstimatedPose + "]";
	}

}
