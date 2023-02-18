package frc.robot.commands;

import frc.robot.ShuffleboardDriverControls;

public class SetDriverYawEnableCommand extends BaseShuffleboardControlsUpdateCommand{

	boolean newValue;
	public SetDriverYawEnableCommand(ShuffleboardDriverControls preferencesToAlter, boolean newValue) {
		super(preferencesToAlter);
		this.newValue=newValue;
	}

	@Override
	public void execute() {
		driverPrefs.setYawLock(newValue);
	}
	

}
