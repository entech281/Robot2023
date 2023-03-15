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

import frc.robot.RobotConstants;
import frc.robot.controllers.SparkMaxPositionController.HomingState;

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
		
		assertPositionAndState(sm,0,HomingState.FINDING_LIMIT);
		
		//positive motor speed, opposite from the usual direction
		verify(mockMotor2, Mockito.atLeastOnce()).set(HOMING_SPEED);				

		//lowerLimit should do nothing, since they are reversed
		fakeLowerLimit.setPressed(true);
		sm.update();
		assertEquals(HomingState.FINDING_LIMIT, sm.getMotionState());
		
		//upperLimit should trigger home
		fakeUpperLimit.setPressed(true);
		sm.update();
		sm.update();
		assertEquals(HomingState.HOMED, sm.getMotionState());
		
		//lower limit should be negative, vs positive
		assertEquals(-LOWER_LIMIT,encoder.getPosition(), COMPARE_TOLERANCE);
		
	}
	
	//when a position is requested, but then a command runs that sets the speed manually,
	//we should cancel the existing requested position. othrewise, we'll
	//fly back to the previously requested position
	
	@Test
	public void testUsingSpeedControlInvalidatesRequestedPosition() {
		c.requestPosition(REQUESTED_POSITION);
		c.update();
		assertPositionAndState(c,0,HomingState.FINDING_LIMIT);
		fakeLowerLimit.setPressed(true);
		c.update();
		c.update();
		assertEquals(HomingState.HOMED, c.getMotionState());

		encoder.setPosition(300);
		//on our way, we get a manual speed request
		c.setMotorSpeed(-10.0);
		assertEquals(RobotConstants.INDICATOR_VALUES.POSITION_NOT_SET, c.getRequestedPosition());
		assertFalse(c.isAtRequestedPosition());
	}
	
	@Test
	public void testSetupInitiallyUninitialized(){
		assertLimits(c,false,false);
		assertPositionAndState(c,0,HomingState.UNINITIALIZED);
	}
	
	@Test
	public void testCommandOutOfLimitsResultsInCappedValue() {
		assertPositionAndState(c,0,HomingState.UNINITIALIZED);
		c.requestPosition(UPPER_LIMIT + UPPER_LIMIT);
		c.update();
		assertPositionAndState(c,0.0,HomingState.FINDING_LIMIT);
        // TODO More mock action needed here.
	}	
	
	@Test
	public void testPositionRequestResultsInHoming() throws Exception{

		assertEquals(HomingState.UNINITIALIZED, c.getMotionState());
		assertFalse(c.isAtLowerLimit());
		assertFalse(c.isAtUpperLimit());

		//requesting position should start  homing before moving to the requested counts
		c.requestPosition(REQUESTED_POSITION);
		verify(mockMotor).set(0);
		verify(mockMotor).set(-HOMING_SPEED);
		
		assertEquals(HomingState.FINDING_LIMIT, c.getMotionState());
		
		assertFalse(c.isAtLowerLimit());
		assertFalse(c.isAtUpperLimit());
		c.update();
		c.update();
		assertEquals(HomingState.FINDING_LIMIT, c.getMotionState());
		
		
		fakeLowerLimit.setPressed(true); 
		c.update(); 
		c.update();
		assertTrue(c.isAtLowerLimit());

		assertEquals(HomingState.HOMED, c.getMotionState());		
		assertEquals(LOWER_LIMIT, c.getActualPosition());
		
		assertFalse(c.isAtRequestedPosition());	

		fakeLowerLimit.setPressed(false);
		c.update();
		c.update();
		assertLimits(c,false,false);
		assertFalse(c.isAtRequestedPosition());  //the users' requested position is still REQUESTED_POSITION
		assertFalse(c.isAtLowerLimit());
		assertEquals(HomingState.HOMED, c.getMotionState());
		assertEquals(LOWER_LIMIT, c.getActualPosition());

	}


	protected void assertLimits(SparkMaxPositionController c,  boolean expectedUpperLimit, boolean expectedLowerLimit) {
		assertEquals(expectedLowerLimit,c.isAtLowerLimit());
		assertEquals(expectedUpperLimit,c.isAtUpperLimit());
	}

	protected void assertPositionAndState(SparkMaxPositionController c, int expectedPosition, HomingState expectedState) {
		assertEquals(expectedPosition,c.getActualPosition(),COMPARE_TOLERANCE);		
		assertEquals(expectedState,c.getMotionState());
	}		

	protected void assertPositionAndState(SparkMaxPositionController c, double expectedPosition, HomingState expectedState) {
		assertEquals(expectedPosition,c.getActualPosition(),COMPARE_TOLERANCE);
		assertEquals(expectedState,c.getMotionState());
	}		

}
