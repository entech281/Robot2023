package frc.robot.controllers;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch.Type;



public class SparkMaxPositionController implements PositionController{

	public SparkMaxPositionController ( CANSparkMax spark, boolean reversed ) {
		this.spark = spark;
		this.reversed = reversed;
	}
	protected CANSparkMax spark;	
    private double desiredPosition = 0.0;
    public static final int CAN_TIMEOUT_MILLIS = 1000;
    public static final double POSITION_NOT_ENABLED=-1;
    private boolean enabled = true;
	private  boolean reversed = false;
	
    public double getDesiredPosition() {
        return desiredPosition;
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
            this.desiredPosition = desiredPosition;
            spark.getPIDController().setReference(correctDirection(desiredPosition), CANSparkMax.ControlType.kPosition);
        }
        else{
            Throwable t = new Throwable("Didnt set position, because this controller is disabled.");
            t.printStackTrace();
        }
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
    	return spark.getReverseLimitSwitch(Type.kNormallyOpen).isPressed();
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

    public String toString() {
    	return getActualPosition() + "/" + getDesiredPosition();
    }
}
