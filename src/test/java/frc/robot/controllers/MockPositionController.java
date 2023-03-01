package frc.robot.controllers;


import frc.robot.controllers.SparkMaxPositionController.MotionState;

/**
 * for unit testing position controller and subsystems that 
 * use them
 * @author davec
 *
 */
public class MockPositionController implements PositionController{

	public static final double POSITION_UNKNOWN = 999;
	
	public MockPositionController ( PositionControllerConfig config) {
		this.config = config;
	}
	private PositionControllerConfig config;
	public boolean isInMotion() {
		return inMotion;
	}

	public void setInMotion(boolean inMotion) {
		this.inMotion = inMotion;
	}

	public boolean isLowerLimitTripped() {
		return lowerLimitTripped;
	}

	public void setLowerLimitTripped(boolean lowerLimitTripped) {
		this.lowerLimitTripped = lowerLimitTripped;
	}

	public boolean isUpperLimitTripped() {
		return upperLimitTripped;
	}

	public void setUpperLimitTripped(boolean upperLimitTripped) {
		this.upperLimitTripped = upperLimitTripped;
	}

	public void setActualPosition(double actualPosition) {
		this.actualPosition = actualPosition;
	}

	public void setRequestedPosition(double requestedPosition) {
		this.requestedPosition = requestedPosition;
	}

	public void setHomed(boolean homed) {
		this.homed = homed;
	}

	public void setMotionState(MotionState motionState) {
		this.motionState = motionState;
	}
	private double actualPosition  = 0;
	private double requestedPosition = 0;
	private boolean inMotion = false;
	private boolean homed = false;
	private MotionState motionState;
	private boolean lowerLimitTripped = false;
	private boolean upperLimitTripped = false;

	public void addToPosition ( double pos) {
		this.actualPosition += pos;
	}

	@Override
	public double getActualPosition() {
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
	public void update() {
		
	}

	@Override
	public void requestPosition(double requestedPosition) {
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
	public MotionState getMotionState() {
		return motionState;
	}

}
