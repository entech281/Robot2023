package frc.robot;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.adapter.DriveInputYawMixer;
import frc.robot.commands.AlignToScoringLocationCommand;
import frc.robot.commands.FilteredDriveCommand;
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
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.VisionSubsystem;

/**
 *
 * @author dcowden 
 * @author aheitkamp
 */
public class CommandFactory {

	public static final double SNAP_YAW_ANGLE = 160.0;
	private RobotState robotState;
	private DriveSubsystem driveSubsystem;
	private VisionSubsystem visionSubsystem;
	private NavXSubSystem navxSubsystem;
	private ArmSubsystem armSubsystem;
    
    public CommandFactory(RobotState robotState, DriveSubsystem drive, NavXSubSystem navx, VisionSubsystem vision, ArmSubsystem arm){
    	this.driveSubsystem = drive;
    	this.navxSubsystem = navx;
    	this.visionSubsystem = vision;
    	this.armSubsystem = arm;
        this.robotState = robotState;

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

    public Command getAutonomousCommand() {
        return snapYawDegreesCommand(SNAP_YAW_ANGLE);
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
