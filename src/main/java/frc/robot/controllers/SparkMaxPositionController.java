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

	public enum HomingState { FINDING_LIMIT, HOMED, UNINITIALIZED }
	
	public static final int CAN_TIMEOUT_MILLIS = 1000;	
	protected CANSparkMax spark;
	private HomingState axisState = HomingState.UNINITIALIZED;
	
    private PositionControllerConfig config;
    private RelativeEncoder encoder;
    private SparkMaxLimitSwitch lowerLimit;
    private Optional<Double> requestedPosition = Optional.empty();
    private SparkMaxLimitSwitch upperLimit;    

	public SparkMaxPositionController (CANSparkMax spark,  PositionControllerConfig config, SparkMaxLimitSwitch lowerLimit, 
			SparkMaxLimitSwitch upperLimit, RelativeEncoder encoder) {
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
		clearRequestedPosition();
	}
    
    public void clearRequestedPosition() {
    	this.requestedPosition= Optional.empty();
    	setMotorSpeedInternal(0.0);
    }

  	@Override
	public double getActualPosition() {
    	return getEncoderValue();
    }	
	
    public PositionControllerConfig getConfig() {
		return this.config;
	}

    @Override
	public HomingState getMotionState() {
  	  return axisState;
    }      

	public double getMotorOutput() {
			return spark.getAppliedOutput();
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
	
	public String getStatusString() {
		if ( axisState == HomingState.HOMED) {
			if ( requestedPosition.isPresent()) {
				if ( this.isAtRequestedPosition()) {
					return "ARRIVED";
				}
				else{
					return "MOVING";
				}
			}
			else {
				return "HOMED";
			}
		}
		else {
			return this.axisState+"";
		}		
	}
	
	public void home() {		
    	startHoming();
    }
	
	@Override
    public void initSendable(SendableBuilder builder) {
  	    builder.setSmartDashboardType("PositionController:" + config.getName());
  	    builder.addStringProperty("Status:", this::getStatusString , null);		  
  	    builder.addDoubleProperty("RequestedPos", this::getRequestedPosition, null);
  	    builder.addDoubleProperty("ActualPos", this::getActualPosition, null);
  	    builder.addBooleanProperty("InPosition", this::isAtRequestedPosition, null);  	    
  	    builder.addDoubleProperty("MotorOut", this::getMotorOutput, null);
  	    builder.addBooleanProperty("Homed", this::isHomed, null);
  	    builder.addBooleanProperty("UpperLimit", this::isAtUpperLimit, null);
  	    builder.addBooleanProperty("LowerLimit", this::isAtLowerLimit, null);


    }
	
    @Override
	public boolean isAtLowerLimit() {
    	return lowerLimit.isPressed();
    }    
    

    @Override
	public boolean isAtRequestedPosition() {
  		if ( requestedPosition.isPresent()) {
  	    	if ( isHomed() ) {
  	    		return Math.abs(getActualPosition() - requestedPosition.get()) < config.getPositionTolerance();
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
  	  return axisState == HomingState.HOMED;
    }    
    
  	@Override
	public void requestPosition(double requestedPosition) {
  	  if ( ! isPositionWithinSoftLimits(requestedPosition)) {
        DriverStation.reportWarning("Invalid Position " + requestedPosition, false);
        requestedPosition = Math.min(requestedPosition, config.getMaxPosition());
        requestedPosition = Math.max(requestedPosition, config.getMinPosition());
     }
  	 this.requestedPosition = Optional.of(requestedPosition);		  
  	 if (axisState == HomingState.UNINITIALIZED) {
  		startHoming();
     }
    }    

    public void setMotorSpeed(double input) {    	
    	clearRequestedPosition();
    	setMotorSpeedInternal(input);
    }
    
    public void setMotorSpeedInternal(double input) {
    	spark.set(correctDirection(input));
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
      				arrivedHome();
      			 }
      			 break;
      		 case HOMED:
      			 updateRequestedPosition();
  	    }
  	}
  	
    private void arrivedHome() {
		setMotorSpeedInternal(0);
		setEncoder(config.getMinPosition());		
		axisState = HomingState.HOMED;    	
    }
 
    private double correctDirection(double input) {
		if ( config.isInverted()) {
			return -input;
		}
		else {
			return input;
		}
	}
    
    private double getEncoderValue() {
    	return correctDirection(encoder.getPosition());
    }

    private boolean isPositionWithinSoftLimits(double position) {
	  	return position >= config.getMinPosition() && position <= config.getMaxPosition() ;
 	}
    
    private void setEncoder( double value ) {
    	encoder.setPosition(correctDirection(value));
    }
    private void startHoming() {
    	//if we are already on the limit switch, we'll get homed in the next update loop
    	setMotorSpeedInternal(-config.getHomingSpeedPercent());
    	axisState = HomingState.FINDING_LIMIT;	    
    }
    
    private void updateRequestedPosition() {
    	if ( requestedPosition.isPresent()) {
        	spark.getPIDController().setReference(correctDirection(requestedPosition.get()), CANSparkMax.ControlType.kPosition);
        	spark.getPIDController().setIAccum(0);    		
    	}
    }
  
}
