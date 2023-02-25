package frc.robot.subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;

/**
 *
 * @author dcowden
 */
public class ArmStatus implements Sendable, SubsystemStatus{

    private double armExtension = RobotConstants.ARM.MIN_EXTENSION_METERS;

    public void setArmExtension(double armExtension){
        this.armExtension = armExtension;
    }

    public double getArmExtension(){
        return armExtension;
    }

    @Override
    public void initSendable(SendableBuilder sb) {
        sb.addDoubleProperty("ArmExtension", this::getArmExtension, null);
    }

}
