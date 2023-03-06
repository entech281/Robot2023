package frc.robot.subsystems;

import java.util.Optional;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotConstants;
import frc.robot.controllers.PositionControllerConfig;

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
public abstract class BaseSparkMaxPositionSubsystem extends EntechSubsystem{

	public enum HomingState { FINDING_LIMIT, HOMED, UNINITIALIZED }	
	public static final int CAN_TIMEOUT_MILLIS = 1000;	
	protected CANSparkMax spark;
	private HomingState axisState = HomingState.UNINITIALIZED;
	protected boolean enabled = false;
    private PositionControllerConfig config;
    private RelativeEncoder encoder;
    private SparkMaxLimitSwitch lowerLimit;
    private Optional<Double> requestedPosition = Optional.empty();
    private SparkMaxLimitSwitch upperLimit;    
    protected boolean liveMode = false;
    public BaseSparkMaxPositionSubsystem () {
    	
    }
    
    public void configure(CANSparkMax spark, PositionControllerConfig config ) {
    	configure(spark, 
    			config, 
    			spark.getReverseLimitSwitch(Type.kNormallyOpen), 
    			spark.getForwardLimitSwitch(Type.kNormallyOpen),
    			spark.getEncoder()
    			);
    }
    
    public void configure(CANSparkMax spark,  PositionControllerConfig config, SparkMaxLimitSwitch lowerLimit, 
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
    
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled( boolean enabled) {
		this.enabled  = enabled;
	}
    public void clearRequestedPosition() {
    	this.requestedPosition= Optional.empty();
    	setMotorSpeedInternal(0.0);
    }

	public double getActualPosition() {
    	return getEncoderValue();
    }	
	
    public PositionControllerConfig getConfig() {
		return this.config;
	}   

	public double getMotorOutput() {
			return spark.getAppliedOutput();
	}      
	
	public Optional<Double> getRequestedPosition() {
		return requestedPosition;
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
  	    builder.addStringProperty("Status:", this::getStatusString , null);		  
  	    builder.addDoubleProperty("RequestedPos", this::getRequestedPositionAsDouble, null);
  	    builder.addDoubleProperty("ActualPos", this::getActualPosition, null);
  	    builder.addDoubleProperty("MotorOut", this::getMotorOutput, null);
  	    builder.addBooleanProperty("Homed", this::isHomed, null);
  	    builder.addBooleanProperty("UpperLimit", this::isAtUpperLimit, null);
  	    builder.addBooleanProperty("LowerLimit", this::isAtLowerLimit, null);
  	    if ( liveMode ) {
  	    	builder.addDoubleProperty("P",this::getP,this::setP);
  	    }

    }
	
	/**
	 * Live Mode things
	 * 
	 */
	private void setP(double p) {
		spark.getPIDController().setP(p);
	}
	private double getP() {
		return spark.getPIDController().getP();
	}
	
	private double getRequestedPositionAsDouble() {
		if ( requestedPosition.isPresent()) {
			return requestedPosition.get();
		}
		else {
			return RobotConstants.INDICATOR_VALUES.POSITION_NOT_SET;
		}
	}
	
	public boolean isAtLowerLimit() {
    	return lowerLimit.isPressed();
    }    

	public boolean isAtUpperLimit() {
    	return upperLimit.isPressed();
    }	
	
	public boolean isAtRequestedPosition() {
  		if ( requestedPosition.isPresent()) {
  	    	if ( isHomed() ) {
  	    		return Math.abs(getActualPosition() - requestedPosition.get()) < config.getPositionTolerance();
  	    	}			
  		}
  		return false;
	}
    
	public boolean isHomed() {;
  	  return axisState == HomingState.HOMED;
    }    
    
	public void requestPosition(double requestedPosition) {
  	  if ( isPositionWithinSoftLimits(requestedPosition)) {
  		  this.requestedPosition = Optional.of(requestedPosition);		  
  		  if (axisState == HomingState.UNINITIALIZED) {
  			startHoming();
  		  }
  	  }
  	  else {
  		  DriverStation.reportWarning("Invalid Position " + requestedPosition, false);
  	  }
    }    

    public void setMotorSpeed(double input) {    	
    	clearRequestedPosition();
    	setMotorSpeedInternal(input);
    }
    

    public void stop() {
    	spark.stopMotor();
    }
    
    @Override
	public void periodic() {	 
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

    private void setMotorSpeedInternal(double input) {
    	spark.set(correctDirection(input));
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

