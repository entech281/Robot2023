package frc.robot.pose;

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
public class AlignmentSolution {
    private double neededAngle = 0;

    public enum AlignmentStrategy{
        HOPELESS_I_GIVE_UP,
        ROTATE_AND_DEPLOY,
        ORTHOGONAL_APPOROACH,
        NO_STRATEGY_SELECTED
    }
    
    private AlignmentStrategy strategy =  AlignmentStrategy.NO_STRATEGY_SELECTED;
    private TargetNode target;
    private RobotPose startingPose;
    private double tempAngle =0.0;

    public double getTempAngle(){
        return tempAngle;
    }
    public void setTempAngle(double newTempAngle){
        this.tempAngle = newTempAngle;
    }
    
    public RobotPose getStartingPose() {
        return startingPose;
    }

    public void setStartingPose(RobotPose startingPose) {
        this.startingPose = startingPose;
    }
    
    public TargetNode getTarget() {
        return target;
    }

    public void setTarget(TargetNode target) {
        this.target = target;
    }
    
    public AlignmentStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(AlignmentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setNeededAngle(double neededAngle) {
        this.neededAngle = neededAngle;
    }


    public double getNeededAngle() {
        return neededAngle;
    }
}
