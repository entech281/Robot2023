package frc.robot;
import java.util.Optional;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.commands.supplier.LateralOffsetSupplier;
import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.pose.AprilTagLocation;
import frc.robot.pose.ScoringLocation;
import frc.robot.util.SendableUtil;

public class RobotState implements Sendable, YawAngleSupplier,LateralOffsetSupplier{


	public double yawAngleDegrees = 0;
	public Optional<Double> ourPoseY = Optional.empty();
	public Optional<Double> photonPoseY = Optional.empty();
	public Optional<Double> lateralOffsetOurs = Optional.empty();
	public Optional<Double> lateralOffsetPhoton = Optional.empty();
	public Optional<AprilTagLocation> selectedTag = Optional.empty();
	public double cameraY = 0;
	public double movingAverageY = 0.0;
	
	@Override	
	public Optional<Double> getLateralOffset(){
		//this is the value that will be used for horizontal align command
		return Optional.of(cameraY);
	}	
	
	@Override
	public double getYawAngleDegrees() {
		//this is the value that will be used for hold yaw!
		return yawAngleDegrees;
	}
	
	public String getTagDesc() {
		if ( selectedTag.isPresent()) {
			AprilTagLocation t = selectedTag.get();
			return String.format("ID%d : %.2fm",t.getId(), t.getYMeters());
		}
		else{
			return "NONE";
		}
	}

	@Override
	public void initSendable(SendableBuilder sb) {
	    sb.addStringProperty("Tag", this::getTagDesc, null);
	    sb.addDoubleProperty("RobotYaw", this::getYawAngleDegrees, null);
	    sb.addDoubleProperty("LateralYOffset", () -> { return SendableUtil.doubleForOptional(getLateralOffset()) ;},null );
		sb.addDoubleProperty("PhotonY", () -> { return SendableUtil.doubleForOptional(photonPoseY) ;},null );	    
		sb.addDoubleProperty("PhotonYOffset", () -> { return SendableUtil.doubleForOptional(lateralOffsetPhoton) ;},null );
		sb.addDoubleProperty("OurY", () -> { return SendableUtil.doubleForOptional(ourPoseY) ;},null );
		sb.addDoubleProperty("OurYOffset", () -> { return SendableUtil.doubleForOptional(lateralOffsetOurs) ;},null );
		sb.addDoubleProperty("CameraY", () -> { return cameraY ;},null );
		sb.addDoubleProperty("CameraY:MovAvg", () -> { return cameraY ;},null );
	}



}
