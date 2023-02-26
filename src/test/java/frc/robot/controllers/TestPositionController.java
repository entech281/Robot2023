package frc.robot.controllers;

import frc.robot.controllers.SparkMaxPositionController.HomingState;

/**
 * for unit testing position controller and subsystems that 
 * use them
 * @author davec
 *
 */
public class TestPositionController implements PositionController{

	public static final double POSITION_UNKNOWN = 999;
	
	public TestPositionController ( PositionControllerConfig config) {
		this.config = config;
	}
	private PositionControllerConfig config;
	private int actualPosition  = 0;
	private int requestedPosition = 0;
	private boolean inMotion = false;
	private boolean homed = false;
	private HomingState homingState;
	private boolean lowerLimitTripped = false;
	private boolean upperLimitTripped = false;

	public void addToPosition ( double pos) {
		this.actualPosition += pos;
	}

	@Override
	public int getActualPosition() {
		return actualPosition;
	}

	@Override
	public double getRequestedPosition() {
		return requestedPosition;
	}

	@Override
	public boolean isAtRequestedPosition() {
		return actualPosition == requestedPosition;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void requestPosition(int requestedPosition) {
		this.requestedPosition = requestedPosition;
		
	}

	@Override
	public boolean isAtLowerLimit() {
		return lowerLimitTripped;
	}

	@Override
	public boolean isAtUpperLimit() {
		return upperLimitTripped;
	}

	@Override
	public boolean inMotion() {
		return inMotion;
	}

	@Override
	public boolean isHomed() {
		return homed;
	}

	@Override
	public HomingState getHomingState() {
		return homingState;
	}

}
