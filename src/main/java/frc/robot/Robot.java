// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.subsystems.SubsystemManager;
import frc.robot.commands.CommandFactory;
import frc.robot.pose.AlignCalc;
import frc.robot.pose.AlignmentSolution;
import frc.robot.pose.ArmPose;
import frc.robot.pose.DrivePose;
import frc.robot.pose.NavxPose;
import frc.robot.pose.PoseCalculator;
import frc.robot.pose.RobotPose;
import frc.robot.pose.TargetNode;
import frc.robot.pose.VisionPose;
import frc.robot.subsystems.DriveSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private SubsystemManager subsystemManager;
  private OperatorInterface oi;
  private CommandFactory commandFactory;
  private Command autoCommand;
  private Supplier<RobotPose> latestRobotPoseSupplier;
  private RobotPose latestRobotPose;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    subsystemManager = new SubsystemManager();
    subsystemManager.initAll();

    latestRobotPose = new PoseCalculator().calculatePose(
      subsystemManager.getDriveSubsystem().getDriveOutput(), 
      subsystemManager.getVisionSubsystem().getVisionOutput(), 
      subsystemManager.getNavXSubSystem().getNavxOutput(), 
      subsystemManager.getArmSubsystem().getArmOutput()
    );

    latestRobotPoseSupplier = () -> { return latestRobotPose; };

    commandFactory = new CommandFactory(subsystemManager, latestRobotPoseSupplier);
    oi = new OperatorInterface(subsystemManager,commandFactory);
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
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    latestRobotPose = new PoseCalculator().calculatePose(
      subsystemManager.getDriveSubsystem().getDriveOutput(), 
      subsystemManager.getVisionSubsystem().getVisionOutput(), 
      subsystemManager.getNavXSubSystem().getNavxOutput(), 
      subsystemManager.getArmSubsystem().getArmOutput()
    );

    subsystemManager.getDriveSubsystem()
      .activateAlignmentSolution(
        new AlignCalc().calculateSolution(TargetNode.A1, latestRobotPose)
      );

    updateAlignment();
    CommandScheduler.getInstance().run();
  }

  
  private void updateAlignment(){
      DriveSubsystem drive = subsystemManager.getDriveSubsystem();
      DrivePose dro = drive.getDriveOutput();
      VisionPose vo = subsystemManager.getVisionSubsystem().getVisionOutput();
      ArmPose ao = subsystemManager.getArmSubsystem().getArmOutput();
      NavxPose no = subsystemManager.getNavXSubSystem().getNavxOutput();
      
      RobotPose rp = new PoseCalculator().calculatePose( dro, vo, no, ao );
      TargetNode tn = oi.getTargetNode();
      
      
      AlignmentSolution as = new AlignCalc().calculateSolution(tn, rp);
      drive.activateAlignmentSolution(as);
      
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
  public void testPeriodic() {}
}
