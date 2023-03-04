package frc.robot;
import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.commands.supplier.TargetYawSupplier;
import frc.robot.commands.supplier.EstimatedPoseSupplier;
import frc.robot.commands.supplier.ScoringLocationSupplier;
import frc.robot.commands.supplier.TargetNodeSupplier;
import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.pose.AprilTagLocation.AprilTagIDLocation;
import frc.robot.pose.RecognizedAprilTagTarget;
import frc.robot.pose.ScoringLocation;
import frc.robot.pose.TargetNode;
import frc.robot.util.SendableUtil;
public class RobotState implements Sendable, EstimatedPoseSupplier , YawAngleSupplier,ScoringLocationSupplier , TargetNodeSupplier, TargetYawSupplier{

	public static final double DISTANCE_UNKNOWN = -1;
	public static final double CLOSE_ENOUGH_TO_DEPLOY_METERS = 1.2192 + 0.4064; //arm is 1.2192 meters in front of robot
	private Optional<RecognizedAprilTagTarget> bestAprilTagTarget = Optional.empty();
	private Optional<Double> targetYawAngle= Optional.empty();
	private Optional<Double> photonYawAngle= Optional.empty();
	private Optional<TargetNode> targetNode = Optional.empty();	
	private Optional<Pose2d> estimatedPose = Optional.empty();	
	
	public Optional<Double> getPhotonYawAngle() {
		return photonYawAngle;
	}

	public void setPhotonYawAngle(Optional<Double> photonYawAngle) {
		this.photonYawAngle = photonYawAngle;
	}	
	
	
	public Optional<RecognizedAprilTagTarget> getBestAprilTagTarget() {
		return bestAprilTagTarget;
	}

	public void setBestAprilTagTarget(Optional<RecognizedAprilTagTarget> bestAprilTagTarget) {
		this.bestAprilTagTarget = bestAprilTagTarget;
	}


	public void setTargetYawAngle(double alignAngle) {
		this.targetYawAngle = Optional.of(alignAngle);
	}
	
	public void setEstimatedPose(Optional<Pose2d> estimatedPose) {
		this.estimatedPose = estimatedPose;
	}
	
	public void setTargetNode(Optional<TargetNode> targetNode) {
		this.targetNode = targetNode;
	}

	@Override
	public Optional<Pose2d> getEstimatedPose() {
		return estimatedPose;
	}
	
	@Override
	public double getYawAngleDegrees() {
		if ( estimatedPose.isPresent()) {
			return estimatedPose.get().getRotation().getDegrees();
		}
		else {
			return RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN;
		}
	}

	public boolean canDeploy() {
		return getTargetDistance() < CLOSE_ENOUGH_TO_DEPLOY_METERS;
	}
	
	@Override
	public void initSendable(SendableBuilder sb) {
	    sb.addDoubleProperty("PoseYaw", this::getYawAngleDegrees, null);
	    sb.addStringProperty("Target", this::getTargetDesc, null);
	    sb.addDoubleProperty("Distance Meters", this::getTargetDistance, null);
	    sb.addBooleanProperty("CanDeploy", this::canDeploy, null);
		sb.addStringProperty("Estimated Pose", () -> { return estimatedPose + ""; }, null);
		sb.addDoubleProperty("TargetYaw", () -> { return SendableUtil.doubleForOptional(getTargetYawAngle()); },null );
		sb.addDoubleProperty("PhotonYaw", () -> { return SendableUtil.doubleForOptional(getPhotonYawAngle()) ;},null );
	}

	public double getTargetDistance() {
		Optional<ScoringLocation> target = getScoringLocation();		
		if ( target.isPresent() && estimatedPose.isPresent() ) {
			AprilTagIDLocation at = target.get().getSelectedTag().getLocation();
			Translation2d start = estimatedPose.get().getTranslation();
			Translation2d end = target.get().computeAbsolutePose().getTranslation();
			return end.getDistance(start);
		}
		else {
			return DISTANCE_UNKNOWN;
		}		
	}
	public String getTargetDesc() {
		Optional<ScoringLocation> target = getScoringLocation();
		if ( target.isPresent()) {
			AprilTagIDLocation at = target.get().getSelectedTag().getLocation();
			TargetNode tn = target.get().getSelectedNode();
			return at.toString() + ":" + tn.getNodeID();
		}
		else {
			return "NONE";
		}
	}
	@Override
	public Optional<ScoringLocation> getScoringLocation() {
		Optional<RecognizedAprilTagTarget> bestTarget = getBestAprilTagTarget();
		Optional<TargetNode> targetNode= getSelectedTarget();
		if ( bestTarget.isPresent() && targetNode.isPresent() ) {
			return Optional.ofNullable(new ScoringLocation(bestTarget.get().getTagLocation(),targetNode.get()));
		}
		else {
			return Optional.empty();
		}		 
	}

	@Override
	public Optional<TargetNode> getSelectedTarget() {
		return targetNode;
	}


	@Override
	public Optional<Double> getTargetYawAngle() {		
		return targetYawAngle;
	}
}
