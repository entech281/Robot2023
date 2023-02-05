/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frc.robot.pose.instructions;

/**
 *
 * @author dcowden
 */
public class RotateInstruction implements AlignmentInstruction{
    public RotateInstruction(double degreesToRotate){
        this.degreesToRotate = degreesToRotate;
    }
    public double getDegressToRotate(){
        return degreesToRotate;
    }
    private double degreesToRotate = 0.0;
}
