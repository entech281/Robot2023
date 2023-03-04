package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotConstants;

public class GripperSubsystem extends EntechSubsystem {

	private DoubleSolenoid gripperSolenoid;	
	private int gripperSolenoidCounter;
	private GripperState gripperState;
	
	public GripperState getGripperState() {
		return gripperState;
	}

	private boolean enabled = false;
	private final int SOLENOID_HIT_COUNT = 10;
	
	public enum GripperState { kClose, kOpen }
	
	@Override
	public void initialize() {
		if (enabled ) {
			gripperSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotConstants.PNEUMATICS.GRIPPER_OPEN, RobotConstants.PNEUMATICS.GRIPPER_CLOSE);
		}
		
	}
	
	@Override
	public void periodic() {
	  if ( enabled ) {
		  handleSolenoid();
	  }
	}
	
    @Override
    public void initSendable(SendableBuilder builder) {
  	  if ( enabled ) {
  	      builder.setSmartDashboardType(getName());		  
  	      builder.addBooleanProperty("Open", this::isOpen, null);
  	  }
    }	
	
	private void handleSolenoid() {
	      if (gripperSolenoidCounter < SOLENOID_HIT_COUNT) {
	          gripperSolenoidCounter += 1;
	          if (gripperState == GripperState.kOpen) {
	              gripperSolenoid.set(DoubleSolenoid.Value.kForward);
	          } else if (gripperState == GripperState.kClose) {
	              gripperSolenoid.set(DoubleSolenoid.Value.kReverse);
	          } else {
	              gripperSolenoid.set(DoubleSolenoid.Value.kOff);
	          }
	      } else {
            gripperSolenoid.set(DoubleSolenoid.Value.kOff);
          }		
	}
    public void setGripperState(GripperState state) {
	    if (state != gripperState) {
	      gripperSolenoidCounter = 0;
	      gripperState = state;
	    }
	}
    public boolean isOpen() {
    	return gripperState == GripperState.kOpen;
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
