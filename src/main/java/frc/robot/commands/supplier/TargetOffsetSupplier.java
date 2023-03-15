package frc.robot.commands.supplier;

import java.util.Optional;

import edu.wpi.first.math.geometry.Transform3d;

public interface TargetOffsetSupplier {

	public Optional<Transform3d> getTargetOffset();
}
