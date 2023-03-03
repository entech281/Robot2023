package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;
import com.revrobotics.SparkMaxPIDController;

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
  public ArmSubsystem( CANSparkMax motor, SparkMaxPositionController controller) {
	  this.enabled=true;
	  this.telescopeMotor = motor;
	  this.positionController = controller;
	  
  }  
  
  public SparkMaxPositionController getPositionController() {
	return positionController;
  }

//for match
  public ArmSubsystem() {
  }  
  
  @Override
  public void initialize() {
	if ( enabled ) {
		double RPM  = 1.0;
		double ACCEL = 1500;
		int SMART_MOTION_SLOT=0;
		
		//following example from here:
		//https://github.com/REVrobotics/SPARK-MAX-Examples/blob/master/Java/Smart%20Motion%20Example/src/main/java/frc/robot/Robot.java
	    telescopeMotor = new CANSparkMax(RobotConstants.CAN.TELESCOPE_MOTOR_ID, MotorType.kBrushless);
	    SparkMaxPIDController pid = telescopeMotor.getPIDController();
	    pid.setP(TUNING.P_GAIN);
	    pid.setI(TUNING.I_GAIN);
	    pid.setD(TUNING.D_GAIN);
	    pid.setOutputRange(-1.0,1.0);
	    //pid.setSmartMotionMaxVelocity(10000*RPM, SMART_MOTION_SLOT);
	    //pid.setSmartMotionMaxAccel(ACCEL, SMART_MOTION_SLOT);
	    //pid.setSmartMotionMinOutputVelocity(0, SMART_MOTION_SLOT);
	    //pid.setSmartMotionMaxAccel(1500, SMART_MOTION_SLOT);

	    telescopeMotor.set(0);
	    telescopeMotor.setIdleMode(IdleMode.kBrake);
	    telescopeMotor.clearFaults();
	    telescopeMotor.setInverted(ARM.SETTINGS.MOTOR_REVERSED);
	    telescopeMotor.getEncoder().setPositionConversionFactor( 1.0/ ARM.SETTINGS.COUNTS_PER_METER);
	    telescopeMotor.getEncoder().setVelocityConversionFactor(1.0 / ARM.SETTINGS.COUNTS_PER_METER);
	    PositionControllerConfig conf = new PositionControllerConfig.Builder("ARM")
	    	.withSoftLimits(ARM.POSITION_PRESETS.MIN_METERS, ARM.POSITION_PRESETS.MAX_METERS)
	    	.withHomingOptions(ARM.HOMING.HOMING_SPEED_PERCENT,ARM.HOMING.HOME_POSITION_BACKOFF_METERS ,ARM.HOMING.HOME_POSITION_METERS )
	    	.withPositionTolerance(ARM.SETTINGS.MOVE_TOLERANCE_METERS)
	    	.build();	    		

	    positionController = new SparkMaxPositionController(
	    		telescopeMotor,
	    		conf,
				telescopeMotor.getReverseLimitSwitch(Type.kNormallyOpen),	    		
				telescopeMotor.getForwardLimitSwitch(Type.kNormallyOpen),
	    		telescopeMotor.getEncoder()
	    );
	    
	}
  }  

  public void setMotorSpeed(double speed) {
	  telescopeMotor.set(speed);
  }  
  
  public ArmStatus getStatus(){
	  if ( enabled) {
		  return new ArmStatus(positionController.getActualPosition()); 
	  }
	  else {
		  return new ArmStatus(RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN);
	  }      
  }
 
  public void requestPosition(double requestedPosition) {
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
  
  public double getRequestedPosition() {
	  if ( isEnabled()) {
		  return positionController.getRequestedPosition();
	  }
	  else {
		  return RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN;
	  }
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
      }
    
  }

  private double getMotorSpeed() {
	  if (enabled) {
		  return telescopeMotor.getAppliedOutput();
	  }
	  return RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN;
  }
  @Override
  public void simulationPeriodic() {
    
  }

}
