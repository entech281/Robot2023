package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;
import frc.robot.RobotConstants.ARM;
import frc.robot.controllers.PositionControllerConfig;
import frc.robot.controllers.SparkMaxPositionController;

import static frc.robot.RobotConstants.ARM.*;
/**
 *
 * @author dcowden
 */
public class ArmSubsystem extends EntechSubsystem{
	

  private CANSparkMax telescopeMotor;
  private SparkMaxPositionController positionController;
  private boolean enabled = false;
  
  //for unit testing
  public ArmSubsystem( CANSparkMax motor, PositionControllerConfig config) {
	  this.enabled=true;
	  this.telescopeMotor = motor;
	  this.positionController = new SparkMaxPositionController(motor,config);
  }  

  //for match
  public ArmSubsystem() {
  }
  
  @Override
  public void initialize() {
	if ( enabled ) {
	    telescopeMotor = new CANSparkMax(RobotConstants.CAN.TELESCOPE_MOTOR_ID, MotorType.kBrushed);
	    telescopeMotor.getPIDController().setP(TUNING.P_GAIN);
	    telescopeMotor.getPIDController().setI(TUNING.I_GAIN);
	    telescopeMotor.getPIDController().setD(TUNING.D_GAIN);
	    telescopeMotor.setSmartCurrentLimit(SETTINGS.MAX_SPIKE_CURRENT);
	    
	    positionController = new SparkMaxPositionController(telescopeMotor,
	    new PositionControllerConfig.Builder("ARM")
	    	.withHomingOptions(ARM.HOMING.HOMING_SPEED_PERCENT,ARM.HOMING.HOME_POSITION_BACKOFF_COUNTS ,ARM.HOMING.HOME_POSITION_COUNTS )
	    	.withPositionTolerance(ARM.SETTINGS.MOVE_TOLERANCE_COUNTS)
	    	.withReversed(ARM.SETTINGS.MOTOR_REVERSED)
	    	.withSoftLimits(ARM.HOMING.MIN_POSITION_COUNTS, ARM.HOMING.MAX_POSITION_COUNTS)
	    	.build()	    		
	    );
	}
  }  
  
  public ArmStatus getStatus(){
      return new ArmStatus(positionController.getActualPosition());
  }
 
  public void requestPosition(int requestedPosition) {
	  positionController.requestPosition(requestedPosition);
  }
  
  public void stop() {
	  positionController.stop();
  }
  
  public boolean isAtRequestedPosition() {
	  return positionController.isAtRequestedPosition();
  }  
  
  
  public void periodic() {	 
	  if (enabled ) {
		  positionController.update();
	  }
  }
    
  @Override
  public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType(getName());
	  positionController.initSendable(builder);	      
  }
  
  public boolean isHomed() {
	  return positionController.isHomed();
  }
  
  public int getActualPosition() {
	  return positionController.getActualPosition();
  }
  
  public boolean inMotion() {
	  return positionController.inMotion();
  }
 

  @Override
  public void simulationPeriodic() {
    
  }

}
