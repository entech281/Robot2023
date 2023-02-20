package frc.robot;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.oi.ShuffleboardDriverControls;
import frc.robot.oi.ShuffleboardFieldDisplay;
import frc.robot.pose.AlignmentCalculator;
import frc.robot.pose.PoseEstimator;
import frc.robot.pose.ScoringLocation;
import frc.robot.subsystems.DriveStatus;
import frc.robot.subsystems.DriveSubsystem;
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
	
	//inject just what we need. later we might need arm-- we can add it then
	public RobotContext( AlignmentCalculator alignmentCalculator, 
			RobotState robotState, 
			ShuffleboardFieldDisplay fieldDisplay, 
			DriveSubsystem drive, NavXSubSystem navx, 
			VisionSubsystem vision, 
			PoseEstimator poseEstimator,
			ShuffleboardDriverControls driverControls) {
	    driveSubsystem = drive;
	    navXSubSystem = navx;
	    visionSubsystem = vision;
	    this.fieldDisplay=fieldDisplay;
	    this.robotState = robotState;
	    this.poseEstimator = poseEstimator;
	    this.driverControls = driverControls;
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
        
        if ( robotState.getScoringLocation().isPresent()) {
        	ScoringLocation scoreloc = robotState.getScoringLocation().get();
            fieldDisplay.displayScoringSolution(estimatedRobotPose.get(),scoreloc.computeAbsolutePose()); 
        }
        
    }    

    private RobotState robotState;
	private DriveSubsystem driveSubsystem;
    private NavXSubSystem navXSubSystem;
    private VisionSubsystem visionSubsystem;
	private PoseEstimator poseEstimator;
	private ShuffleboardFieldDisplay fieldDisplay;
	private ShuffleboardDriverControls driverControls;
	
	
}
