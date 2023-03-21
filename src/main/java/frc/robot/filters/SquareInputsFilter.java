package frc.robot.filters;

public class SquareInputsFilter extends DriveInputFilter {

	@Override
	protected DriveInput doFilter(DriveInput di) {
		DriveInput newDi = new DriveInput(di);
		newDi.setForward(Math.copySign(di.getForward()* di.getForward(),di.getForward()));
		newDi.setRight(Math.copySign(di.getRight()* di.getRight(),di.getRight()));
		newDi.setRotation(Math.copySign(di.getRotation()* di.getRotation(),di.getRotation()));
		return newDi;
	}

}
