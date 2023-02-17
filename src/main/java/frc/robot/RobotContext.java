package frc.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.logging.PoseLogger;
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
	public RobotContext( DriveSubsystem drive, NavXSubSystem navx, VisionSubsystem vision) {
	    driveSubsystem = drive;
	    navXSubSystem = navx;
	    visionSubsystem = vision;
	    Shuffleboard.getTab("Field").add(fieldDisplay).withWidget(BuiltInWidgets.kField);
	}
    private static PoseLogger poseLogger = new PoseLogger();	
	private Field2d fieldDisplay = new Field2d();
	
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
        	fieldDisplay.getObject("Estimated").setPose(estimatedRobotPose);
        	poseLogger.logPose2d("pose-us", estimatedRobotPose);
        }
        
        if ( vs.getPhotonEstimatedPose() != null ) {
        	fieldDisplay.getObject("PhotonEstiamted").setPose(estimatedRobotPose);
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
    
    private DriveSubsystem driveSubsystem;
    private NavXSubSystem navXSubSystem;
    private VisionSubsystem visionSubsystem;
	private Pose2d estimatedRobotPose;
	private PoseEstimator poseEstimator;
	private DriverPreferences driverPreferences = new DriverPreferences();
	
    public DriverPreferences getDriverPreferences() {
		return driverPreferences;
	}
	public void setDriverPreferences(DriverPreferences driverPreferences) {
		this.driverPreferences = driverPreferences;
	}
	public PoseEstimator getPoseEstimator() {
		return poseEstimator;
	}
	public void setPoseEstimator(PoseEstimator poseEstimator) {
		this.poseEstimator = poseEstimator;
	}
	
}
