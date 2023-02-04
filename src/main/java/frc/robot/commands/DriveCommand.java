// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends EntechCommandBase {
    private final DriveSubsystem drive;
    private final Joystick joystick;

    /**
     * Creates a new ArcadeDrive. This command will drive your robot according to the joystick
     * This command does not terminate.
     *
     * @param drive The drive subsystem on which this command will run
     * @param stick Driver joystick object
     */
    public DriveCommand(DriveSubsystem Drive, Joystick Joystick) {
        super(Drive);
        drive = Drive;
        joystick = Joystick;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        drive.drive(new DriveInput(-joystick.getY(), joystick.getX(), joystick.getZ()));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    // Returns true if command should run when robot is disabled.
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
