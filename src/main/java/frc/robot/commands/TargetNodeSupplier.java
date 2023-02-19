package frc.robot.commands;

import java.util.Optional;

import frc.robot.pose.TargetNode;

public interface TargetNodeSupplier {

	public Optional<TargetNode> getSelectedTarget();
}
