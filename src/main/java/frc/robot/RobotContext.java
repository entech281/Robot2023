package frc.robot;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.pose.AlignmentCalculator;
import frc.robot.pose.PoseEstimator;
import frc.robot.subsystems.DriveStatus;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.NavxStatus;
import frc.robot.subsystems.VisionStatus;
import frc.robot.subsystems.VisionSubsystem;

public class RobotContext {
	
	//inject just what we need. later we might need arm-- we can add it then
	public RobotContext( AlignmentCalculator alignmentCalculator, RobotState robotState, 
			ShuffleboardFieldDisplay fieldDisplay, 
			DriveSubsystem drive, NavXSubSystem navx, 
			VisionSubsystem vision, PoseEstimator poseEstimator) {
	    driveSubsystem = drive;
	    navXSubSystem = navx;
	    visionSubsystem = vision;
	    this.fieldDisplay=fieldDisplay;
	    this.robotState = robotState;
	    this.poseEstimator = poseEstimator;
	}

    /**
     * This is called each periodic loop before commands are executed.
     * It's where computations that need to happen every loop, and
     * involve mutiple objects and stuff go
     */
    public void periodic() {
    	DriveStatus ds = driveSubsystem.getStatus();
    	VisionStatus vs =visionSubsystem.getStatus();
    	NavxStatus ns = navXSubSystem.getStatus();
    	
    	//this is where we estimate robot pose from various sources.
    	//set which estimator we use in Robot.robotInit()
        Optional<Pose2d> estimatedRobotPose =  poseEstimator.estimateRobotPose(vs,ns,ds);
        robotState.setEstimatedPose(estimatedRobotPose);
        
        if ( estimatedRobotPose.isPresent() ) {
        	SmartDashboard.putString("our pose",estimatedRobotPose.get().toString());
        	//fieldDisplay.setRobotPose(estimatedRobotPose);
        }
        
        if ( vs.getPhotonEstimatedPose().isPresent() ) {
        	SmartDashboard.putString("photon pose",estimatedRobotPose.toString());
        	fieldDisplay.setRobotPose(vs.getPhotonEstimatedPose().get().toPose2d());	
        }         
 

    }    

    private RobotState robotState;
	private DriveSubsystem driveSubsystem;
    private NavXSubSystem navXSubSystem;
    private VisionSubsystem visionSubsystem;
	private PoseEstimator poseEstimator;
	private ShuffleboardFieldDisplay fieldDisplay;
	
	
}
