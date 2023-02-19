package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StoppingCounter {

	private int targetCounts = 0;
	private int currentCounts = 0;
	private String name;
	
	public StoppingCounter(String name, int targetCounts) {
		this.name = name;
		this.targetCounts = targetCounts;		
	}
	
	public boolean isFinished(boolean conditionToCheck) {
		if ( conditionToCheck) {
			currentCounts++;
		}
		else {
			currentCounts = 0;
		}
		SmartDashboard.putNumber("StopCounter::" + name, currentCounts);
		return currentCounts > targetCounts;

	}
}
