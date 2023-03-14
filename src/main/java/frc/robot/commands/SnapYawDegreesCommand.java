// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.commands.supplier.EstimatedPoseSupplier;
import frc.robot.controllers.RobotYawPIDController;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
/**
 *
 * 
 * @author aheitkamp
 */
public class SnapYawDegreesCommand extends EntechCommandBase {

    protected final DriveSubsystem drive;
    protected final RobotYawPIDController pid;
    private EstimatedPoseSupplier estimatedPoseSupplier;

    /**
     * Creates a new snap yaw degrees command that will snap the robot to the specified angle
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param angle The angle you want to snap to
     */
    public SnapYawDegreesCommand(DriveSubsystem drive, double desiredAngle, EstimatedPoseSupplier estimatedPoseSupplier) {
        super(drive);
        this.drive = drive;
        this.estimatedPoseSupplier = estimatedPoseSupplier;
        
        pid = new RobotYawPIDController();
        pid.setSetpoint(desiredAngle);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    	double yawAngleDegrees = estimatedPoseSupplier.getEstimatedPose().get().getRotation().getDegrees();
        double calcValue = pid.calculate(-yawAngleDegrees); 
        DriveInput di = new DriveInput(0, 0, calcValue);
        
        drive.drive(di);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
    	return pid.isStable();    	
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }    
    
}
