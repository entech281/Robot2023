package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.pose.ArmStatus;

/**
 *
 * @author dcowden
 */
public class ArmSubsystem extends EntechSubsystem{
 
  
  public ArmStatus getStatus(){
      return new ArmStatus();
  }
  
  @Override
  public void initialize() {
    // Create the internal objects here
  }

  @Override
  public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType(getName());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

}
