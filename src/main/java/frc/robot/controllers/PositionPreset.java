package frc.robot.controllers;

public class PositionPreset {
	
	
	public PositionPreset(String name, double position) {
		this.name = name;
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPosition() {
		return position;
	}
	public void setPosition(double position) {
		this.position = position;
	}
	private String name;
	private double position;
}
