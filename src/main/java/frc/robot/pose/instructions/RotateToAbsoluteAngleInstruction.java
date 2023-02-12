package frc.robot.pose.instructions;

public class RotateToAbsoluteAngleInstruction extends AlignmentInstruction {

	private double absoluteTargetAngleDegrees = 0.0;
	
	public RotateToAbsoluteAngleInstruction(double angleDegrees) {
		absoluteTargetAngleDegrees = angleDegrees;
	}
	
	public double getAbsoluteTargetAngleDegrees() {
		return absoluteTargetAngleDegrees;
	}
}
