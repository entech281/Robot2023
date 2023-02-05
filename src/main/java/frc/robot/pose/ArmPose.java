package frc.robot.pose;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;

/**
 *
 * @author dcowden
 */
public class ArmPose implements Sendable{
    
    public ArmPose(boolean clawOpen, double armExtension, double verticalAngle){
        this.clawOpen = clawOpen;
        this.armExtension = armExtension;
        this.verticalAngle = verticalAngle;
    }

    private boolean clawOpen = RobotConstants.ARM.INIT_CLAW_STATE;
    private double armExtension = RobotConstants.ARM.MIN_EXTENSION_INCHES;
    private double verticalAngle= RobotConstants.ARM.MIN_ANGLE_DEGREES;
    
    
    public boolean getClawOpen(){
        return clawOpen;
    }

    public double getArmExtension(){
        return armExtension;
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
