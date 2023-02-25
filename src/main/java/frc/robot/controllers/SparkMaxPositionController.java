package frc.robot.controllers;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.wpilibj.DriverStation;


public class SparkMaxPositionController implements PositionController{
	public enum MotionState { IDLE, FINDING_LIMIT, BACKING_OFF, HOMED }
	public SparkMaxPositionController ( CANSparkMax spark, PositionControllerConfig config) {
		this.spark = spark;
		this.config = config;
	}
	
	protected CANSparkMax spark;	
    private PositionControllerConfig config;
    private int desiredPosition = 0;
    
    public static final int CAN_TIMEOUT_MILLIS = 1000;
    
    //TODO: shouldnt need these!
    public static final double POSITION_NOT_ENABLED=-1;
    public static final double POSITION_UNKNOWN = -999;
    
    private boolean enabled = true;

    
	@Override
	public boolean isInMotion() {
		return spark.get() > 0 ;
	}    
    
    private double getCurrentError() {
    	return Math.abs(desiredPosition - getActualPosition());
    }


	public double getDesiredPosition() {
        return desiredPosition;
    }

	@Override
	public boolean isAtDesiredPosition() {
		return getCurrentError() < config.getToleranceCounts();
	}	
	
    protected double correctDirection(double input){
        if ( config.isReversed() ){
            return -input;
        }
        else{
            return input;
        }
    }    

    public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}    
 

    /**
     * When you call this, the talon will be put in the right mode for control
     *
     * @param desiredPosition
     */
    @Override
    public void setDesiredPosition(int desiredPosition) {

        if(enabled){
        	if ( isPositionAcceptable(desiredPosition) ) {
                this.desiredPosition = desiredPosition;
                spark.getPIDController().setReference(correctDirection(desiredPosition), CANSparkMax.ControlType.kPosition);        		
        	}
        	else {
        		DriverStation.reportWarning("Invalid Position " + desiredPosition, false);
        	}
        }
        else{
            Throwable t = new Throwable("Didnt set position, because this controller is disabled.");
            t.printStackTrace();
        }
    }
    
    private boolean isPositionAcceptable(double position) {
    	return position >= minPosition && position <= maxPosition ;
    }

    @Override
    public double getActualPosition() {
        if(enabled)
            return correctDirection(spark.getEncoder().getPosition());
        return POSITION_NOT_ENABLED;
    }

    @Override
    public boolean isAtLowerLimit() {
    	return spark.getReverseLimitSwitch(Type.kNormallyOpen).isPressed();
    }

    @Override
    public boolean isAtUpperLimit() {
    	return spark.getForwardLimitSwitch(Type.kNormallyOpen).isPressed();
    }

    @Override
    public void resetPosition(){
        spark.getEncoder().setPosition(0);
    }


    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void configure() {        

    }    

    @Override    
    public String toString() {
    	return getActualPosition() + "/" + getDesiredPosition();
    }


	@Override
	public void home() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isHome() {
		// TODO Auto-generated method stub
		return false;
	}

}
