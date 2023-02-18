// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

public class SimpleDriveCommand extends EntechCommandBase {
    protected final DriveSubsystem drive;
    protected final Supplier<DriveInput> operatorInput;
    protected final Supplier<Double> yawAngleSupplier;

    /**
     * Creates a new ArcadeDrive. This command will drive your robot according to
     * the joystick
     * This command does not terminate.
     *
     * @param drive The drive subsystem on which this command will run
     * @param stick Driver joystick object
     */
    public SimpleDriveCommand(DriveSubsystem drive, Supplier<DriveInput> operatorInput, Supplier<Double> yawAngleSupplier) {
        super(drive);
        this.drive = drive;
        this.operatorInput = operatorInput;
        this.yawAngleSupplier = yawAngleSupplier;
    }


    @Override
    public void execute() {
    	DriveInput di = operatorInput.get();
    	di.setYawAngleDegrees(yawAngleSupplier.get());
        drive.drive(di );
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
