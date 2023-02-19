package frc.robot;
import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.commands.EstimatedPoseSupplier;
import frc.robot.commands.YawAngleSupplier;
import frc.robot.pose.RecognizedAprilTagTarget;
import frc.robot.pose.TargetNode;
public class RobotState implements Sendable, EstimatedPoseSupplier , YawAngleSupplier{


	public Optional<RecognizedAprilTagTarget> getBestAprilTagTarget() {
		return bestAprilTagTarget;
	}

	public void setBestAprilTagTarget(Optional<RecognizedAprilTagTarget> bestAprilTagTarget) {
		this.bestAprilTagTarget = bestAprilTagTarget;
	}

	public Optional<Double> getAlignAngle() {
		return alignAngle;
	}

	public void setAlignAngle(Optional<Double> alignAngle) {
		this.alignAngle = alignAngle;
	}

	private Optional<Pose2d> estimatedPose;
	
	public void setEstimatedPose(Optional<Pose2d> estimatedPose) {
		this.estimatedPose = estimatedPose;
	}

	private Optional<RecognizedAprilTagTarget> bestAprilTagTarget = Optional.empty();
	private Optional<Double> alignAngle= Optional.empty();
	
	

	@Override
	public Optional<Pose2d> getEstimatedPose() {
		return estimatedPose;
	}
	
	@Override
	public double getYawAngleDegrees() {
		return 0.0;
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		// TODO Auto-generated method stub
		
	}
}
