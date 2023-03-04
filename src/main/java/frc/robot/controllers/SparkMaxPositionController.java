package frc.robot.controllers;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotConstants.SHUFFLEBOARD;

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

	public enum MotionState { BACKING_OFF, FINDING_LIMIT, HOMED, UNINITIALIZED }
	

	public static final int CAN_TIMEOUT_MILLIS = 1000;	
	public static final int NO_POSITION = -999;	
	private MotionState axisState = MotionState.UNINITIALIZED;
	private PositionControllerConfig config;
	
    private RelativeEncoder encoder;

    private SparkMaxLimitSwitch lowerLimit;
    private double requestedPosition = 0;
    private double internalRequstedPosition = 0;
    protected CANSparkMax spark;    
    private SparkMaxLimitSwitch upperLimit;
    

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
		this.spark.set(0);
	}
    
    @Override
	public double getActualPosition() {
    	return getEncoderValue();
    }
    
	public String getStateString() {
  	  return axisState+"";
    }    
    
	public void forgetHome() {
		axisState = MotionState.UNINITIALIZED;
		spark.set(0);
		spark.stopMotor();
	}
	

	private double correctDirection(double input) {
		if ( config.isInverted()) {
			return -input;
		}
		else {
			return input;
		}
	}
	
    @Override
	public MotionState getMotionState() {
  	  return axisState;
    }

    @Override
	public double getRequestedPosition() {
        return requestedPosition;
    }    

  
    public double getInternalPosition() {
    	return spark.getEncoder().getPosition();
    }
    
  
	public void home() {
    	startHoming();
    }

	@Override
    public void initSendable(SendableBuilder builder) {
  	    builder.setSmartDashboardType("PositionController:" + config.getName());
  	    builder.addStringProperty("Status:", this::getHomingStateString , null);		  
  	    builder.addBooleanProperty("InMotion", this::inMotion, null);
  	    builder.addDoubleProperty("RequestedPos", this::getRequestedPosition, null);
  	    builder.addDoubleProperty("ActualPos", this::getActualPosition, null);
  	    builder.addDoubleProperty("InternalPos", this::getInternalPosition, null);
  	    builder.addDoubleProperty("MotorOut", this::getMotorOutput, null);
  	    builder.addDoubleProperty("ERROR", this::getError, null);
  	    builder.addDoubleProperty("INTERROR", this::getInternalError, null);
  	    builder.addBooleanProperty("Homed", this::isHomed, null);
  	    builder.addBooleanProperty("UpperLimit", this::isAtUpperLimit, null);
  	    builder.addBooleanProperty("LowerLimit", this::isAtLowerLimit, null);
        builder.addDoubleProperty("PID P", this::getP, this::setP);
        //builder.addDoubleProperty("PID I", this::getI, this::setI);
        //builder.addDoubleProperty("PID D", this::getD, this::setD);
        //builder.addDoubleProperty("PID SetPoint", this::getRequestedPosition, this::requestPosition);
        //builder.addDoubleProperty("PID FF", this::getFF, this::setFF);
    }
	
	public String getHomingStateString() {
		return this.axisState+"";
	}
	public PositionControllerConfig getConfig() {
		return this.config;
	}
	public double getMotorOutput() {
			return spark.getAppliedOutput();
	}
	
	public boolean inMotion() {
		return correctDirection(encoder.getVelocity()) > 0;
	}
	
    @Override
	public boolean isAtLowerLimit() {
    	return lowerLimit.isPressed();
    }    
    
    public void setMotorSpeed(double input) {
    	spark.set(correctDirection(input));
    }
    
  	@Override
	public boolean isAtRequestedPosition() {
    	if ( isHomed() ) {
    		return isWithinToleranceOfPosition(requestedPosition);
    	}
    	else {
    		return false;
    	}
	}    
    

  	@Override
	public boolean isAtUpperLimit() {
    	return upperLimit.isPressed();
    }    


    @Override
	public boolean isHomed() {
  	  return axisState == MotionState.HOMED;
    }    
    public double getInternalRequestedPosition() {
    	return internalRequstedPosition;
    }
    
    private boolean isPositionWithinSoftLimits(double position) {
	  	return position >= config.getMinPosition() && position <= config.getMaxPosition() ;
 	}
    
    protected boolean isWithinToleranceOfPosition(double position) {    	
		double actualPosition = getEncoderValue();
		return Math.abs(actualPosition - position) < config.getPositionTolerance();
	}
    
    protected boolean isErrorLessThanTolerance( double val1, double val2, double tolerance) {
    	return Math.abs(val1 - val2)< tolerance;
    }
    
  	@Override
	public void requestPosition(double requestedPosition) {
  	  if ( isPositionWithinSoftLimits(requestedPosition)) {
  		  this.requestedPosition = requestedPosition;		  
  		  if (axisState == MotionState.UNINITIALIZED) {
  			startHoming();
  		  }
  	  }
  	  else {
  		  DriverStation.reportWarning("Invalid Position " + requestedPosition, false);
  	  }
    }
 
  	public double getError() {
  		return getActualPosition() - this.getRequestedPosition();
  	}
  	
  	public double getInternalError() {
  		return  this.getInternalRequestedPosition() - this.getInternalPosition();
  	}
    private void startHoming() {
    	if ( isAtLowerLimit() ) {
    		setMotorSpeed(0);
    	}
    	else {
    		setMotorSpeed(-config.getHomingSpeedPercent());
    	}
    	axisState = MotionState.FINDING_LIMIT;    
    }
    
    public void resetPosition(){
        encoder.setPosition(0);
    }

    private void setPositionInternal(double desiredPosition) {
    	spark.getPIDController().setReference(correctDirection(desiredPosition), CANSparkMax.ControlType.kPosition);
    }

    private void setEncoder( double value ) {
    	spark.getEncoder().setPosition(correctDirection(value));
    }
    
    private double getEncoderValue() {
    	return correctDirection(spark.getEncoder().getPosition());
    }
    public void stop() {
    	spark.stopMotor();
    }
    
    private void stopWithWarning(String warningMessage) {
    	stop();
    	DriverStation.reportWarning(warningMessage,false);
    }
    @Override
	public void update() {	 
      	 switch ( axisState) {
      		 case UNINITIALIZED:
      			 //spark.set(0);
      			 break;
      		 case FINDING_LIMIT:
      			 if ( isAtLowerLimit() ) {
      				setMotorSpeed(0);
      				setEncoder(0);
      				setPositionInternal(config.getHomePosition());
      				axisState = MotionState.HOMED;
      			 }
      			 break;
//      		 case BACKING_OFF:
//      			 break;
//      			 //in this state, we cant rely on users' requested position
//      			 if ( Math.abs(getInternalError()) <  config.getPositionTolerance()) {
//      				 if ( isAtLowerLimit() ) {
//      					stopWithWarning("Low Limit Still active after trying to back off: " + config.getBackoff() + ". try increasing backoff amount");
//      				 }
//      				 setEncoder(config.getHomePosition());
//      				 //telescopeMotor.stopMotor();  future configurable? this will stop the motor. Not doing this leaves the motor on and locked on this position 
//      				 axisState = MotionState.HOMED;      				 
//      			 }
//      			 break;
      		 case HOMED:
      			 setPositionInternal(requestedPosition);
//      			 if (isAtLowerLimit()  ) {
//      				 stopWithWarning("Low Limit Reached! Please Move Axis off the switch. We will home on next comamand.");
//      				 axisState = MotionState.UNINITIALIZED;
//      			 }
//      			 if ( isAtUpperLimit() ) {
//      				 stopWithWarning("Upper Limit Reached! Please Move Axis off the switch. We will home on next comamand." );
//      				 axisState = MotionState.UNINITIALIZED;
//      			}
  	    }
  	}
	
    private double getP() {
      if (spark != null) {
        return spark.getPIDController().getP();
      }
      return 0;
    }
  
    private void setP(double p) {
      if (spark != null) {
        spark.getPIDController().setP(p);
      }
    }
  
    private double getI() {
      if (spark != null) {
        return spark.getPIDController().getI();
      }
      return 0;
    }
  
    private void setI(double i) {
      if (spark != null) {
        spark.getPIDController().setI(i);
      }
    }
  
    private double getD() {
      if (spark != null) {
        return spark.getPIDController().getD();
      }
      return 0;
    }
  
    private void setD(double d) {
      if (spark != null) {
        spark.getPIDController().setD(d);
      }
    }

    private double getFF() {
      if (spark != null) {
        return spark.getPIDController().getFF();
      }
      return 0;
    }

    private void setFF(double f) {
      if (spark != null) {
        spark.getPIDController().setFF(f);
      }
    }
}
