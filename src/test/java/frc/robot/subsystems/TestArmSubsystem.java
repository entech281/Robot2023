package frc.robot.subsystems;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revrobotics.CANSparkMax;

public class TestArmSubsystem {

	@Test
	public void testBasicStartup() {
		ArmSubsystem as = new ArmSubsystem();
		as.initialize();
	}
	
	@Test
	public void testDeployingArmMovesElbow() {
		
		//set up the arm with motor mocks, which will 
		//track what happens
		//what we want to see is the PIDcontrollers get a deisred position update
		CANSparkMax fakeElbow = mock(CANSparkMax.class, Mockito.RETURNS_DEEP_STUBS);
		CANSparkMax fakeTelescopeMotor = mock(CANSparkMax.class, Mockito.RETURNS_DEEP_STUBS);
		ArmSubsystem as = new ArmSubsystem(fakeElbow, fakeTelescopeMotor);
		as.deployArm();
		
		verify(
				fakeElbow.getPIDController()
		).setReference(ArmSubsystem.ARM_UP_POSITION, CANSparkMax.ControlType.kPosition);
		
	}
}
