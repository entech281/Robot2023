package frc.robot;

import java.util.Optional;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.commands.supplier.LateralOffsetSupplier;
import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.pose.AprilTagLocation;
import frc.robot.pose.LateralOffset;
import frc.robot.pose.ScoringLocation;
import frc.robot.pose.TargetNode;
import frc.robot.util.SendableUtil;

public class RobotState implements Sendable, YawAngleSupplier,LateralOffsetSupplier{


	public double yawAngleDegrees = 0;
	public Optional<LateralOffset> closestScoringLocationOffset = Optional.empty();
	public double realLateralOffset = 0; 
	public double cameraY = 0;
	public double movingAverageY = 0.0;
	public Color alignState = Color.kBlack;
	
	@Override	
	public Optional<Double> getLateralOffset(){
		//this is the value that will be used for horizontal align command
		return Optional.of(realLateralOffset);
	}	
	
	@Override
	public double getYawAngleDegrees() {
		//this is the value that will be used for hold yaw!
		return yawAngleDegrees;
	}


	public String getClosestTargetDesc() {
		if ( closestScoringLocationOffset.isPresent()) {
			LateralOffset t = closestScoringLocationOffset.get();
			ScoringLocation sl = t.getNearestLocation();
			AprilTagLocation atag = sl.getSelectedTag();
			TargetNode tn = sl.getSelectedNode();
			return String.format("tag %d:%s @ %.2fm",atag.getId(),tn.getNodeID()+"",t.getLateralOffsetToLocationMeters());
		}
		else{
			return "NONE";
		}
	}
	
	
	@Override
	public void initSendable(SendableBuilder sb) {
	    sb.addStringProperty("Closest", this::getClosestTargetDesc, null);
	    sb.addDoubleProperty("RobotYaw", this::getYawAngleDegrees, null);
	    sb.addDoubleProperty("LateralYOffset", () -> { return SendableUtil.doubleForOptional(getLateralOffset()) ;},null );
		sb.addDoubleProperty("CameraY", () -> { return cameraY ;},null );
		sb.addDoubleProperty("CameraY:MovAvg", () -> { return movingAverageY ;},null );
		sb.addStringProperty("AlignColor", () -> { return alignState.toString() ;}, null);
	}



}
