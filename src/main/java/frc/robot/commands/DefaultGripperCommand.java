package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.subsystems.GripperSubsystem;


/** An example command that uses an example subsystem. */
public class DefaultGripperCommand extends EntechCommandBase {

  private final GripperSubsystem gripperSubsystem;
  private Supplier<Boolean> isOpenSupplier;

 
  public DefaultGripperCommand(GripperSubsystem subsystem, Supplier<Boolean> isOpenSupplier) {
      super(subsystem);
      gripperSubsystem = subsystem;
      this.isOpenSupplier = isOpenSupplier;
  }  


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
	gripperSubsystem.setOpen(isOpenSupplier.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return false;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return true;
  }
}
