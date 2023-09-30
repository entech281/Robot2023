package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import frc.robot.RobotConstants;

public class BrakeSubsystem extends EntechSubsystem {
	
	private DoubleSolenoid brakeSolenoid;
	
	private BrakeState brakeStatus;
	private int brakeSolenoidCounter;

	private final int SOLENOID_HIT_COUNT = 20;
	
	public BrakeState getBrakeStatus() {
		return brakeStatus;
	}

	private boolean enabled = true;
	
	public enum BrakeState { kRetract, kDeploy }
	
	@Override
	public void initialize() {
		if (enabled) {
			brakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
							RobotConstants.PNEUMATICS.BRAKE_SOLENOID_DEPLOYED,
							RobotConstants.PNEUMATICS.BRAKE_SOLENOID_RETRACTED);

            brakeStatus = BrakeState.kRetract;
		}
	}
	
	@Override
	public void periodic() {
	  if ( enabled ) {
		  handleSolenoid();
	  }
	}

	private void setBrakeSolenoids(DoubleSolenoid.Value newValue) {
        brakeSolenoid.set(newValue);
	}
	
    @Override
    public void initSendable(SendableBuilder builder) {
  	  if ( enabled ) {
  	      builder.setSmartDashboardType(getName());
  	      builder.addBooleanProperty("Brake deployed",this::isBrakeDeployed, null);
  	  }
    }	

	private void handleSolenoid() {
		if (brakeSolenoidCounter < SOLENOID_HIT_COUNT) {
			brakeSolenoidCounter += 1;
			if (brakeStatus == BrakeState.kDeploy) {
				setBrakeSolenoids(DoubleSolenoid.Value.kForward);
			} else if (brakeStatus == BrakeState.kRetract) {
				setBrakeSolenoids(DoubleSolenoid.Value.kReverse);	        	  
			} else {
				setBrakeSolenoids(DoubleSolenoid.Value.kOff);
			}
		} else {
			setBrakeSolenoids(DoubleSolenoid.Value.kOff);
		}		
  }

	public boolean isBrakeDeployed() {
		return brakeStatus == BrakeState.kDeploy;
	}
	
    public void setBrakeState(BrakeState state) {
		if (!DriverStation.isDisabled()){
			if (state != brakeStatus) {
				brakeSolenoidCounter = 0;
				brakeStatus = state;
			  }
			brakeStatus = state;	
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
