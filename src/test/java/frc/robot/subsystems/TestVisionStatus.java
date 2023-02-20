package frc.robot.subsystems;



import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import frc.robot.pose.AprilTagLocation;
import frc.robot.pose.RecognizedAprilTagTarget;

public class TestVisionStatus {

	
	@Test
	public void testVisionStatusReturnsBestTargetEvenWhennotSet() {
		VisionStatus vs = new VisionStatus();
		vs.setLatency(2.2);
		vs.setPhotonEstimatedPose(new Pose3d());
		vs.addRecognizedTarget(new RecognizedAprilTagTarget(new Transform3d(), AprilTagLocation.BLUE_LEFT));
		String s = vs + "";
		System.out.println(s);
		
		//note: we did not call setbesttarget. it should return the first item in the list, if a best was not set
		assertNotNull( vs.getBestAprilTagTarget());
	}
	
}
