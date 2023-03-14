package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.filters.DriveInput;
import frc.robot.filters.FieldPoseToFieldAbsoluteDriveFilter;
import frc.robot.filters.FieldRelativeDriveInputFilter;
import frc.robot.filters.HoldYawFilter;
import frc.robot.filters.JoystickDeadbandFilter;
import frc.robot.filters.NoRotationFilter;
import frc.robot.oi.ShuffleboardDriverControls;
import frc.robot.subsystems.DriveSubsystem;

public class FilteredDriveCommand extends SimpleDriveCommand {
    private ShuffleboardDriverControls driverControls;

    // DriveFilters used
    private JoystickDeadbandFilter jsDeadbandFilter;
    private FieldRelativeDriveInputFilter fieldRelativeFilter;
    private NoRotationFilter yawLockFilter;
    private FieldPoseToFieldAbsoluteDriveFilter yawAngleCorrectionFilter;
    private HoldYawFilter yawHoldFilter;

	/**
     * Creates a new ArcadeDrive. This command will drive your robot according to
     * the joystick
     * This command does not terminate.
     *
     * @param drive The drive subsystem on which this command will run
     * @param stick Driver joystick object
     */
    public FilteredDriveCommand(DriveSubsystem drive, Supplier<DriveInput> operatorInput,  ShuffleboardDriverControls driverControls) {
        super(drive,operatorInput);
        this.driverControls = driverControls;

        this.jsDeadbandFilter = new JoystickDeadbandFilter();
        this.fieldRelativeFilter = new FieldRelativeDriveInputFilter();
        this.yawLockFilter = new NoRotationFilter();
        this.yawAngleCorrectionFilter = new FieldPoseToFieldAbsoluteDriveFilter();
        this.yawHoldFilter = new HoldYawFilter();
        yawHoldFilter.setEnabled(true);
    }

    @Override 
    public void initialize() {
        yawHoldFilter.reset();  
    }

    @Override
    public void execute() {
    	DriveInput di = operatorInput.get();

        // Special case: set the setpoint for the HoldYawFilter if nothing has until now
        if ( ! yawHoldFilter.isSetpointValid() ) {
            yawHoldFilter.updateSetpoint(operatorInput.get().getYawAngleDegrees());
        }

    	DriveInput filtered = di;
        if (jsDeadbandFilter.getEnabled()) {
            filtered = jsDeadbandFilter.filter(filtered);
        }
    	
    	if (driverControls.isYawEnabled()) {
            // Drive holding trigger and is allowed to twist, update the hold yaw filter setpoint to current value
            yawHoldFilter.updateSetpoint(filtered.getYawAngleDegrees());
        } else {
            if (yawHoldFilter.getEnabled()) {
                filtered = yawHoldFilter.filter(filtered);
                if ( ! yawHoldFilter.isActive() ) {
    		        filtered = yawLockFilter.filter(filtered);
                }
            } else {
    		    filtered = yawLockFilter.filter(filtered);
            }
    	}
        
    	if (driverControls.isFieldAbsolute()) {
            filtered = yawAngleCorrectionFilter.filter(filtered);
        } else {
    		filtered = fieldRelativeFilter.filter(filtered);
    	}
    	
    	drive.drive(filtered);
    }
}
