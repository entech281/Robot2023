package frc.robot.subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;

public class ElbowStatus implements Sendable, SubsystemStatus{

    private double verticalAngle= RobotConstants.ARM.MIN_ANGLE_DEGREES;
    
	@Override
	public void initSendable(SendableBuilder sb) {
        sb.addDoubleProperty("Vertical Angle", this::getVerticalAngle, null);
		
	}
    public void setVerticalAngle(double verticalAngle){
        this.verticalAngle = verticalAngle;
    }

    public double getVerticalAngle(){
        return verticalAngle;
    }
	
}
