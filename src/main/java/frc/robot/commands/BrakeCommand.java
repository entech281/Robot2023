package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

public class BrakeCommand extends EntechCommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveSubsystem m_drive;
  
    /**
     * Creates a new DriveBrakeForSeconds.
     *
     * @param subsystem The subsystem used by this command.
     */
    public BrakeCommand(DriveSubsystem subsystem) {
        super(subsystem);
        m_drive = subsystem;
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      m_drive.setBrakeMode();
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      m_drive.setCoastMode();
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
  
    // Returns true if this command should run when robot is disabled.
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
  }
  