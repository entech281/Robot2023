package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;
import frc.robot.RobotConstants.ELBOW;
import frc.robot.controllers.PositionControllerConfig;
import frc.robot.controllers.SparkMaxPositionController;


public class ElbowSubsystem extends EntechSubsystem{

	  private CANSparkMax elbowMotor;
	  private SparkMaxPositionController positionController;
	  private boolean enabled = true;	

	  
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
			elbowMotor = new CANSparkMax(RobotConstants.CAN.ELBOW_MOTOR_ID, MotorType.kBrushless);
			elbowMotor.getPIDController().setP(ELBOW.TUNING.P_GAIN);
			elbowMotor.getPIDController().setI(ELBOW.TUNING.I_GAIN);
			elbowMotor.getPIDController().setD(ELBOW.TUNING.D_GAIN);
			elbowMotor.setSmartCurrentLimit(ELBOW.SETTINGS.MAX_SPIKE_CURRENT);

			elbowMotor.set(0);
			elbowMotor.setIdleMode(IdleMode.kBrake);
			elbowMotor.clearFaults();
			elbowMotor.setInverted(ELBOW.SETTINGS.MOTOR_REVERSED);	
		    elbowMotor.getEncoder().setPositionConversionFactor(ELBOW.SETTINGS.COUNTS_PER_DEGREE);
		    elbowMotor.getEncoder().setVelocityConversionFactor(ELBOW.SETTINGS.COUNTS_PER_DEGREE);			
			PositionControllerConfig conf =  new PositionControllerConfig.Builder("ELBOW")
			    	.withHomingOptions(ELBOW.HOMING.HOMING_SPEED_PERCENT,ELBOW.HOMING.HOME_POSITION_BACKOFF_DEGREES ,ELBOW.HOMING.HOME_POSITION_METERS )
			    	.withPositionTolerance(ELBOW.SETTINGS.MOVE_TOLERANCE_DEGREES)  	
			    	.withSoftLimits(ELBOW.HOMING.MIN_POSITION_DEGREES, ELBOW.HOMING.MAX_POSITION_DEGREES)
			    	.withInverted(true)
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
	  
	  public boolean isSafeToExtendArm() {
		  return this.getActualPosition() > ELBOW.POSITION_PRESETS.SAFE_ANGLE;
	  }
	  
	  private double getMotorSpeed() {
		  if (enabled) {
			  return elbowMotor.getAppliedOutput();
		  }
		  return RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN;
	  }
	  
	  public void setMotorSpeed(double speed) {
		  positionController.setMotorSpeed(speed);
	  }
	  
	  public double getRequestedPosition() {
		  if ( isEnabled()) {
			  return positionController.getRequestedPosition();
		  }
		  else {
			  return RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN;
		  }
	  }
	  public void forgetHome() {
		  positionController.forgetHome();
	  }
	  public double getActualPosition() {
		  if ( isEnabled()) {
			  return positionController.getActualPosition();
		  }
		  else {
			  return RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN;
		  }	  
	  }
	  
	  public boolean isEnabled() {
		  return enabled;
	  }
	  
	  public SparkMaxPositionController getPositionController() {
			return positionController;
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
	      builder.addBooleanProperty("Enabled", this::isEnabled, null);
	      if ( enabled ) {
	          builder.addBooleanProperty("AtSetPoint", this::isAtRequestedPosition, null);
	          builder.addDoubleProperty("RequestedPos", this::getRequestedPosition, null);
	          builder.addDoubleProperty("ActualPos", this::getActualPosition, null);  
	          builder.addDoubleProperty("MotorOutput", this::getMotorSpeed, null); 	  
	          builder.addBooleanProperty("CanExtendArm", this::isSafeToExtendArm, null);
	      }
	  }
	 

	  @Override
	  public void simulationPeriodic() {
	    
	  }
	  
	  @Override
	  public ElbowStatus getStatus() {
		  if ( enabled) {
			  return new ElbowStatus(positionController.getActualPosition()); 
		  }
		  else {
			  return new ElbowStatus(RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN);
		  }
	  }

}
