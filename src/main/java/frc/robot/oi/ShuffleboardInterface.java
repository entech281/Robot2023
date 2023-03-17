package frc.robot.oi;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotConstants;
import frc.robot.RobotState;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.subsystems.SubsystemHolder;
import frc.robot.util.PoseUtil;

public class ShuffleboardInterface {

	private ShuffleboardTab PREMATCH_TAB = Shuffleboard.getTab(RobotConstants.SHUFFLEBOARD.TABS.PREMATCH);	
	private ShuffleboardTab MATCH_TAB = Shuffleboard.getTab(RobotConstants.SHUFFLEBOARD.TABS.MATCH);
	private ShuffleboardTab DEBUG_TAB = Shuffleboard.getTab(RobotConstants.SHUFFLEBOARD.TABS.DEBUG);
	
	//private Field2d fieldDisplay = new Field2d();
	//public final String SCORING_SOLUTION = "ScoringSolution";
	public final double TRAJECTORY_SPEED_DOESNT_MATTER = 10.0;

	
	public ShuffleboardInterface() {
		//MATCH_TAB.add("Estimated Location",fieldDisplay).withWidget(BuiltInWidgets.kField).withSize(4, 3).withPosition(0, 0);	
	}
	
	public void addRobotState(RobotState robotState) {
		MATCH_TAB.add("RobotState",robotState).withSize(2, 2).withPosition(8, 0);	
	}

	public void addTestCommands(List<Command> testCommands) {
		testCommands.forEach( (c)->{
			DEBUG_TAB.add(c.getName(),(Sendable)c).withSize(2, 1);
		});		
	}
	
	public void addPreMatchCommands(List<Command> testCommands) {
		testCommands.forEach( (c)->{
			PREMATCH_TAB.add(c.getName(),(Sendable)c).withSize(2, 1);
		});			
	}
	public void addSubsystems(SubsystemHolder allSubsystems) {

		ArmSubsystem arm = allSubsystems.getArm();
		ElbowSubsystem elbow = allSubsystems.getElbow();
		
		MATCH_TAB.add(allSubsystems.getDrive()).withSize(2, 2).withPosition(4,2);
		MATCH_TAB.add(allSubsystems.getNavx()).withSize(2, 2).withPosition(4,0);
		MATCH_TAB.add(allSubsystems.getVision()).withSize(2, 2).withPosition(6,0);
		MATCH_TAB.add(arm).withSize(2, 1).withPosition(6,2);
		MATCH_TAB.add(elbow).withSize(2, 1).withPosition(6,3);
		MATCH_TAB.add(allSubsystems.getGripper()).withSize(2, 1).withPosition(4,4);


		if ( arm.isEnabled()) {
			MATCH_TAB.add("ArmController",arm.getPositionController()).withSize(2, 3).withPosition(0, 0);		
		}
		if ( elbow.isEnabled()) {
			MATCH_TAB.add("ElbowController",elbow.getPositionController()).withSize(2, 3).withPosition(2, 0);		
		}		
	}
	
//	public void setRobotPose(Pose2d estimatedPose) {
//		fieldDisplay.setRobotPose(estimatedPose);
//	}
//	
//	public Field2d getField() {
//		return fieldDisplay;
//	}
	
//	public void displayScoringSolution(Pose2d start, Pose2d end) {
//		
//		//special note: the end of the trajectory is pointing towards us
//		//but for this, we need it to point away, because we're not actually
//		//going to end up in the same pose ( pointing the same way as the tag
//		Pose2d endFlipped = end.transformBy(PoseUtil.flipBy180Degrees2d());
//        Trajectory t =   TrajectoryGenerator.generateTrajectory( 
//				List.of(
//						start,
//						start.interpolate(endFlipped, 0.25),
//						start.interpolate(endFlipped, 0.5),
//						start.interpolate(endFlipped, 0.75),
//						endFlipped
//						),  
//				new TrajectoryConfig(TRAJECTORY_SPEED_DOESNT_MATTER,TRAJECTORY_SPEED_DOESNT_MATTER)
//		);     
//        fieldDisplay.getObject(SCORING_SOLUTION).setTrajectory(t);
//		
//	}	
	
}
