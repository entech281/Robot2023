package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Exercise 2: Add the OnboardIO subsystem to the subsystem manager.  
// This involves storing the subsystem privately in this class, initializing it, and providing a get method.
// You can use the drive subsystem as an example.

public class SubsystemManager {
    private DriveSubsystem driveSubsystem;
    private NavXSubSystem navXSubSystem;

    public SubsystemManager() {
    }

    public DriveSubsystem getDriveSubsystem() {
        return driveSubsystem;
    }

    public NavXSubSystem getNavXSubSystem() {
        return navXSubSystem;
    }


    public void initAll() {
        navXSubSystem  = new NavXSubSystem();
        driveSubsystem = new DriveSubsystem(navXSubSystem.getGyro());

        // Initialize the known subsystems
        driveSubsystem.initialize();

        // Make sure they all send their logging data
        SmartDashboard.putData(driveSubsystem);
    }
}
