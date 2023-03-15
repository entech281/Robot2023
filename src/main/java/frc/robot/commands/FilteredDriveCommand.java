package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

public class FilteredDriveCommand extends SimpleDriveCommand {

	/**
     * Creates a new ArcadeDrive. This command will drive your robot according to
     * the joystick
     * This command does not terminate.
     *
     * @param drive The drive subsystem on which this command will run
     * @param stick Driver joystick object
     */
    public FilteredDriveCommand(DriveSubsystem drive, Supplier<DriveInput> operatorInput) {
        super(drive,operatorInput);
    }

    @Override 
    public void initialize() {
    }

    @Override
    public void execute() {
    	drive.filteredDrive(operatorInput.get());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}