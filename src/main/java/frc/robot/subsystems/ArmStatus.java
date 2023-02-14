package frc.robot.subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;

/**
 *
 * @author dcowden
 */
public class ArmStatus implements Sendable, SubsystemStatus{

    private boolean clawOpen = RobotConstants.ARM.INIT_CLAW_STATE;
    private double armExtension = RobotConstants.ARM.MIN_EXTENSION_INCHES;
    private double verticalAngle= RobotConstants.ARM.MIN_ANGLE_DEGREES;
    
    
    public void setClawOpen(boolean clawOpen) {
        this.clawOpen = clawOpen;
    }

    public boolean getClawOpen(){
        return clawOpen;
    }

    public void setArmExtension(double armExtension){
        this.armExtension = armExtension;
    }

    public double getArmExtension(){
        return armExtension;
    }
    
    public void setVerticalAngle(double verticalAngle){
        this.verticalAngle = verticalAngle;
    }

    public double getVerticalAngle(){
        return verticalAngle;
    }

    @Override
    public void initSendable(SendableBuilder sb) {
        sb.addBooleanProperty("Claw", this::getClawOpen, null);
        sb.addDoubleProperty("ArmExtension", this::getArmExtension, null);
        sb.addDoubleProperty("Vertical Angle", this::getVerticalAngle, null);
    }

}
