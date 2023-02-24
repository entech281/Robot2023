package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotConstants;

public class GripperSubsystem extends EntechSubsystem{

	private DoubleSolenoid gripperSolenoid;	
	private int gripperSolenoidCounter;
	private GripperState gripperState;
	private boolean enabled = false;
	private final int SOLENOID_HIT_COUNT = 10;
	public enum GripperState { kClose, kOpen }
	
	@Override
	public void initialize() {
        gripperSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotConstants.PNEUMATICS.GRIPPER_OPEN, RobotConstants.PNEUMATICS.GRIPPER_CLOSE);
		
	}
	
    public void setGripperState(GripperState state) {
	    if (state != gripperState) {
	      gripperSolenoidCounter = 0;
	      gripperState = state;
	    }
	}
	public void openGripper() {
	    if (enabled) {
		    setGripperState(GripperState.kOpen);	    	
	    }
	}
	
	public void closeGripper() {
	    if (enabled) {
		    setGripperState(GripperState.kClose);	    	
	    }
	}
	
	@Override
	public SubsystemStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
