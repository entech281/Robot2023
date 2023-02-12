// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

/**
 *
 * 
 * @author aheitkamp
 */
public class SnapYawDegreesCommand extends EntechCommandBase {

	public static final double P_GAIN = 0.0865;
	public static final double I_GAIN = 0.5;
	public static final double D_GAIN = 0.0075;
    public static final double ANGLE_TOLERANCE = 1;
    public static final double SPEED_LIMIT = 0.75;
    public static final int STOP_COUNT = 4;
    private Supplier<Double> yawAngleSupplier;
    private final DriveSubsystem drive;
    private final PIDController pid;
    private StoppingCounter counter;

    /**
     * Creates a new snap yaw degrees command that will snap the robot to the specified angle
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param angle The angle you want to snap to
     */
    public SnapYawDegreesCommand(DriveSubsystem drive,  double desiredAngle, Supplier<Double> yawAngleSupplier) {
        super(drive);
        this.drive = drive;
        this.yawAngleSupplier = yawAngleSupplier;
        pid = new PIDController(P_GAIN, I_GAIN, D_GAIN);
        pid.enableContinuousInput(-180, 180);
        pid.setTolerance(ANGLE_TOLERANCE);
        pid.setSetpoint(desiredAngle);
        counter = new StoppingCounter("SnapYawCommand",STOP_COUNT);
    }

    @Override
    public void initialize() {
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
        
        drive.drive(di );
    }

    @Override
    public void end(boolean interrupted) {
        drive.brake();
    }

    @Override
    public boolean isFinished() {
    	return counter.isFinished(pid.atSetpoint());
//        if (pid.atSetpoint()) {
//            stoppingCounter++;
//        } else {
//            stoppingCounter = 0;
//        }
//        SmartDashboard.putNumber("Counter", stoppingCounter);
//        return stoppingCounter >= STOP_COUNT;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
