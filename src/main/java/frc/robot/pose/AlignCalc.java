package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import frc.robot.RobotConstants;

public class AlignCalc {

    public static final Pose2d FIELD_ZERO = new Pose2d ( 0.0,0.0,new Rotation2d(0.0));

    public AlignmentSolution calculateSolution( TargetNode tn, RobotPose rp){
        
        AlignmentSolution as = new AlignmentSolution();
        FieldAprilTag fat = rp.getSelectedTag();
        Pose2d robotBase = rp.getCalculatedPose();        
        Pose2d target = computePoseOfTarget( tn, fat );
        as.setStartingPose(rp);
        as.setTarget(tn);
        as.setTargetPose(target);
        as.setRobotPose(robotBase);
        
        
        Transform2d deltaToTarget = computeAngleToTarget (robotBase, target);
        if ( tn.noTargetSelected() ){
            as.setStrategy(AlignmentSolution.AlignmentStrategy.HOPELESS_I_GIVE_UP);
            return as;
        }

        if ( tn.is2ndRow() ){
            if ( canDeploy2ndRowFromHere(deltaToTarget)){
                as.setStrategy(AlignmentSolution.AlignmentStrategy.ROTATE_AND_DEPLOY);             
            }
            else{
                as.setStrategy(AlignmentSolution.AlignmentStrategy.HOPELESS_I_GIVE_UP);
            }
        }
        else if ( tn.isThirdRow()){
            if ( canDeployThirdRowFromHere(deltaToTarget)){
                as.setStrategy(AlignmentSolution.AlignmentStrategy.ROTATE_AND_DEPLOY);
            }
            else{
                as.setStrategy(AlignmentSolution.AlignmentStrategy.HOPELESS_I_GIVE_UP);
            }
        }
        return as;
    }   
    
    private boolean canDeployThirdRowFromHere(Transform2d deltaToScoringNode){
        return deltaToScoringNode.getRotation().getDegrees() <= RobotConstants.VISION.MAXIMUM_3RDROW_APPROACH_ANGLE_DEGREES;
    }
    
    private boolean canDeploy2ndRowFromHere(Transform2d deltaToScoringNode){
        return deltaToScoringNode.getRotation().getDegrees() <= RobotConstants.VISION.MAXIMUM_2NDROW_APPROACH_ANGLE_DEGREES;
    }
    
    private Pose2d computePoseOfTarget(TargetNode tn, FieldAprilTag fat ){
        Transform2d offsetToTarget = new Transform2d( tn.getOffsetToTarget(), Rotation2d.fromDegrees(0.0));
        return fat.getPositionInches().plus(offsetToTarget);
    }
    
    private Transform2d computeAngleToTarget ( Pose2d robotBase, Pose2d scoringNode){
        Transform2d delta = scoringNode.minus(robotBase);
        return delta;
    }    
}