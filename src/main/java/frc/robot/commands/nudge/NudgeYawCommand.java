// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.nudge;

import frc.robot.commands.EntechCommandBase;
import frc.robot.subsystems.Drivetrain;

/**
 *
 * 
 * @author aheitkamp
 */
public class NudgeYawCommand extends EntechCommandBase {
    private final Drivetrain drive;
    private final DIRECTION direction;
    
    public enum DIRECTION {
        LEFT, RIGHT
    }

    /**
     * Creates a new NudgeRightCommand which will move the robot right for 0.5 seconds at half power
     * 
     *
     * @param drive The drive subsystem on which this command will run
     */
    public NudgeYawCommand(Drivetrain drive, NudgeYawCommand.DIRECTION direction) {
        super(drive);
        this.drive = drive;
        this.direction = direction;
    }
   
    @Override
    public void initialize() {
        switch (direction) {
        case LEFT:
            drive.nudgeYawLeft();
            break;
        case RIGHT:
            drive.nudgeYawRight();
            break;
        }
    }

    @Override
    public void execute() {
    }
    
    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
