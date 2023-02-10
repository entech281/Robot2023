package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.pose.RobotPose;

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
    
    public void updateCurrentRobotPose(RobotPose newPose) {
        navXSubSystem.setRobotPose(newPose);
        driveSubsystem.setRobotPose(newPose);
        visionSubsystem.setRobotPose(newPose);
        armSubsystem.setRobotPose(newPose);    	
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
