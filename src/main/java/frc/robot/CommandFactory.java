package frc.robot;

import java.util.List;
import java.util.function.Supplier;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.LayoutType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotConstants.ARM;
import frc.robot.adapter.DriveInputYawMixer;
import frc.robot.commands.AlignToAprilTagOffset;
import frc.robot.commands.AlignToGamePieceCommand;
import frc.robot.commands.AlignToScoringLocationCommand;
import frc.robot.commands.ArmForgetHomeCommand;
import frc.robot.commands.DriveDirectionCommand;
import frc.robot.commands.FilteredDriveCommand;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.PositionArmCommand;
import frc.robot.commands.PositionElbowCommand;
import frc.robot.commands.SetDriverYawEnableCommand;
import frc.robot.commands.SimpleDriveCommand;
import frc.robot.commands.SnapYawDegreesCommand;
import frc.robot.commands.ToggleFieldAbsoluteCommand;
import frc.robot.commands.ZeroGyroCommand;
import frc.robot.commands.nudge.NudgeDirectionCommand;
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
    	Command c1 = new NudgeDirectionCommand(driveSubsystem,NudgeDirectionCommand.DIRECTION.FORWARD);
    	c1.setName("Nudge Forward");
    	Command c2 = new NudgeDirectionCommand(driveSubsystem,NudgeDirectionCommand.DIRECTION.RIGHT);
    	c2.setName("Nudge Right");
        Command c3 = new SequentialCommandGroup(
        new DriveDirectionCommand(driveSubsystem, 2,0.0, 0.5)
        , new DriveDirectionCommand(driveSubsystem, 0.0, 2, 0.5)
        , new DriveDirectionCommand(driveSubsystem, -2,0.0, 0.5)
        , new DriveDirectionCommand(driveSubsystem, 0.0, -2, 0.5)
        );
        c3.setName("Autonomous");
    	return List.of( c1, c2, c3);

    }
    public List<Command> getTestCommands(){
    	//these will be available to run ad-hoc on the TESTING tab
    	return List.of (
    			alignToGamePieceCommand(),
    			alignToAprilTag(AlignToAprilTagOffset.AprilTagOffset.CENTER),
    			alignToAprilTag(AlignToAprilTagOffset.AprilTagOffset.LEFT),
    			alignToAprilTag(AlignToAprilTagOffset.AprilTagOffset.RIGHT)
    	);
    			
    	
    }
    public Command alignToGamePieceCommand() {
    	AlignToGamePieceCommand c =  new AlignToGamePieceCommand(driveSubsystem,visionSubsystem);
    	ShuffleboardTab t = Shuffleboard.getTab(RobotConstants.SHUFFLEBOARD.TABS.DEBUG);    	
    	c.populateControls(t);    	
    	return c;
    }
    public Command alignToAprilTag(AlignToAprilTagOffset.AprilTagOffset offset) {
    	return new AlignToAprilTagOffset(driveSubsystem, visionSubsystem,offset);
    }
    
    public Command moveArmCommand(double position) {
    	Command p = new PositionArmCommand ( armSubsystem,position,true);
    	return p;
    }
    public Command forgetArmHome() {
    	return new ArmForgetHomeCommand( armSubsystem);
    }
    public Command deployHighCommand() {
    	//note that the subsystems will HOME before the moves are complete!
    	return new SequentialCommandGroup(
    			new PositionArmCommand ( armSubsystem, RobotConstants.ARM.POSITION_PRESETS.SCORE_HIGH_METERS,true),
    			new PositionElbowCommand ( elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES, true ),
    			new GripperCommand( gripperSubsystem, GripperState.kOpen) 
    	);
    }
    //this is probably also the home position
    public Command carryPosition() {
    	return new SequentialCommandGroup(
    			new PositionArmCommand ( armSubsystem, RobotConstants.ARM.POSITION_PRESETS.CARRY_METERS,true),
    			new PositionElbowCommand ( elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.CARRY_DEGREES, true ),
    			new GripperCommand( gripperSubsystem, GripperState.kClose) 
    	);    	
    }
    
    private Supplier<DriveInput> addYawToOperatorJoystickInput(Supplier<DriveInput> operatorJoystickInput){
    	return new DriveInputYawMixer(robotState, operatorJoystickInput);
    }
    
    public void setDefaultDriveCommand (Command newDefaultCommand ) {
    	driveSubsystem.setDefaultCommand(newDefaultCommand);
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
}
