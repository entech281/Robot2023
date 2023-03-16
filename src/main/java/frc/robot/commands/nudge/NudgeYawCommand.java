// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.nudge;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.commands.EntechCommandBase;
import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

/**
 *
 * 
 * @author aheitkamp
 */
public class NudgeYawCommand extends EntechCommandBase {
    private final DriveSubsystem drive;
    private Timer timer;
    private final DriveInput direction;
    private final YawAngleSupplier yawAngleSupplier;
    private static final double NUDGE_TIME = 0.05;
    private static final double NUDGE_SPEED = 0.25;
    
    public interface DIRECTION {
        public static final DriveInput LEFT = new DriveInput(0, 0, NUDGE_SPEED);
        public static final DriveInput RIGHT = new DriveInput(0, 0, -NUDGE_SPEED);
    }

    /**
     * Creates a new NudgeRightCommand which will move the robot right for 0.5 seconds at half power
     * 
     *
     * @param drive The drive subsystem on which this command will run
     */
    public NudgeYawCommand(DriveSubsystem drive, DriveInput direction, YawAngleSupplier yawAngleSupplier) {
        super(drive);
        this.drive = drive;
        this.yawAngleSupplier = yawAngleSupplier;
        this.direction = new DriveInput(direction);
    }
   
    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
    }

    @Override
    public void execute() {
    	DriveInput di = new DriveInput(direction);
    	di.setYawAngleDegrees(yawAngleSupplier.getYawAngleDegrees());
        
        drive.drive(di);
    }
    
    @Override
    public void end(boolean interrupted) {
        drive.stop();
        drive.setHoldYawAngle(yawAngleSupplier.getYawAngleDegrees());
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
