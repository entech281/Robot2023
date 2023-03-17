package frc.robot.adapter;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.NavXSubSystem;

public class JoystickDriveInputSupplier implements Supplier<DriveInput>{
	
	private Joystick joystick;
    private NavXSubSystem navx;
	
	public JoystickDriveInputSupplier(Joystick joystick, NavXSubSystem navx) {
		this.joystick = joystick;
        this.navx = navx;
	}

	@Override
	public DriveInput get() {
		return new DriveInput(-joystick.getY(),joystick.getX(), joystick.getZ(), navx.getYaw() );
	}

}
