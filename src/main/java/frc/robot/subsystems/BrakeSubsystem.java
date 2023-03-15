package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotConstants;

public class BrakeSubsystem extends EntechSubsystem {

	private DoubleSolenoid leftGripperSolenoid;	
	private DoubleSolenoid rightGripperSolenoid;
	
	private int gripperSolenoidCounter;
	private BrakeState brakeStatus;
	
	public BrakeState getBrakeStatus() {
		return brakeStatus;
	}

	private boolean enabled = true;
	private final int SOLENOID_HIT_COUNT = 20;
	
	public enum BrakeState { kIn, kOut, kUnknown }
	
	@Override
	public void initialize() {
		if (enabled ) {
			rightGripperSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotConstants.PNEUMATICS.BRAKE_SOLENOID_IN, RobotConstants.PNEUMATICS.BRAKE_SOLENOID_OUT);
            brakeStatus = BrakeState.kUnknown;
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
  	      builder.addBooleanProperty("BrakeOpen", this::isOpen, this::setOpen);
  	  }
    }	
	
	private void handleSolenoid() {
	      if (gripperSolenoidCounter < SOLENOID_HIT_COUNT) {
	          gripperSolenoidCounter += 1;
	          if (brakeStatus == BrakeState.kOut) {
	        	  setSolenoids(DoubleSolenoid.Value.kForward);
	          } else if (brakeStatus == BrakeState.kIn) {
	        	  setSolenoids(DoubleSolenoid.Value.kReverse);	        	  
	          } else {
	        	  setSolenoids(DoubleSolenoid.Value.kOff);	
	          }
	      } else {
	    	  setSolenoids(DoubleSolenoid.Value.kOff);
          }		
	}

    public void setBrakeStatus(BrakeState state) {
    	//this guards against shorting a solenoid by holding it too long/often
	    if (state != brakeStatus) {
	      gripperSolenoidCounter = 0;
	      brakeStatus = state;
	    }
	}

    public boolean isOpen() {
    	return brakeStatus == BrakeState.kOut;
    }
	
	public void setOpen(boolean open) {
		//VERY IMPORTANT to use setGripperState here,
		//so we only trigger solendoids on a changed value
		if (open) {
			setBrakeStatus(BrakeState.kIn);
		}
		else {
			setBrakeStatus(BrakeState.kOut);
		}
	}
	
	@Override
	public BrakeStatus getStatus() {
		return new BrakeStatus(brakeStatus);
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
