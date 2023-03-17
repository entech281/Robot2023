package frc.robot;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.pose.AprilTagLocation;
import frc.robot.pose.PoseEstimator;
import frc.robot.pose.RecognizedAprilTagTarget;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveStatus;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElbowSubsystem;
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
	

	//inject just what we need. later we might need arm-- we can add it then
	public RobotContext(
			RobotState robotState, 
			DriveSubsystem drive, 
			NavXSubSystem navx, 
			VisionSubsystem vision,		
			PoseEstimator poseEstimator) {
	    driveSubsystem = drive;
	    navXSubSystem = navx;
	    visionSubsystem = vision;
	    this.robotState = robotState;
	    this.poseEstimator = poseEstimator;
	}
	
    /**
     * This is called each periodic loop before commands are executed.
     * It's where computations that need to happen every loop, and
     * involve mutiple objects and stuff go
     */
    public void periodic() {
    	
    	//pollSubsystems
    	VisionStatus vs =visionSubsystem.getStatus();
    	NavxStatus ns = navXSubSystem.getStatus();
    	DriveStatus ds = driveSubsystem.getStatus();
    	
    	robotState.setYawAngleDegrees(ns.getYawAngleDegrees());
    	
    	//our estimate for the pose
    	Optional<Pose2d> estimatedRobotPose =  poseEstimator.estimateRobotPose(vs,ns,ds);
        
    	//photonvision pose estimate
    	Optional<Pose2d> photonEstimatedPose = vs.getPhotonEstimatedPose2d();
    	
    	//target, if we have one
    	Optional<Double> targetY = getTargetY(vs);
    	Optional<Double> lateralOffsetPhoton = Optional.empty();
    	Optional<Double> lateralOffsetOurs = Optional.empty();
    	
    	
    	if ( estimatedRobotPose.isPresent() ){
    		SmartDashboard.putNumber("OurPose:Y", estimatedRobotPose.get().getY());
    		if ( targetY.isPresent()) {
    			lateralOffsetOurs = Optional.of(targetY.get()- estimatedRobotPose.get().getY());
    			SmartDashboard.putNumber("OurPose:OFFSET", lateralOffsetOurs.get());
    		}
    		else {
    			SmartDashboard.putNumber("OurPose:OFFSET", RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN);
    		}
    	}

    	if ( photonEstimatedPose.isPresent() ){
    		SmartDashboard.putNumber("PhotonPose:Y", photonEstimatedPose.get().getY());
    		if ( targetY.isPresent()) {
    			lateralOffsetPhoton = Optional.of(targetY.get()- photonEstimatedPose.get().getY());
    			SmartDashboard.putNumber("PhotonPose:OFFSET", lateralOffsetPhoton.get());
    		}
    		else {
    			SmartDashboard.putNumber("PhotonPose:OFFSET", RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN);
    		}    		
    	}
    	

    	//temporary till we get code that finds the the closest target
    	//this will just align on the tag itself
    	robotState.setLateralOffset(lateralOffsetPhoton);
    	//robotState.setLateralOffset(lateralOffsetOurs);
    	
    }    
    
    private Optional<Double> getTargetY(VisionStatus vs ){
    	if ( vs.getBestAprilTagTarget().isPresent()) {
    		RecognizedAprilTagTarget rat = vs.getBestAprilTagTarget().get();
    		return Optional.of(rat.getY());
    	}    	
    	return Optional.empty();
    }

    private RobotState robotState;
	private DriveSubsystem driveSubsystem;
    private NavXSubSystem navXSubSystem;
    private VisionSubsystem visionSubsystem;
	private PoseEstimator poseEstimator;

}
