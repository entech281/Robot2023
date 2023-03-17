package frc.robot;
import java.util.Optional;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.commands.supplier.LateralOffsetSupplier;
import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.util.SendableUtil;

public class RobotState implements Sendable, YawAngleSupplier,LateralOffsetSupplier{


	private Optional<Double> lateralOffset = Optional.empty();
	private double yawAngleDegrees = 0;
	

	public Optional<Double> getLateralOffset(){
		return lateralOffset;
	}	
	
	public void setLateralOffset(double lateralOffset) {
		this.lateralOffset = Optional.of(lateralOffset);
	}
	
	public void setLateralOffset(Optional<Double> newLateralOffset) {
		this.lateralOffset = newLateralOffset;
	}
	
	@Override
	public double getYawAngleDegrees() {
		return yawAngleDegrees;
	}

	public void setYawAngleDegrees(double newYawAngleDegrees) {
		this.yawAngleDegrees = newYawAngleDegrees;
	}
	
	
	@Override
	public void initSendable(SendableBuilder sb) {
	    sb.addDoubleProperty("RobotYaw", this::getYawAngleDegrees, null);
		sb.addDoubleProperty("LateralOffset", () -> { return SendableUtil.doubleForOptional(this.getLateralOffset()) ;},null );
	}



}
