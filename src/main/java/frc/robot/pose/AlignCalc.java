package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import frc.robot.RobotConstants;

public class AlignCalc {

    public static final Pose2d FIELD_ZERO = new Pose2d ( 0.0,0.0,new Rotation2d(0.0));

    
    public AlignmentSolution calculateSolution( ScoringLocation loc , RobotPose rp){
        
        AlignmentSolution as = new AlignmentSolution(loc, rp);
        Pose2d robotBase = rp.getCalculatedPose();  
        Pose2d targetPose = loc.computeAbsolutePose();
        
        Transform2d deltaToTarget = targetPose.minus(robotBase);        
        double angleToTarget = deltaToTarget.getRotation().getDegrees();
        double distanceToTarget = deltaToTarget.getTranslation().getNorm();
        
        
        if ( canDeployImmediately(angleToTarget, distanceToTarget)){
            as.setDeployNow(true);
        }
        else{
            boolean canDeployThirdRow = ( loc.isThirdRow() && angleToTarget <= RobotConstants.VISION.MAXIMUM_3RDROW_APPROACH_ANGLE_DEGREES );
            boolean canDeploy2ndRow = ( loc.is2ndRow() && angleToTarget <= RobotConstants.VISION.MAXIMUM_2NDROW_APPROACH_ANGLE_DEGREES );

            if (canDeployThirdRow || canDeploy2ndRow){
                as.setDegreesToRotate(angleToTarget);
            }
            if ( ! isDistanceWithinTolerance(distanceToTarget)){
                as.setDistanceToMove(distanceToTarget);
            }
        }
  
        return as;
    }   
    
    protected static boolean canDeployImmediately ( double angleToTarget, double distanceToTarget){
        return isAngleWithinTolerance(angleToTarget) && isDistanceWithinTolerance(distanceToTarget);
    }
    
    protected static boolean isAngleWithinTolerance ( double angleToTarget){
         return (angleToTarget <= RobotConstants.ALIGNMENT.ANGLE_TOLERANCE_DEGREES);
    }
    public static  boolean isDistanceWithinTolerance ( double distanceToTarget){
        return (distanceToTarget <= RobotConstants.ALIGNMENT.DISTANCE_TOLERANCE_INCHES);
    }
}