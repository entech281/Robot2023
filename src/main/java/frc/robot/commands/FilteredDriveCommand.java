package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.DriverPreferences;
import frc.robot.filters.DriveInput;
import frc.robot.filters.FieldRelativeDriveInputFilter;
import frc.robot.filters.TurnToggleFilter;
import frc.robot.subsystems.DriveSubsystem;

public class FilteredDriveCommand extends SimpleDriveCommand {
    private DriverPreferences driverPreferences;


	/**
     * Creates a new ArcadeDrive. This command will drive your robot according to
     * the joystick
     * This command does not terminate.
     *
     * @param drive The drive subsystem on which this command will run
     * @param stick Driver joystick object
     */
    public FilteredDriveCommand(DriveSubsystem drive, Joystick joystick, Supplier<Double> yawAngleSupplier, DriverPreferences driverPreferences) {
        super(drive,joystick,yawAngleSupplier);
        this.driverPreferences =driverPreferences;
    }

    @Override
    public void execute() {
    	DriveInput operatorInput = new DriveInput(-joystick.getY(), joystick.getX(), joystick.getZ(), yawAngleSupplier.get());
    	DriveInput filtered = operatorInput;
    	
    	if ( driverPreferences.isFieldRelativeDriving()) {
    		filtered = new FieldRelativeDriveInputFilter().filter(operatorInput);
    	}
    	
    	if ( driverPreferences.isTwistLocked()) {
    		filtered = new TurnToggleFilter().filter(filtered);
    	}
    	
    	drive.drive(filtered);
    }

}
