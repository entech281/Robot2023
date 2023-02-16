package frc.robot.commands;

import frc.robot.DriverPreferences;

public class SetDriverYawEnableCommand extends BaseDriverPreferencesCommand{

	boolean newValue;
	public SetDriverYawEnableCommand(DriverPreferences preferencesToAlter, boolean newValue) {
		super(preferencesToAlter);
		this.newValue=newValue;
	}

	@Override
	public void execute() {
		driverPrefs.setTwistLock(newValue);
	}
	

}
