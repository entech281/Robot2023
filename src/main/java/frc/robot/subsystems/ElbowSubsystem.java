package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;
import static frc.robot.RobotConstants.ELBOW;
import frc.robot.controllers.PositionControllerConfig;
import frc.robot.controllers.SparkMaxPositionController;


public class ElbowSubsystem extends EntechSubsystem{

	  private CANSparkMax elbowMotor;
	  private SparkMaxPositionController positionController;
	  private boolean enabled = false;	
	
	  
	  //for unit testing
	  public ElbowSubsystem( CANSparkMax motor, PositionControllerConfig config) {
		  this.enabled=true;
		  this.elbowMotor = motor;
		  this.positionController = new SparkMaxPositionController(config);
	  }  	  
	
	  //for match
	  public ElbowSubsystem () {
		    positionController = new SparkMaxPositionController(
		    new PositionControllerConfig.Builder("ELBOW")
		    	.withHomingOptions(ELBOW.HOMING.HOMING_SPEED_PERCENT,ELBOW.HOMING.HOME_POSITION_BACKOFF_COUNTS ,ELBOW.HOMING.HOME_POSITION_COUNTS )
		    	.withPositionTolerance(ELBOW.SETTINGS.MOVE_TOLERANCE_COUNTS)
		    	.withReversed(ELBOW.SETTINGS.MOTOR_REVERSED)
		    	.withLimitSwitchTypes(Type.kNormallyOpen,Type.kNormallyOpen)
		    	.withSwappedLimitSwitches(false)		    	
		    	.withSoftLimits(ELBOW.HOMING.MIN_POSITION_COUNTS, ELBOW.HOMING.MAX_POSITION_COUNTS)
		    	.build()	    		
		    );		  
		  
	  }
	  @Override
	  public void initialize() {
		if ( enabled ) {
			elbowMotor = new CANSparkMax(RobotConstants.CAN.TELESCOPE_MOTOR_ID, MotorType.kBrushed);
			elbowMotor.getPIDController().setP(ELBOW.TUNING.P_GAIN);
			elbowMotor.getPIDController().setI(ELBOW.TUNING.I_GAIN);
			elbowMotor.getPIDController().setD(ELBOW.TUNING.D_GAIN);
			elbowMotor.setSmartCurrentLimit(ELBOW.SETTINGS.MAX_SPIKE_CURRENT);
			positionController.setSparkMax(elbowMotor);
		}
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
	  
	  @Override
	  public ElbowStatus getStatus() {
		 return new ElbowStatus(getActualPosition());
	  }

}
