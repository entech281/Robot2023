package frc.robot.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import frc.robot.controllers.SparkMaxPositionController.MotionState;

public class TestSparkMaxPositionController {

	final double HOMING_SPEED=33.0;
	final int BACKOFF_COUNTS = 10;
	final int HOME_COUNTS = 20;
	final int POSITION_TOLERANCE = 2;
	final int LOWER_LIMIT = 30;
	final int UPPER_LIMIT = 800;
	final int REQUESTED_POSITION = 500;	
	
	protected PositionControllerConfig config;
	protected SparkMaxPositionController c;
	protected CANSparkMax mockMotor;
	

	@BeforeEach
	public void setupMockController() {
		config = new PositionControllerConfig.Builder("test")
				.withHomingOptions(HOMING_SPEED , BACKOFF_COUNTS, HOME_COUNTS)
				.withPositionTolerance(POSITION_TOLERANCE)
				.withReversed(false)
				.withSoftLimits(LOWER_LIMIT, UPPER_LIMIT).build();

		mockMotor = createMockSparkMax();
		c = new SparkMaxPositionController(mockMotor,config);
		c.setEnabled(true);		
	}
	
	@Test
	public void testSetupInitiallyUninitialized(){

		assertLimits(c,false,false);
		assertPositionAndState(c,0,MotionState.UNINITIALIZED);
	}
	
	@Test
	public void testCommandOutOfLimitsResultsInNoMovement() {
		assertPositionAndState(c,0,MotionState.UNINITIALIZED);
		c.requestPosition(UPPER_LIMIT + UPPER_LIMIT);
		c.update();
		assertPositionAndState(c,0,MotionState.UNINITIALIZED);
	}	
	
	@Test
	public void testPositionRequestResultsInHoming() throws Exception{

		assertEquals(MotionState.UNINITIALIZED, c.getMotionState());
		assertFalse(c.isAtLowerLimit());
		assertFalse(c.isAtUpperLimit());

		//requesting position should start  homing before moving to the requested counts
		c.requestPosition(REQUESTED_POSITION);
		verify(mockMotor).set(HOMING_SPEED);
		
		assertEquals(MotionState.FINDING_LIMIT, c.getMotionState());
		
		assertFalse(c.isAtLowerLimit());
		assertFalse(c.isAtUpperLimit());
		c.update();
		c.update();
		assertEquals(MotionState.FINDING_LIMIT, c.getMotionState());
		
		
		//as soon as we hit the limit, we will back off of the limit switch and move away BACKCOFF_CONTS 
		lowerLimit.setPressed(true); 
		c.update(); 
		assertTrue(lowerLimit.isPressed());
		assertTrue(c.isAtLowerLimit());
		assertEquals(0, c.getActualPosition());
		verify(mockMotor.getPIDController()).setReference((double)BACKOFF_COUNTS,CANSparkMax.ControlType.kPosition);
		
		assertEquals(MotionState.BACKING_OFF, c.getMotionState());
		assertFalse(c.isAtRequestedPosition());	
		assertEquals(REQUESTED_POSITION, c.getRequestedPosition());

		//once to BACKOFF_COUNTS -1, we should be marked HOME. position should be HOME_COUNTS
		lowerLimit.setPressed(false);
		encoder.setPosition(BACKOFF_COUNTS-1);
		c.update();
		assertLimits(c,false,false);
		assertFalse(c.isAtRequestedPosition());  //the users' requested position is still REQUESTED_POSITION
		assertPositionAndState(c,HOME_COUNTS,MotionState.HOMED);

	}


	protected void assertLimits(SparkMaxPositionController c,  boolean expectedUpperLimit, boolean expectedLowerLimit) {
		assertEquals(expectedLowerLimit,c.isAtLowerLimit());
		assertEquals(expectedUpperLimit,c.isAtUpperLimit());
	}

	protected void assertPositionAndState(SparkMaxPositionController c, int expectedPosition, MotionState expectedState) {
		assertEquals(expectedPosition,c.getActualPosition());		
		assertEquals(expectedState,c.getMotionState());
	}		
	
	
	protected MockLimitSwitch upperLimit = new MockLimitSwitch();
	protected MockLimitSwitch lowerLimit = new MockLimitSwitch();
	protected MockRevEncoder encoder = new MockRevEncoder();
	
	protected CANSparkMax createMockSparkMax() {
		CANSparkMax fakeMotor = mock(CANSparkMax.class, Mockito.RETURNS_DEEP_STUBS);				
		when(fakeMotor.getEncoder()).thenReturn(encoder);
		when(fakeMotor.getForwardLimitSwitch(Type.kNormallyOpen).isPressed()).thenAnswer(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				return upperLimit.isPressed();
			}
			
		});
		when(fakeMotor.getReverseLimitSwitch(Type.kNormallyOpen).isPressed()).thenAnswer(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				return lowerLimit.isPressed();
			}
			
		});

		return fakeMotor;
	}

	
	
}
