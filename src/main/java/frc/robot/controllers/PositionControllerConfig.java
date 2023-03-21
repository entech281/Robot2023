package frc.robot.controllers;

import java.util.Optional;

public class PositionControllerConfig {


	private PositionControllerConfig() {
		
	}


	public String getName() {
		return name;
	}

	private String name = "";
	public void setName(String name) {
		this.name = name;
	}


	public double getPositionTolerance() {
		return positionTolerance;
	}


	public void setPositionTolerance(double positionTolerance) {
		this.positionTolerance = positionTolerance;
	}


	public double getHomingSpeedPercent() {
		return homingSpeedPercent;
	}


	public void setHomingSpeedPercent(double homingSpeedPercent) {
		this.homingSpeedPercent = homingSpeedPercent;
	}


	public double getMinPosition() {
		return minPosition;
	}


	public void setMinPosition(double minPosition) {
		this.minPosition = minPosition;
	}


	public double getMaxPosition() {
		return maxPosition;
	}


	public void setMaxPosition(double maxPosition) {
		this.maxPosition = maxPosition;
	}

	private double positionTolerance = 5;
	private double homingSpeedPercent = 10.0;

	private double minPosition = 0;
	private double maxPosition = 0;
	private boolean inverted = false;
	private Optional<Double> homeAtCurrentAmps = Optional.empty();
	private double homingSpeedUnitsPerSec = 0.0;
	private boolean homeClosedLoop = false;	

	
	public double getHomingSpeedUnitsPerSec() {
		return homingSpeedUnitsPerSec;
	}


	public boolean isHomeClosedLoop() {
		return homeClosedLoop;
	}


	
	public Optional<Double> getHomeAtCurrentAmps() {
		return homeAtCurrentAmps;
	}


	public void setHomeAtCurrentAmps(Optional<Double> homeAtCurrentAmps) {
		this.homeAtCurrentAmps = homeAtCurrentAmps;
	}


	public boolean isInverted() {
		return inverted;
	}


	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}


	public static class Builder {
		public Builder(String name ) {
			c.name = name;
		}
		private PositionControllerConfig c = new PositionControllerConfig();
		
		public Builder withSoftLimits(double lowerLimit, double upperLimit) {
			c.maxPosition=upperLimit;
			c.minPosition=lowerLimit;
			return this;
		}

		public Builder withPositionTolerance( double positionToleranceCounts) {
			c.positionTolerance= positionToleranceCounts;
			return this;
		}
		public Builder withInverted(boolean inverted) {
			c.inverted = inverted;
			return this;
		}
		public Builder withHomingVelocity ( double homingVelocity) {
			c.homingSpeedUnitsPerSec = homingVelocity;
			c.homeClosedLoop = true;
			return this;
		}
		public Builder withHomingOptions ( double homingSpeedPercent) {
			c.homingSpeedPercent = homingSpeedPercent;
			c.homeClosedLoop = false;
			return this;
		}
		public Builder withHomeAtCurrentAmps(double currentAmps) {
			c.homeAtCurrentAmps = Optional.of(currentAmps);
			return this;
		}
		public PositionControllerConfig build() {
			return c;
		}
	}	
	
}
