package frc.robot.pose;

import java.util.ArrayList;
import java.util.List;

public class VisionOutput {

    private double latency;
    private final List<RecognizedAprilTagTarget> recognizedTargets = new ArrayList<>();
    
    public List<RecognizedAprilTagTarget> getRecognizedTargets() {
        return recognizedTargets;
    }

    public double getLatency() {
        return this.latency;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }

    public void addRecognizedTarget( RecognizedAprilTagTarget targetToAdd) {
        recognizedTargets.add(targetToAdd);
    }
    public boolean hasTargets() {
        return !recognizedTargets.isEmpty();
    }

    

}
