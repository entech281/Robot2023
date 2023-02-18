package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.pose.AlignmentCalculator;
import frc.robot.pose.PoseEstimator;
import frc.robot.pose.TargetNode;
import frc.robot.subsystems.DriveStatus;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.NavxStatus;
import frc.robot.subsystems.VisionStatus;
import frc.robot.subsystems.VisionSubsystem;

public class RobotContext {
	
	//inject just what we need. later we might need arm-- we can add it then
	public RobotContext( ShuffleboardFieldDisplay fieldDisplay, DriveSubsystem drive, NavXSubSystem navx, VisionSubsystem vision) {
	    driveSubsystem = drive;
	    navXSubSystem = navx;
	    visionSubsystem = vision;
	    this.fieldDisplay=fieldDisplay;
	}
	
	public Pose2d getEstimatedRobotPose() {
		return estimatedRobotPose;
	}

	protected boolean hasEstimatedPose() {
		return this.estimatedRobotPose != null;
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
        this.estimatedRobotPose =  poseEstimator.estimateRobotPose(vs,ns,ds);

        
        if ( hasEstimatedPose() ) {
        	SmartDashboard.putString("our pose",estimatedRobotPose.toString());
        	//fieldDisplay.setRobotPose(estimatedRobotPose);
        }
        
        if ( vs.getPhotonEstimatedPose() != null ) {
        	SmartDashboard.putString("photon pose",estimatedRobotPose.toString());
        	fieldDisplay.setRobotPose(vs.getPhotonEstimatedPose().toPose2d());	
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

    
    private DriveSubsystem driveSubsystem;
    private NavXSubSystem navXSubSystem;
    private VisionSubsystem visionSubsystem;
	private Pose2d estimatedRobotPose;
	private PoseEstimator poseEstimator;
	private ShuffleboardFieldDisplay fieldDisplay;
	
	public PoseEstimator getPoseEstimator() {
		return poseEstimator;
	}
	public void setPoseEstimator(PoseEstimator poseEstimator) {
		this.poseEstimator = poseEstimator;
	}
	
}
