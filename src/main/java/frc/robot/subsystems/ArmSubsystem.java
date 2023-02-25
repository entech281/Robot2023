package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;
import frc.robot.controllers.PositionControllerConfig;
import frc.robot.controllers.HomingController;
import frc.robot.controllers.HomingControllerConfig;
import frc.robot.controllers.PositionController;
import frc.robot.controllers.SparkMaxHomingController;
import frc.robot.controllers.SparkMaxPositionController;

/**
 *
 * @author dcowden
 */
public class ArmSubsystem extends EntechSubsystem{
	
  //reality note:  
  // 42 counts per ref
  // gearbox: 48 to 1
  // sprocket diameter approx. 1.5" 
  // -- 4.71 inch/ref --> ABOUT 0.002 inches per count
  private CANSparkMax telescopeMotor;
  private PositionController positionController;
  private HomingController homingController;
  
  public interface PositionPreset {
	  public static int MIN = 0;
	  public static int HOME =  0;
	  public static int CARRY = 20;
	  public static int SCORE_LOW = 20;
	  public static int SCORE_MIDDLE = 85;
	  public static int SCORE_HIGH = 9;
	  public static int MAX = 1000;
  }
  
  public static int TOLERANCE_COUNTS = 5; 
  private boolean enabled = false;
  private boolean homed = false;

  //for unit testing
  public ArmSubsystem(HomingController homingController, PositionController positionController, CANSparkMax motor) {
	  this.positionController=positionController;
	  this.homingController = homingController;
	  this.enabled=true;
	  this.telescopeMotor = motor;
  }  

  //for match
  public ArmSubsystem() {
	  
  }
  
  @Override
  public void initialize() {
	if ( enabled ) {
	    telescopeMotor = new CANSparkMax(RobotConstants.CAN.TELESCOPE_MOTOR_ID, MotorType.kBrushed);
	    positionController = new SparkMaxPositionController(telescopeMotor,false,TOLERANCE_COUNTS,PositionPreset.MIN, PositionPreset.MAX);
	    PositionControllerConfig config = new PositionControllerConfig.Builder()
	    		.withBackoffDistanceCounts(10)
	    		.withHomePositionCounts(0)
	    		.withHomingSpeedPercent(25.0)
	    		.withReversed(false)
	    		.build();
	    		
	    homingController = new SparkMaxHomingController(telescopeMotor,config);
	}
  }  
  
  public ArmStatus getStatus(){
	  //TODO: compute actual position in counts
      return new ArmStatus();
  }
 
  
  public void home() {
	  homingController.home();
  }
  
  public boolean isInMotion() {
	  return positionController.isInMotion();
  }
  
  public void setPosition(int desiredPosition) {
	  if ( homingController.isHome()) {
		  positionController.setDesiredPosition(desiredPosition);		  
	  }
	  else {
		  
	  }
  }
  public boolean isHomed() {
	  return homingController.isHome();
  }
  public void stop() {
	  telescopeMotor.set(0);
  }
  
  protected void reset() {
	  positionController.resetPosition();
  }
  
  public boolean isAtDesiredPosition() {
	  return positionController.isAtDesiredPosition();
  }  
  
  @Override
  public void initSendable(SendableBuilder builder) {
	  if ( enabled ) {
	      builder.setSmartDashboardType(getName());
	      builder.addStringProperty("Arm:", () -> { return positionController+""; }, null);		  
	      builder.addBooleanProperty("InMotion", () -> { return positionController.isInMotion(); }, null);
	      builder.addBooleanProperty("Enabled", () -> { return positionController.isEnabled(); }, null);
	      builder.addBooleanProperty("Homed", () -> { return isHomed(); }, null);
	  }
  }

  @Override
  public void periodic() {	 
     if (enabled ) {
    	 homingController.update();
     }
  }

  private void checkArrivedPosition() {
	  if ( enabled ) {
		  if (positionController.isAtDesiredPosition()) {
			  stop();
		  }
	  }
  }

  
  @Override
  public void simulationPeriodic() {
    
  }

}
