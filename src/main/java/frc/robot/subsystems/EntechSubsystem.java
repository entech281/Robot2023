/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.pose.RobotPose;

public abstract class EntechSubsystem extends SubsystemBase {

    public EntechSubsystem() {
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    private RobotPose robotPose;
    
    public RobotPose getCurrentPose() {
		return robotPose;
	}
	public void setRobotPose(RobotPose robotPose) {
		this.robotPose = robotPose;
	}
	public abstract void initialize();
    public abstract SubsystemStatus getStatus();
    
    
}
