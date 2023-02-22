package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

/**
 *
 * @author dcowden
 */
public class NavxStatus implements SubsystemStatus {
    private double forwardDistance;
    private double rightDistance;
    private double yawAngleDegrees;
    private double pitchAngleDegrees;
    
    public NavxStatus() {
        this.forwardDistance = 0.0;
        this.rightDistance = 0.0;
        this.yawAngleDegrees = 0.0;
        this.pitchAngleDegrees = 0.0;
    }

    public NavxStatus(double forward, double right, double yaw, double pitch) {
        this.yawAngleDegrees = yaw;
        this.forwardDistance = forward;
        this.rightDistance = right;
        this.pitchAngleDegrees = pitch;           
    }

    public Pose2d getPose2d() {
        return new Pose2d(forwardDistance, rightDistance, Rotation2d.fromDegrees(yawAngleDegrees));
    }

    public void setYawAngleDegrees(double yawAngleDegrees){
        this.yawAngleDegrees = yawAngleDegrees;
    }
    
    public double getYawAngleDegrees(){
        return yawAngleDegrees;
    }

    public void setPitchAngleDegrees(double tiltAngleDegrees){
        this.pitchAngleDegrees = tiltAngleDegrees;
    }
    
    public double getPitchAngleDegrees(){
        return pitchAngleDegrees;
    }
    public double getForward() {
        return forwardDistance;
    }
    public double getRight() {
        return rightDistance;
    }
    
}
