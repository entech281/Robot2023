/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class EntechSubsystem extends SubsystemBase {

    public EntechSubsystem() {        
    }
	public abstract void initialize();
	public abstract boolean isEnabled();
    public abstract SubsystemStatus getStatus();
    
    
}
