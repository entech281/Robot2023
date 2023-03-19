// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.nudge;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.commands.EntechCommandBase;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

/**
 *
 * 
 * @author aheitkamp
 */
public class NudgeDirectionCommand extends EntechCommandBase {
    private final DriveSubsystem drive;
    private Timer timer;
    private final DriveInput direction;

    private static final double NUDGE_TIME = 0.25;
    private static final double NUDGE_FWD_SPEED = 0.2;
    private static final double NUDGE_LAT_SPEED = 0.4;
    private static final double NUDGE_SCORE_SKIP_SPEED = 0.6;
    
    public interface DIRECTION {
        public static final DriveInput LEFT = new DriveInput(0, -NUDGE_LAT_SPEED, 0);
        public static final DriveInput RIGHT = new DriveInput(0, NUDGE_LAT_SPEED, 0);
        public static final DriveInput SCORE_RIGHT = new DriveInput(0, NUDGE_SCORE_SKIP_SPEED, 0);
        public static final DriveInput SCORE_LEFT = new DriveInput(0, -NUDGE_SCORE_SKIP_SPEED, 0);
        public static final DriveInput FORWARD = new DriveInput(NUDGE_FWD_SPEED, 0, 0);
        public static final DriveInput BACKWARD = new DriveInput(-NUDGE_FWD_SPEED, 0, 0);
    }

    /**
     * Creates a new NudgeLeftCommand which will move the robot left for 0.5 seconds at half power
     * 
     *
     * @param drive The drive subsystem on which this command will run
     */
    public NudgeDirectionCommand(DriveSubsystem drive, DriveInput direction) {
        super(drive);
        this.drive = drive;
        this.direction = new DriveInput(direction);
        //this.direction.setOverrideAutoYaw(true);
    }
    
    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
    }

    @Override
    public void execute() {
        drive.drive(direction );
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
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
