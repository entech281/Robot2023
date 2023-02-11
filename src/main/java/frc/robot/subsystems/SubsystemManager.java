package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Exercise 2: Add the OnboardIO subsystem to the subsystem manager.  
// This involves storing the subsystem privately in this class, initializing it, and providing a get method.
// You can use the drive subsystem as an example.

public class SubsystemManager {
    private DriveSubsystem driveSubsystem;
    private NavXSubSystem navXSubSystem;
    private VisionSubsystem visionSubsystem;
    private ArmSubsystem armSubsystem;
    
    public SubsystemManager() {
    }

    public DriveSubsystem getDriveSubsystem() {
        return driveSubsystem;
    }

    public NavXSubSystem getNavXSubSystem() {
        return navXSubSystem;
    }
    public VisionSubsystem getVisionSubsystem() {
        return visionSubsystem;
    }

    public ArmSubsystem getArmSubsystem(){
        return armSubsystem;
    }
    
    public void initAll() {
        navXSubSystem  = new NavXSubSystem();
        driveSubsystem = new DriveSubsystem(getNavXSubSystem());
        visionSubsystem = new VisionSubsystem();
        armSubsystem = new ArmSubsystem();
        
        // Initialize the known subsystems
        navXSubSystem.initialize();
        driveSubsystem.initialize();
        visionSubsystem.initialize();
        armSubsystem.initialize();
        
        // Make sure they all send their logging data
        SmartDashboard.putData(navXSubSystem);
        SmartDashboard.putData(driveSubsystem);
        SmartDashboard.putData(visionSubsystem);
        SmartDashboard.putData(armSubsystem);
    }
}
