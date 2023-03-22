// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.subsystems.DriveSubsystem;
/**
 *
 * 
 * @author aheitkamp
 */
public class DriveYawToNearestPerpendicular extends EntechCommandBase {

    protected final DriveSubsystem drive;
    private YawAngleSupplier yawSupplier;

    /**
     * Creates a new snap yaw degrees command that will snap the robot to the specified angle
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param current_angle The current yaw angle
     */
    public DriveYawToNearestPerpendicular(DriveSubsystem drive, YawAngleSupplier yawSupplier) {
        super(drive);
        this.drive = drive;
        this.yawSupplier = yawSupplier;
    }

    @Override
    public void initialize() {
        if (Math.abs(yawSupplier.getYawAngleDegrees()) < 90.0) {
            drive.setHoldYawAngle(0.0);
        } else {
            drive.setHoldYawAngle(180.0);
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
