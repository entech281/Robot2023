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
public class SparkMaxPositionController implements Sendable, PositionController{

	public enum MotionState { BACKING_OFF, FINDING_LIMIT, HOMED, UNINITIALIZED }
	

	public static final int CAN_TIMEOUT_MILLIS = 1000;	
	public static final int NO_POSITION = -999;	
	private MotionState axisState = MotionState.UNINITIALIZED;
	private PositionControllerConfig config;
	
    private RelativeEncoder encoder;

    private SparkMaxLimitSwitch lowerLimit;
    private double requestedPosition = 0;
    protected CANSparkMax spark;    
    private SparkMaxLimitSwitch upperLimit;
    

	public SparkMaxPositionController (CANSparkMax spark,  PositionControllerConfig config, SparkMaxLimitSwitch lowerLimit, SparkMaxLimitSwitch upperLimit, RelativeEncoder encoder) {
		this.spark = spark;		
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.encoder = encoder;
		this.config = config;
		this.spark.set(0);
	}
    
    @Override
	public double getActualPosition() {
    	return encoder.getPosition();
    }
    
	public String getStateString() {
  	  return axisState+"";
    }    
    
	public void forgetHome() {
		axisState = MotionState.UNINITIALIZED;
		spark.set(0);
		spark.stopMotor();
	}
	
    @Override
	public MotionState getMotionState() {
  	  return axisState;
    }

    @Override
	public double getRequestedPosition() {
        return requestedPosition;
    }    

  
	public void home() {
    	requestPosition(config.getHomePosition());
    }

	@Override
    public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType("PositionController:" + config.getName());
      builder.addStringProperty("Status:", this::getStateString , null);		  
      builder.addBooleanProperty("InMotion", this::inMotion, null);
      builder.addDoubleProperty("RequestedPos", this::getRequestedPosition, null);
      builder.addDoubleProperty("ActualPos", this::getActualPosition, null);
      builder.addBooleanProperty("Homed", this::isHomed, null);
      builder.addBooleanProperty("UpperLimit", this::isAtLowerLimit, null);
      builder.addBooleanProperty("LowerLimit", this::isAtUpperLimit, null);
      builder.addDoubleProperty("MotorOuptut", this::getMotorOutput, null);
    }  
	
	
	public double getMotorOutput() {
			return spark.getAppliedOutput();
	}
	
	public boolean inMotion() {
		return encoder.getVelocity() > 0;
	}
    @Override
	public boolean isAtLowerLimit() {
    	return lowerLimit.isPressed();
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
    
    private boolean isPositionWithinSoftLimits(double position) {
	  	return position >= config.getMinPosition() && position <= config.getMaxPosition() ;
 	}
    
    protected boolean isWithinToleranceOfPosition( double position) {
		double actualPosition = encoder.getPosition();
		return Math.abs(actualPosition - position) < config.getPositionTolerance();
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
 
    private void startHoming() {
		  spark.set(config.getHomingSpeedPercent());
		  axisState = MotionState.FINDING_LIMIT;    	
    }
    
    public void resetPosition(){
        encoder.setPosition(0);
    }

    private void setPositionInternal(double desiredPosition) {
    	spark.getPIDController().setReference(desiredPosition, CANSparkMax.ControlType.kSmartMotion);
    }
    
    public void stop() {
    	spark.stopMotor();
    }
    
    @Override
	public void update() {	 
      	 switch ( axisState) {
      		 case UNINITIALIZED:
      			 //spark.set(0);
      			 break;
      		 case FINDING_LIMIT:
      			 if ( isAtLowerLimit() ) {
      				spark.set(0);
      				encoder.setPosition(0);
      				setPositionInternal(config.getBackoff());
      				axisState = MotionState.BACKING_OFF;
      			 }
      			 break;
      		 case BACKING_OFF:
      			 if ( isWithinToleranceOfPosition(config.getBackoff())) {
      				encoder.setPosition(config.getHomePosition());
      				 //telescopeMotor.stopMotor();  future configurable? this will stop the motor. Not doing this leaves the motor on and locked on this position 
      				 axisState = MotionState.HOMED;
      			 }
      			 break;
      		 case HOMED:
      			 //setPositionInternal(requestedPosition);
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
