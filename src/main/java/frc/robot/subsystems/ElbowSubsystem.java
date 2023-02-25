package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;
import frc.robot.controllers.PositionController;
import frc.robot.controllers.SparkMaxPositionController;


public class ElbowSubsystem extends EntechSubsystem{
	
	  //for unit testing
	  public ElbowSubsystem(PositionController positionController) {
		  this.positionController=positionController;
	  }
	  
	  //for match
	  public ElbowSubsystem() {
		  
	  }
	  
	  private CANSparkMax elbowMotor;
	  private PositionController positionController;
	  
	  public interface Preset {
		  public static double MIN = 0.0;		  
		  public static double HOME =  0.0;
		  public static double CARRY = 20.0;
		  public static double SCORE_LOW = 20.0;
		  public static double SCORE_MIDDLE = 85.0;
		  public static double SCORE_HIGH = 95.0;
		  public static double MAX = 300.0;		  
	  }
	  
	  public static int TOLERANCE_COUNTS = 50; 
	  
	  private boolean enabled = false;
	  private boolean homed = false;

	  @Override
	  public void initialize() {
		if ( enabled ) {
		    elbowMotor = new CANSparkMax(RobotConstants.CAN.TELESCOPE_MOTOR_ID, MotorType.kBrushed);
		    positionController = new SparkMaxPositionController(elbowMotor,false,TOLERANCE_COUNTS,Preset.MIN, Preset.MAX);
		}

	  }
	  
	  
	  public ElbowStatus getStatus(){
	      return new ElbowStatus();
	  }
	  public boolean isHome() {
		  return homed;
	  }
	  
	  public boolean isInMotion() {
		  return positionController.isInMotion();
	  }
	  
	  public void home() {
		  positionController.setDesiredPosition(Preset.HOME);
	  }
	  
	  public void carry() {
		  goToPositionIfHomed(Preset.CARRY);
	  }
	  
	  public void scoreLow() {
		  goToPositionIfHomed(Preset.SCORE_LOW);
	  }
	  
	  public void scoreMiddle() {
		  goToPositionIfHomed(Preset.SCORE_MIDDLE);
	  }
	  
	  public void scoreHigh() {
		  goToPositionIfHomed(Preset.SCORE_HIGH);
	  }
	  public void setPosition ( double position) {
		  goToPositionIfHomed(position);
	  }
	  private void goToPositionIfHomed(double position) {
		  if ( isHome()) {
			  positionController.setDesiredPosition(position);
		  }
		  
	  }

	  
	  public boolean isAtDesiredPosition() {
		  return positionController.isAtDesiredPosition();
	  }
	  public void stop() {
		  elbowMotor.set(0);
	  }
	  public void reset() {
		  positionController.resetPosition();
	  }
	  public boolean isAtLowerLimit() {
		  return positionController.isAtLowerLimit();
	  }

	  public boolean isAtUpperLimit() {
		  return positionController.isAtUpperLimit();
	  }
	  
	  @Override
	  public void initSendable(SendableBuilder builder) {
		  if ( enabled ) {
		      builder.setSmartDashboardType(getName());
		      builder.addStringProperty("Arm:", () -> { return positionController+""; }, null);		  
		      builder.addBooleanProperty("InMotion", () -> { return positionController.isInMotion(); }, null);
		      builder.addBooleanProperty("Enabled", () -> { return positionController.isEnabled(); }, null);
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
			  if (positionController.isAtDesiredPosition()) {
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
