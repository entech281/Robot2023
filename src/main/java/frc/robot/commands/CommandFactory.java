package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.RobotConstants;
import frc.robot.commands.nudge.NudgeDirectionCommand;
import frc.robot.commands.nudge.NudgeYawCommand;
import frc.robot.logging.PoseLogger;
import frc.robot.pose.AlignmentCalculator;
import frc.robot.pose.PoseEstimator;
import frc.robot.pose.RecognizedAprilTagTarget;
import frc.robot.pose.ScoringLocation;
import frc.robot.pose.TargetNode;
import frc.robot.subsystems.DriveStatus;
import frc.robot.subsystems.NavxStatus;
import frc.robot.subsystems.SubsystemManager;
import frc.robot.subsystems.VisionStatus;

/**
 *
 * @author dcowden 
 * @author aheitkamp
 */
public class CommandFactory {

    private final SubsystemManager sm;
    private PoseEstimator poseEstimator;
    private Pose2d estimatedRobotPose;
    private static PoseLogger poseLogger = new PoseLogger();
    
    public CommandFactory(SubsystemManager subsystemManager,  PoseEstimator poseEstimator){
        this.sm = subsystemManager;
        this.poseEstimator = poseEstimator;
    }

    public void periodic() {
    	DriveStatus ds = sm.getDriveSubsystem().getStatus();
    	VisionStatus vs = sm.getVisionSubsystem().getStatus();
    	NavxStatus ns = sm.getNavXSubSystem().getStatus();
    	
    	//this is where we estimate robot pose from various sources.
    	//set which estimator we use in Robot.robotInit()
        estimatedRobotPose =  poseEstimator.estimateRobotPose(vs,ns,ds);
        
        poseLogger.logPose2d("pose-us", estimatedRobotPose);   
        if (vs.getPhotonEstimatedPose() != null) {
            poseLogger.logPose2d("pose-photon", vs.getPhotonEstimatedPose().toPose2d());
        }     

        
        /**
         * TEMPORARY Code, just so that we can see 
         * How things look before doing actual control
         * once we like this, it belongs inside of whatever command we run
         * 
         * Also we are TEMPORARILY printing out what we get from MagicPhotonVisionEstimator
         */
         
         AlignmentCalculator calc = new AlignmentCalculator();
         double alignAngle = calc.calculateAngleToScoringLocation(vs, TargetNode.A3, estimatedRobotPose);
         SmartDashboard.putNumber("AlignAngle", alignAngle);
         
    }
    
    
    //private because this is just a provider for drive commands
    private Pose2d getCurrentEstimatedPose() {
    	return estimatedRobotPose;
    }
    
    //private because this is just a provider for drive commands
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
    public Command filteredDriveComamnd(Joystick joystick) {
    	return new FilteredDriveCommand(sm.getDriveSubsystem(),joystick,this::getCurrentYawDegrees);
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
        return new SnapYawDegreesCommand(sm.getDriveSubsystem(), angle,this::getCurrentYawDegrees );
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
