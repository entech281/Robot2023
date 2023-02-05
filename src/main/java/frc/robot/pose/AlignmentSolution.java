package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;

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
    
    public enum AlignmentStrategy{
        HOPELESS_I_GIVE_UP,
        ROTATE_AND_DEPLOY,
        ORTHOGONAL_APPOROACH,
        NO_STRATEGY_SELECTED
    }
    
    private AlignmentStrategy strategy =  AlignmentStrategy.NO_STRATEGY_SELECTED;
    private TargetNode target;
    private RobotPose startingPose;

    public Pose2d getRobotPose() {
        return robotPose;
    }

    public void setRobotPose(Pose2d robotPose) {
        this.robotPose = robotPose;
    }

    public Pose2d getTargetPose() {
        return targetPose;
    }

    public void setTargetPose(Pose2d targetPose) {
        this.targetPose = targetPose;
    }
    private Pose2d robotPose;
    private Pose2d targetPose;

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
}
