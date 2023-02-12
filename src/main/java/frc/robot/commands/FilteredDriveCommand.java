package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.filters.DriveFilterManager;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

public class FilteredDriveCommand extends DriveCommand {
    private final DriveFilterManager filterManager = new DriveFilterManager();

    public DriveFilterManager getFilterManager() {
		return filterManager;
	}

	/**
     * Creates a new ArcadeDrive. This command will drive your robot according to
     * the joystick
     * This command does not terminate.
     *
     * @param drive The drive subsystem on which this command will run
     * @param stick Driver joystick object
     */
    public FilteredDriveCommand(DriveSubsystem drive, Joystick joystick, Supplier<Double> yawAngleSupplier) {
        super(drive,joystick,yawAngleSupplier);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    	DriveInput operatorInput =new DriveInput(-joystick.getY(), joystick.getX(), joystick.getZ(),yawAngleSupplier.get());
    	DriveInput filteredInput = filterManager.filtered(operatorInput);
    	drive.drive(filteredInput);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
