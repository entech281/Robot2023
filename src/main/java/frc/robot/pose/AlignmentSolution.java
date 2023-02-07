package frc.robot.pose;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.pose.instructions.AlignmentInstruction;
import java.util.ArrayList;
import java.util.List;

/**
 * An alignment solution should include everything we need if we
 * wanted to execute a fully autonomous score from our current position.
 * 
 * The AlignmentStrategy is how we'd pull it off, and the rest of the data
 * should be the necessary details
 * 
 * Note: we also store a reference to the RobotPose we were given 
 * as the basis for our calculations, and the scoring node we are going after
 * @author dcowden
 */
public class AlignmentSolution implements Sendable{
    
    public AlignmentSolution ( ScoringLocation sl, RobotPose rp){
        this.scoringLocation = sl;
        this.robotPose = rp;
    }


    
    public enum AlignmentStrategy{
        HOPELESS_I_GIVE_UP,
        DEPLOY_NOW,
        ROTATE_AND_DEPLOY,
        ROTATE_THEN_FOWARD,
        ORTHOGONAL_APPOROACH,
        NO_STRATEGY_SELECTED,
        CONFUSED
    }
    
    public ScoringLocation getScoringLocation() {
        return scoringLocation;
    }
    
    public RobotPose getRobotPose() {
        return robotPose;
    }
    
    public AlignmentStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(AlignmentStrategy strategy) {
        this.strategy = strategy;
    }

    public void addAlignmentInstruction( AlignmentInstruction is ){
        alignmentInstructions.add(is);
    }
    public List<AlignmentInstruction> getAlignmentInstructions() {
        return alignmentInstructions;
    }
    
    @Override
    public void initSendable(SendableBuilder sb) {
        sb.addStringProperty("Alignment strategy", this::toString, null);
    }    
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.strategy);
        sb.append(" : ");
        for ( AlignmentInstruction ai: alignmentInstructions){
            sb.append("\t* ").append (ai).append("\n");
        }
        return sb.toString();
    }
    
    private final List<AlignmentInstruction> alignmentInstructions = new ArrayList<>();
    private final RobotPose robotPose; // to store what we were given
    private AlignmentStrategy strategy =  AlignmentStrategy.NO_STRATEGY_SELECTED;
    private final ScoringLocation scoringLocation;    
}