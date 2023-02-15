// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.nudge;

import frc.robot.commands.EntechCommandBase;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * 
 * @author aheitkamp
 */
public class NudgeYawCommand extends EntechCommandBase {
    private final DriveSubsystem drive;
    private Timer timer;
    private final DriveInput direction;
    private final Supplier<Double> yawAngleSupplier;
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
    public NudgeYawCommand(DriveSubsystem drive, DriveInput direction, Supplier<Double>yawAngleSupplier) {
        super(drive);
        this.drive = drive;
        this.yawAngleSupplier = yawAngleSupplier;
        this.direction = new DriveInput(direction);
        //this.direction.setOverrideAutoYaw(true);
        //this.direction.setOverrideYawLock(true);
    }
   
    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
    }

    @Override
    public void execute() {
    	DriveInput di = new DriveInput(direction);
    	di.setYawAngleDegrees(yawAngleSupplier.get());
        drive.drive(di );
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
