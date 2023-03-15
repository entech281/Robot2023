package frc.robot.learning;

public class TestInnerMethodScoping {

	
	protected double A_LITTLE = 1.1;
	private static TestInnerMethodScoping my_instance = new TestInnerMethodScoping();
	public static Kindas KINDAS = my_instance.new Kindas();
	
	public double giveOne() {
		return 1.0;
	}
	
	public double giveTwo() {
		return 2.0;
	}
	
	public double giveThree() {
		return 3.0;
	}
	
	public double kindaGiveOne() {
		return 1.0 + A_LITTLE;
		
	}
	
	public double kindaGiveTwo() {
		return giveTwo() + A_LITTLE;
	}
	
	public class Kindas {
		
		public  double giveThree() {
			return giveThree() + A_LITTLE;
		}
	}
	
}
