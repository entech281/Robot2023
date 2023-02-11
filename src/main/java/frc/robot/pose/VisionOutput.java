package frc.robot.pose;

import java.util.ArrayList;
import java.util.List;
public class VisionOutput {

    private double latency;
    private AprilTagLocation aprilTagLocation;
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

    public AprilTagLocation getAprilTagLocation() {
        return this.aprilTagLocation;
    }

    public void setAprilTagLocation(AprilTagLocation aprilTagLocation) {
        this.aprilTagLocation = aprilTagLocation;
    }

    //public Pose2d getTagPosesRelativeToCamera() {
    //    return this.tagPosesRelativeToCamera;
    //}

    //public void setTagPosesRelativeToCamera(Pose2d tagPosesRelativeToCamera) {
    //    this.tagPosesRelativeToCamera = tagPosesRelativeToCamera;
    //}    
}
