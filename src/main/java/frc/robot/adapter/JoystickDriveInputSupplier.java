package frc.robot.adapter;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.filters.DriveInput;

public class JoystickDriveInputSupplier implements Supplier<DriveInput>{
	
	private Joystick joystick;
	
	public JoystickDriveInputSupplier(Joystick joystick) {
			this.joystick = joystick;
	}

	@Override
	public DriveInput get() {
		return new DriveInput(-joystick.getY(),joystick.getX(), joystick.getZ() );
	}

}
