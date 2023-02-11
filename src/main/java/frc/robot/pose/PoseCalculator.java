package frc.robot.pose;

/**
 *
 * @author dcowden
 */
public class PoseCalculator {
    
    public RobotPose calculatePose ( DrivePose ddo, VisionPose vo, NavxPose no, ArmPose ao){
        RobotPose newPose = new RobotPose();

        newPose.setBodyPose(no);

        FieldAprilTag fat = vo.getMostCentralAprilTag(); //could be null if we have no target
        if ( fat != null){
            newPose.setCalculatedPose(vo.getPoseRelativeToTag());
        }
        else{
            newPose.setCalculatedPose(null);
        }
        
        //lots of math needs to be added
        return newPose;
    }
}
