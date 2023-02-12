package frc.robot.pose.instructions;

public class MoveDistanceInstruction extends AlignmentInstruction {

	public double getMoveXDistanceInches() {
		return moveXDistanceInches;
	}

	public double getMoveYDistanceInches() {
		return moveYDistanceInches;
	}

	private double moveXDistanceInches = 0.0;
	private double moveYDistanceInches = 0.0;
	
	public MoveDistanceInstruction( double moveXDistanceInches, double moveYDistanceInches) {
		this.moveXDistanceInches = moveXDistanceInches;
		this.moveYDistanceInches = moveYDistanceInches;
	}
}
