package frc.robot.pose;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import java.util.ArrayList;
import java.util.List;
import edu.wpi.first.math.geometry.Pose2d;

public class VisionPose implements Sendable {
    
    private double pipelineLatency;
    private Pose2d poseRelativeToTag;
    private final List<RecognizedAprilTagTarget> recognizedTargets = new ArrayList<>();
    
    public List<RecognizedAprilTagTarget> getRecognizedTargets(){
        return recognizedTargets;
    }

    public double getPipelineLatency() {
        return this.pipelineLatency;
    }

    public FieldAprilTag getMostCentralAprilTag(){
        if ( recognizedTargets.size() == 1){
            RecognizedAprilTagTarget rat = recognizedTargets.get(0);
            return FieldAprilTagCollection.findFromTag(rat.getTagId());
        }
        return null;
    }
    public void setPipelineLatency(double pipelineLatency) {
        this.pipelineLatency = pipelineLatency;
    }

    public void addRecognizedTarget( RecognizedAprilTagTarget targetToAdd){
        recognizedTargets.add(targetToAdd);
    }
    public boolean hasTargets() {
        return !recognizedTargets.isEmpty();
    }

    @Override
    public void initSendable(SendableBuilder sb) {
        sb.addDoubleProperty("Pipeline Latency",this::getPipelineLatency, null);
        sb.addIntegerProperty("DetectedTargets", ()-> {
            return recognizedTargets.size();
        }, null);
    }

    public Pose2d getPoseRelativeToTag() {
        return this.poseRelativeToTag;
     }
 
     public void setPoseRelativeToTag(Pose2d poseRelativeToTag) {
        this.poseRelativeToTag = poseRelativeToTag;
     }    

}
