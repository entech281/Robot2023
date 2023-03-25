package frc.robot.util;

import java.util.function.BooleanSupplier;

/**
 * 
 * 
 * The Crosshair controller is a three stage movement controller for high precision no over shoot moves.<br>
 * Stage 1 ("Ramming"): run at maxSpeed until error is rammingPrecent less than startingError.<br>
 * Stage 2 ("Porportinal"): run at pGain * error (or minSpeed to prevent the output from being under powered) until threshold is reached.<br>
 * Stage 3 ("Conditional"): run at minSpeed until all added conditionals have been met.<br>
 * Ensure minSpeed has the correct signage to move towards the target conditions in stage 3.<br>
 * Also ensure that stage 1 & 2 unshoot the target position with the conditionals.
 * 
 * @apiNote It is important to note that Stage 1 & 2 should under shoot the desired position and the conditions should confirm that it has reached its final destination. 
 * Also, minSpeed should have proper signage to move in the direction towards your conditions true.
 * @version 1.1
 * @author aheitkamp
 */
public class CrosshairController {
   BooleanSupplier[] conditions;
   protected double pGain = 0;
   protected double rammingPrecent = 0.7;
   protected double threshold = 0.01;
   protected double maxSpeed = 1;
   protected double minSpeed = 0;
   protected double startingError = 0;
   protected double error = 0;

   /**
    * 
    *
    * Creates a new CrosshairController
    */
   public CrosshairController() {}

   /**
    * 
    *
    * Creates a new CrosshairController
    * @param pGain the porportinal gain for stage 2
    */
   public CrosshairController(double pGain) {
      this.pGain = pGain;
   }

   /**
    * 
    *
    * Creates a new CrosshairController
    * @param pGain the porportinal gain for stage 2
    * @param minSpeed the minimum moving speed
    * @param maxSpeed the maximum moving speed
    */
   public CrosshairController(double pGain, double minSpeed, double maxSpeed) {
      this.pGain = pGain;
      this.minSpeed = minSpeed;
      this.maxSpeed = maxSpeed;
   }

   /**
    * 
    *
    * Creates a new CrosshairController
    * @param pGain the porportinal gain for stage 2
    * @param rammingPrecent the precent of error corrected by the ramming phase
    */
   public CrosshairController(double pGain, double rammingPrecent) {
      this.pGain = pGain;
      this.rammingPrecent = rammingPrecent;
   }

   /**
    * 
    *
    * Creates a new CrosshairController
    * @param pGain the porportinal gain for stage 2
    * @param minSpeed the minimum moving speed
    * @param maxSpeed the maximum moving speed
    * @param rammingPrecent the precent of error corrected by the ramming phase
    */
   public CrosshairController(double pGain, double minSpeed, double maxSpeed, double rammingPrecent) {
      this.pGain = pGain;
      this.minSpeed = minSpeed;
      this.maxSpeed = maxSpeed;
      this.rammingPrecent = rammingPrecent;
   }

   /**
    * 
    *
    * Creates a new CrosshairController
    * @param pGain the porportinal gain for stage 2
    * @param minSpeed the minimum moving speed
    * @param maxSpeed the maximum moving speed
    * @param rammingPrecent the precent of error corrected by the ramming phase
    * @param startingError the amount of error to the target when starting
    */
   public CrosshairController(double pGain, double minSpeed, double maxSpeed, double rammingPrecent, double startingError) {
      this.pGain = pGain;
      this.minSpeed = minSpeed;
      this.maxSpeed = maxSpeed;
      this.rammingPrecent = rammingPrecent;
      this.startingError = startingError;
   }

   /**
    * 
    *
    * @return has the error passed the threshold to move to stage 3
    */
   protected boolean hasTraveled() {
      return Math.abs(error) <= Math.abs(threshold);
   }

   public double calculate(double error) {
      this.error = error;
      if (!hasTraveled()) {
         double calculated = (-Math.abs(maxSpeed)) * error / Math.abs(error);
         if (isPastRammingPrecent()) {
            calculated = error * -pGain;
            if (Math.abs(calculated) >= Math.abs(maxSpeed)) {
               calculated = (-Math.abs(maxSpeed)) * error / Math.abs(error);
            }
            if (Math.abs(calculated) <= Math.abs(minSpeed)) {
               calculated = (-Math.abs(minSpeed)) * error / Math.abs(error);
            }
         }
         return calculated;
      } else {
         if (areConditionsMet()) {
            return 0.0;
         }
         return minSpeed;
      }
   }

   /**
    * 
    *
    * @return are all conditions add true to finnish the move
    */
   protected boolean areConditionsMet() {
      if (conditions == null) {
         return true;
      }
      for (BooleanSupplier condition: conditions) {
         if (condition.getAsBoolean()) {
            continue;
         } else {
            return false;
         }
      }
      return true;
   }

   /**
    * 
    *
    * @return has the error been lowered enough to enter stage 2
    */
   protected boolean isPastRammingPrecent() {
      return Math.abs(error) <= (Math.abs(startingError) - (Math.abs(startingError) * Math.abs(rammingPrecent)));
   }

   /**
    * 
    *
    * sets the {@value startingError} and the stored {@value error} to 0
    */
   public void reset() {
      startingError = 0;
      error = 0;
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

   public double getPGain() {
      return pGain;
   }

   public void setPGain(double pGain) {
      this.pGain = pGain;
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

   /**
    * 
    *
    * Adds a condition to the conditional array for stage 3
    * @param condition
    */
   public void addCondition(BooleanSupplier condition) {
      if (conditions == null) {
         conditions = new BooleanSupplier[] { () -> condition.getAsBoolean() };
      } else {
         BooleanSupplier[] newArray = new BooleanSupplier[conditions.length + 1];
         for (int i = 0; i < conditions.length; i++) {
            newArray[i] = conditions[i];
         }
         newArray[conditions.length] = condition;
         conditions = newArray;
      }
   }

   /**
    * 
    *
    * @return has the controller completed its move
    */
   public boolean isFinnished() {
      return areConditionsMet() && hasTraveled();
   }
}
