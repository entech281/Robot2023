package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.RobotConstants;
import frc.robot.commands.nudge.NudgeDirectionCommand;
import frc.robot.commands.nudge.NudgeYawCommand;
import frc.robot.pose.DriveStatus;
import frc.robot.pose.NavxStatus;
import frc.robot.pose.PoseEstimator;
import frc.robot.pose.RecognizedAprilTagTarget;
import frc.robot.pose.ScoringLocation;
import frc.robot.pose.TargetNode;
import frc.robot.pose.VisionStatus;
import frc.robot.subsystems.SubsystemManager;

/**
 *
 * @author dcowden 
 * @author aheitkamp
 */
public class CommandFactory {

    private final SubsystemManager sm;
    private PoseEstimator poseEstimator;
    private Pose2d estimatedRobotPose;

    
    public CommandFactory(SubsystemManager subsystemManager,  PoseEstimator poseEstimator){
        this.sm = subsystemManager;
        this.poseEstimator = poseEstimator;
    }

    public void periodic() {
    	DriveStatus ds = sm.getDriveSubsystem().getStatus();
    	VisionStatus vs = sm.getVisionSubsystem().getStatus();
    	NavxStatus ns = sm.getNavXSubSystem().getStatus();
        estimatedRobotPose =  poseEstimator.estimateRobotPose(vs,ns,ds);        
    }
    private Pose2d getCurrentEstimatedPose() {
    	return estimatedRobotPose;
    }
 
    private double getCurrentYawDegrees() {
    	return estimatedRobotPose.getRotation().getDegrees();
    }
    
    public void setDefaultDriveCommand (Command newDefaultCommand ) {
    	sm.getDriveSubsystem().setDefaultCommand(newDefaultCommand);
    }
    
    public Command buttonFilterCommand(int buttonNumber, boolean enabled) {
        return new ButtonFilterCommand(sm.getDriveSubsystem(), buttonNumber, enabled);
    }

    public Command turnToggleFilter(boolean enabled) {
        return buttonFilterCommand(RobotConstants.DRIVER_STICK.TURN_TOGGLE, enabled);
    }

    public Command driveCommand(Joystick joystick) {
        return new DriveCommand(sm.getDriveSubsystem(), joystick,this::getCurrentYawDegrees);
    }

    public Command toggleFieldAbsolute() {
        return new ToggleFieldAbsoluteCommand(sm.getDriveSubsystem());
    }

    public Command alignToScoringLocation(TargetNode targetNode, Joystick joystick) {
    	VisionStatus vs= sm.getVisionSubsystem().getStatus();
    	RecognizedAprilTagTarget rat = vs.getBestAprilTagTarget();
    	if ( rat == null ) {
    		return new PrintCommand("Cannot Align: No active vision target");
    	}
    	else {
    		ScoringLocation s = new ScoringLocation(rat.getTagLocation(),targetNode);
    		return new AlignToScoringLocationCommand(sm.getDriveSubsystem(),joystick,s,this::getCurrentEstimatedPose );
    	}
        
    }    
    
    public Command snapYawDegreesCommand(double angle) {
        return new SnapYawDegreesCommand(sm.getDriveSubsystem(),angle,this::getCurrentYawDegrees );
    }

    public Command getAutonomousCommand() {
        return snapYawDegreesCommand(160);
    }

    public Command getZeroGyro() {
        return new ZeroGyroCommand(sm.getNavXSubSystem());
    }

    public Command nudgeLeftCommand() {
        return new NudgeDirectionCommand(sm.getDriveSubsystem(), NudgeDirectionCommand.DIRECTION.LEFT);
    }

    public Command nudgeRightCommand() {
        return new NudgeDirectionCommand(sm.getDriveSubsystem(), NudgeDirectionCommand.DIRECTION.RIGHT);
    }

    public Command nudgeForwardCommand() {
        return new NudgeDirectionCommand(sm.getDriveSubsystem(), NudgeDirectionCommand.DIRECTION.FORWARD);
    }

    public Command nudgeBackwardCommand() {
        return new NudgeDirectionCommand(sm.getDriveSubsystem(), NudgeDirectionCommand.DIRECTION.BACKWARD);
    }

    public Command nudgeYawLeftCommand() {
        return new NudgeYawCommand(sm.getDriveSubsystem(), NudgeYawCommand.DIRECTION.LEFT,this::getCurrentYawDegrees);
    }

    public Command nudgeYawRightCommand() {
        return new NudgeYawCommand(sm.getDriveSubsystem(), NudgeYawCommand.DIRECTION.RIGHT,this::getCurrentYawDegrees);
    }
}
