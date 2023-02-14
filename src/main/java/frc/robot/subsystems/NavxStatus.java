package frc.robot.subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 *
 * @author dcowden
 */
public class NavxStatus implements Sendable , SubsystemStatus{
    
    private double yawAngleDegrees = 0.0;


    public void setYawAngleDegrees(double yawAngleDegrees){
        this.yawAngleDegrees = yawAngleDegrees;
    }
    
    public double getYawAngleDegrees(){
        return yawAngleDegrees;
    }


    @Override
    public void initSendable(SendableBuilder sb) {
        sb.addDoubleProperty("Yaw Angle",this::getYawAngleDegrees, null);
    }
    
}