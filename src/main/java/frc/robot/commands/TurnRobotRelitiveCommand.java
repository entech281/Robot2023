// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.util.StoppingCounter;

import edu.wpi.first.wpilibj.DriverStation;
/**
 *
 * 
 * @author aheitkamp
 */
public class TurnRobotRelitiveCommand extends EntechCommandBase {

    protected final DriveSubsystem drive;
    private YawAngleSupplier yawSupplier;
    private double angleToWait;
    private final double AngleToTurn;
    private static final double TOLERANCE = 1.0;
    private static final int STOPPING_COUNT = 5;
    private StoppingCounter sc;

    /**
     * Creates a new snap yaw degrees command that will snap the robot to the specified angle
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param current_angle The current yaw angle
     */
    public TurnRobotRelitiveCommand(DriveSubsystem drive, YawAngleSupplier yawSupplier, double AngleToTurn) {
        super(drive);
        this.drive = drive;
        this.yawSupplier = yawSupplier;
        this.AngleToTurn = AngleToTurn;
    }

    @Override
    public void initialize() {
        drive.resetEncoders();
        double newAngle = yawSupplier.getYawAngleDegrees() + AngleToTurn;
        drive.setHoldYawAngle(newAngle);
        angleToWait = newAngle;
        sc = new StoppingCounter("TurnRelitive", STOPPING_COUNT);
        DriverStation.reportWarning("INIT", false);
    }

    @Override
    public void execute() {
        DriverStation.reportWarning("exe", false);
        drive.driveFilterYawOnly(new DriveInput(0, 0, 0, yawSupplier.getYawAngleDegrees()));
    }

    @Override
    public void end(boolean interrupted) {
        DriverStation.reportWarning("END", false);
        drive.resetEncoders();
    }

    @Override
    public boolean isFinished() {
        return sc.isFinished(Math.abs(yawSupplier.getYawAngleDegrees() - angleToWait) < TOLERANCE);
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
    
}
