package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;
import frc.robot.controllers.PositionController;
import frc.robot.controllers.SparkMaxPositionController;

/**
 *
 * @author dcowden
 */
public class ArmSubsystem extends EntechSubsystem{

  private CANSparkMax telescopeMotor;
  private PositionController controller;
  
  public interface Presets {
	  public static double HOME =  0.0;
	  public static double CARRY = 20.0;
	  public static double SCORE_LOW = 20.0;
	  public static double SCORE_MIDDLE = 85.0;
	  public static double SCORE_HIGH = 95.0;
  }
  
  public static int TOLERANCE_COUNTS = 50; 
  private boolean enabled = false;
  private boolean homed = false;

  
  public ArmStatus getStatus(){
      return new ArmStatus();
  }
  public boolean isHome() {
	  return homed;
  }
  
  public void home() {
	  controller.setDesiredPosition(Presets.HOME);
  }
  
  public void carry() {
	  goToPositionIfHomed(Presets.CARRY);
  }
  
  public void scoreLow() {
	  goToPositionIfHomed(Presets.SCORE_LOW);
  }
  
  public void scoreMiddle() {
	  goToPositionIfHomed(Presets.SCORE_MIDDLE);
  }
  
  public void scoreHigh() {
	  goToPositionIfHomed(Presets.SCORE_HIGH);
  }
  
  private void goToPositionIfHomed(double position) {
	  if ( isHome()) {
		  controller.setDesiredPosition(position);
	  }
	  
  }
  @Override
  public void initialize() {
	if ( enabled ) {
	    telescopeMotor = new CANSparkMax(RobotConstants.ARM.TELESCOPE_MOTOR_ID, MotorType.kBrushed);
	    controller = new SparkMaxPositionController(telescopeMotor,false,TOLERANCE_COUNTS);
	}

  }
  public void stop() {
	  telescopeMotor.set(0);
  }
  public void reset() {
	  controller.resetPosition();
  }
  public boolean isAtLowerLimit() {
	  return controller.isAtLowerLimit();
  }

  public boolean isAtUpperLimit() {
	  return controller.isAtUpperLimit();
  }
  
  @Override
  public void initSendable(SendableBuilder builder) {
	  if ( enabled ) {
	      builder.setSmartDashboardType(getName());
	      builder.addStringProperty("Arm:", () -> { return controller+""; }, null);		  
	      builder.addBooleanProperty("InMotion", () -> { return controller.isInMotion(); }, null);
	      builder.addBooleanProperty("Enabled", () -> { return controller.isEnabled(); }, null);
	  }
  }

  @Override
  public void periodic() {
     if (enabled ) {
    	 checkForHomed();
    	 checkArrivedPosition();
     }
  }

  private void checkArrivedPosition() {
	  if ( enabled ) {
		  if (controller.isAtDesiredPosition()) {
			  stop();
		  }
	  }
  }
  private void checkForHomed() {
      if (isAtLowerLimit()) {
          reset();
          homed = true;
      }
      if (!isHome()) {
     	 home();
      }	  
  }
  
  @Override
  public void simulationPeriodic() {
    
  }

}
