// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

/**
 *
 * 
 * @author aheitkamp
 */
public class DriveForwardTillTrueCommand extends EntechCommandBase {
    private final DriveSubsystem drive;
    private final BooleanSupplier condition;
    private final Supplier<Double> yawAngleSupplier;

    /**
     * Creates a new DriveForwardTillTrueCommand that will drive the robot forward until a given condition is true
     * 
     * 
     * @param drive The drive subsystem on which this command will run
     * @param condition the condition that when true will make the robot stop driving
     */
    public DriveForwardTillTrueCommand(DriveSubsystem drive, BooleanSupplier condition, Supplier<Double> yawAngleSupplier) {
        super(drive);
        this.drive = drive;
        this.condition = condition;
        this.yawAngleSupplier = yawAngleSupplier;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        drive.drive(new DriveInput(1, 0, 0,yawAngleSupplier.get()));
    }

    @Override
    public void end(boolean interrupted) {
        drive.brake();
    }

    @Override
    public boolean isFinished() {
        return condition.getAsBoolean();
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
