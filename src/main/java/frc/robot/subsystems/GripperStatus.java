package frc.robot.subsystems;



public class GripperStatus implements SubsystemStatus{

    private GripperSubsystem.GripperState state;

    public GripperStatus ( GripperSubsystem.GripperState state) {
    	this.state = state;
    }

    public boolean isClawOpen(){
        return state == GripperSubsystem.GripperState.kOpen;
    }
    
    public boolean isClawClosed(){
        return state == GripperSubsystem.GripperState.kClose;
    }	    
}
