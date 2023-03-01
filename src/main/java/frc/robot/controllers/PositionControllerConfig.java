package frc.robot.controllers;


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



	public double getHomePosition() {
		return homePosition;
	}


	public void setHomePosition(double homePosition) {
		this.homePosition = homePosition;
	}


	public double getBackoff() {
		return backoff;
	}


	public void setBackoff(double backoff) {
		this.backoff = backoff;
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

	private double homePosition = 0;
	private double backoff = 0;
	private double positionTolerance = 5;
	private double homingSpeedPercent = 10.0;	
	private double minPosition = 0;
	private double maxPosition = 0;



	public static class Builder {
		public Builder(String name ) {
			c.name = name;
		}
		private PositionControllerConfig c = new PositionControllerConfig();
		
		public Builder withSoftLimits(double lowerLimitCounts, double upperLimitCounts) {
			c.maxPosition=upperLimitCounts;
			c.minPosition=lowerLimitCounts;
			return this;
		}

		public Builder withPositionTolerance( double positionToleranceCounts) {
			c.positionTolerance= positionToleranceCounts;
			return this;
		}
		public Builder withHomingOptions ( double homingSpeedPercent, double backoffCounts , double homePositionCounts) {
			c.backoff = backoffCounts;
			c.homingSpeedPercent = homingSpeedPercent;
			c.homePosition = homePositionCounts;
			return this;
		}

		public PositionControllerConfig build() {
			return c;
		}
	}	
	
}
