package frc.robot.subsystems;



public class BrakeStatus implements SubsystemStatus{

    private BrakeSubsystem.BrakeState state;

    public BrakeStatus( BrakeSubsystem.BrakeState state) {
    	this.state = state;
    }

    public boolean isDeployed(){
        return state == BrakeSubsystem.BrakeState.kOut;
    }
    
    public boolean isRetracted(){
        return state == BrakeSubsystem.BrakeState.kIn;
    }	    
}
