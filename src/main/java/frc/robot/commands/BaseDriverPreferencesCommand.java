package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.DriverPreferences;

public abstract class BaseDriverPreferencesCommand extends InstantCommand{

	protected DriverPreferences driverPrefs;
	
	public BaseDriverPreferencesCommand ( DriverPreferences preferencesToAlter) {
		this.driverPrefs = preferencesToAlter;
	}

}
