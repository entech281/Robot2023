
package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 *
 * @author dcowden
 */
public class RobotPose implements Sendable {

    private Pose2d calculatedPose;
    private NavxStatus bodyPose;
    private ArmStatus armPose;
    private VisionStatus visionPose;
    private AlignmentSolution alignmentSolution;
    
    
    public AlignmentSolution getAlignmentSolution() {
		return alignmentSolution;
	}

	public void setAlignmentSolution(AlignmentSolution alignmentSolution) {
		this.alignmentSolution = alignmentSolution;
	}

	public void setCalculatedPose(Pose2d calculatedPose) {
        this.calculatedPose = calculatedPose;
    }

    public Pose2d getCalculatedPose() {
        return calculatedPose;
    }

    public void setBodyPose(NavxStatus bodyPose) {
        this.bodyPose = bodyPose;
    }

    public NavxStatus getBodyPose(){
        return bodyPose;
    }

    public void setArmPose(ArmStatus armPose) {
        this.armPose = armPose;
    }

    public ArmStatus getArmPose(){
        return armPose;
    }

    public void setVisionPose(VisionStatus visionPose) {
        this.visionPose = visionPose;
    }

    public VisionStatus getVisionPose(){
        return visionPose;
    }

    @Override
    public void initSendable(SendableBuilder sb) {
        passSenableBuilderDownToSubOjbects(sb);
    }

    private void passSenableBuilderDownToSubOjbects( SendableBuilder sb ){
        armPose.initSendable(sb);
        visionPose.initSendable(sb);
        bodyPose.initSendable(sb);
    }

}
