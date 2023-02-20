package frc.robot.commands.supplier;
import java.util.Optional;

import frc.robot.pose.ScoringLocation;
public interface ScoringLocationSupplier {
	public Optional<ScoringLocation> getScoringLocation();
}
