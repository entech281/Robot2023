package frc.robot;
import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.filters.DriveInput;
import frc.robot.pose.ScoringLocation;;
public class RobotState {

	public DriveInput getOperatorDriveInput() {
		return null;
	}
	public Optional<ScoringLocation> getScoringLocation(){
		return Optional.ofNullable(null);
	}
	
	public Optional<Pose2d> getEstimatedPose() {
		return Optional.ofNullable(new Pose2d());
	}
	
	public double getCalculatedAlignAngle() {
		return 0.0;
	}
}
