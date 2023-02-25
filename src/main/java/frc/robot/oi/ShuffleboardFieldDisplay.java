package frc.robot.oi;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import frc.robot.RobotConstants;
import frc.robot.util.PoseUtil;


public class ShuffleboardFieldDisplay {

	private ShuffleboardTab visionTab = Shuffleboard.getTab(RobotConstants.SHUFFLEBOARD.TABS.MATCH);
	private Field2d fieldDisplay = new Field2d();
	public final String SCORING_SOLUTION = "ScoringSolution";
	public final double TRAJECTORY_SPEED_DOESNT_MATTER = 10.0;
	
	public ShuffleboardFieldDisplay() {
		visionTab.add("Estimated Location",fieldDisplay).withWidget(BuiltInWidgets.kField).withSize(4, 3).withPosition(0, 0);	
	}
	
	public void setRobotPose(Pose2d estimatedPose) {
		fieldDisplay.setRobotPose(estimatedPose);
	}
	
	public Field2d getField() {
		return fieldDisplay;
	}
	
	public void displayScoringSolution(Pose2d start, Pose2d end) {
		
		//special note: the end of the trajectory is pointing towards us
		//but for this, we need it to point away, because we're not actually
		//going to end up in the same pose ( pointing the same way as the tag
		Pose2d endFlipped = end.transformBy(PoseUtil.flipBy180Degrees2d());
        Trajectory t =   TrajectoryGenerator.generateTrajectory( 
				List.of(
						start,
						start.interpolate(endFlipped, 0.25),
						start.interpolate(endFlipped, 0.5),
						start.interpolate(endFlipped, 0.75),
						endFlipped
						),  
				new TrajectoryConfig(TRAJECTORY_SPEED_DOESNT_MATTER,TRAJECTORY_SPEED_DOESNT_MATTER)
		);     
        fieldDisplay.getObject(SCORING_SOLUTION).setTrajectory(t);
		
	}
}
