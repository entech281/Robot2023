// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.Drivetrain;
import frc.robot.utils.StoppingCounter;
/**
 *
 * 
 * @author aheitkamp
 */
public class DriveYawToNearestPerpendicular extends EntechCommandBase {

    protected final Drivetrain drive;
    private YawAngleSupplier yawSupplier;
    private double angleToWait;
    private static final double TOLERANCE = 2.0;
    private static final int STOPPING_COUNT = 5;
    private StoppingCounter sc;

    /**
     * Creates a new snap yaw degrees command that will snap the robot to the specified angle
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param current_angle The current yaw angle
     */
    public DriveYawToNearestPerpendicular(Drivetrain drive, YawAngleSupplier yawSupplier) {
        super(drive);
        this.drive = drive;
        this.yawSupplier = yawSupplier;
    }

    @Override
    public void initialize() {
        if (Math.abs(yawSupplier.getYawAngleDegrees()) < 90.0) {
            drive.setHoldYawAngle(0.0);
            angleToWait = 0.0;
        } else {
            drive.setHoldYawAngle(180.0);
            angleToWait = 180.0;
        }
        sc = new StoppingCounter("NearestPerp", STOPPING_COUNT);
        DriverStation.reportWarning("INIT" + this, false);
    }

    @Override
    public void execute() {
        //DriverStation.reportWarning("exe" + this, false);
        drive.driveFilterYawRobotRelative(new DriveInput(0, 0, 0, yawSupplier.getYawAngleDegrees()));
    }

    @Override
    public void end(boolean interrupted) {
        DriverStation.reportWarning("END" + this, false);
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
