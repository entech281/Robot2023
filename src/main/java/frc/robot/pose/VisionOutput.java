package frc.robot.pose;

import java.util.ArrayList;
import java.util.List;

import frc.robot.RobotConstants;
public class VisionOutput {

    private double latency;
    private final List<RecognizedAprilTagTarget> recognizedTargets = new ArrayList<>();
    private final List<AprilTagLocation> AprilTagLocation = new ArrayList<>();

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
    
    // public static getAprilTagLocation(int id) {
    //     for(AprilTagIDLocation a : AprilTagIDLocation.values()) {
    //       if(id == a.getId()) return a;
    //     }
    //     return;
    //   }

    // public int getTargetLocation() {
    //     AprilTagIDs = ;
    //    if (AprilTagIDs =  ) {
   
    //    }
}
