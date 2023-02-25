package frc.robot.controllers;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch.Type;

public class SparkMaxHomingController implements HomingController{

	private CANSparkMax motor;
	private PositionControllerConfig config;
	private HomingState state = HomingState.IDLE;
	
	public static final double HOMING_TOLERANCE = 10.0;
	private enum HomingState { IDLE, FINDING_LIMIT, BACKING_OFF, HOMED }
	public SparkMaxHomingController(CANSparkMax motor,PositionControllerConfig config) {
		this.config = config;
		this.motor = motor;
	}

	@Override
	public void home() {
		motor.set(correctDirection(config.getHomingSpeed()));
		state = HomingState.FINDING_LIMIT;
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
	public void update() {
		if ( state == HomingState.FINDING_LIMIT ) {
			if ( motor.getForwardLimitSwitch(Type.kNormallyOpen).isPressed()) {
				motor.set(0);
				motor.getEncoder().setPosition(config.getHomePosition());
				motor.getPIDController().setReference(config.getBackoffDDistance(), CANSparkMax.ControlType.kPosition);
				state = HomingState.BACKING_OFF;				
			}			
		}
		else if ( state == HomingState.BACKING_OFF) {
			if ( isAtDesiredPosition() ) {
				state = HomingState.HOMED;
			}
			motor.set(0);
		}
		
	}  	
	public boolean isHomed() {
		return state == HomingState.HOMED;
	}
	
	private boolean isAtDesiredPosition() {
		return  Math.abs(motor.getEncoder().getPosition() - config.getBackoffDDistance()) <  HOMING_TOLERANCE;
	}

	@Override
	public boolean isHome() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
