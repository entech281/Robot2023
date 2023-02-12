// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

/**
 *
 * 
 * @author aheitkamp
 */
public class AlignToAngleCommand extends EntechCommandBase {

	public static final double P_GAIN = 0.0865;
	public static final double I_GAIN = 0.5;
    public static final double D_GAIN = 0.0075;
    public static final double ANGLE_TOLERANCE = 1;
    public static final double SPEED_LIMIT = 0.75;
    public static final int STOP_COUNT = 4;
    private final DriveSubsystem drive;
    private final PIDController pid;
    private final Joystick joystick;
    private Supplier<Double> yawAngleSupplier;
    private Supplier<Double> desiredAngleSupplier;
    private StoppingCounter counter;
    /**
     * Creates a new snap yaw degrees command that will snap the robot to the specified angle
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param latestPose  The supplier of latest pose of the robot to get the current yaw
     * @param joystick the joystick you controll the robot with
     */
    public AlignToAngleCommand(DriveSubsystem drive,  Joystick joystick,Supplier<Double> desiredAngleSupplier, Supplier<Double> yawAngleSupplier) {
        super(drive);
        this.drive = drive;
        this.joystick = joystick;

        pid = new PIDController(P_GAIN, I_GAIN, D_GAIN);
        pid.enableContinuousInput(-180, 180);
        pid.setTolerance(ANGLE_TOLERANCE);
        counter = new StoppingCounter("AutoAlignCommand",STOP_COUNT);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        double calcValue = Math.max(
            -SPEED_LIMIT, 
            Math.min(
                pid.calculate(
                    MathUtil.inputModulus(desiredAngleSupplier.get(), -180.0, 180.0), 
                    yawAngleSupplier.get()
                ), 
                SPEED_LIMIT
            )
        );
        DriveInput di = new DriveInput(-joystick.getY(), joystick.getX(), calcValue);
        di.setOverrideYawLock(true);
        di.setOverrideAutoYaw(true);


        drive.drive(di );
    }

    @Override
    public void end(boolean interrupted) {
        drive.brake();
    }

    @Override
    public boolean isFinished() {
    	return counter.isFinished(pid.atSetpoint());    	
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
