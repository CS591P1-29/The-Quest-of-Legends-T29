import java.util.Scanner;



public class Main {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		
		
		
		/*
		TheQuestSimulation theQuest = new TheQuestSimulation(scan);
		theQuest.initialize(); // Pre-process all data
		theQuest.simulate();
		*/
		
		
		TheQuestOfLegendsSimulation theQuestOfLegends = new TheQuestOfLegendsSimulation(scan);
		theQuestOfLegends.initialize(); // Pre-process all data
		theQuestOfLegends.simulate();
		
		scan.close();
	}
	

	public void init() {
		
	}
	
}
