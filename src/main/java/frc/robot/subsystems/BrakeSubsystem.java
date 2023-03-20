package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotConstants;

public class BrakeSubsystem extends EntechSubsystem {
	
	private DoubleSolenoid brakeSolenoid;
	
	private int BrakeSolenoidCounter;
	private BrakeState brakeStatus;
	
	public BrakeState getBrakeStatus() {
		return brakeStatus;
	}

	private boolean enabled = false;
	private final int SOLENOID_HIT_COUNT = 20;
	
	public enum BrakeState { kRetract, kDeploy, kUnknown }
	
	@Override
	public void initialize() {
		if (enabled ) {
			brakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotConstants.PNEUMATICS.BRAKE_SOLENOID_IN, RobotConstants.PNEUMATICS.BRAKE_SOLENOID_OUT);
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
        brakeSolenoid.set(newValue);		
	}
	
    @Override
    public void initSendable(SendableBuilder builder) {
  	  if ( enabled ) {
  	      builder.setSmartDashboardType(getName());		 
  	  }
    }	
	
	private void handleSolenoid() {
	      if (BrakeSolenoidCounter < SOLENOID_HIT_COUNT) {
	          BrakeSolenoidCounter += 1;
	          if (brakeStatus == BrakeState.kDeploy) {
	        	  setSolenoids(DoubleSolenoid.Value.kForward);
	          } else if (brakeStatus == BrakeState.kRetract) {
	        	  setSolenoids(DoubleSolenoid.Value.kReverse);	        	  
	          } else {
	        	  setSolenoids(DoubleSolenoid.Value.kOff);	
	          }
	      } else {
	    	  setSolenoids(DoubleSolenoid.Value.kOff);
        }		
	}

    public void setBrakeState(BrakeState state) {
    	//this guards against shorting a solenoid by holding it too long/often
	    if (state != brakeStatus) {
	      BrakeSolenoidCounter = 0;
	      brakeStatus = state;
	    }
	}

    public boolean isOpen() {
    	return brakeStatus == BrakeState.kDeploy;
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
