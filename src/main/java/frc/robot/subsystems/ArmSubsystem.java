package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

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
  private boolean enabled = true;
  
  //for unit testing
  public ArmSubsystem( CANSparkMax motor, PositionControllerConfig config) {
	  this.enabled=true;
	  this.telescopeMotor = motor;
	  this.positionController = new SparkMaxPositionController(config);
	  
  }  

  
  //for match
  public ArmSubsystem() {
	    positionController = new SparkMaxPositionController(
	    new PositionControllerConfig.Builder("ARM")
	    	.withHomingOptions(ARM.HOMING.HOMING_SPEED_PERCENT,ARM.HOMING.HOME_POSITION_BACKOFF_COUNTS ,ARM.HOMING.HOME_POSITION_COUNTS )
	    	.withPositionTolerance(ARM.SETTINGS.MOVE_TOLERANCE_COUNTS)
	    	.withReversed(ARM.SETTINGS.MOTOR_REVERSED)
	    	.withLimitSwitchTypes(Type.kNormallyOpen,Type.kNormallyOpen)
	    	.withSwappedLimitSwitches(true)
	    	.withSoftLimits(ARM.HOMING.MIN_POSITION_COUNTS, ARM.HOMING.MAX_POSITION_COUNTS)
	    	.build()	    		
	    );

  }  
  
  @Override
  public void initialize() {
	if ( enabled ) {
	    telescopeMotor = new CANSparkMax(RobotConstants.CAN.TELESCOPE_MOTOR_ID, MotorType.kBrushless);
	    telescopeMotor.getPIDController().setP(TUNING.P_GAIN);
	    telescopeMotor.getPIDController().setI(TUNING.I_GAIN);
	    telescopeMotor.getPIDController().setD(TUNING.D_GAIN);
	    telescopeMotor.setSmartCurrentLimit(SETTINGS.MAX_SPIKE_CURRENT);
	    positionController.setSparkMax(telescopeMotor);
	    telescopeMotor.set(0);
	    telescopeMotor.setIdleMode(IdleMode.kBrake);
	    telescopeMotor.clearFaults();
	}
  }  
  
  public ArmStatus getStatus(){
      return new ArmStatus(positionController.getActualPosition());
  }
 
  public void requestPosition(int requestedPosition) {
	  positionController.requestPosition(requestedPosition);
  }
  
  public void forgetHome() {
	  positionController.forgetHome();
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
