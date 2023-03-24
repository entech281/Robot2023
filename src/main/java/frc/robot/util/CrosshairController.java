package frc.robot.util;

import java.util.function.Supplier;

public class CrosshairController {
   Supplier<Boolean>[] conditions;
   protected double p = 0;
   protected double rammingPrecent = 0.7;
   protected double threshold = 0.01;
   protected double maxSpeed = 1;
   protected double minSpeed = 0;
   protected double startingError = 0;
   protected double prevError = 0;

   public CrosshairController() {}

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

   public boolean hasTraveled() {
      return prevError <= threshold;
   }

   public double calculate(double error) {
      if (!hasTraveled()) {
         double calculated = maxSpeed;
         if (isPastRammingPrecent(error)) {
            calculated = Math.max(error * p, minSpeed);
         }
         return calculated;
      } else {
         if (isConditionsMet()) {
            return 0.0;
         }
         return minSpeed;
      }
   }

   protected boolean isConditionsMet() {
      if (conditions == null) {
         return true;
      }
      for (Supplier<Boolean> condition: conditions) {
         if (condition.get()) {
            continue;
         } else {
            return false;
         }
      }
      return true;
   }

   protected boolean isPastRammingPrecent(double error) {
      return error <= (startingError - (startingError * rammingPrecent));
   }

   public void reset() {
      startingError = 0;
      prevError = 0;
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

   public double getThreshold() {
      return this.threshold;
   }

   public void setThreshold(double threshold) {
      this.threshold = threshold;
   }

   public void addCondition(Supplier<Boolean> condition) {
      if (conditions == null) {
         conditions = (Supplier<Boolean>[]) new Supplier[] { () -> condition };
      } else {
         Supplier<Boolean>[] newArray = (Supplier<Boolean>[]) new Supplier[conditions.length + 1];
         for (int i = 0; i < conditions.length; i++) {
            newArray[i] = conditions[i];
         }
         newArray[conditions.length] = condition;
      }
   }

   public boolean isFinnished() {
      return isConditionsMet() && hasTraveled();
   }
}
