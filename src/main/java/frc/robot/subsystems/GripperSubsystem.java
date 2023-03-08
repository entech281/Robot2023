package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotConstants;

public class GripperSubsystem extends EntechSubsystem {

	private DoubleSolenoid leftGripperSolenoid;	
	private DoubleSolenoid rightGripperSolenoid;
	
	private int gripperSolenoidCounter;
	private GripperState gripperState;
	
	public GripperState getGripperState() {
		return gripperState;
	}

	private boolean enabled = true;
	private final int SOLENOID_HIT_COUNT = 20;
	
	public enum GripperState { kClose, kOpen }
	
	@Override
	public void initialize() {
		if (enabled ) {
			leftGripperSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotConstants.PNEUMATICS.LEFT_GRIPPER_OPEN, RobotConstants.PNEUMATICS.LEFT_GRIPPER_CLOSE);
			rightGripperSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotConstants.PNEUMATICS.RIGHT_GRIPPER_OPEN, RobotConstants.PNEUMATICS.RIGHT_GRIPPER_CLOSE);
		}
		
	}
	
	@Override
	public void periodic() {
	  if ( enabled ) {
		  handleSolenoid();
	  }
	}
	
	public void setSolenoids(DoubleSolenoid.Value newValue) {
        leftGripperSolenoid.set(newValue);
        rightGripperSolenoid.set(newValue);		
	}
	
    @Override
    public void initSendable(SendableBuilder builder) {
  	  if ( enabled ) {
  	      builder.setSmartDashboardType(getName());		  
  	      builder.addBooleanProperty("Open", this::isOpen, this::setOpen);
  	  }
    }	
	
	private void handleSolenoid() {
	      if (gripperSolenoidCounter < SOLENOID_HIT_COUNT) {
	          gripperSolenoidCounter += 1;
	          if (gripperState == GripperState.kOpen) {
	        	  setSolenoids(DoubleSolenoid.Value.kForward);
	          } else if (gripperState == GripperState.kClose) {
	        	  setSolenoids(DoubleSolenoid.Value.kReverse);	        	  
	          } else {
	        	  setSolenoids(DoubleSolenoid.Value.kOff);	
	          }
	      } else {
	    	  setSolenoids(DoubleSolenoid.Value.kOff);
          }		
	}
    public void setGripperState(GripperState state) {
    	//this guards against shorting a solenoid by holding it too long/often
	    if (state != gripperState) {
	      gripperSolenoidCounter = 0;
	      gripperState = state;
	    }
	}
    public boolean isOpen() {
    	return gripperState == GripperState.kOpen;
    }
	public void setOpen(boolean open) {
		//VERY IMPORTANT to use setGripperState here,
		//so we only trigger solendoids on a changed value
		if (open) {
			setGripperState(GripperState.kOpen);
		}
		else {
			setGripperState(GripperState.kClose);
		}
	}
	
	@Override
	public GripperStatus getStatus() {
		return new GripperStatus(gripperState);
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	
	
}
