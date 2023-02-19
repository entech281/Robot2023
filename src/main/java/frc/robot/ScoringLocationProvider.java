package frc.robot;

import java.util.Optional;


import frc.robot.commands.ScoringLocationSupplier;
import frc.robot.commands.TargetNodeSupplier;
import frc.robot.pose.RecognizedAprilTagTarget;
import frc.robot.pose.ScoringLocation;
import frc.robot.pose.TargetNode;

public class ScoringLocationProvider implements ScoringLocationSupplier{
	
	private RobotState robotState;
	private TargetNodeSupplier nodeSupplier;
	
	public ScoringLocationProvider( TargetNodeSupplier nodeSupplier , RobotState robotState) {
		this.robotState = robotState;
		this.nodeSupplier = nodeSupplier;
	}

	
	@Override
	public Optional<ScoringLocation> getScoringLocation(){
		Optional<RecognizedAprilTagTarget> bestTarget = robotState.getBestAprilTagTarget();
		Optional<TargetNode> targetNode= nodeSupplier.getSelectedTarget();
		if ( bestTarget.isPresent() && targetNode.isPresent() ) {
			return Optional.ofNullable(new ScoringLocation(bestTarget.get().getTagLocation(),targetNode.get()));
		}
		else {
			return Optional.empty();
		}
	}
	
}
