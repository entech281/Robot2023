package frc.robot.util;

public class CrosshairController {
    protected double p;
    protected double rammingPrecent = 0.7;
    protected double maxSpeed = 1;
    protected double minSpeed = 0;
    
     public CrosshairController(double pGain) {
        p = pGain;
     }

     public double getP() {
         return p;
     }

     public void setP(double p) {
      this.p = p;
     }

     public double calculate() {
        return 0.0;
     }

     public void reset() {
        
     }
}
