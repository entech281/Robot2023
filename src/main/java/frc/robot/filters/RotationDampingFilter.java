package frc.robot.filters;

public class RotationDampingFilter extends DriveInputFilter {

	private double dampingFactor = 0.5;

	@Override
	protected DriveInput doFilter(DriveInput di) {
		DriveInput newDi = new DriveInput(di);
		newDi.setRotation(di.getRotation()*dampingFactor);
		return newDi;
	}
	
	public double getDampingFactor() {
		return dampingFactor;
	}
	public void setDampingFactor(double dampingFactor) {
		this.dampingFactor = dampingFactor;
	}	

}
