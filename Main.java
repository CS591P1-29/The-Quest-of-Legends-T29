import java.util.Scanner;



public class Main {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		
		
		
		Simulation theQuest = new Simulation(scan);
		theQuest.initialize(); // Pre-process all data
		
		theQuest.simulate();
		
		
		scan.close();
	}
	

	public void init() {
		
	}
	
}
