package frc.robot.controllers;

import java.util.Optional;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotConstants;

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
public class SparkMaxPositionController implements Sendable, PositionController {

	public enum MotionState { FINDING_LIMIT, HOMED, UNINITIALIZED }
	
	public static final int CAN_TIMEOUT_MILLIS = 1000;	
	private MotionState axisState = MotionState.UNINITIALIZED;
	private PositionControllerConfig config;
	
    private RelativeEncoder encoder;
    private SparkMaxLimitSwitch upperLimit;
    private SparkMaxLimitSwitch lowerLimit;
    private Optional<Double> requestedPosition = Optional.empty();
    protected CANSparkMax spark;    

	public SparkMaxPositionController (CANSparkMax spark,  PositionControllerConfig config, SparkMaxLimitSwitch lowerLimit, SparkMaxLimitSwitch upperLimit, RelativeEncoder encoder) {
		this.spark = spark;		
		
		if ( config.isInverted()) {
			this.lowerLimit = upperLimit;
			this.upperLimit = lowerLimit;	
		}
		else {
			this.lowerLimit = lowerLimit;
			this.upperLimit = upperLimit;			
		}

		this.encoder = encoder;
		this.config = config;
		setMotorSpeed(0);
	}
    
    @Override
	public double getActualPosition() {
    	return getEncoderValue();
    }

  	@Override
	public void requestPosition(double requestedPosition) {
  	  if ( isPositionWithinSoftLimits(requestedPosition)) {
  		  this.requestedPosition = Optional.of(requestedPosition);		  
  		  if (axisState == MotionState.UNINITIALIZED) {
  			startHoming();
  		  }
  	  }
  	  else {
  		  DriverStation.reportWarning("Invalid Position " + requestedPosition, false);
  	  }
    }	
	
    @Override
	public MotionState getMotionState() {
  	  return axisState;
    }

    @Override
	public double getRequestedPosition() {
    	if ( requestedPosition.isPresent()) {
    		return requestedPosition.get();
    	}
    	else {
    		return RobotConstants.INDICATOR_VALUES.POSITION_NOT_SET;
    	}
    }      
  
	public void home() {
    	requestedPosition = Optional.of(config.getMinPosition());		
    	startHoming();
    }

	@Override
    public void initSendable(SendableBuilder builder) {
  	    builder.setSmartDashboardType("PositionController:" + config.getName());
  	    builder.addStringProperty("Status:", this::getStatusString , null);		  
  	    builder.addDoubleProperty("RequestedPos", this::getRequestedPosition, null);
  	    builder.addDoubleProperty("ActualPos", this::getActualPosition, null);
  	    builder.addDoubleProperty("MotorOut", this::getMotorOutput, null);
  	    builder.addBooleanProperty("Homed", this::isHomed, null);
  	    builder.addBooleanProperty("UpperLimit", this::isAtUpperLimit, null);
  	    builder.addBooleanProperty("LowerLimit", this::isAtLowerLimit, null);

    }
	
	public String getStatusString() {
		if ( axisState == MotionState.HOMED) {
			if ( requestedPosition.isPresent()) {
				if ( this.isAtRequestedPosition()) {
					return "ARRIVED";
				}
				else{
					return "MOVING";
				}
			}
			else {
				return "HOMED/IDLE";
			}
		}
		else {
			return this.axisState+"";
		}		
	}
	public PositionControllerConfig getConfig() {
		return this.config;
	}
	
	public double getMotorOutput() {
			return spark.getAppliedOutput();
	}
	
    @Override
	public boolean isAtLowerLimit() {
    	return lowerLimit.isPressed();
    }    
    

    public void setMotorSpeed(double input) {    	
    	cancelRequestedPosition();
    	setMotorSpeedInternal(input);
    }
    
    public void setMotorSpeedInternal(double input) {
    	spark.set(correctDirection(input));
    }
    
  	@Override
	public boolean isAtRequestedPosition() {
  		if ( requestedPosition.isPresent()) {
  	    	if ( isHomed() ) {
  	    		return Math.abs(getEncoderValue() - requestedPosition.get()) < config.getPositionTolerance();
  	    	}			
  		}
  		return false;
	}    
    
  	@Override
	public boolean isAtUpperLimit() {
    	return upperLimit.isPressed();
    }    

    @Override
	public boolean isHomed() {
  	  return axisState == MotionState.HOMED;
    }
    
    private void cancelRequestedPosition() {
    	this.requestedPosition= Optional.empty();
    }
    private boolean isPositionWithinSoftLimits(double position) {
	  	return position >= config.getMinPosition() && position <= config.getMaxPosition() ;
 	}
    

	private double correctDirection(double input) {
		if ( config.isInverted()) {
			return -input;
		}
		else {
			return input;
		}
	}
  	
    private void startHoming() {
    	if ( isAtLowerLimit() ) {
    		setMotorSpeed(0);
			axisState = MotionState.HOMED;
    	}
    	else {
    		setMotorSpeedInternal(-config.getHomingSpeedPercent());
    	}
    	axisState = MotionState.FINDING_LIMIT;    
    }
   
    private void updateRequestedPosition() {
    	if ( requestedPosition.isPresent()) {
        	spark.getPIDController().setReference(correctDirection(requestedPosition.get()), CANSparkMax.ControlType.kPosition);
        	spark.getPIDController().setIAccum(0);    		
    	}
    }

    private void setEncoder( double value ) {
    	encoder.setPosition(correctDirection(value));
    }
    
    private double getEncoderValue() {
    	return correctDirection(encoder.getPosition());
    }
    public void stop() {
    	spark.stopMotor();
    }
    
    @Override
	public void update() {	 
      	 switch ( axisState) {
      		 case UNINITIALIZED:
      			 break;
      		 case FINDING_LIMIT:
      			 if ( isAtLowerLimit() ) {
      				setMotorSpeedInternal(0);
      				setEncoder(config.getMinPosition());
      				axisState = MotionState.HOMED;
      			 }
      			 break;
      		 case HOMED:
      			 updateRequestedPosition();
  	    }
  	}
  
}
