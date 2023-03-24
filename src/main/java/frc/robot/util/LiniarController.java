package frc.robot.util;

public class LiniarController {
    private double pGain;
    private double rammingPrecent = 0.7;
    private double maxSpeed = 1;
    private double minSpeed = 0;
    
     public LiniarController(double pGain) {
        this.pGain = pGain;
     }

     public double calculate() {
        return 0.0;
     }

     public void reset() {
        
     }
}
