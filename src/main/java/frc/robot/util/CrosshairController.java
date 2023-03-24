package frc.robot.util;

import java.util.function.Supplier;

public class CrosshairController {
   Supplier<Boolean>[] conditions;
   protected double p = 0;
   protected double rammingPrecent = 0.7;
   protected double maxSpeed = 1;
   protected double minSpeed = 0;
   protected double startingError = 0;

   public CrosshairController(double pGain) {
      p = pGain;
   }

   public CrosshairController(double pGain, double minSpeed, double maxSpeed) {
      p = pGain;
      this.minSpeed = minSpeed;
      this.maxSpeed = maxSpeed;
   }

   public CrosshairController(double pGain, double rammingPrecent) {
      p = pGain;
      this.rammingPrecent = rammingPrecent;
   }

   public CrosshairController(double pGain, double minSpeed, double maxSpeed, double rammingPrecent) {
      p = pGain;
      this.minSpeed = minSpeed;
      this.maxSpeed = maxSpeed;
      this.rammingPrecent = rammingPrecent;
   }

   public CrosshairController(double pGain, double minSpeed, double maxSpeed, double rammingPrecent, double startingError) {
      p = pGain;
      this.minSpeed = minSpeed;
      this.maxSpeed = maxSpeed;
      this.rammingPrecent = rammingPrecent;
      this.startingError = startingError;
   }

   public double calculate(double error) {
      double calculated = maxSpeed;
      if (isPastRammingPrecent(error)) {
         calculated = Math.max(error * p, minSpeed);
      }
      return calculated;
   }

   protected boolean isPastRammingPrecent(double error) {
      return error <= (startingError - (startingError * rammingPrecent));
   }

   public void reset() {
      startingError = 0;
   }

   public double getRammingPrecent() {
      return this.rammingPrecent;
   }

   public void setRammingPrecent(double rammingPrecent) {
      this.rammingPrecent = rammingPrecent;
   }

   public double getMaxSpeed() {
      return this.maxSpeed;
   }

   public void setMaxSpeed(double maxSpeed) {
      this.maxSpeed = maxSpeed;
   }

   public double getMinSpeed() {
      return this.minSpeed;
   }

   public void setMinSpeed(double minSpeed) {
      this.minSpeed = minSpeed;
   }

   public double getP() {
      return p;
   }

   public void setP(double p) {
      this.p = p;
   }

   public double getStartingError() {
      return this.startingError;
   }

   public void setStartingError(double startingError) {
      this.startingError = startingError;
   }

}
