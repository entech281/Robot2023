package frc.robot.pose;


/**
 *
 * @author dcowden
 */
public class PoseCalculator {
    
    public RobotPose calculatePose ( DrivePose ddo, VisionPose vo, NavxPose no, ArmPose ao){
        

        RobotPose newPose = new RobotPose();
        
        //code that reads all this stuff and figures out what we think our actual pose is goes here
        //this includes reading the navx positoin and angle, and information about the calculated offset to any tags we can see. we can potentially see more than one!

        
        return newPose;
    }
}
