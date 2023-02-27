package frc.robot.controllers;

import frc.robot.controllers.SparkMaxPositionController.MotionState;

public interface PositionController {

	int getActualPosition();

	double getRequestedPosition();

	boolean isAtRequestedPosition();

	void setEnabled(boolean enabled);

	void update();

	void requestPosition(int requestedPosition);

	boolean isAtLowerLimit();

	boolean isAtUpperLimit();

	boolean inMotion();

	boolean isHomed();

	MotionState getMotionState();

}