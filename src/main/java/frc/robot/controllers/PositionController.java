package frc.robot.controllers;

import frc.robot.controllers.SparkMaxPositionController.HomingState;

public interface PositionController {

	double getActualPosition();
	double getRequestedPosition();
	boolean isAtRequestedPosition();
	void update();
	void requestPosition(double requestedPosition);
	boolean isAtLowerLimit();
	boolean isAtUpperLimit();
	boolean isHomed();

	HomingState getMotionState();

}