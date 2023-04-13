// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.util.EntechUtils;
import frc.robot.util.StoppingCounter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
/**
 *
 * 
 * @author aheitkamp
 */
public class TurnRobotRelativeCommand extends EntechCommandBase {

    protected final DriveSubsystem drive;
    private YawAngleSupplier yawSupplier;
    private double angleToWait;
    private final double robotRelativeAngle;
    private static final double TOLERANCE = 1.0;
    private static final int STOPPING_COUNT = 5;
    private static double TIMEOUT_SECS = 2.5;
    private StoppingCounter sc;
    private Timer timer;    

    /**
     * Creates a new snap yaw degrees command that will snap the robot to the specified angle
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param current_angle The current yaw angle
     */
    public TurnRobotRelativeCommand(DriveSubsystem drive, YawAngleSupplier yawSupplier, double robotRelativeAngle) {
        super(drive);
        this.drive = drive;
        this.yawSupplier = yawSupplier;
        this.robotRelativeAngle = robotRelativeAngle;
    }

    @Override
    public void initialize() {
        drive.resetEncoders();
        if ( DriverStation.getAlliance() == Alliance.Red) {
            drive.setHoldYawAngle(-148.0);
            angleToWait = -148.0;
        }
        else {
            drive.setHoldYawAngle(-32);
            angleToWait = -32;

        }

        sc = new StoppingCounter("TurnRelative", STOPPING_COUNT);
        DriverStation.reportWarning("INIT", false);
        timer = new Timer();
        timer.start();        
    }

    @Override
    public void execute() {
        //DriverStation.reportWarning("exe", false);
        drive.driveFilterYawOnly(new DriveInput(0, 0, 0, yawSupplier.getYawAngleDegrees()));
    }

    @Override
    public void end(boolean interrupted) {
        DriverStation.reportWarning("END", false);
        //drive.resetEncoders();
    }

    @Override
    public boolean isFinished() {

    	if ( timer.get() > TIMEOUT_SECS ) {
    		DriverStation.reportWarning("TurnRobotRelativeTimedOut at " + TIMEOUT_SECS + "secs" , false);
    		return true;
    	}
    	//String msg = String.format("TurnRobotRelative:Yaw=%.2f, waiting for %.2f", yawSupplier.getYawAngleDegrees(),angleToWait);
    	//DriverStation.reportWarning(msg , false);
    	
        return sc.isFinished(Math.abs(yawSupplier.getYawAngleDegrees() - angleToWait) < TOLERANCE);
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
    
}
