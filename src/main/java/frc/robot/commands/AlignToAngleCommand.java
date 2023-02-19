// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

/**
 *
 * 
 * @author aheitkamp
 */
public class AlignToAngleCommand extends BaseDrivePIDCommand {

    private Supplier<Double> desiredAngleSupplier;

    
    /**
     * Creates a new snap yaw degrees command that will snap the robot to the specified angle
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param latestPose  The supplier of latest pose of the robot to get the current yaw
     * @param joystick the joystick you controll the robot with
     */
    public AlignToAngleCommand(DriveSubsystem drive,      		
    		Supplier<DriveInput> operatorInput,
    		Supplier<Double> desiredAngleSupplier) {
        super(drive,operatorInput);
        this.desiredAngleSupplier = desiredAngleSupplier;
    }

    @Override
    public void execute() {

        double calcValue = Math.max(
            -SPEED_LIMIT, 
            Math.min(
                pid.calculate(
                    MathUtil.inputModulus(desiredAngleSupplier.get(), -180.0, 180.0), 
                    operatorInput.get().getYawAngleDegrees()
                ), 
                SPEED_LIMIT
            )
        );

        DriveInput di = operatorInput.get();
        di.setRotation(calcValue);
        drive.drive(di );
    }

}
