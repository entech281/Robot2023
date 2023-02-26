package frc.robot.controllers;

import frc.robot.controllers.SparkMaxPositionController.HomingState;

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

	HomingState getHomingState();

}