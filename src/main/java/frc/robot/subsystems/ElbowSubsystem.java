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
	  public ElbowSubsystem( CANSparkMax motor, SparkMaxPositionController controller) {
		  this.enabled=true;
		  this.elbowMotor = motor;
		  this.positionController = controller;
	  }  	  
	
	  //for match
	  public ElbowSubsystem () {
		  
	  }
	  @Override
	  public void initialize() {
		if ( enabled ) {
			elbowMotor = new CANSparkMax(RobotConstants.CAN.TELESCOPE_MOTOR_ID, MotorType.kBrushed);
			elbowMotor.getPIDController().setP(ELBOW.TUNING.P_GAIN);
			elbowMotor.getPIDController().setI(ELBOW.TUNING.I_GAIN);
			elbowMotor.getPIDController().setD(ELBOW.TUNING.D_GAIN);
			elbowMotor.setSmartCurrentLimit(ELBOW.SETTINGS.MAX_SPIKE_CURRENT);

			PositionControllerConfig conf =  new PositionControllerConfig.Builder("ELBOW")
			    	.withHomingOptions(ELBOW.HOMING.HOMING_SPEED_PERCENT,ELBOW.HOMING.HOME_POSITION_BACKOFF_METERS ,ELBOW.HOMING.HOME_POSITION_METERS )
			    	.withPositionTolerance(ELBOW.SETTINGS.MOVE_TOLERANCE_METERS)  	
			    	.withSoftLimits(ELBOW.HOMING.MIN_POSITION_METERS, ELBOW.HOMING.MAX_POSITION_METERS)
			    	.build();
			
		    positionController = new SparkMaxPositionController(
		    		elbowMotor,
		    		conf,
		    		elbowMotor.getReverseLimitSwitch(Type.kNormallyOpen),	    		
		    		elbowMotor.getForwardLimitSwitch(Type.kNormallyOpen),
		    		elbowMotor.getEncoder()
		    );			
			
		}
	  }  
	  	 
	  public void requestPosition(double requestedPosition) {
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
	  
	  public double getActualPosition() {
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
