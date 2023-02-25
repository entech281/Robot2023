package frc.robot.subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;

public class GripperStatus implements Sendable,SubsystemStatus{

    private boolean clawOpen = RobotConstants.ARM.INIT_CLAW_STATE;

	@Override
	public void initSendable(SendableBuilder sb) {
        sb.addBooleanProperty("Claw", this::getClawOpen, null);
	}
    
    public void setClawOpen(boolean clawOpen) {
        this.clawOpen = clawOpen;
    }

    public boolean getClawOpen(){
        return clawOpen;
    }	
}
