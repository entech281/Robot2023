/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frc.robot.pose.instructions;

/**
 *
 * @author dcowden
 */
public class MoveDistanceInstruction implements AlignmentInstruction{
    
    public MoveDistanceInstruction(double inchesToMove){
        this.distanceInches = inchesToMove;
    }
    private double distanceInches = 0.0;

    public double getDistanceInches() {
        return distanceInches;
    }
}
