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
public class SnapYawDegreesCommand extends BaseDrivePIDCommand {

    private Supplier<Double> yawAngleSupplier;


    /**
     * Creates a new snap yaw degrees command that will snap the robot to the specified angle
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param angle The angle you want to snap to
     */
    public SnapYawDegreesCommand(DriveSubsystem drive, double desiredAngle, Supplier<Double> yawAngleSupplier) {
        super(drive,null);
        this.yawAngleSupplier = yawAngleSupplier;
        pid.setSetpoint(desiredAngle);
    }

    @Override
    public void execute() {
        double calcValue = Math.max(
            -SPEED_LIMIT, 
            Math.min(
                pid.calculate(MathUtil.inputModulus(yawAngleSupplier.get(), -180, 180)), 
                SPEED_LIMIT
            )
        );
        DriveInput di = new DriveInput(0, 0, calcValue);
        di.setOverrideYawLock(true);
        di.setOverrideAutoYaw(true);
        
        //TODO: should we pass yawAngleDegrees into the drive input, to support different behavior
        //in field absolute vs not?
        
        drive.drive(di);
    }

}
