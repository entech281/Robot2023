/// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.logging.ExceptionHandler;
import frc.robot.oi.OperatorInterface;
import frc.robot.oi.ShuffleboardDriverControls;
import frc.robot.oi.ShuffleboardInterface;
import frc.robot.pose.AlignmentCalculator;
import frc.robot.pose.VisionFirstNavxAsBackupPoseEstimator;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.BrakeSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;
import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.SubsystemHolder;
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
  private ShuffleboardDriverControls shuffleboardControls;
  private ShuffleboardInterface shuffleboardInterface;
  private SubsystemHolder allSubsystems;
  private ExceptionHandler exceptionHandler = new ExceptionHandler();

  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

	  
	ArmSubsystem arm = new ArmSubsystem();
	ElbowSubsystem elbow = new ElbowSubsystem();
	GripperSubsystem gripper = new GripperSubsystem();	  
	VisionSubsystem vision = new VisionSubsystem();
	NavXSubSystem navx = new NavXSubSystem();
	DriveSubsystem drive = new DriveSubsystem();
	LEDSubsystem led = new LEDSubsystem();
    BrakeSubsystem brake = new BrakeSubsystem();
	
	allSubsystems = new SubsystemHolder(drive, navx, vision, arm, elbow, gripper, led, brake);
	
	allSubsystems.asList().forEach((s)-> {
		if ( s.isEnabled()) {
			s.initialize();
		}				
	});

	shuffleboardControls = new ShuffleboardDriverControls();	
	shuffleboardInterface = new ShuffleboardInterface();	

	RobotState robotState = new RobotState();
	shuffleboardInterface.addRobotState(robotState);
	robotContext = new RobotContext(
			robotState,drive,navx,vision, elbow,led,new VisionFirstNavxAsBackupPoseEstimator(true)
	);	
	commandFactory = new CommandFactory(robotState,allSubsystems);

	oi = new OperatorInterface(commandFactory);

	setupShuffleboardInterface();
	Compressor c = new Compressor(PneumaticsModuleType.CTREPCM);
	c.enableDigital();
	navx.zeroYaw();
	drive.initEncoders();
	
    // Enable telematics of the SmartDashboard data.  Joystick data is currently true
    DataLogManager.start();
    DriverStation.startDataLog(DataLogManager.getLog(), true);
    led.setColor(Color.kBisque);
  }
  private void setupShuffleboardInterface() {
			
		shuffleboardInterface.addSubsystems(allSubsystems);
		shuffleboardInterface.addTestCommands(commandFactory.getTestCommands());
		shuffleboardInterface.addPreMatchCommands(commandFactory.getPrematchCommands());
		List<Command> autoChoices = commandFactory.getAutoCommandChoices();
		autoChoices.forEach((c)->{
            shuffleboardControls.addAutoCommandChoice(c,c == autoChoices.get(0));
		});	  

  }
  
  private void doPeriodic() {
    robotContext.periodic();	  
    CommandScheduler.getInstance().run();		  
//    try {
//			robotContext.periodic();	  
//	  }
//	  catch (Throwable t) {
//		  exceptionHandler.handleException(t);
//          t.printStackTrace();
//	  }
//	  try {
//		    CommandScheduler.getInstance().run();		  
//	  }
//	  catch (Throwable t) {
//		  exceptionHandler.handleException(t);
//          t.printStackTrace();
//	  }
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
  public void disabledExit() {
    allSubsystems.getNavx().assignAlliance();
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {

    // Get selected routine 
    autoCommand = shuffleboardControls.getSelectedAutoCommand();

    //allSubsystems.getArm().clearRequestedPosition();
    //allSubsystems.getElbow().clearRequestedPosition();
    // schedule the autonomous command
    if (autoCommand != null) {
      autoCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
	  doPeriodic();  
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running which will
    // use the default command which is ArcadeDrive. If you want the autonomous
    // to continue until interrupted by another command, remove
    // this line or comment it out.
    if (autoCommand != null) {
      autoCommand.cancel();
    }
    oi.setDefaultCommands();
    commandFactory.retractBrakeCommand().schedule();
    allSubsystems.getDrive().setDriveMode(DriveMode.BRAKE);

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
