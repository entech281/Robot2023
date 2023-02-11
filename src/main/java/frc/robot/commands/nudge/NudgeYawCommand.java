// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.nudge;

import frc.robot.commands.EntechCommandBase;
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
public class NudgeYawCommand extends EntechCommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final DriveSubsystem drive;
    private Timer timer;
    private final Supplier<RobotPose> latestPose;
    private final DriveInput direction;

    private static final double NUDGE_TIME = 0.25;
    
    public interface DIRECTION {
        public static final DriveInput LEFT = new DriveInput(0, 0, 0.5);
        public static final DriveInput RIGHT = new DriveInput(0, 0, -0.5);
    }

    /**
     * Creates a new NudgeRightCommand which will move the robot right for 0.5 seconds at half power
     * 
     *
     * @param drive The drive subsystem on which this command will run
     */
    public NudgeYawCommand(DriveSubsystem drive, DriveInput direction, Supplier<RobotPose> latestPose) {
        super(drive);
        this.drive = drive;
        this.direction = direction.clone();
        this.direction.setOverrideAutoYaw(true);
        this.direction.setOverrideYawLock(true);
        this.latestPose = latestPose;
    }
   
    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
    }

    @Override
    public void execute() {
        drive.drive(direction.clone(), latestPose.get());
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