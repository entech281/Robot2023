// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.util.StoppingCounter;
import static frc.robot.commands.DriveCommandConstants.*;
/**
 *
 * 
 * @author aheitkamp
 */
public class SnapYawDegreesCommand extends EntechCommandBase {

    protected final DriveSubsystem drive;
    protected final PIDController pid;
    protected StoppingCounter counter;
    private YawAngleSupplier yawAngleSupplier;


    /**
     * Creates a new snap yaw degrees command that will snap the robot to the specified angle
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param angle The angle you want to snap to
     */
    public SnapYawDegreesCommand(DriveSubsystem drive, double desiredAngle, YawAngleSupplier yawAngleSupplier) {
        super(drive);
        this.drive = drive;
        this.yawAngleSupplier = yawAngleSupplier;
        
        pid = new PIDController(P_GAIN, I_GAIN, D_GAIN);
        pid.enableContinuousInput(-180, 180);
        pid.setTolerance(ANGLE_TOLERANCE);
        counter = new StoppingCounter("PIDDriveCommand",STOP_COUNT);

        pid.setSetpoint(desiredAngle);
    }

    @Override
    public void execute() {
    	double yawAngleDegrees = yawAngleSupplier.getYawAngleDegrees();
        double calcValue = Math.max(
            -SPEED_LIMIT, 
            Math.min(
                pid.calculate(MathUtil.inputModulus(yawAngleDegrees, -180, 180)), 
                SPEED_LIMIT
            )
        );
        DriveInput di = new DriveInput(0, 0, calcValue);
        
        //TODO: should we pass yawAngleDegrees into the drive input, to support different behavior
        //in field absolute vs not?
        
        drive.drive(di);
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
