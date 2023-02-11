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

    private static final double P_GAIN = 0.0865;
    private static final double I_GAIN = 0.5;
    private static final double D_GAIN = 0.0075;
    private static final double ANGLE_TOLERANCE = 1;
    private static final double SPEED_LIMIT = 0.75;
    private static final double STOPPING_COUNT = 4;

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

        pid = new PIDController(P_GAIN, I_GAIN, D_GAIN);
        pid.enableContinuousInput(-180, 180);
        pid.setTolerance(ANGLE_TOLERANCE);
        pid.setSetpoint(angle);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double calcValue = Math.max(-SPEED_LIMIT, Math.min(pid.calculate(latestPose.get().getCalculatedPose().getRotation().getDegrees()), SPEED_LIMIT));
        DriveInput di = new DriveInput(0, 0, calcValue);
        di.setOverrideYawLock(true);

        drive.drive(di, latestPose.get());
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
        return stoppingCounter >= STOPPING_COUNT;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
