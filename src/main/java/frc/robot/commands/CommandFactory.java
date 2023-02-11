package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.function.Supplier;

import frc.robot.subsystems.SubsystemManager;
import frc.robot.RobotConstants;
import frc.robot.pose.RobotPose;

/**
 *
 * @author dcowden 
 * @author aheitkamp
 */
public class CommandFactory {

    private final SubsystemManager sm;
    private final Supplier<RobotPose> latestRobotPose;

    public CommandFactory(SubsystemManager subsystemManager, Supplier<RobotPose> latestRobotPose){
        this.sm = subsystemManager;
        this.latestRobotPose = latestRobotPose;
    }

    public Command buttonFilterCommand(int buttonNumber, boolean enabled) {
        return new ButtonFilterCommand(sm.getDriveSubsystem(), buttonNumber, enabled);
    }

    public Command turnToggleFilter(boolean enabled) {
        return buttonFilterCommand(RobotConstants.DRIVER_STICK.TURN_TOGGLE, enabled);
    }

    public Command driveCommand(Joystick joystick) {
        return new DriveCommand(sm.getDriveSubsystem(), joystick, latestRobotPose);
    }

    public Command toggleFieldAbsolute() {
        return new ToggleFieldAbsoluteCommand(sm.getDriveSubsystem());
    }

    public Command snapYawDegreesCommand(double angle) {
        return new SnapYawDegreesCommand(sm.getDriveSubsystem(), latestRobotPose, angle);
    }

    public Command getAutonomousCommand() {
        return snapYawDegreesCommand(160);
    }

    public Command getZeroGyro() {
        return new ZeroGyroCommand(sm.getNavXSubSystem());
    }

    public Command nudgeLeftCommand() {
        return new NudgeLeftCommand(sm.getDriveSubsystem(), latestRobotPose);
    }

    public Command nudgeRightCommand() {
        return new NudgeRightCommand(sm.getDriveSubsystem(), latestRobotPose);
    }
}
