package frc.robot.controllers;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.wpilibj.DriverStation;


public class SparkMaxPositionController implements PositionController{

	public SparkMaxPositionController ( CANSparkMax spark, boolean reversed, int moveToleranceCounts, double minPosition, double maxPosition) {
		this.spark = spark;
		this.reversed = reversed;
		this.moveToleranceCounts = moveToleranceCounts;
		this.minPosition = minPosition;
		this.maxPosition = maxPosition;
	}
	
	protected CANSparkMax spark;	
    private double desiredPosition = 0.0;
    
    public static final int CAN_TIMEOUT_MILLIS = 1000;
    public static final double POSITION_NOT_ENABLED=-1;
    public static final double POSITION_UNKNOWN = -999;
    private double minPosition = 0.0;
    private double maxPosition = 0.0;
    private boolean enabled = true;
	private  boolean reversed = false;	
	private int moveToleranceCounts;
	
    public int getMoveToleranceCounts() {
		return moveToleranceCounts;
	}

    
	@Override
	public boolean isInMotion() {
		return spark.get() > 0 ;
	}    
    
    private double getCurrentError() {
    	return Math.abs(desiredPosition - getActualPosition());
    }
    
	public void setMoveTolerance(int moveToleranceCounts) {
		this.moveToleranceCounts = moveToleranceCounts;
	}

	public double getDesiredPosition() {
        return desiredPosition;
    }

	@Override
	public boolean isAtDesiredPosition() {
		return getCurrentError() < moveToleranceCounts;
	}	
	
    protected double correctDirection(double input){
        if ( reversed ){
            return -input;
        }
        else{
            return input;
        }
    }    

    public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}    

    public boolean isReversed(){
        return reversed;
    }    

    /**
     * When you call this, the talon will be put in the right mode for control
     *
     * @param desiredPosition
     */
    @Override
    public void setDesiredPosition(double desiredPosition) {

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

}
