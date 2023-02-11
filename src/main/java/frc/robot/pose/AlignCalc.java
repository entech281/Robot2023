package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.filters.DriveInput;

public class AlignCalc {


    public AlignmentSolution calculateSolution( TargetNode tn, RobotPose rp){
        return new AlignmentSolution();
    }    
    
    static public DriveInput CalculateDrive(Pose2d currentRobotPose, Pose2d finalRobotPose) {
        DriveInput fakeJoystick;
        fakeJoystick = new DriveInput(0.,0.,0.);

        fakeJoystick.setRotation(scaleToJoystickValues(currentRobotPose.getRotation().getDegrees() - finalRobotPose.getRotation().getDegrees(), 40));
        fakeJoystick.setForward(scaleToJoystickValues(currentRobotPose.getX() - finalRobotPose.getX(), 24));
        fakeJoystick.setRight(scaleToJoystickValues(currentRobotPose.getY() - finalRobotPose.getY(), 24));
        return fakeJoystick;
    }

    private static double scaleToJoystickValues(double value, double value_for_max) {
        double axis = value/value_for_max;
        if (axis > 1.0) axis = 1.0;
        if (axis < -1.0) axis = -1.0;
        return axis;
    }

    public double AlignToNode() {
        AprilTagLocation aprilTagLocation = AprilTagLocation.findFromTag(1);
        TargetNode selectedNode = TargetNode.A1;

        Double robotToNodeX = selectedNode.getXIn() + aprilTagLocation.getXIn();
        Double robotToNodeY = selectedNode.getYIn() - aprilTagLocation.getYIn();
        Double turnAngle = Math.atan2(robotToNodeY,robotToNodeX);

        return turnAngle;
    }
    
}