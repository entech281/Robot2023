package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;

/**
 *
 * @author dcowden
 */
public class ArmSubsystem extends EntechSubsystem{

  private CANSparkMax elbowMotor;
  private CANSparkMax telescopeMotor;
  private boolean enabled = false;
  
  public ArmStatus getStatus(){
      return new ArmStatus();
  }
  
  @Override
  public void initialize() {
	if ( enabled ) {
	    elbowMotor = new CANSparkMax(RobotConstants.ARM.ELBOW_MOTOR_ID, MotorType.kBrushed);
	    telescopeMotor = new CANSparkMax(RobotConstants.ARM.TELESCOPE_MOTOR_ID, MotorType.kBrushed);

	    elbowMotor.setInverted(false);
	    telescopeMotor.setInverted(false);		
	}

  }

  @Override
  public void initSendable(SendableBuilder builder) {
	  if ( enabled ) {
	      builder.setSmartDashboardType(getName());
	      builder.addDoubleProperty("Elbow Motor", () -> { return elbowMotor.get(); }, null);
	      builder.addDoubleProperty("Telescope Motor", () -> { return telescopeMotor.get(); }, null);		  
	  }
  }

  @Override
  public void periodic() {
     if (enabled ) {
    	 
     }
  }

  @Override
  public void simulationPeriodic() {
    
  }

  private boolean isAtLowerElbowLimit() {
	  if ( enabled ) {
		  return elbowMotor.getReverseLimitSwitch(Type.kNormallyOpen).isPressed();
	  }
	  else {
		  return false;
	  }
    
  }

  private boolean isAtFarTelescopeLimit() {
	  if (enabled) {
		  return telescopeMotor.getReverseLimitSwitch(Type.kNormallyOpen).isPressed();
	  }
	  else {
		  return false;
	  }
  }
}
