// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.math.controller.PIDController;

import frc.robot.filters.DriveInput;
import frc.robot.pose.RobotPose;
import frc.robot.subsystems.DriveSubsystem;

/**
 *
 * 
 * @author aheitkamp
 */
public class SnapYawDegreesCommand extends EntechCommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final DriveSubsystem drive;
    private final PIDController pid;
    private final Supplier<RobotPose> latestPose;
    private int stoppingCounter = 0;

    /**
     * Creates a new snap yaw degrees command that will snap the robot to the specified angle
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param latestPose  The supplier of latest pose of the robot to get the current yaw
     * @param angle The angle you want to snap to
     */
    public SnapYawDegreesCommand(DriveSubsystem drive, Supplier<RobotPose> latestPose, double angle) {
        super(drive);
        this.drive = drive;
        this.latestPose = latestPose;

        pid = new PIDController(0.0865, 0.5, 0.0075);
        pid.enableContinuousInput(-180, 180);
        pid.setTolerance(1);
        pid.setSetpoint(angle);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double calcValue = Math.max(-0.75, Math.min(pid.calculate(latestPose.get().getCalculatedPose().getRotation().getDegrees()), 0.75));
        DriveInput di = new DriveInput(0, 0, calcValue);
        di.setOverrideYawLock(true);

        drive.drive(di);
    }

    @Override
    public void end(boolean interrupted) {
        drive.brake();
    }

    @Override
    public boolean isFinished() {
        if (pid.atSetpoint()) {
            stoppingCounter++;
        } else {
            stoppingCounter = 0;
        }
        return stoppingCounter >= 4;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
