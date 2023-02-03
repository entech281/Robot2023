
package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

/**
 *
 * @author dcowden
 */
public class RobotPose {

    public RobotPose(Pose2d newPose){
        pose = newPose;
    }

    private Pose2d pose;
  
    public Pose2d getPose(){
        return pose;
    }
   
    //arm pose code in progress
    
    //public RobotPose(/*Arm Pose*/ newPose){
        //armPose = newpose;
    //}

    //private /*Arm Pose*/ armPose;

    //public /*Arm Pose*/ getarmPose(){
        //return armPose;
    //}
    
}
