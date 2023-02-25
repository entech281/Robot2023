package frc.robot.subsystems;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.PositionArmCommand;
import frc.robot.controllers.TestPositionController;

public class TestArmSubsystem {

	public static final double TOLERANCE = 0.1;
	
	protected void periodicSteps(EntechSubsystem subsystem, int steps) {
		for ( int i=0;i<steps;i++) {
			subsystem.periodic();
		}
	}
	
	
	
	@Test
	public void testArmHomes() {
		
		TestPositionController tp = new TestPositionController();
		tp.setLowerLimitTripped(false);
		tp.setUpperLimitTripped(false);
		CANSparkMax fakeArm = mock(CANSparkMax.class, Mockito.RETURNS_DEEP_STUBS);
		ArmSubsystem as = new ArmSubsystem(tp,fakeArm);
		
		periodicSteps(as,2);
		
		assertEquals(TestPositionController.POSITION_UNKNOWN, tp.getActualPosition());
		assertFalse(as.isHome(),"Should Be Homed");
		assertFalse(as.isAtDesiredPosition(), "Shouldnt be at desired position yet");
		assertTrue(as.isInMotion(), "Should Be in Motion (homing)");
		
		tp.setActualPosition(10);
		tp.setLowerLimitTripped(true);
		periodicSteps(as,2);
		
		assertTrue(as.isHome(),"Should be Homed now.");
		assertEquals(0, tp.getActualPosition(),TOLERANCE, "Actual Position should be zero");
		assertEquals(0, tp.getActualPosition(),TOLERANCE, "Current Position should be zero");
		assertFalse(as.isInMotion());
		
	}
	
	//@Test
	public void testMovingToPosition() {
		TestPositionController tp = new TestPositionController();
		tp.setLowerLimitTripped(true);
		CANSparkMax fakeArm = mock(CANSparkMax.class, Mockito.RETURNS_DEEP_STUBS);
		ArmSubsystem as = new ArmSubsystem(tp,fakeArm);		
		//a few periodic loops will mark it homed
		periodicSteps(as,2);
		
		
		assertFalse(as.isInMotion());
		assertTrue(as.isHome(),"Should Be Homed");
		
		double TARGET_POS = 20.0;
		PositionArmCommand apc  = new PositionArmCommand(as, TARGET_POS);
		apc.initialize();
		while ( ! apc.isFinished()) {
			tp.addToPosition(2);
			apc.execute();
		}
		apc.end(false);
		
		assertFalse(as.isInMotion());
		assertEquals(TARGET_POS,as.getStatus().getArmExtension(),TOLERANCE,"Arm Extension should match target");
	}
}
