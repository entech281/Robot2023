package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElbowSubsystem;


/** An example command that uses an example subsystem. */
public class ArmElbowForgetHomesCommand extends EntechCommandBase {

  private final ElbowSubsystem elbowSubsystem;
  private final ArmSubsystem armSubsystem;


  /**
   * Creates a new GripperCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ArmElbowForgetHomesCommand(ElbowSubsystem elbowSubsystem,ArmSubsystem armSubsystem) {
      super(elbowSubsystem,armSubsystem);
      this.elbowSubsystem = elbowSubsystem;
      this.armSubsystem = armSubsystem;
  }


  @Override
  public void initialize() {
	  elbowSubsystem.forgetHome();
	  armSubsystem.forgetHome();
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
      return true;
  }
}
