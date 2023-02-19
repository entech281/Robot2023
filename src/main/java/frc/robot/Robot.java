/// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.oi.OperatorInterface;
import frc.robot.oi.ShuffleboardDriverControls;
import frc.robot.oi.ShuffleboardFieldDisplay;
import frc.robot.pose.AlignmentCalculator;
import frc.robot.pose.VisionFirstNavxAsBackupPoseEstimator;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.VisionSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {  
  private OperatorInterface oi;
  private CommandFactory commandFactory;
  private Command autoCommand;
  private RobotContext robotContext;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

	ShuffleboardTab MATCH_TAB = Shuffleboard.getTab(RobotConstants.SHUFFLEBOARD.TABS.MATCH);  
	ShuffleboardDriverControls shuffleboardControls = new ShuffleboardDriverControls();
	ShuffleboardFieldDisplay fieldDisplay = new ShuffleboardFieldDisplay();
	  
	  
	DriveSubsystem drive = new DriveSubsystem();
	VisionSubsystem vision = new VisionSubsystem();
	NavXSubSystem navx = new NavXSubSystem();
	ArmSubsystem arm = new ArmSubsystem();
	
	List.of(drive,vision,navx,arm).forEach((s)-> {
		s.initialize();
		MATCH_TAB.add(s);
	});

	//this looks like a little more typing, but its useful to note that this allows
	//us to declare which subsystem these ACTUALLY use, vs giving everyone a subsystem manager,
	//which allows them to get everything, but then its not clear what they need
	RobotState robotState = new RobotState();
	MATCH_TAB.add("RobotState",robotState);
	
	robotContext = new RobotContext(new AlignmentCalculator(),
			robotState, fieldDisplay,drive,navx,vision, new VisionFirstNavxAsBackupPoseEstimator(false),
			shuffleboardControls
	);
	
	
	commandFactory = new CommandFactory(robotState,drive,navx,vision,arm);
	oi = new OperatorInterface(commandFactory,shuffleboardControls);
  }

  private void doPeriodic() {
		robotContext.periodic();
	    CommandScheduler.getInstance().run();	  
  }
  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
	  doPeriodic();
  }
  
  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {

    // Get selected routine 
    autoCommand = commandFactory.getAutonomousCommand();

    // schedule the autonomous command
    if (autoCommand != null) {
      autoCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running which will
    // use the default command which is ArcadeDrive. If you want the autonomous
    // to continue until interrupted by another command, remove
    // this line or comment it out.
    if (autoCommand != null) {
      autoCommand.cancel();
    }
    oi.setDefaultDriveCommand();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
	  doPeriodic();
  }

@Override
public void simulationPeriodic() {
	  doPeriodic();
}
  
  
  
}
