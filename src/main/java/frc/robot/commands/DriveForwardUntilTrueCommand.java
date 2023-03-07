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
public class DriveForwardUntilTrueCommand extends EntechCommandBase {
    private final DriveSubsystem drive;
    private final BooleanSupplier condition;
    private final Supplier<DriveInput> operatorInput;

    /**
     * Creates a new DriveForwardUntilTrueCommand that will drive the robot forward until a given condition is true
     * TODO: It would be better if the conditions are simple parameters, vs outside the command.
     * It doesnt buy us a lot to push logic outside of the command, because that'd complicate command factory or operator interface.
     * Another strategy might be to pass a (value) object as input vs a function
     * 
     * @param drive The drive subsystem on which this command will run
     * @param condition the condition that when true will make the robot stop driving
     */
    public DriveForwardUntilTrueCommand(DriveSubsystem drive, BooleanSupplier condition, Supplier<DriveInput> operatorInput) {
        super(drive);
        this.drive = drive;
        this.condition = condition;
        this.operatorInput = operatorInput;
    }

    @Override
    public void execute() {
    	DriveInput di = operatorInput.get();
    	di.setForward(1);
    	di.setRight(0);
    	di.setRotation(0);
        drive.drive(di);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
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
