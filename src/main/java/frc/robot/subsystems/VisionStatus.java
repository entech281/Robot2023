package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.pose.RecognizedAprilTagTarget;

public class VisionStatus implements SubsystemStatus{

    private double latency;
    private Optional<RecognizedAprilTagTarget> bestTarget = Optional.empty();
    private final List<RecognizedAprilTagTarget> targets = new ArrayList<>();
    private Pose3d photonEstimatedPose;
    

    public Optional<Pose3d> getPhotonEstimatedPose() {
		return Optional.ofNullable(photonEstimatedPose);
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

    public Optional<RecognizedAprilTagTarget> getBestAprilTagTarget() {
    	if ( this.bestTarget.isPresent()) {
    		return bestTarget;
    	}
    	else {
        	if (targets.size() > 0 ){
        		return Optional.of(targets.get(0));
        	}
        	else {
        		return Optional.empty();
        	}    		
    	}
    }

    public void setBestTarget(RecognizedAprilTagTarget target) {
        this.bestTarget = Optional.ofNullable(target);
    }

	@Override
	public String toString() {
		return "VisionStatus [latency=" + latency + ", bestTarget=" + bestTarget + ", targets=" + targets
				+ ", photonEstimatedPose=" + photonEstimatedPose + "]";
	}

}
