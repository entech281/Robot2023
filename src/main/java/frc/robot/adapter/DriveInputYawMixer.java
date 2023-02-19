package frc.robot.adapter;

import java.util.function.Supplier;

import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.filters.DriveInput;

public class DriveInputYawMixer implements Supplier<DriveInput>{

	private Supplier<DriveInput> originalOperatorInput;
	private YawAngleSupplier yawAngleSupplier;
	
	public DriveInputYawMixer(YawAngleSupplier yawAngleSupplier , Supplier<DriveInput> originalOperatorInput) {
		this.originalOperatorInput = originalOperatorInput;
		this.yawAngleSupplier = yawAngleSupplier;
	}
	
	
	@Override
	public DriveInput get() {
		DriveInput original = originalOperatorInput.get();
		original.setYawAngleDegrees(yawAngleSupplier.getYawAngleDegrees());
		return original;
	}

}
