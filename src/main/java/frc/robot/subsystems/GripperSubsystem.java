package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotConstants;

public class GripperSubsystem extends EntechSubsystem{

	private DoubleSolenoid gripperSolenoid;	
	private int gripperSolenoidCounter;
	private GripperState gripperState;
	private boolean enabled = false;
	private final int SOLENOID_HIT_COUNT = 10;
	
	public enum GripperState { kClose, kOpen }
	
	@Override
	public void initialize() {
		if (enabled ) {
			gripperSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotConstants.PNEUMATICS.GRIPPER_OPEN, RobotConstants.PNEUMATICS.GRIPPER_CLOSE);
		}
		
	}
	
	@Override
	public void periodic() {
	  if ( enabled ) {
	      if (gripperSolenoidCounter < SOLENOID_HIT_COUNT) {
	          gripperSolenoidCounter += 1;
	          if (gripperState == GripperState.kOpen) {
	              gripperSolenoid.set(DoubleSolenoid.Value.kForward);
	          } else if (gripperState == GripperState.kClose) {
	              gripperSolenoid.set(DoubleSolenoid.Value.kReverse);
	          } else {
	              gripperSolenoid.set(DoubleSolenoid.Value.kOff);
	          }
	      }		  
	  }
	}
	
    public void setGripperState(GripperState state) {
	    if (state != gripperState) {
	      gripperSolenoidCounter = 0;
	      gripperState = state;
	    }
	}
	public void open() {
		setGripperState(GripperState.kOpen);	    	
	}
	
	public void close() {
		setGripperState(GripperState.kClose);	    	
	}
	
	@Override
	public SubsystemStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
