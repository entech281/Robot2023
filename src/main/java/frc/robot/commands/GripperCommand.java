package frc.robot.commands;

import frc.robot.subsystems.GripperSubsystem;

/** An example command that uses an example subsystem. */
public class GripperCommand extends EntechCommandBase {

  private final GripperSubsystem gripperSubsystem;
  private GripperSubsystem.GripperState state;

  /**
   * Creates a new GripperCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public GripperCommand(GripperSubsystem subsystem, GripperSubsystem.GripperState state) {
      super(subsystem);
      gripperSubsystem = subsystem;
      this.state = state;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    gripperSubsystem.setGripperState(state);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Nothing to do.  Everything is managed in the ArmSubsystem periodic()
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return true;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
