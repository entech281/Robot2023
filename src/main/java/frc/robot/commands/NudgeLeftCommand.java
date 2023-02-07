// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * 
 * @author aheitkamp
 */
public class NudgeLeftCommand extends EntechCommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final DriveSubsystem drive;
    private Timer timer;

    /**
     * 
     *
     * @param drive The drive subsystem on which this command will run
     */
    public NudgeLeftCommand(DriveSubsystem Drive) {
        super(Drive);
        drive = Drive;
    }

    // Called when the command is initially scheduled.    
    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        DriveInput DI = new DriveInput(0, 0.5, 0);
        drive.drive(DI);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drive.brake();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return timer.get() > 1;
    }

    // Returns true if command should run when robot is disabled.
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
