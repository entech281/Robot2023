// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.DriverPreferences;

/** An example command that uses an example subsystem. */
public class ToggleFieldAbsoluteCommand extends  BaseDriverPreferencesCommand {

  /**
   * Creates a new ToggleFieldAbsoluteCommand.
   *
   * @param drive The subsystem used by this command.
   */
  public ToggleFieldAbsoluteCommand(DriverPreferences prefs) {
    super(prefs);

  }


  @Override
  public void execute() {
	driverPrefs.setFieldAbsoluteDriving(! driverPrefs.isFieldAbsoluteDriving());
  }


}
