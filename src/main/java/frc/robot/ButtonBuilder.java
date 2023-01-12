package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.List;

import java.util.ArrayList;

/**
 * @author Peter Labadorf
 *
 * {
 * @summary This class reduces boilerplate button managment code in the operator
 * interface implementation by building and keeping references to all the
 * buttons on the joystick.}
 *
 */
class JoystickButtonManager {

    private List<JoystickButton> buttons = new ArrayList<>();
    private Joystick joystick;

    public JoystickButtonManager(Joystick joystick) {
        this.joystick = joystick;
    }

    public BuilderWithoutHandlers addButton(int port) {
        return new Builder(new JoystickButton(joystick, port));
    }

    public interface BuilderWithoutHandlers {

        public Builder whenPressed(Command command);

        public Builder whenReleased(Command command);

        public Builder whileHeld(Command command);
        
        public Builder whileHeldContinuous(Command command);
        
    }

    public class Builder implements BuilderWithoutHandlers {

        private JoystickButton buttonBeingBuilt;

        public Builder(JoystickButton button) {
            buttonBeingBuilt = button;
        }

        @Override
        public Builder whenPressed(Command command) {
            buttonBeingBuilt.onTrue(command);
            return this;
        }

        @Override
        public Builder whenReleased(Command command) {
            if (null != buttonBeingBuilt) {
                buttonBeingBuilt.onFalse(command);
            }
            return this;
        }

        @Override
        public Builder whileHeld(Command command) {
            buttonBeingBuilt.whileTrue(command);
            return this;
        }
        
        public JoystickButton add() {
            buttons.add(buttonBeingBuilt);
            return buttonBeingBuilt;
        }

        @Override
        public Builder whileHeldContinuous(Command command) {
            buttonBeingBuilt.whileTrue(command);
            return this;
        }

    }
}
