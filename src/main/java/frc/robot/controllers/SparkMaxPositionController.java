package frc.robot.controllers;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;

public class SparkMaxPositionController implements Sendable, PositionController{
	public enum MotionState { IDLE, FINDING_LIMIT, BACKING_OFF, HOMED }
	
	public SparkMaxPositionController ( CANSparkMax spark, PositionControllerConfig config) {
		this.spark = spark;
		this.config = config;
	}
	
	protected CANSparkMax spark;	
    private PositionControllerConfig config;

    private HomingState axisState = HomingState.UNINITIALIZED;
    private int requestedPosition = 0;
    
    public enum HomingState { UNINITIALIZED, FINDING_LIMIT, BACKING_OFF, HOMED }    
    
    public static final int CAN_TIMEOUT_MILLIS = 1000;    
    private boolean enabled = true;
    
	public boolean isInMotion() {
		return spark.getEncoder().getVelocity() > 0;
	}    
    
    @Override
	public int getActualPosition() {
	  return (int)spark.getEncoder().getPosition();
    }

    private boolean isPositionAcceptable(double position) {
	  	return position >= config.getMinPositionCounts() && position <= config.getMaxPositionCounts() ;
 	}    
  
	@Override
	public double getRequestedPosition() {
        return requestedPosition;
    }

	  @Override
	public boolean isAtRequestedPosition() {
		  double actualPosition = spark.getEncoder().getPosition();
		  return Math.abs(actualPosition - requestedPosition) < config.getPositionToleranceCounts();
	  }  
	
    protected double correctDirection(double input){
        if ( config.isReversed() ){
            return -input;
        }
        else{
            return input;
        }
    }    

    @Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}    
 

    @Override
	public void update() {	 
  	  if (enabled ) {
      	 switch ( axisState) {
      		 case UNINITIALIZED:
      			 //do nothing
      		 case FINDING_LIMIT:
      			 if ( isAtLowerLimit() ) {
      				spark.getEncoder().setPosition(0);
      				setPositionInternal(config.getBackoffCounts());
      				axisState = HomingState.BACKING_OFF;
      			 }
      		 case BACKING_OFF:
      			 if ( isAtRequestedPosition()) {
      				spark.getEncoder().setPosition(config.getHomePositionCounts());
      				 //telescopeMotor.stopMotor();  future configurable? this will stop the motor. Not doing this leaves the motor on and locked on this position 
      				 axisState = HomingState.HOMED;
      			 }
      		 case HOMED:
      			 setPositionInternal(requestedPosition);
      			 if (isAtLowerLimit()  ) {
      				 stop();
      				 DriverStation.reportWarning("Low Limit Reached! Please Move Axis off the switch. We will home on next comamand." , false);
      				 axisState = HomingState.UNINITIALIZED;
      			 }
      			 if ( isAtUpperLimit() ) {
      				 stop();
      				 DriverStation.reportWarning("Upper Limit Reached! Please Move Axis off the switch. We will home on next comamand." , false);
      				 axisState = HomingState.UNINITIALIZED;
      			 }
  	     }
  	  }
    }    

    private void setPositionInternal(int desiredPosition) {
    	spark.getPIDController().setReference(correctDirection(desiredPosition), CANSparkMax.ControlType.kPosition);
    }    
    
    
    @Override
	public void requestPosition(int requestedPosition) {
  	  if ( isPositionAcceptable(requestedPosition)) {
  		  this.requestedPosition = requestedPosition;		  
  		  if (axisState == HomingState.UNINITIALIZED) {
  			  spark.set(correctDirection(config.getHomingSpeedPercent()));
  			  axisState = HomingState.FINDING_LIMIT;
  		  }		  
  	  }
  	  else {
  		  DriverStation.reportWarning("Invalid Position " + requestedPosition, false);
  	  }
    }
    
    public void stop() {
    	spark.stopMotor();
    }
 
    @Override
	public boolean isAtLowerLimit() {
    	return spark.getReverseLimitSwitch(Type.kNormallyOpen).isPressed();
    }

    @Override
	public boolean isAtUpperLimit() {
    	return spark.getForwardLimitSwitch(Type.kNormallyOpen).isPressed();
    }

    public void resetPosition(){
        spark.getEncoder().setPosition(0);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
	public boolean inMotion() {
  	  return spark.getEncoder().getVelocity() > 0;
    }
    
    @Override
    public void initSendable(SendableBuilder builder) {
  	  if ( enabled ) {
  	      builder.setSmartDashboardType("PositionController:" + config.getName());
  	      builder.addStringProperty("Status:", this::getHomingStateString , null);		  
  	      builder.addBooleanProperty("InMotion", this::inMotion, null);
  	      builder.addDoubleProperty("RequestedPosition", this::getRequestedPosition, null);
  	      builder.addIntegerProperty("ActualPosition", this::getActualPosition, null);
  	      builder.addBooleanProperty("Homed", this::isHomed, null);
  	  }
    }
    
    public String getHomingStateString() {
  	  return axisState+"";
    }
    @Override
	public boolean isHomed() {
  	  return axisState == HomingState.HOMED;
    }
    @Override
	public HomingState getHomingState() {
  	  return axisState;
    }

}
