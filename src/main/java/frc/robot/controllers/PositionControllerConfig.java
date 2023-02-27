package frc.robot.controllers;

import com.revrobotics.SparkMaxLimitSwitch.Type;

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
	public void setName(String name) {
		this.name = name;
	}
	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}
	public void setHomePositionCounts(int homePositionCounts) {
		this.homePositionCounts = homePositionCounts;
	}
	public void setBackoffCounts(int backoffCounts) {
		this.backoffCounts = backoffCounts;
	}
	public void setPositionToleranceCounts(int positionToleranceCounts) {
		this.positionToleranceCounts = positionToleranceCounts;
	}
	public void setHomingSpeedPercent(double homingSpeedPercent) {
		this.homingSpeedPercent = homingSpeedPercent;
	}
	public void setMinPositionCounts(int minPositionCounts) {
		this.minPositionCounts = minPositionCounts;
	}
	public void setMaxPositionCounts(int maxPositionCounts) {
		this.maxPositionCounts = maxPositionCounts;
	}

	private boolean reversed = false;
	private int homePositionCounts = 0;
	private int backoffCounts = 0;
	private int positionToleranceCounts = 5;
	private double homingSpeedPercent = 10.0;	
	private int minPositionCounts = 0;
	private int maxPositionCounts = 0;
	private boolean swapLimitSwitches = false;


	public Type getLowerLimitSwitchType() {
		return lowerLimitSwitchType;
	}
	public void setLowerLimitSwitchType(Type lowerLimitSwitchType) {
		this.lowerLimitSwitchType = lowerLimitSwitchType;
	}
	public Type getUpperLimitSwitchType() {
		return upperLimitSwitchType;
	}
	public void setUpperLimitSwitchType(Type upperLimitSwitchType) {
		this.upperLimitSwitchType = upperLimitSwitchType;
	}

	private Type lowerLimitSwitchType = Type.kNormallyOpen;
	private Type upperLimitSwitchType = Type.kNormallyOpen;
	
	public boolean isSwapLimitSwitches() {
		return swapLimitSwitches;
	}
	public void setSwapLimitSwitches(boolean swapLimitSwitches) {
		this.swapLimitSwitches = swapLimitSwitches;
	}

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
		public Builder withSwappedLimitSwitches(boolean swapped) {
			c.swapLimitSwitches = swapped;
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
		public Builder withLimitSwitchTypes ( Type lowerLimitSwitchType, Type upperLimitSwitchType) {
			c.lowerLimitSwitchType = lowerLimitSwitchType;
			c.upperLimitSwitchType = upperLimitSwitchType;
			return this;
		}
		public PositionControllerConfig build() {
			return c;
		}
	}	
	
}
