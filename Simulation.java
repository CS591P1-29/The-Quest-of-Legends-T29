import java.util.Scanner;

public abstract class Simulation implements DataInitialize {

	protected Scanner scan;
	
	public Simulation(Scanner scan) {
		this.scan = scan;
	}
	
	public void simulate() {
		
	}
	
}
