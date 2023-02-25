package frc.robot.controllers;

/**
 * for unit testing position controller and subsystems that 
 * use them
 * @author davec
 *
 */
public class TestPositionController implements PositionController{

	public static final double POSITION_UNKNOWN = 999;
	
	private double actualPosition = POSITION_UNKNOWN;
	private double requestedPosition = 0.0;
	public static double TOLERANCE = 0.1;
	private boolean reversed = false;
	private boolean enabled = false;
	private boolean lowerLimitTripped = false;
	private boolean upperLimitTripped = false;

	public void addToPosition ( double pos) {
		this.actualPosition += pos;
	}
	

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setLowerLimitTripped(boolean lowerLimitTripped) {
		this.lowerLimitTripped = lowerLimitTripped;
	}

	public void setUpperLimitTripped(boolean upperLimitTripped) {
		this.upperLimitTripped = upperLimitTripped;
	}
	
	
	@Override
	public double getDesiredPosition() {
		return actualPosition;
	}

	@Override
	public double getActualPosition() {
		return actualPosition;
	}
	
	public void setActualPosition(double actual) {
		actualPosition = actual;
	}

	@Override
	public void setDesiredPosition(double preset) {
		requestedPosition = preset;		
	}

	@Override
	public boolean isInMotion() {
		return !isAtDesiredPosition();
	}

	@Override
	public boolean isAtDesiredPosition() {
		System.out.println(actualPosition +"/" + requestedPosition);
		return  Math.abs(requestedPosition - actualPosition) < TOLERANCE ;
	}

	@Override
	public boolean isReversed() {
		return reversed;
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
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPosition() {
		this.setActualPosition(0.0);
		
	}

}
