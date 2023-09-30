package frc.robot.utils;

public class Counter {
	private int targetCount;
	private int currentCount=0;
	
	
	public Counter(int targetCount) {
		this.targetCount = targetCount;
	}
	
	public void reset() {
		this.currentCount = 0;
	}
	public int value() {
		return this.currentCount;
	}
	
	public void up() {
		this.currentCount++;
	}
	public void down() {
		this.currentCount--;
	}
	
	public boolean atOrAboveTarget() {
		return this.currentCount >= this.targetCount;
	}
	public boolean atTarget() {
		return this.currentCount == this.targetCount;
	}
	public boolean aboveTarget() {
		return this.currentCount > this.targetCount;
	}
}
