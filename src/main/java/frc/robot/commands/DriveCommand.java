// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.filters.DriveInput;
import frc.robot.pose.RobotPose;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends EntechCommandBase {
    private final DriveSubsystem drive;
    private final Joystick joystick;
    private final Supplier<RobotPose> latestPose;

    /**
     * Creates a new ArcadeDrive. This command will drive your robot according to
     * the joystick
     * This command does not terminate.
     *
     * @param drive The drive subsystem on which this command will run
     * @param stick Driver joystick object
     */
    public DriveCommand(DriveSubsystem drive, Joystick joystick, Supplier<RobotPose> latestPose) {
        super(drive);
        this.drive = drive;
        this.joystick = joystick;
        this.latestPose = latestPose;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        drive.drive(new DriveInput(-joystick.getY(), joystick.getX(), joystick.getZ()), latestPose.get());
    }

    @Override
    public void end(boolean interrupted) {
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
