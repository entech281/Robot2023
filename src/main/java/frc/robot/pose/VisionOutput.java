package frc.robot.pose;

import java.util.ArrayList;
import java.util.List;
public class VisionOutput {

    private double latency;
//    private List <AprilTagLocation> aprilTagLocations = new ArrayList<>();
    private final List<RecognizedAprilTagTarget> recognizedTargets = new ArrayList<>();

    public List<RecognizedAprilTagTarget> getRecognizedTargets() {
        return recognizedTargets;
    }

    public void addRecognizedTarget( RecognizedAprilTagTarget targetToAdd) {
        recognizedTargets.add(targetToAdd);
    }

    public double getLatency() {
        return this.latency;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }

    public boolean hasTargets() {
        return !recognizedTargets.isEmpty();
    }

    // public List<AprilTagLocation> getAprilTagLocations() {
    //     return this.aprilTagLocations;
    // }

    // public void setAprilTagLocations(List<AprilTagLocation> aprilTagLocations) {
    //     this.aprilTagLocations = aprilTagLocations;
    // }

}
