package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotConstants;

public class GripperSubsystem extends EntechSubsystem {
	
	private DoubleSolenoid GripperSolenoid;
	
	private int gripperSolenoidCounter;
	private GripperState gripperState;
	
	public GripperState getGripperState() {
		return gripperState;
	}

	private boolean enabled = true;
	private final int SOLENOID_HIT_COUNT = 20;
	
	public enum GripperState { kClose, kOpen, kUnknown }
	
	@Override
	public void initialize() {
		if (enabled ) {
			GripperSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 
					RobotConstants.PNEUMATICS.RIGHT_GRIPPER_OPEN, 
					RobotConstants.PNEUMATICS.RIGHT_GRIPPER_CLOSE);
            gripperState = GripperState.kUnknown;
		}
	}
	
	@Override
	public void periodic() {
	  if ( enabled ) {
		  handleSolenoid();
	  }
	}
	
	public void setSolenoids(DoubleSolenoid.Value newValue) {
        GripperSolenoid.set(newValue);		
	}

	public void toggleGripper() {
		if(getGripperState() == GripperState.kOpen) {
			setGripperState(GripperState.kClose);
		} else {
			setGripperState(GripperState.kOpen);
		}
	}
	
    @Override
    public void initSendable(SendableBuilder builder) {
  	  if ( enabled ) {
  	      builder.setSmartDashboardType(getName());		  
  	      builder.addBooleanProperty("BripperOpen", this::isOpen, this::setOpen);
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
