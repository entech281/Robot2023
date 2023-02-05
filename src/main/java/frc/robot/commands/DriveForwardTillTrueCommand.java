// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

public class DriveForwardTillTrueCommand extends EntechCommandBase {
    private final DriveSubsystem drive;
    private final BooleanSupplier condition;

    /**
     * Creates a new ArcadeDrive. This command will drive your robot according to
     * the joystick
     * This command does not terminate.
     *
     * @param drive The drive subsystem on which this command will run
     * @param stick Driver joystick object
     */
    public DriveForwardTillTrueCommand(DriveSubsystem Drive, BooleanSupplier Condition) {
        super(Drive);
        drive = Drive;
        condition = Condition;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        drive.drive(new DriveInput(1, 0, 0));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drive.brake();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return condition.getAsBoolean();
    }

    // Returns true if command should run when robot is disabled.
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
