package frc.robot.controllers;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Overview:
 * 
 * This controller allows user to control a position oriented axis, including managed homing to a lower limit switch.
 * 
 * Overall, it works like this:
 *  * user supplies a number of configuration optoins
 *  * user requests moving to a position via requestPosition
 *  
 *  * When the user first requests a position,the axis wil home itself if not homed. This consists of these steps
 *  
 *    (1) move at config.homingSpeed towards the lower limit
 *    (2) when the lower limit switch is pressed, move fowrard config.backoffCounts
 *    (3) when this position is reached, set position to config.homeCounts
 *    (4) execute the users original move, by moving to requestedPosition
 *    
 *   Requests to move outside the soft limits ( config.mincounts and config.maxcounts ) are ignored, but a warning is
 *   logged to driver station 
 * @author davec
 *
 */
public class SparkMaxPositionController implements Sendable, PositionController{

	public enum MotionState { BACKING_OFF, FINDING_LIMIT, HOMED, UNINITIALIZED }
	

	public static final int CAN_TIMEOUT_MILLIS = 1000;	
	
	
	public static final int NO_POSITION = -999;	
	
	private MotionState axisState = MotionState.UNINITIALIZED;
	
	private PositionControllerConfig config;
	
	private boolean enabled = true;	
    private RelativeEncoder encoder;

    private SparkMaxLimitSwitch lowerLimit;
    private int requestedPosition = 0;
    protected CANSparkMax spark;    
    private SparkMaxLimitSwitch upperLimit;
    
    //for unit testing
	public SparkMaxPositionController (CANSparkMax spark,  PositionControllerConfig config, SparkMaxLimitSwitch lowerLimit, SparkMaxLimitSwitch upperLimit, RelativeEncoder encoder) {
		this.spark = spark;		
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.encoder = encoder;
		this.config = config;
	}
    //for stupid wpilib where the max has to be set up in initialize, not in constructor
	public SparkMaxPositionController (  PositionControllerConfig config) {
		this.config = config;
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
	public int getActualPosition() {
    	if ( hasSpark()) {
    		return (int)encoder.getPosition();
    	}
    	else {
    		return NO_POSITION;
    	}
    }
    
	public String getHomingStateString() {
  	  return axisState+"";
    }    
    
    @Override
	public MotionState getMotionState() {
  	  return axisState;
    }

    @Override
	public double getRequestedPosition() {
        return requestedPosition;
    }    
    
    private boolean hasSpark() {
    	return spark != null;
    }
  
	public void home() {
    	requestPosition(config.getHomePositionCounts());
    }

	@Override
    public void initSendable(SendableBuilder builder) {
  	  if ( enabled ) {
  	      builder.setSmartDashboardType("PositionController:" + config.getName());
  	      builder.addStringProperty("Status:", this::getHomingStateString , null);		  
  	      builder.addBooleanProperty("InMotion", this::inMotion, null);
  	      builder.addDoubleProperty("RequestedPos", this::getRequestedPosition, null);
  	      builder.addIntegerProperty("ActualPos", this::getActualPosition, null);
  	      builder.addBooleanProperty("Homed", this::isHomed, null);
  	      builder.addBooleanProperty("UpperLimit", this::isAtLowerLimit, null);
  	      builder.addBooleanProperty("LowerLimit", this::isAtUpperLimit, null);
  	  }
    }  
	
	
	public boolean inMotion() {
		if ( hasSpark() ) {
			return encoder.getVelocity() > 0;
		}
		else {
			return false;
		}
	}
    @Override
	public boolean isAtLowerLimit() {
    	if ( hasSpark() ) {
    		return lowerLimit.isPressed();
    	}
    	else {
    		return false;
    	}
    }    
    
    @Override
	public boolean isAtRequestedPosition() {
		
		return isWithinToleranceOfPosition(requestedPosition);
	}    
    

    @Override
	public boolean isAtUpperLimit() {
    	if ( hasSpark() ) {
    		return upperLimit.isPressed();
    	}
    	else {
    		return false;
    	}
    }    
 
    public boolean isEnabled() {
        return this.enabled;
    }    

    @Override
	public boolean isHomed() {
  	  return axisState == MotionState.HOMED;
    }    
    
    private boolean isPositionWithinSoftLimits(double position) {
	  	return position >= config.getMinPositionCounts() && position <= config.getMaxPositionCounts() ;
 	}
    
    protected boolean isWithinToleranceOfPosition( int position) {
		double actualPosition = encoder.getPosition();
		return Math.abs(actualPosition - position) < config.getPositionToleranceCounts();
	}
    
    @Override
	public void requestPosition(int requestedPosition) {
  	  if ( isPositionWithinSoftLimits(requestedPosition)) {
  		  this.requestedPosition = requestedPosition;		  
  		  if (axisState == MotionState.UNINITIALIZED) {
  			  spark.set(correctDirection(config.getHomingSpeedPercent()));
  			  axisState = MotionState.FINDING_LIMIT;
  		  }		  
  	  }
  	  else {
  		  DriverStation.reportWarning("Invalid Position " + requestedPosition, false);
  	  }
    }
 
    public void resetPosition(){
        encoder.setPosition(0);
    }

    @Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

    private void setLimitSwitches ( CANSparkMax spark) {
		if ( config.isSwapLimitSwitches()) {
			upperLimit = spark.getReverseLimitSwitch(config.getUpperLimitSwitchType());
			lowerLimit = spark.getForwardLimitSwitch(config.getLowerLimitSwitchType());
		}
		else {
			lowerLimit = spark.getReverseLimitSwitch(config.getLowerLimitSwitchType());
			upperLimit = spark.getForwardLimitSwitch(config.getUpperLimitSwitchType());			
		}		
	}

    private void setPositionInternal(int desiredPosition) {
    	spark.getPIDController().setReference(correctDirection(desiredPosition), CANSparkMax.ControlType.kPosition);
    }

    
    /**
	 * I hate this! but its necesary because:
	 * wpilib initializes sendables before INITIALIZE is called on the subsystem
	 * but i want to populate this controll in the senable
	 * but to do that, i'd ahve to put that code in the constructor, meaning we try to load the motors too early
	 * BUT this forces us to deal with having all methods inside senadable deal with a null spark max!! CMON WPILIB!
	 * @param spark
	 */
	public void setSparkMax ( CANSparkMax spark ) {
		this.spark = spark;
		this.encoder = spark.getEncoder();
		setLimitSwitches(spark);
	}
    
    public void stop() {
    	spark.stopMotor();
    }
    
    @Override
	public void update() {	 
  	  if (enabled ) {
      	 switch ( axisState) {
      		 case UNINITIALIZED:
      			 break;
      		 case FINDING_LIMIT:
      			 if ( isAtLowerLimit() ) {
      				encoder.setPosition(0);
      				setPositionInternal(config.getBackoffCounts());
      				axisState = MotionState.BACKING_OFF;
      			 }
      			 break;
      		 case BACKING_OFF:
      			 if ( isWithinToleranceOfPosition(config.getBackoffCounts())) {
      				encoder.setPosition(config.getHomePositionCounts());
      				 //telescopeMotor.stopMotor();  future configurable? this will stop the motor. Not doing this leaves the motor on and locked on this position 
      				 axisState = MotionState.HOMED;
      			 }
      			 break;
      		 case HOMED:
      			 setPositionInternal(requestedPosition);
      			 if (isAtLowerLimit()  ) {
      				 stop();
      				 DriverStation.reportWarning("Low Limit Reached! Please Move Axis off the switch. We will home on next comamand." , false);
      				 axisState = MotionState.UNINITIALIZED;
      			 }
      			 if ( isAtUpperLimit() ) {
      				 stop();
      				 DriverStation.reportWarning("Upper Limit Reached! Please Move Axis off the switch. We will home on next comamand." , false);
      				 axisState = MotionState.UNINITIALIZED;
      			 }
  	     }
  	  }
    }

}
