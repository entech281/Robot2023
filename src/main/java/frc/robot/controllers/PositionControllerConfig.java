package frc.robot.controllers;

public class PositionControllerConfig {


	private PositionControllerConfig() {
		
	}
	public boolean isReversed() {
		return reversed;
	}

	public int getHomePositionCounts() {
		return homePositionCounts;
	}

	public int getBackoffCounts() {
		return backoffCounts;
	}

	public int getPositionToleranceCounts() {
		return positionToleranceCounts;
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

	public String getName() {
		return name;
	}

	private String name = "";
	private boolean reversed = false;
	private int homePositionCounts = 0;
	private int backoffCounts = 0;
	private int positionToleranceCounts = 5;
	private double homingSpeedPercent = 10.0;	
	private int minPositionCounts = 0;
	private int maxPositionCounts = 0;

	public static class Builder {
		public Builder(String name ) {
			c.name = name;
		}
		private PositionControllerConfig c = new PositionControllerConfig();
		
		public Builder withSoftLimits(int lowerLimitCounts, int upperLimitCounts) {
			c.maxPositionCounts=upperLimitCounts;
			c.minPositionCounts=lowerLimitCounts;
			return this;
		}
		public Builder withPositionTolerance( int positionToleranceCounts) {
			c.positionToleranceCounts = positionToleranceCounts;
			return this;
		}
		public Builder withReversed(boolean reversed) {
			c.reversed = reversed;
			return this;
		}
		public Builder withHomingOptions ( double homingSpeedPercent, int backoffCounts , int homePositionCounts) {
			c.backoffCounts = backoffCounts;
			c.homingSpeedPercent = homingSpeedPercent;
			c.homePositionCounts = homePositionCounts;
			return this;
		}
		public PositionControllerConfig build() {
			return c;
		}
	}	
	
}
