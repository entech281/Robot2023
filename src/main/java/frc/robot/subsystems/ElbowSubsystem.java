package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;
import static frc.robot.RobotConstants.ELBOW;
import frc.robot.controllers.PositionControllerConfig;
import frc.robot.controllers.SparkMaxPositionController;


public class ElbowSubsystem extends EntechSubsystem{

	  private CANSparkMax elbowMotor;
	  private SparkMaxPositionController positionController;
	  private boolean enabled = false;	
	
	
	  @Override
	  public void initialize() {
		if ( enabled ) {
			elbowMotor = new CANSparkMax(RobotConstants.CAN.TELESCOPE_MOTOR_ID, MotorType.kBrushed);
			elbowMotor.getPIDController().setP(ELBOW.TUNING.P_GAIN);
			elbowMotor.getPIDController().setI(ELBOW.TUNING.I_GAIN);
			elbowMotor.getPIDController().setD(ELBOW.TUNING.D_GAIN);
			elbowMotor.setSmartCurrentLimit(ELBOW.SETTINGS.MAX_SPIKE_CURRENT);
		    
		    positionController = new SparkMaxPositionController(elbowMotor,
		    new PositionControllerConfig.Builder("ELBOW")
		    	.withHomingOptions(ELBOW.HOMING.HOMING_SPEED_PERCENT,ELBOW.HOMING.HOME_POSITION_BACKOFF_COUNTS ,ELBOW.HOMING.HOME_POSITION_COUNTS )
		    	.withPositionTolerance(ELBOW.SETTINGS.MOVE_TOLERANCE_COUNTS)
		    	.withReversed(ELBOW.SETTINGS.MOTOR_REVERSED)
		    	.withSoftLimits(ELBOW.HOMING.MIN_POSITION_COUNTS, ELBOW.HOMING.MAX_POSITION_COUNTS)
		    	.build()	    		
		    );
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
	  public SubsystemStatus getStatus() {
		 return new ElbowStatus(getActualPosition());
	  }

}
