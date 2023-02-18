package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.ShuffleboardDriverControls;

public abstract class BaseShuffleboardControlsUpdateCommand extends InstantCommand{

	protected ShuffleboardDriverControls driverPrefs;
	
	public BaseShuffleboardControlsUpdateCommand ( ShuffleboardDriverControls  preferencesToAlter) {
		this.driverPrefs = preferencesToAlter;
	}

}
