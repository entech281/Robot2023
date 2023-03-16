package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.supplier.LateralOffsetSupplier;
import frc.robot.commands.supplier.TargetYawSupplier;
import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.controllers.RobotLateralPIDController;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Tries to align to the target scoring location, by rotating the robot about its axis
 * (Operator can still drive in the meantime)
 * The scoring location is set the start of the command. To align to a new target, make a new command instance
 * @param joystick the joystick you controll the robot with
 *
 *
 * @author dcowden
 */
public class HorizontalAlignWithTagCommand extends EntechCommandBase {

    protected final DriveSubsystem drive;
    protected final LateralOffsetSupplier lateralOffsetSupplier;
    protected final Supplier<DriveInput> operatorInput;
    private RobotLateralPIDController pid;


    /**
     * Tries to align to the target scoring location, by rotating the robot about its axis
     * (Operator can still drive in the meantime)
     * @param joystick the joystick you controll the robot with
     */
    public HorizontalAlignWithTagCommand(DriveSubsystem drive, Supplier<DriveInput> operatorInput, LateralOffsetSupplier lateralOffsetSupplier ) {
        super(drive);
        this.drive = drive;
        this.lateralOffsetSupplier = lateralOffsetSupplier;
        this.operatorInput = operatorInput;
        pid = new RobotLateralPIDController();
        pid.setSetpoint(0.0);
    }

    @Override
    public void execute() {
        DriveInput di = operatorInput.get();
        DriveInput newDi = new DriveInput(di);
        if ( lateralOffsetSupplier.getLateralOffset().isPresent()) {
        	double lateralOffset = lateralOffsetSupplier.getLateralOffset().get();
        	
        	SmartDashboard.putData("HorizontalAlignPID",pid);
        	
        	double calcValue = pid.calculate(lateralOffset);
        	DriverStation.reportWarning("LateralOffsetFromHorizontalAlign=" + lateralOffset + ", output=" + calcValue,false);        	
        	newDi.setRight(calcValue);
        }
        else {
        	DriverStation.reportWarning("LateralOffsetFromHorizontalAlign: no offset available",false);
        }
        drive.filteredDrive(newDi);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
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
