package frc.robot;

import java.util.Optional;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotConstants.ARM;
import frc.robot.RobotConstants.ELBOW;
import frc.robot.commands.ArmEmergencyStopCommand;
import frc.robot.oi.ShuffleboardDriverControls;
import frc.robot.oi.ShuffleboardInterface;
import frc.robot.pose.AlignmentCalculator;
import frc.robot.pose.PoseEstimator;
import frc.robot.pose.ScoringLocation;
import frc.robot.subsystems.ArmStatus;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveStatus;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElbowStatus;
import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.subsystems.GripperStatus;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.NavxStatus;
import frc.robot.subsystems.VisionStatus;
import frc.robot.subsystems.VisionSubsystem;


/**
 * This is where we collect everything we need to compute 
 * scoring solution and strategy.
 * 
 * At the end of periodic, RobotState should be updated 
 * with all our calculations
 * @author davec
 *
 */
public class RobotContext {
	
	public static final double ANGLE_NOT_FOUND = 999;
	//inject just what we need. later we might need arm-- we can add it then
	public RobotContext( AlignmentCalculator alignmentCalculator, 
			RobotState robotState, 
			ShuffleboardInterface fieldDisplay, 
			DriveSubsystem drive, NavXSubSystem navx, 
			VisionSubsystem vision, 
		    ArmSubsystem armSubsystem,
		    ElbowSubsystem elbowSubsystem,
		    GripperSubsystem gripperSubsystem,			
			PoseEstimator poseEstimator,
			ShuffleboardDriverControls driverControls) {
	    driveSubsystem = drive;
	    navXSubSystem = navx;
	    visionSubsystem = vision;
	    this.fieldDisplay=fieldDisplay;
	    this.robotState = robotState;
	    this.poseEstimator = poseEstimator;
	    this.driverControls = driverControls;
	    this.elbowSubsystem = elbowSubsystem;
	    this.armSubsystem = armSubsystem;
	    this.gripperSubsystem = gripperSubsystem;
	    this.alignmentCalculator = alignmentCalculator;
	}

	
	
    /**
     * This is called each periodic loop before commands are executed.
     * It's where computations that need to happen every loop, and
     * involve mutiple objects and stuff go
     */
    public void periodic() {
    	
    	//pollSubsystems
    	DriveStatus ds = driveSubsystem.getStatus();
    	VisionStatus vs =visionSubsystem.getStatus();
    	NavxStatus ns = navXSubSystem.getStatus();
    	ArmStatus as = armSubsystem.getStatus();
    	ElbowStatus es = elbowSubsystem.getStatus();
    	GripperStatus gs = gripperSubsystem.getStatus();
    	
    	//estimate pose
    	Optional<Pose2d> estimatedRobotPose =  poseEstimator.estimateRobotPose(vs,ns,ds);
        robotState.setEstimatedPose(estimatedRobotPose);
        if ( estimatedRobotPose.isPresent() ) {
        	SmartDashboard.putString("our pose",estimatedRobotPose.get().toString());
        	fieldDisplay.setRobotPose(estimatedRobotPose.get());
        }

        
        //get selected target and scoring location
        robotState.setBestAprilTagTarget(vs.getBestAprilTagTarget());
        robotState.setTargetNode(driverControls.getSelectedTarget());
        
        if ( vs.getBestAprilTagTarget().isPresent()) {
        	PhotonTrackedTarget pt = vs.getBestAprilTagTarget().get().getPhotonTarget();
        	if ( pt != null) {
        		robotState.setPhotonYawAngle(Optional.of(pt.getYaw()));
        	}        	
        }
        
        if ( robotState.getScoringLocation().isPresent()) {
        	ScoringLocation scoreloc = robotState.getScoringLocation().get();
        	Pose2d absoluteScoringPose = scoreloc.computeAbsolutePose();
        	
        	Pose2d realRobotPose = estimatedRobotPose.get();
            fieldDisplay.displayScoringSolution(realRobotPose,absoluteScoringPose); 
            double targetYaw = alignmentCalculator.calculateAngleToScoringLocation(absoluteScoringPose, realRobotPose);
            robotState.setTargetYawAngle(targetYaw);
        }
        //installArmSafetyWatchdog();

    }    

    private void installArmSafetyWatchdog() {
        if ( armSubsystem.getActualPosition() > ARM.POSITION_PRESETS.SAFE && elbowSubsystem.getActualPosition() < ELBOW.POSITION_PRESETS.SAFE_ANGLE) {
        	CommandScheduler.getInstance().schedule(new ArmEmergencyStopCommand(armSubsystem,elbowSubsystem));
        }    	
    }
    
    private RobotState robotState;
	private DriveSubsystem driveSubsystem;
    private NavXSubSystem navXSubSystem;
    private VisionSubsystem visionSubsystem;
    private ArmSubsystem armSubsystem;
    private ElbowSubsystem elbowSubsystem;
    private GripperSubsystem gripperSubsystem;
	private PoseEstimator poseEstimator;
	private AlignmentCalculator alignmentCalculator;
	private ShuffleboardInterface fieldDisplay;
	private ShuffleboardDriverControls driverControls;
	
	
}
