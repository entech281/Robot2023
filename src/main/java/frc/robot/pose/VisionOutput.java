package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import java.util.ArrayList;
import java.util.List;

public class VisionOutput {
    //private Pose2d tagPosesRelativeToCamera;
    //private boolean cameraHasTargets;
    //private int tagIDs;
    private double latency;
    private final List<RecognizedAprilTagTarget> recognizedTargets = new ArrayList<>();
    
    public List<RecognizedAprilTagTarget> getRecognizedTargets(){
        return recognizedTargets;
    }
    
    //public PhotonTrackedTarget getNumberOfTargets() {
    //    return this.NumberOfTargets;
    //}

    //public void setNumberOfTargets(PhotonTrackedTarget NumberOfTargets) {
    //    this.NumberOfTargets = NumberOfTargets;
    //}
    //private PhotonTrackedTarget NumberOfTargets;

    public double getLatency() {
        return this.latency;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }

    //public int getTagIDs() {
    //    return this.tagIDs;
    //}

    //public void setTagIDs(int tagIDs) {
    //    this.tagIDs = tagIDs;
    //}

    public void addRecognizedTarget( RecognizedAprilTagTarget targetToAdd){
        recognizedTargets.add(targetToAdd);
    }
    public boolean hasTargets() {
        return !recognizedTargets.isEmpty();
    }

    //public boolean getCameraHasTargets() {
    //    return this.cameraHasTargets;
    //}

    //public void setCameraHasTargets(boolean cameraHasTargets) {
    //    this.cameraHasTargets = cameraHasTargets;
    //}

    //public Pose2d getTagPosesRelativeToCamera() {
    //    return this.tagPosesRelativeToCamera;
    //}/

    //public void setTagPosesRelativeToCamera(Pose2d tagPosesRelativeToCamera) {
    //    this.tagPosesRelativeToCamera = tagPosesRelativeToCamera;
    //}    
}
