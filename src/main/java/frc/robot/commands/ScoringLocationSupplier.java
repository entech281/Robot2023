package frc.robot.commands;
import java.util.Optional;

import frc.robot.pose.ScoringLocation;
public interface ScoringLocationSupplier {
	public Optional<ScoringLocation> getScoringLocation();
}
