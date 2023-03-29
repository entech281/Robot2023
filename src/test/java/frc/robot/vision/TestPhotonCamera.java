package frc.robot.vision;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.math.WPIMathJNI;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.util.CombinedRuntimeLoader;
import edu.wpi.first.util.WPIUtilJNI;
import frc.robot.RobotConstants;
import frc.robot.util.PoseUtil;

public class TestPhotonCamera {

	private static PhotonCamera camera;
	private static Transform3d ROBOT_TO_CAM = PoseUtil.robotToCameraTransform3d();	
	private enum VisionMode { APRILTAG, COLOR };
	private VisionMode mode = VisionMode.COLOR;
	public static final int PIPELINE_COLOR = 0;
	public static final int PIPELINE_APRILTAG = 1;
	
	//@Test
	public void testRunningCamera() throws Exception{
		
		
        NetworkTableInstance nt = NetworkTableInstance.create();
        nt.setServer("10.2.81.99");
        nt.startClient4("TESTCLIENT");

		camera = new PhotonCamera(nt,"OV5647");
		if ( mode == VisionMode.COLOR) {
			camera.setPipelineIndex(PIPELINE_COLOR);
		}
		else {
			camera.setPipelineIndex(PIPELINE_APRILTAG);
		}
		while ( true) {

			PhotonPipelineResult r = camera.getLatestResult();
			if ( r.hasTargets()) {
				PhotonTrackedTarget ptt = r.getBestTarget();
				if ( ptt != null ) {
					
					if ( mode == VisionMode.APRILTAG) {
						processAprilTagTarget(ptt);
					}
					else {
						processConeTarget(ptt);
					}					
				}
				else {
					System.out.println("No Best Target");
				}								
			}
			else {
				System.out.println("No Targets");
			}			
			

			Thread.sleep(1000);
		}		
	}

	private void processAprilTagTarget(PhotonTrackedTarget target) {

	}
	
	private void processConeTarget(PhotonTrackedTarget target) {
		double dist = PhotonUtils.calculateDistanceToTargetMeters(
				Units.inchesToMeters(36.0),
				Units.inchesToMeters(60),
				0.0,
				Units.degreesToRadians(target.getPitch()));		
		System.out.println( averageX(target) + "," + averageY(target) + "," + Units.metersToInches(dist) );				
	}
	
	public double averageY ( PhotonTrackedTarget tt) {
		double y = 0.0;
		for ( TargetCorner tc : tt.getMinAreaRectCorners()) {
			y += tc.y;
		}
		return y/4;
	}

	public double averageX ( PhotonTrackedTarget tt) {
		double x = 0.0;
		for ( TargetCorner tc : tt.getMinAreaRectCorners()) {
			x += tc.x;
		}
		return x/4;
	}	
}
