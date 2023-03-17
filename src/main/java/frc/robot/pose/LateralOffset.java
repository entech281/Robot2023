package frc.robot.pose;

public class LateralOffset {



	private double lateralOffsetToLocationMeters;
	private ScoringLocation nearestLocation;
	
	public LateralOffset( double lateralOffsetToLocation, ScoringLocation nearestLocation) {
		this.nearestLocation = nearestLocation;
		this.lateralOffsetToLocationMeters = lateralOffsetToLocation;
	}
	
	public double getLateralOffsetToLocationMeters() {
		return lateralOffsetToLocationMeters;
	}

	public ScoringLocation getNearestLocation() {
		return nearestLocation;
	}
	
	
}
