// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;

/**
 *
 * 
 * @author aheitkamp
 */
public class SnapYawDegreesCommand extends EntechCommandBase {
    private final DriveSubsystem drive;
    private final PIDController PID;
    private final NavXSubSystem navX;
    private int stoppingCounter = 0;  

    /**
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param NavX The NavX subsystem the command will use
     * @param Angle The angle you want to snap to
     */
    public SnapYawDegreesCommand(DriveSubsystem Drive, NavXSubSystem NavX, double Angle) {
        super(Drive);
        drive = Drive;
        navX = NavX;


        PID = new PIDController(0.0865, 0.5, 0.0075);
        PID.enableContinuousInput(-180, 180);
        PID.setTolerance(1);
        PID.setSetpoint(Angle);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double calcValue = Math.max(-0.75, Math.min(PID.calculate(navX.getAngle()), 0.75));
        DriveInput DI = new DriveInput(0, 0, calcValue);
        DI.setOverrideYawLock(true);

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
        if (PID.atSetpoint()) {
            stoppingCounter++;
        } else {
            stoppingCounter = 0;
        }
        return stoppingCounter == 4;
    }

    // Returns true if command should run when robot is disabled.
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
