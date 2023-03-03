package frc.robot.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;

import frc.robot.controllers.SparkMaxPositionController.MotionState;

public class TestSparkMaxPositionController {

	final double HOMING_SPEED=0.33;
	final double BACKOFF = 0.01;
	final double HOME = 20;
	final double POSITION_TOLERANCE = 2;
	final double LOWER_LIMIT = 30;
	final double UPPER_LIMIT = 800;
	final double REQUESTED_POSITION = 500;	
	
	protected PositionControllerConfig config;
	protected SparkMaxPositionController c;
	protected CANSparkMax mockMotor;
	protected MockLimitSwitch fakeUpperLimit = new MockLimitSwitch();
	protected MockLimitSwitch fakeLowerLimit = new MockLimitSwitch();
	protected MockRevEncoder encoder = new MockRevEncoder();	

	@BeforeEach
	public void setupMockController() {
		config = new PositionControllerConfig.Builder("test")
				.withHomingOptions(HOMING_SPEED , BACKOFF, HOME)
				.withPositionTolerance(POSITION_TOLERANCE)
				.withSoftLimits(LOWER_LIMIT, UPPER_LIMIT).build();


		mockMotor = mock(CANSparkMax.class, Mockito.RETURNS_DEEP_STUBS);
		
		SparkMaxLimitSwitch lowerLimit = mock(SparkMaxLimitSwitch.class);
		SparkMaxLimitSwitch upperLimit = mock(SparkMaxLimitSwitch.class);
		
		when(lowerLimit.isPressed()).thenAnswer(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				return fakeLowerLimit.isPressed();
			}
			
		});
		when(upperLimit.isPressed()).thenAnswer(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				return fakeUpperLimit.isPressed();
			}
			
		});		

		c = new SparkMaxPositionController(mockMotor,config,lowerLimit, upperLimit,encoder);
	
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
		verify(mockMotor).set(0);
		
		assertEquals(MotionState.FINDING_LIMIT, c.getMotionState());
		
		assertFalse(c.isAtLowerLimit());
		assertFalse(c.isAtUpperLimit());
		c.update();
		c.update();
		assertEquals(MotionState.FINDING_LIMIT, c.getMotionState());
		
		
		//as soon as we hit the limit, we will back off of the limit switch and move away BACKCOFF_CONTS 
		fakeLowerLimit.setPressed(true); 
		c.update(); 
		assertTrue(c.isAtLowerLimit());
		assertEquals(0, c.getActualPosition());
		verify(mockMotor.getPIDController()).setReference((double)BACKOFF,CANSparkMax.ControlType.kPosition);
		
		assertEquals(MotionState.BACKING_OFF, c.getMotionState());
		assertFalse(c.isAtRequestedPosition());	
		assertEquals(REQUESTED_POSITION, c.getRequestedPosition());

		//once to BACKOFF_COUNTS -1, we should be marked HOME. position should be HOME_COUNTS
		fakeLowerLimit.setPressed(false);
		encoder.setPosition(BACKOFF-1);
		c.update();
		assertLimits(c,false,false);
		assertFalse(c.isAtRequestedPosition());  //the users' requested position is still REQUESTED_POSITION
		assertFalse(c.isAtLowerLimit());
		assertEquals(MotionState.HOMED, c.getMotionState());
		assertEquals(HOME, c.getActualPosition());

	}


	protected void assertLimits(SparkMaxPositionController c,  boolean expectedUpperLimit, boolean expectedLowerLimit) {
		assertEquals(expectedLowerLimit,c.isAtLowerLimit());
		assertEquals(expectedUpperLimit,c.isAtUpperLimit());
	}

	protected void assertPositionAndState(SparkMaxPositionController c, int expectedPosition, MotionState expectedState) {
		assertEquals(expectedPosition,c.getActualPosition());		
		assertEquals(expectedState,c.getMotionState());
	}		
	
	

	
	
	
}
