import java.util.Scanner;



public class Main {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		String gameType = "";
		
		do {
			System.out.println(ZshColor.ANSI_RED + " - Which game do you wanna play? Please input 1 or 2.");
			System.out.println(ZshColor.ANSI_RED + " - 1. The Quest");
			System.out.println(ZshColor.ANSI_RED + " - 2. The Quest Of Legends" + ZshColor.ANSI_RESET);
			gameType = scan.nextLine();
			if (gameType.compareTo("1") == 0) {
				TheQuestSimulation theQuest = new TheQuestSimulation(scan);
				theQuest.initialize(); // Pre-process all data
				theQuest.simulate();
			}
			else if (gameType.compareTo("2") == 0) {
				TheQuestOfLegendsSimulation theQuestOfLegends = new TheQuestOfLegendsSimulation(scan);
				theQuestOfLegends.initialize(); // Pre-process all data
				theQuestOfLegends.simulate();
			}
		} while (gameType.compareTo("1") != 0 && gameType.compareTo("2") != 0);
		
		scan.close();
	}
	

	public void init() {
		
	}
	
}
