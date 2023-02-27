package frc.robot.pose;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.commands.instructions.AlignmentInstruction;

/**
 * An alignment solution should include everything we need if we
 * wanted to execute a fully autonomous score from our current position.
 * 
 * The AlignmentStrategy is how we'd pull it off, and the rest of the data
 * should be the necessary details
 * 
 * Note: we also store a reference to the RobotPose we were given 
 * as the basis for our calculations, and the scoring node we are going after
 * @author dcowden
 */
public class AlignmentSolution {
    
	private double alignAngle = 0.0;

	public double getAlignAngle() {
		return alignAngle;
	}

	public void setAlignAngle(double alignAngle) {
		this.alignAngle = alignAngle;
	}
  
}
