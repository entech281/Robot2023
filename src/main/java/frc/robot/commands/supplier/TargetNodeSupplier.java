package frc.robot.commands.supplier;

import java.util.Optional;

import frc.robot.pose.TargetNode;

public interface TargetNodeSupplier {

	public Optional<TargetNode> getSelectedTarget();
}
