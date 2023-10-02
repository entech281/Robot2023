package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;

public class DriveSetRotationEnableCommand extends EntechCommandBase {

    Drivetrain drive;
	boolean newValue;
	public DriveSetRotationEnableCommand(Drivetrain drive, boolean newValue) {
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
