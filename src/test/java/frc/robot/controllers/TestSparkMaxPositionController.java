package frc.robot.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;

import frc.robot.controllers.SparkMaxPositionController.MotionState;

public class TestSparkMaxPositionController {

	final double HOMING_SPEED=0.33;
	final double POSITION_TOLERANCE = 2;
	final double LOWER_LIMIT = 30;
	final double UPPER_LIMIT = 800;
	final double REQUESTED_POSITION = 500;	
	final double COMPARE_TOLERANCE = 0.00001;
	protected PositionControllerConfig config;
	protected SparkMaxPositionController c;
	protected CANSparkMax mockMotor;
	protected MockLimitSwitch fakeUpperLimit = new MockLimitSwitch();
	protected MockLimitSwitch fakeLowerLimit = new MockLimitSwitch();
	protected MockRevEncoder encoder = new MockRevEncoder();	

	@BeforeEach
	public void setupMockController() {
		config = new PositionControllerConfig.Builder("test")
				.withHomingOptions(HOMING_SPEED)
				.withPositionTolerance(POSITION_TOLERANCE)
				.withInverted(false)
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
	public void testInvertedAxis() {
		//in an inverted test, the encoder reads backwards, 
		//and the limit switches are reversed
		
		PositionControllerConfig config2 = new PositionControllerConfig.Builder("test")
				.withHomingOptions(HOMING_SPEED)
				.withPositionTolerance(POSITION_TOLERANCE)
				.withInverted(true)
				.withSoftLimits(LOWER_LIMIT, UPPER_LIMIT).build();

		CANSparkMax mockMotor2 = mock(CANSparkMax.class, Mockito.RETURNS_DEEP_STUBS);
		
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
		SparkMaxPositionController sm = new SparkMaxPositionController(mockMotor2,config2,lowerLimit, upperLimit,encoder);
		
		sm.requestPosition(REQUESTED_POSITION);
		sm.update();
		assertEquals(REQUESTED_POSITION,sm.getRequestedPosition());
		assertPositionAndState(sm,0,MotionState.FINDING_LIMIT);
		
		//positive motor speed, opposite from the usual direction
		verify(mockMotor2, Mockito.atLeastOnce()).set(HOMING_SPEED);				

		//lowerLimit should do nothing, since they are reversed
		fakeLowerLimit.setPressed(true);
		sm.update();
		assertEquals(MotionState.FINDING_LIMIT, sm.getMotionState());
		
		//upperLimit should trigger home
		fakeUpperLimit.setPressed(true);
		sm.update();
		sm.update();
		assertEquals(MotionState.HOMED, sm.getMotionState());
		
		//lower limit should be negative, vs positive
		assertEquals(encoder.getPosition(),-LOWER_LIMIT, COMPARE_TOLERANCE);
		
		//should be heading to negative reference
		verify(mockMotor2.getPIDController()).setReference((double)-REQUESTED_POSITION,CANSparkMax.ControlType.kPosition);
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
		verify(mockMotor).set(-HOMING_SPEED);
		
		assertEquals(MotionState.FINDING_LIMIT, c.getMotionState());
		
		assertFalse(c.isAtLowerLimit());
		assertFalse(c.isAtUpperLimit());
		c.update();
		c.update();
		assertEquals(MotionState.FINDING_LIMIT, c.getMotionState());
		
		
		fakeLowerLimit.setPressed(true); 
		c.update(); 
		c.update();
		assertTrue(c.isAtLowerLimit());

		verify(mockMotor.getPIDController()).setReference((double)REQUESTED_POSITION,CANSparkMax.ControlType.kPosition);
		assertEquals(MotionState.HOMED, c.getMotionState());		
		assertEquals(LOWER_LIMIT, c.getActualPosition());
		
		assertFalse(c.isAtRequestedPosition());	
		assertEquals(REQUESTED_POSITION, c.getRequestedPosition());

		fakeLowerLimit.setPressed(false);
		c.update();
		c.update();
		assertLimits(c,false,false);
		assertFalse(c.isAtRequestedPosition());  //the users' requested position is still REQUESTED_POSITION
		assertFalse(c.isAtLowerLimit());
		assertEquals(MotionState.HOMED, c.getMotionState());
		assertEquals(LOWER_LIMIT, c.getActualPosition());

	}


	protected void assertLimits(SparkMaxPositionController c,  boolean expectedUpperLimit, boolean expectedLowerLimit) {
		assertEquals(expectedLowerLimit,c.isAtLowerLimit());
		assertEquals(expectedUpperLimit,c.isAtUpperLimit());
	}

	protected void assertPositionAndState(SparkMaxPositionController c, int expectedPosition, MotionState expectedState) {
		assertEquals(expectedPosition,c.getActualPosition(),COMPARE_TOLERANCE);		
		assertEquals(expectedState,c.getMotionState());
	}		

}
