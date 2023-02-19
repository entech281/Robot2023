package frc.robot.commands;
import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
public interface EstimatedPoseSupplier {

	public Optional<Pose2d> getEstimatedPose();
}
