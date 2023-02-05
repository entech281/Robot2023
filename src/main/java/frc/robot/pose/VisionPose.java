package frc.robot.pose;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import java.util.ArrayList;
import java.util.List;

public class VisionPose implements Sendable{
    
    private double pipelineLatency;
    private final List<RecognizedAprilTagTarget> recognizedTargets = new ArrayList<>();
    
    public List<RecognizedAprilTagTarget> getRecognizedTargets(){
        return recognizedTargets;
    }

    public double getPipelineLatency() {
        return this.pipelineLatency;
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

}
