package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

public class DriveSetRotationEnableCommand extends EntechCommandBase {

    DriveSubsystem drive;
	boolean newValue;
	public DriveSetRotationEnableCommand(DriveSubsystem drive, boolean newValue) {
		//super(drive);
        this.drive = drive;
		this.newValue = newValue;
	}

	@Override
	public void initialize() {
		drive.setRotationAllowed(newValue);
	}
	
    @Override
    public boolean isFinished() {
        return true;
    }
    
    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
