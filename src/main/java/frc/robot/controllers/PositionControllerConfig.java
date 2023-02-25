package frc.robot.controllers;

public class PositionControllerConfig {


	private PositionControllerConfig() {
		
	}
	private PositionControllerConfig(double homingSpeed, boolean reversed, int homePosition) {
		this.homePosition = homePosition;
		this.reversed = reversed;
		this.homingSpeedPercent=homingSpeed;
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
		return homePosition;
	}
	public void setHomePosition(int homePosition) {
		this.homePosition = homePosition;
	}

	private int homePosition = 0;
	private int backoffDDistance = 0;
	private double homingSpeedPercent = 10.0;	


	public double getBackoffDDistance() {
		return backoffDDistance;
	}
	public void setBackoffDDistance(int backoffDDistance) {
		this.backoffDDistance = backoffDDistance;
	}

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
