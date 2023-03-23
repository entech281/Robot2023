package frc.robot.vision;

import org.junit.jupiter.api.Test;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.math.WPIMathJNI;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.util.CombinedRuntimeLoader;
import edu.wpi.first.util.WPIUtilJNI;
import frc.robot.RobotConstants;
import frc.robot.util.PoseUtil;

public class TestPhotonCamera {

	private static PhotonCamera camera;
	private static Transform3d ROBOT_TO_CAM = PoseUtil.robotToCameraTransform3d();	
	
	@Test
	public void testRunningCamera() throws Exception{
		
//        NetworkTablesJNI.Helper.setExtractOnStaticLoad(false);
//        WPIUtilJNI.Helper.setExtractOnStaticLoad(false);
//        WPIMathJNI.Helper.setExtractOnStaticLoad(false);
//        CameraServerJNI.Helper.setExtractOnStaticLoad(false);
//        CombinedRuntimeLoader.loadLibraries(TestPhotonCamera.class, "wpiutiljni", "wpimathjni", "ntcorejni",
//                "cscorejnicvstatic");		
        NetworkTableInstance nt = NetworkTableInstance.create();
        nt.setServer("10.2.81.99");
        nt.startClient4("CAMERATEST");

		camera = new PhotonCamera(nt,"OV5647");
		
		while ( true) {
			PhotonPipelineResult r = camera.getLatestResult();
			if ( r.hasTargets()) {
				PhotonTrackedTarget ptt = r.getBestTarget();
				if ( ptt != null ) {

					System.out.println(ptt.getBestCameraToTarget().getY());
				}
				else {
					System.out.println("No Target");
				}								
			}
			else {
				System.out.println("No Targets");
			}

			Thread.sleep(1000);
		}		
	}

}
