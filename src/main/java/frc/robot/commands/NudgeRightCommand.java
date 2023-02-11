// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.pose.RobotPose;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * 
 * @author aheitkamp
 */
public class NudgeRightCommand extends EntechCommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final DriveSubsystem drive;
    private Timer timer;
    private final Supplier<RobotPose> latestPose;

    private static final double NUDGE_TIME = 0.5;
    private static final double NUDGE_SPEED = 0.5;

    /**
     * Creates a new NudgeRightCommand which will move the robot right for 0.5 seconds at half power
     * 
     *
     * @param drive The drive subsystem on which this command will run
     */
    public NudgeRightCommand(DriveSubsystem drive, Supplier<RobotPose> latestPose) {
        super(drive);
        this.drive = drive;
        this.latestPose = latestPose;
    }
   
    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
    }

    @Override
    public void execute() {
        DriveInput di = new DriveInput(0, NUDGE_SPEED, 0);
        di.setOverrideAutoYaw(true);
        drive.drive(di, latestPose.get());
    }
    
    @Override
    public void end(boolean interrupted) {
        drive.brake();
    }

    @Override
    public boolean isFinished() {
        return timer.get() > NUDGE_TIME;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
