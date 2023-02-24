package frc.robot.commands.instructions;

public class MoveDistanceInstruction extends AlignmentInstruction {

	public double getMoveXDistanceMeters() {
		return moveXDistanceMeters;
	}

	public double getMoveYDistanceMeters() {
		return moveYDistanceMeters;
	}

	private double moveXDistanceMeters = 0.0;
	private double moveYDistanceMeters = 0.0;
	
	public MoveDistanceInstruction( double moveXDistanceMeters, double moveYDistanceMeters) {
		this.moveXDistanceMeters = moveXDistanceMeters;
		this.moveYDistanceMeters = moveYDistanceMeters;
	}
}
