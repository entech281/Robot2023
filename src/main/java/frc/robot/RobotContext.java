package frc.robot;

import java.util.Optional;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.pose.LateralAlignCalculator;
import frc.robot.pose.LateralOffset;
import frc.robot.pose.MovingAveragePose;
import frc.robot.pose.PoseEstimator;
import frc.robot.pose.RecognizedAprilTagTarget;
import frc.robot.pose.ScoringLocation;
import frc.robot.subsystems.DriveStatus;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.subsystems.LEDSubsystem;
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
	
	public static final int NUM_SAMPLES = 3;
    public static final double ALIGN_TOLERANCE_METERS = 0.08;
    public static final double ALIGN_CLOSE_METERS = 0.3;
    public static final double ALIGN_KINDA_CLOSE_METERS = 0.8;
    
	
	private LateralAlignCalculator lateralAlignCalculator = new LateralAlignCalculator();
	private MovingAveragePose movingAveragePose = new MovingAveragePose(NUM_SAMPLES);
	private LinearFilter movingAverageY = LinearFilter.movingAverage(NUM_SAMPLES);
	
	//inject just what we need. later we might need arm-- we can add it then
	public RobotContext(
			RobotState robotState, 
			DriveSubsystem drive, 
			NavXSubSystem navx, 
			VisionSubsystem vision,	
			ElbowSubsystem elbow,
			LEDSubsystem ledSubsystem,
			PoseEstimator poseEstimator) {
		this.elbow = elbow;
		this.ledSubsystem = ledSubsystem;
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
    	
    	robotState.yawAngleDegrees = navXSubSystem.getYaw();
    	robotState.cameraY = vs.getCameraY();
    	robotState.movingAverageY = movingAverageY.calculate(vs.getCameraY());
        
    	//photonvision pose estimate
    	Optional<Pose2d> photonEstimatedPose = vs.getPhotonEstimatedPose2d();

    	
    	if (photonEstimatedPose.isPresent() ){
    		Pose2d pep = photonEstimatedPose.get();
    		movingAveragePose.update(pep);
    		LateralOffset lateralOffset = lateralAlignCalculator.findOffsetToNearestTarget(movingAveragePose.getX(), movingAveragePose.getY());
    		robotState.closestScoringLocationOffset = Optional.of(lateralOffset);

    		setAlignState ( getAlignColor(lateralOffset)); 		
    		robotState.realLateralOffset = lateralOffset.getLateralOffsetToLocationMeters();
    		
    		capSpeedIfTooCloseToTag(pep,lateralOffset);

    	}
    	else {
    		setAlignState(Color.kRed);
     				
    	}
    }   

    private void setAlignState(Color c) {
		ledSubsystem.setColor(c);
		robotState.alignState = c;      	
    }
    private void capSpeedIfTooCloseToTag(Pose2d currentRobotPose, LateralOffset lateralOffset) {
    	driveSubsystem.clearSpeedLimit(); //clear speed as default
		Pose2d tagPose = lateralOffset.getNearestLocation().computeAbsolutePose();
		double distanceFromTagMeters = Math.abs(tagPose.getX() - currentRobotPose.getX());
		double MAX_SPEED_WHEN_TAG_CLOSE = RobotConstants.DRIVE.SPEED_LIMIT_WITH_ARM_OUT;
		double armProjectionMeters = RobotConstants.ARM.MAX_EXTENSION_METERS * Math.sin(Units.degreesToRadians(elbow.getActualPosition()));
		
		if ( (distanceFromTagMeters < (RobotConstants.ALIGNMENT.TAG_DISTANCE_TO_REDUCE_SPEED) + armProjectionMeters) ) {
				driveSubsystem.setMaxSpeedPercent(RobotConstants.DRIVE.SPEED_LIMIT_WITH_ARM_OUT);
				DriverStation.reportWarning(
						String.format("Forward Speed Reduced to %.2f : tag within %.2f meters.",MAX_SPEED_WHEN_TAG_CLOSE,distanceFromTagMeters),
				false);
		}    	
    }
    
    private Color getAlignColor(LateralOffset offset) {
    	double absOffset = Math.abs(offset.getLateralOffsetToLocationMeters());
    	ScoringLocation scoringLocation = offset.getNearestLocation();
    	if ( absOffset < scoringLocation.getAlignmentToleranceMeters()) {
    		return Color.kGreen;
    	}
    	else if ( absOffset < scoringLocation.getAlignmentToleranceMeters() + RobotConstants.ALIGNMENT.ALIGN_CLOSE_WINDOW_METERS) {
    		return Color.kYellow;
    	}
    	else {
    		return Color.kOrange;
    	}
    }


    private RobotState robotState;
	private DriveSubsystem driveSubsystem;
    private NavXSubSystem navXSubSystem;
    private VisionSubsystem visionSubsystem;
    private LEDSubsystem ledSubsystem;
	private PoseEstimator poseEstimator;
	private ElbowSubsystem elbow;


}
