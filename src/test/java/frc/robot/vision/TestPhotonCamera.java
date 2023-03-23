package frc.robot.vision;

import org.junit.jupiter.api.Test;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Transform3d;
import frc.robot.RobotConstants;
import frc.robot.util.PoseUtil;

public class TestPhotonCamera {

	private static PhotonCamera camera;
	private static Transform3d ROBOT_TO_CAM = PoseUtil.robotToCameraTransform3d();	
	
	@Test
	public void testRunningCamera() {
		camera = new PhotonCamera(RobotConstants.VISION.PHOTON_HOST);
		while ( true) {
			PhotonPipelineResult r = camera.getLatestResult();
			PhotonTrackedTarget ptt = r.getBestTarget();
			if ( ptt != null ) {

				System.out.println(ptt.getBestCameraToTarget().getX());
			}
			else {
				System.out.println("No Target");
			}
			Thread.sleep(1000);
		}		
	}

}
