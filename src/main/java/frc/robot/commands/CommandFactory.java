package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.RobotConstants;
import frc.robot.RobotContext;
import frc.robot.ShuffleboardDriverControls;
import frc.robot.commands.nudge.NudgeDirectionCommand;
import frc.robot.commands.nudge.NudgeYawCommand;
import frc.robot.filters.DriveInput;
import frc.robot.pose.RecognizedAprilTagTarget;
import frc.robot.pose.ScoringLocation;
import frc.robot.pose.TargetNode;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.VisionStatus;
import frc.robot.subsystems.VisionSubsystem;

/**
 *
 * @author dcowden 
 * @author aheitkamp
 */
public class CommandFactory {

	public static final double SNAP_YAW_ANGLE = 160.0;
	private RobotContext robotContext;
	private DriveSubsystem driveSubsystem;
	private VisionSubsystem visionSubsystem;
	private NavXSubSystem navxSubsystem;
	private ArmSubsystem armSubsystem;

    
    public CommandFactory(RobotContext robotContext, DriveSubsystem drive, NavXSubSystem navx, VisionSubsystem vision, ArmSubsystem arm){
    	this.driveSubsystem = drive;
    	this.navxSubsystem = navx;
    	this.visionSubsystem = vision;
    	this.armSubsystem = arm;
        this.robotContext = robotContext;
    }
    
    //private because this is just a provider for drive commands
    private Pose2d getCurrentEstimatedPose() {
    	return robotContext.getEstimatedRobotPose();
    }
    
    //private because this is just a provider for drive commands
    private double getCurrentYawDegrees() {
    	return robotContext.getEstimatedRobotPose().getRotation().getDegrees();
    }
    
    public void setDefaultDriveCommand (Command newDefaultCommand ) {
    	driveSubsystem.setDefaultCommand(newDefaultCommand);
    }

    public Command filteredDriveCommand( Supplier<DriveInput> operatorInput, ShuffleboardDriverControls driverControls) {
    	return new FilteredDriveCommand(driveSubsystem,operatorInput,this::getCurrentYawDegrees,driverControls);
    }
    
    public Command driveCommand(Supplier<DriveInput> operatorInput) {
        return new SimpleDriveCommand(driveSubsystem, operatorInput,this::getCurrentYawDegrees);
    }

	public Command toggleFieldAbsoluteCommand( ShuffleboardDriverControls shuffleboardControls ) {
		return new ToggleFieldAbsoluteCommand(shuffleboardControls);
	}

	public Command setDriverYawEnableCommand(ShuffleboardDriverControls shuffleboardControls , boolean newValue) {
		return new SetDriverYawEnableCommand(shuffleboardControls,newValue);		
	}
	
    public Command alignToScoringLocation(Supplier<ScoringLocation> target, Supplier<DriveInput> operatorInput) {
    	VisionStatus vs= visionSubsystem.getStatus();
    	RecognizedAprilTagTarget rat = vs.getBestAprilTagTarget();
    	if ( rat == null ) {
    		return new PrintCommand("Cannot Align: No active vision target");
    	}
    	else {
    		//ScoringLocation s = new ScoringLocation(rat.getTagLocation(),robotContext.getDriverPreferences().getSelectedNode());
    		//ScoringLocation s = new ScoringLocation(rat.getTagLocation(),targetNode);
    		return new AlignToScoringLocationCommand(driveSubsystem,operatorInput,target,this::getCurrentEstimatedPose );
    	}
        
    }    
    
    public Command snapYawDegreesCommand(double angle) {
        return new SnapYawDegreesCommand(driveSubsystem, angle,this::getCurrentYawDegrees );
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
        return new NudgeYawCommand(driveSubsystem, NudgeYawCommand.DIRECTION.LEFT,this::getCurrentYawDegrees);
    }

    public Command nudgeYawRightCommand() {
        return new NudgeYawCommand(driveSubsystem, NudgeYawCommand.DIRECTION.RIGHT,this::getCurrentYawDegrees);
    }
}
