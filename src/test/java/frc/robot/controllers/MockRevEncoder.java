package frc.robot.controllers;

import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;

public class MockRevEncoder implements RelativeEncoder{

	private double position = 0.0;
	private double velocity = 0.0;
	
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	@Override
	public double getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public double getVelocity() {
		// TODO Auto-generated method stub
		return velocity;
	}

	public void incrementPosition(int byAmount) {
		this.position += byAmount;
	}
	
	@Override
	public REVLibError setPosition(double position) {
		this.position = position;
		return REVLibError.kOk;
	}

	@Override
	public REVLibError setPositionConversionFactor(double factor) {
		throw new RuntimeException("Not Implemented");		
	}

	@Override
	public REVLibError setVelocityConversionFactor(double factor) {
		throw new RuntimeException("Not Implemented");	
	}

	@Override
	public double getPositionConversionFactor() {
		throw new RuntimeException("Not Implemented");	
	}

	@Override
	public double getVelocityConversionFactor() {
		throw new RuntimeException("Not Implemented");	
	}

	@Override
	public REVLibError setAverageDepth(int depth) {
		throw new RuntimeException("Not Implemented");	
	}

	@Override
	public int getAverageDepth() {
		throw new RuntimeException("Not Implemented");	
	}

	@Override
	public REVLibError setMeasurementPeriod(int period_ms) {
		throw new RuntimeException("Not Implemented");	
	}

	@Override
	public int getMeasurementPeriod() {
		throw new RuntimeException("Not Implemented");	
	}

	@Override
	public int getCountsPerRevolution() {
		throw new RuntimeException("Not Implemented");	
	}

	@Override
	public REVLibError setInverted(boolean inverted) {
		throw new RuntimeException("Not Implemented");	
	}

	@Override
	public boolean getInverted() {
		throw new RuntimeException("Not Implemented");	
	}

}
