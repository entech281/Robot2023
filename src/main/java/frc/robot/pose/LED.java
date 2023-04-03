package frc.robot.pose;

import edu.wpi.first.wpilibj.util.Color;

public class LED {

    private int ledNumber;
    private Color ledColor;
    
    public LED(int ledNumber, Color ledColor) {
        this.ledNumber = ledNumber;
        this.ledColor = ledColor;
    }

    public int getLEDNumber() {
        return ledNumber;
    }

    public Color ledColor() {
        return ledColor;
    }
}
