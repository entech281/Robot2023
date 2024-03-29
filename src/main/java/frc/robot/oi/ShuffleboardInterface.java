package frc.robot.oi;

import java.util.List;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.RobotConstants;
import frc.robot.RobotState;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.subsystems.SubsystemHolder;

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
		MATCH_TAB.add("RobotState",robotState).withSize(2, 3).withPosition(2, 0);
	}

	public void addVisionCamera() {
		MATCH_TAB.addCamera("Vision", "Arudcam","http://10.2.81.99:1182/?action=stream" ).withSize(3, 2).withPosition(7, 0);
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

		MATCH_TAB.add(allSubsystems.getDrive()).withSize(2, 3).withPosition(0,0);
		//MATCH_TAB.add(allSubsystems.getNavx()).withSize(2, 2).withPosition(4,0);
		//MATCH_TAB.add(allSubsystems.getVision()).withSize(2, 2).withPosition(2,2);
		//MATCH_TAB.add(arm).withSize(2, 1).withPosition(6,2);
		//MATCH_TAB.add(elbow).withSize(2, 1).withPosition(2,3);
		//MATCH_TAB.add(allSubsystems.getGripper()).withSize(2, 1).withPosition(2,4);

		//VideoSource photonvision = CameraServer.getServer("photonvision").getSource();
		//MATCH_TAB.add(photonvision).withSize(3, 2).withPosition(7, 3);
		if ( arm.isEnabled()) {
			MATCH_TAB.add("ArmController",arm.getPositionController()).withSize(2, 2).withPosition(4, 2);
		}
		if ( elbow.isEnabled()) {
			MATCH_TAB.add("ElbowController",elbow.getPositionController()).withSize(2, 2).withPosition(4, 0);
		}
	}

}
