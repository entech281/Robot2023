package frc.robot.commands;

import frc.robot.oi.ShuffleboardDriverControls;

public class SetDriverYawEnableCommand extends BaseShuffleboardControlsUpdateCommand{

	boolean newValue;
	public SetDriverYawEnableCommand(ShuffleboardDriverControls preferencesToAlter, boolean newValue) {
		super(preferencesToAlter);
		this.newValue=newValue;
	}

	@Override
	public void execute() {
		driverControls.setYawLock(newValue);
	}
	

}
