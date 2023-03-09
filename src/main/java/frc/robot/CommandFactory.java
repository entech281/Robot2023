package frc.robot;

import java.util.List;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotConstants.ARM;
import frc.robot.adapter.DriveInputYawMixer;
import frc.robot.commands.AlignToScoringLocationCommand;
import frc.robot.commands.DefaultGripperCommand;
import frc.robot.commands.DriveBrakeForSeconds;
import frc.robot.commands.DriveDirectionCommand;
import frc.robot.commands.DriveDistanceCommand;
import frc.robot.commands.FilteredDriveCommand;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.HomeArmCommand;
import frc.robot.commands.HomeElbowCommand;
import frc.robot.commands.PositionElbowCommand;
import frc.robot.commands.PositionTelescopeCommand;
import frc.robot.commands.SetArmSpeedCommand;
import frc.robot.commands.SetDriverYawEnableCommand;
import frc.robot.commands.SimpleDriveCommand;
import frc.robot.commands.SnapYawDegreesCommand;
import frc.robot.commands.ToggleFieldAbsoluteCommand;
import frc.robot.commands.ZeroGyroCommand;
import frc.robot.commands.nudge.NudgeDirectionCommand;
import frc.robot.commands.nudge.NudgeElbowDownCommand;
import frc.robot.commands.nudge.NudgeElbowUpCommand;
import frc.robot.commands.nudge.NudgeTelescopeBackwardsCommand;
import frc.robot.commands.nudge.NudgeTelescopeForwardCommand;
import frc.robot.commands.nudge.NudgeYawCommand;
import frc.robot.commands.supplier.TargetNodeSupplier;
import frc.robot.filters.DriveInput;
import frc.robot.oi.ShuffleboardDriverControls;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.GripperSubsystem.GripperState;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.SubsystemHolder;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.RobotConstants;
/**
 *
 * @author dcowden
 * @author aheitkamp
 */
public class CommandFactory {

	private RobotState robotState;
	private DriveSubsystem driveSubsystem;
	private VisionSubsystem visionSubsystem;
	private NavXSubSystem navxSubsystem;
	private ArmSubsystem armSubsystem;
	private ElbowSubsystem elbowSubsystem;
	private GripperSubsystem gripperSubsystem;

    public CommandFactory(RobotState robotState, SubsystemHolder allSubsystems ){
    	this.driveSubsystem = allSubsystems.getDrive();
    	this.navxSubsystem = allSubsystems.getNavx();
    	this.visionSubsystem = allSubsystems.getVision();
    	this.armSubsystem = allSubsystems.getArm();
        this.elbowSubsystem = allSubsystems.getElbow();
        this.gripperSubsystem = allSubsystems.getGripper();
        this.robotState = robotState;
    }

    public List<Command> getAutoCommandChoices(){
    	//these commands will be available for autonomous mode on the PREMATCH tab
        // First option in list will be the default choice
        Command c5 = autonomousFarCommand();
        c5.setName("Autonomous Far");
        Command c6 = autonomousBalanceDeadRecCommand();
        c6.setName("Autonomous Balance DeadRec");
    	return List.of( c5, c6 );

    }
    
    public List<Command> getPrematchCommands(){
    	return List.of(
    		homeTelescopeAndElbow()
    	);
    }
    public List<Command> getTestCommands(){
    	//these will be available to run ad-hoc on the TESTING tab
    	return List.of (
			highScoringElbowCommand(),
			middleScoringElbowCommand(),
			groundScoringElbowCommand(),
			loadingElbowCommand(),
			carryElbowCommand(),
			new PositionTelescopeCommand(armSubsystem,1.4, false),
			new PositionTelescopeCommand(armSubsystem,0.2, false),
			new PositionTelescopeCommand(armSubsystem,0.08, false),

			new PositionElbowCommand(elbowSubsystem,RobotConstants.ELBOW.POSITION_PRESETS.MIN_POSITION_DEGREES, false),
			new GripperCommand(gripperSubsystem,GripperState.kClose,"CloseGripper"),
			new GripperCommand(gripperSubsystem,GripperState.kOpen,"OpenGripper"),
			new HomeArmCommand(armSubsystem),
            new HomeArmCommand(armSubsystem, true),
            homeTelescopeAndElbow()
    	);
    }

    public Command getAutonomousChoice() {
        return autonomousFarCommand();
    }
    
    public Command autonomousFarCommand() {
        double MOVE_DISTANCE_METERS = -4.0;
        double HOLD_BRAKE_TIME = 2.0;
        SequentialCommandGroup sg =  new SequentialCommandGroup(
        	new ZeroGyroCommand(navxSubsystem)
            , new GripperCommand(gripperSubsystem, GripperState.kClose)
            , new PositionElbowCommand(elbowSubsystem, 100, true)
            , new PositionTelescopeCommand(armSubsystem, 1.38, true)
            , new GripperCommand(gripperSubsystem, GripperState.kOpen)
            , new WaitCommand(1)
            , new PositionTelescopeCommand ( armSubsystem, RobotConstants.ARM.POSITION_PRESETS.CARRY_METERS,true)
            , new PositionElbowCommand ( elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.CARRY_DEGREES, true)
            , new DriveDistanceCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.4, 0.3, .1)
            , new DriveBrakeForSeconds(driveSubsystem, HOLD_BRAKE_TIME)
        );
        sg.setName("AutonomousFarCommand");
        return sg;
    }
    
    public Command autonomousBalanceDeadRecCommand() {
        double MOVE_DISTANCE_METERS = -2.5;
        double HOLD_BRAKE_TIME = 2.0;
        SequentialCommandGroup sg =  new SequentialCommandGroup(
        	new ZeroGyroCommand(navxSubsystem)
            , new GripperCommand(gripperSubsystem, GripperState.kClose)
            , new PositionElbowCommand(elbowSubsystem, 100, true)
            , new PositionTelescopeCommand(armSubsystem, 1.38, true)
            , new GripperCommand(gripperSubsystem, GripperState.kOpen)
            , new WaitCommand(1)
            , new PositionTelescopeCommand ( armSubsystem, RobotConstants.ARM.POSITION_PRESETS.CARRY_METERS,true)
            , new PositionElbowCommand ( elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.CARRY_DEGREES, true)
            , new DriveDistanceCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.4, 0.3, .1)
            , new DriveBrakeForSeconds(driveSubsystem, HOLD_BRAKE_TIME)
        );
        sg.setName("AutonomousBalanceDeadRecCommand");
        return sg;
    }

    public Command autonomusMiddleCommand() {
        return new SequentialCommandGroup(
        new PositionElbowCommand(elbowSubsystem, 80, true)
        , new PositionTelescopeCommand(armSubsystem, 1.1, true)
        );
    }

    public Command deployHighCommand() {
    	//note that the subsystems will HOME before the moves are complete!
    	Command s = new SequentialCommandGroup(
    			new PositionTelescopeCommand ( armSubsystem, RobotConstants.ARM.POSITION_PRESETS.SCORE_HIGH_METERS,true),
    			new PositionElbowCommand ( elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES, true )
    	);
    	s.setName("deployHighCommand");
    	return s;
    }
    private Supplier<DriveInput> addYawToOperatorJoystickInput(Supplier<DriveInput> operatorJoystickInput){
    	return new DriveInputYawMixer(robotState, operatorJoystickInput);
    }

    public void setDefaultDriveCommand (Command newDefaultCommand ) {
    	driveSubsystem.setDefaultCommand(newDefaultCommand);
    }
    public void setDefaultGripperCommand ( Command newDefaultCommand) {
    	gripperSubsystem.setDefaultCommand(newDefaultCommand);
    }

    public Command gripperPanelSyncCommand( Supplier<Boolean> panelGripperButtonSupplier ) {
    	return new DefaultGripperCommand(gripperSubsystem, panelGripperButtonSupplier);
    }
    
    public Command filteredDriveCommand( Supplier<DriveInput> operatorInput, ShuffleboardDriverControls driverControls) {
    	return new FilteredDriveCommand(driveSubsystem,addYawToOperatorJoystickInput( operatorInput),driverControls);
    }

    public Command driveCommand(Supplier<DriveInput> operatorInput) {
        return new SimpleDriveCommand(driveSubsystem, addYawToOperatorJoystickInput(operatorInput));
    }

	public Command toggleFieldAbsoluteCommand( ShuffleboardDriverControls shuffleboardControls ) {
		return new ToggleFieldAbsoluteCommand(shuffleboardControls);
	}

	public Command setDriverYawEnableCommand(ShuffleboardDriverControls shuffleboardControls , boolean newValue) {
		return new SetDriverYawEnableCommand(shuffleboardControls,newValue);
	}

    public Command alignToScoringLocation(TargetNodeSupplier targetSupplier, Supplier<DriveInput> operatorInput) {
  		return new AlignToScoringLocationCommand(driveSubsystem,addYawToOperatorJoystickInput(operatorInput),robotState, robotState  );
    }

    public Command snapYawDegreesCommand(double angle) {
        return new SnapYawDegreesCommand(driveSubsystem, angle,robotState );
    }

    public Command getZeroGyro() {
        return new ZeroGyroCommand(navxSubsystem);
    }

    public Command nudgeLeftCommand() {
        return new NudgeDirectionCommand(driveSubsystem, NudgeDirectionCommand.DIRECTION.LEFT);
    }

    public Command nudgeRightCommand() {
        return new NudgeDirectionCommand(driveSubsystem, NudgeDirectionCommand.DIRECTION.RIGHT);
    }

    public Command nudgeForwardCommand() {
        return new NudgeDirectionCommand(driveSubsystem, NudgeDirectionCommand.DIRECTION.FORWARD);
    }

    public Command nudgeBackwardCommand() {
        return new NudgeDirectionCommand(driveSubsystem, NudgeDirectionCommand.DIRECTION.BACKWARD);
    }

    public Command nudgeYawLeftCommand() {
        return new NudgeYawCommand(driveSubsystem, NudgeYawCommand.DIRECTION.LEFT,robotState);
    }

    public Command nudgeYawRightCommand() {
        return new NudgeYawCommand(driveSubsystem, NudgeYawCommand.DIRECTION.RIGHT,robotState);
    }

    public Command driveDistanceCommand(double distanceMeters) {
        return new DriveDirectionCommand(driveSubsystem, distanceMeters, 0, 0);
    }

    public Command groundRetractedPosition() {
        return new PositionElbowCommand(elbowSubsystem, 12, true);
    }

    public Command nudgeElbowUpCommand() {
        return new NudgeElbowUpCommand(elbowSubsystem, false);
    }

    public Command nudgeElbowDownCommand() {
        return new NudgeElbowDownCommand(elbowSubsystem, false);
    }

    public Command nudgeArmForwardCommand() {
        return new NudgeTelescopeForwardCommand(armSubsystem, false);
    }

    public Command nudgeArmBackwardCommand() {
        return new NudgeTelescopeBackwardsCommand(armSubsystem, false);
    }

    public Command openGripperCommand() {
        return new GripperCommand(gripperSubsystem, GripperState.kOpen);
    }

    public Command closeGripperCommand() {
        return new GripperCommand(gripperSubsystem, GripperState.kClose);
    }

    public Command farScoringPositionCommand() {
        return new SequentialCommandGroup(
            new ConditionalCommand(new InstantCommand(), groundRetractedPosition(), elbowSubsystem::isSafeToExtendArm),
            new ParallelCommandGroup(
                new PositionElbowCommand(elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES, true),
                new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MAX_ARM_LENGTH_M, true)
            )
        );
    }

    public Command middleScoringPositionCommand() {
        return new SequentialCommandGroup(
            new ConditionalCommand(new InstantCommand(), groundRetractedPosition(), elbowSubsystem::isSafeToExtendArm),
            new ParallelCommandGroup(
                new PositionElbowCommand(elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.SCORE_MIDDLE_DEGREES, true),
                new PositionTelescopeCommand(armSubsystem, 1.1, true)
            )
        );
    }

    public Command groundScoringPosition() {
        return new ParallelCommandGroup(
            new ConditionalCommand(new InstantCommand(), 
            new SequentialCommandGroup( groundRetractedPosition(), new PositionElbowCommand(elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.SCORE_LOW_DEGREES, false)),
            elbowSubsystem::isSafeToExtendArm),
            new PositionTelescopeCommand(armSubsystem, 0, true)
        );
    }

    public Command loadingPositionCommand() {
        return new SequentialCommandGroup(
            new ConditionalCommand(new InstantCommand(), groundRetractedPosition(), elbowSubsystem::isSafeToExtendArm),
            new ParallelCommandGroup(
                new PositionElbowCommand(elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.LOAD_STATION_DEGREES, true),
                new PositionTelescopeCommand(armSubsystem, 1.4, true)
            )
        );
    }

    /**
     * This series moves only the elbow
     */
    public Command highScoringElbowCommand() {
    	return createNamedElbowPositionCommand( RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES, "highScoringElbowCommand");
    }

    public Command middleScoringElbowCommand() {
        return createNamedElbowPositionCommand( RobotConstants.ELBOW.POSITION_PRESETS.SCORE_MIDDLE_DEGREES, "middleScoringElbowCommand");
    }

    public Command groundScoringElbowCommand() {
        return createNamedElbowPositionCommand( RobotConstants.ELBOW.POSITION_PRESETS.SCORE_LOW_DEGREES, "groundScoringElbowCommand");
    }

    public Command loadingElbowCommand() {
        return createNamedElbowPositionCommand( RobotConstants.ELBOW.POSITION_PRESETS.LOAD_STATION_DEGREES, "loadingElbowCommand");
    }    

    public Command carryElbowCommand() {
        return createNamedElbowPositionCommand( RobotConstants.ELBOW.POSITION_PRESETS.CARRY_DEGREES, "carryElbowCommand");
    }   
    
    private Command createNamedElbowPositionCommand(double position, String name) {
    	Command p = new  PositionElbowCommand(elbowSubsystem, position, true);
    	p.setName(name);
    	return p;
    }
    
    public Command homeTelescopeAndElbow() {
    	SequentialCommandGroup sg =  new SequentialCommandGroup( 
    			armPositionHome(),
    			createNamedElbowPositionCommand(RobotConstants.ELBOW.POSITION_PRESETS.MIN_POSITION_DEGREES,"minimum pos")
    			
    	);
    	
    	sg.setName("homeTelescopeAndElbow");
    	return sg;
    }

//    public Command homeElbowCommand() {
//        return new HomeElbowCommand(elbowSubsystem);
//    }
//
//    public Command homeTelescopeCommand() {
//        return new HomeArmCommand(armSubsystem);
//    }

    public Command armPositionHome() {
        return new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_ARM_LENGTH_M, true);
     }

     public Command armPositionFullExtension() {
        return new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MAX_ARM_LENGTH_M, true);
     }
  
}
