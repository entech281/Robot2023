package frc.robot.controllers;

public class PositionControllerConfig {


	private PositionControllerConfig() {
		
	}

	public double getHomingSpeed() {
		return homingSpeedPercent;
	}
	public void setHomingSpeed(double homingSpeed) {
		this.homingSpeedPercent = homingSpeed;
	}
	
	private boolean reversed = false;
	public boolean isReversed() {
		return reversed;
	}
	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

	public double getHomePosition() {
		return homePositionCounts;
	}
	public void setHomePosition(int homePosition) {
		this.homePositionCounts = homePosition;
	}

	public int getHomePositionCounts() {
		return homePositionCounts;
	}

	public int getBackoffCounts() {
		return backoffCounts;
	}

	public int getToleranceCounts() {
		return toleranceCounts;
	}

	public double getHomingSpeedPercent() {
		return homingSpeedPercent;
	}

	public int getMinPositionCounts() {
		return minPositionCounts;
	}

	public int getMaxPositionCounts() {
		return maxPositionCounts;
	}

	private int homePositionCounts = 0;
	private int backoffCounts = 0;
	private int toleranceCounts = 5;
	private double homingSpeedPercent = 10.0;	
	private int minPositionCounts = 0;
	private int maxPositionCounts = 0;

	public static class Builder {
		private PositionControllerConfig c = new PositionControllerConfig();
		public Builder() {
			
		}
		public Builder withReversed(boolean reversed) {
			c.setReversed(reversed);
			return this;
		}
		public Builder withHomingSpeedPercent( double speed) {
			c.setHomingSpeed(speed);
			return this;
		}
		public Builder withHomePositionCounts( int homePosition) {
			c.setHomePosition(homePosition);
			return this;
		}
		public Builder withBackoffDistanceCounts( int distance) {
			c.setBackoffDDistance(distance);
			return this;
		}
		public PositionControllerConfig build() {
			return c;
		}
	}	
	
}
