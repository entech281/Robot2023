package frc.robot.subsystems;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

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
		CANSparkMax fakeElbow = mock(CANSparkMax.class);
		CANSparkMax fakeTelescopeMotor = mock(CANSparkMax.class);
		ArmSubsystem as = new ArmSubsystem(fakeElbow, fakeTelescopeMotor);
		as.deployArm();
		
		verify(fakeElbow).set(100);
		
	}
}
