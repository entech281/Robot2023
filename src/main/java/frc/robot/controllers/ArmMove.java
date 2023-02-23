package frc.robot.controllers;

import java.util.Optional;


/**
 * A single move. We will wait for each
 * of these to become true before moving on
 * to the next move
 * This move can wait for one or both joints to arrive at a particular positoin
 * @author davec
 *
 */
public class ArmMove {

	public ArmMove ( Optional<Double> elbowSetpoint, Optional<Double> telescopeSetpoint, double tolerance) {
		this.elbowSetpoint = elbowSetpoint;
		this.telescopeSetpoint = telescopeSetpoint;
		this.tolerance = tolerance;
	}
	
	public Optional<Double> getElbowSetpoint() {
		return elbowSetpoint;
	}
	public Optional<Double> getTelescopeSetpoint() {
		return telescopeSetpoint;
	}
	
	public void updatePosition ( double elbowValue, double teleScopeValue) {
		currentElbowPosition = elbowValue;
		currentTeleScopePosition = teleScopeValue;		
	}
	public boolean isComplete(double elbowValue, double teleScopeValue) { 
		boolean complete = true;
		if ( telescopeSetpoint.isPresent()) {
			if ( Math.abs(teleScopeValue - telescopeSetpoint.get()) > tolerance) {
				complete = false;
			}
		}
		if ( elbowSetpoint.isPresent()) {
			if ( Math.abs(elbowValue - elbowSetpoint.get()) > tolerance) {
				complete = false;
			}
		}
		return complete;
	}
	
	private double tolerance = 0.0;
	private Optional<Double> elbowSetpoint = Optional.empty();
	private Optional<Double> telescopeSetpoint = Optional.empty();
	private double currentElbowPosition = 0.0;
	private double currentTeleScopePosition = 0.0;
	
}
