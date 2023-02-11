package frc.robot.pose;


/**
 *
 * @author dcowden
 */
public class PoseCalculator {
    
    public RobotPose calculatePose ( DrivePose ddo, VisionPose vo, NavxPose no, ArmPose ao){
        RobotPose newPose = new RobotPose();
        newPose.setCalculatedPose(vo.getMostCentralAprilTag().getPositionInches());
        //lots of math needs to be added
        return newPose;
    }
}
