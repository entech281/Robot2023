package frc.robot;

import java.util.Optional;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.util.Color;

import frc.robot.pose.LateralAlignCalculator;
import frc.robot.pose.LateralOffset;
import frc.robot.pose.MovingAveragePose;
import frc.robot.pose.PoseEstimator;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.VisionStatus;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.utils.Counter;


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
    public static final double ALIGN_TOLERANCE_INCHES = 2.0;
    public static final int MISSING_ESTIMATES_TO_TRIGGER_NO_TAG = 5;
    private Counter missingPoseEstimateCounter = new Counter ( MISSING_ESTIMATES_TO_TRIGGER_NO_TAG);
	private LateralAlignCalculator lateralAlignCalculator = new LateralAlignCalculator();
	private MovingAveragePose movingAveragePose = new MovingAveragePose(NUM_SAMPLES);
	private LinearFilter movingAverageY = LinearFilter.movingAverage(NUM_SAMPLES);
	
	//inject just what we need. later we might need arm-- we can add it then
	public RobotContext(
			RobotState robotState, 
			Drivetrain drive, 
			NavXSubSystem navx, 
			VisionSubsystem vision,	
			ElbowSubsystem elbow,
			LEDSubsystem ledSubsystem,
			PoseEstimator poseEstimator) {
		this.elbow = elbow;
		this.ledSubsystem = ledSubsystem;
	    Drivetrain = drive;
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
    	
    	robotState.yawAngleDegrees = navXSubSystem.getYaw();
    	robotState.cameraY = vs.getCameraY();
    	robotState.movingAverageY = movingAverageY.calculate(vs.getCameraY());
        
    	//photonvision pose estimate
    	Optional<Pose2d> photonEstimatedPose = vs.getPhotonEstimatedPose2d();

    	double maxSpeed = 1.0;
    	if (photonEstimatedPose.isPresent() ){
    		Pose2d pep = photonEstimatedPose.get();
    		movingAveragePose.update(pep);
    		robotState.estimatedPose = Optional.of(movingAveragePose.getMovingAverage());
    		LateralOffset lateralOffset = lateralAlignCalculator.findOffsetToNearestTarget(movingAveragePose.getX(), movingAveragePose.getY());
    		robotState.closestScoringLocationOffset = Optional.of(lateralOffset);

    		setAlignState ( getAlignColor(lateralOffset)); 		
    		robotState.realLateralOffset = lateralOffset.getLateralOffsetToLocationMeters();
    		
    		if (elbow.getActualPosition() > RobotConstants.ELBOW.POSITION_PRESETS.SAFE_ANGLE) {
    			maxSpeed = RobotConstants.DRIVE.SPEED_LIMIT_WITH_ARM_OUT;
    		};
    		missingPoseEstimateCounter.reset();
    	}
    	else {
    		missingPoseEstimateCounter.up();
    		if ( missingPoseEstimateCounter.atOrAboveTarget()) {
    			setAlignState(Color.kRed);
    		}
    	}
    }   

    private void setAlignState(Color c) {
		ledSubsystem.setColor(c);
		robotState.alignState = c;      	
    }
    
    private Color getAlignColor(LateralOffset offset) {
    	double absOffset = Math.abs(offset.getLateralOffsetToLocationMeters());

    	if ( absOffset < Units.inchesToMeters(ALIGN_TOLERANCE_INCHES)) {
    		return Color.kGreen;
    	}
    	else {
    		return Color.kBlue;
    	}
    }

    private RobotState robotState;
	private Drivetrain Drivetrain;
    private NavXSubSystem navXSubSystem;
    private VisionSubsystem visionSubsystem;
    private LEDSubsystem ledSubsystem;
	private PoseEstimator poseEstimator;
	private ElbowSubsystem elbow;


}
