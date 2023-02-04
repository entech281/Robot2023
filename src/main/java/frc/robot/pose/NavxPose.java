package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;

/**
 *
 * @author dcowden
 */
public class NavxPose {
    
    public NavxPose(double angle, Pose2d newpose){
        this.angle = angle;
        this.pose = newpose;
    }

    private double angle = 0.0;
    private Pose2d pose;
    
    
    public double getAngle(){
        return angle;
    }

    public Pose2d getPose(){
        return pose;
    }
    
}
