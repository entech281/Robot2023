package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotConstants;

public class BrakeSubsystem extends EntechSubsystem {
	
	private Solenoid brakeSolenoid;
	
	private BrakeState brakeStatus;
	
	public BrakeState getBrakeStatus() {
		return brakeStatus;
	}

	private boolean enabled = true;
	
	public enum BrakeState { kRetract, kDeploy }
	
	@Override
	public void initialize() {
		if (enabled ) {
			brakeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 
					RobotConstants.PNEUMATICS.BRAKE_SOLENOID);
            brakeStatus = BrakeState.kRetract;
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
  	      builder.addBooleanProperty("Brake deployed",this::isBrakeDeployed, null);
  	  }
    }	
	
	private void handleSolenoid() {
          if (brakeStatus == BrakeState.kDeploy) {
        	  brakeSolenoid.set(true);
          } else 
          {
        	  brakeSolenoid.set(false);       	  
          } 
	}

	public boolean isBrakeDeployed() {
		return brakeStatus == BrakeState.kDeploy;
	}
	
    public void setBrakeState(BrakeState state) {
	    brakeStatus = state;
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
