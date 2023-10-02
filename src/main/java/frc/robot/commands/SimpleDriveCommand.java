// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.Drivetrain;

public class SimpleDriveCommand extends EntechCommandBase {
    protected final Drivetrain drive;
    protected final Supplier<DriveInput> operatorInput;

    /**
     * Creates a new ArcadeDrive. This command will drive your robot according to
     * the joystick
     * This command does not terminate.
     *
     * @param drive The drive subsystem on which this command will run
     * @param stick Driver joystick object
     */
    public SimpleDriveCommand(Drivetrain drive, Supplier<DriveInput> operatorInput) {
        super(drive);
        this.drive = drive;
        this.operatorInput = operatorInput;
    }

    @Override
    public void execute() {
        drive.drive(operatorInput.get() );
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
