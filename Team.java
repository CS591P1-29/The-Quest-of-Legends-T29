import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Team {

	protected int num = 0;
	protected ArrayList<Roles> members = new ArrayList<Roles> ();
	
	public ArrayList<Roles> getMembers() {
		return this.members;
	}
	
	public int getNum() {
		return members.size();
	}
	
	public void buildTeam(Scanner scan) {
		System.out.println(ZshColor.ANSI_RED + " - Here are the lists: " + ZshColor.ANSI_RESET);
		int idx = 0;
		for (Roles role: Heroes.heroes) {
			idx ++;
			System.out.println("Index: " + idx + "   " + role.toString());
		}
		System.out.println(ZshColor.ANSI_RED + " - Please select " + num + " heros.");
		System.out.println(" - Just enter the indexes like 3 6, which means you select the 3rd and 6th heroes." + ZshColor.ANSI_RESET);
		
		ArrayList<Integer> alist = new ArrayList<Integer> ();
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer> ();
		boolean completeReading = false;
		do {
			try {
				alist.clear();
				map.clear();
				for (int i = 0; i < num; i ++) {
					idx = scan.nextInt();
					alist.add(idx - 1);
				}
				boolean hasReplicate = false;
				for (int i = 0; i < num; i ++) {
					if (map.get(alist.get(i)) == null && 0 <= alist.get(i) && alist.get(i) < Heroes.heroes.size()) {
						map.put(alist.get(i), 1);
					}
					else {
						hasReplicate = true;
					}
				}
				if (hasReplicate) {
					System.out.println(ZshColor.ANSI_RED + " - One hero can only be select once in one team.\n" 
							 + " - Index should be valid." + ZshColor.ANSI_RESET);
				}
				else {
					completeReading = true;
				}
			} catch (Exception e) {
				System.out.println(ZshColor.ANSI_RED + " - Invalid input!" + ZshColor.ANSI_RESET);
				scan.nextLine();
			}
		} while (!completeReading);
		for (int i = 0; i < num; i ++) {
			members.add(Heroes.heroes.get(alist.get(i)));
		}
		scan.nextLine();
	}
	
	public void changeWeapon(Scanner scan, int params) {
		int idx_1 = 0;
		if (params == -1) {
			/*
			 * The Quest
			 */
			System.out.println(ZshColor.ANSI_BLUE + this.toString());
			do {
				System.out.println(ZshColor.ANSI_RED + " - Whose equipped weapon would you like to change? Please answer his index: " + ZshColor.ANSI_RESET);
				try {
					idx_1 = scan.nextInt();
				} catch (Exception e) {
					System.out.println(ZshColor.ANSI_RED + " - Invalid input!");
					scan.nextLine();
				}
			} while (idx_1 <= 0 || idx_1 > getNum());
			scan.nextLine();
		}
		else {
			/*
			 *  The Quest Of Legends
			 */
			idx_1 = params;
		}
		
		Backpack backpack = ((Heroes) members.get(idx_1 - 1)).getBackpack();
		if (backpack.getWeapons().size() == 0) {
			System.out.println(ZshColor.ANSI_RED + " - Hey. This guy has no weapons.");
		}
		else if (backpack.getWeapons().size() == 1 && ((Heroes) members.get(idx_1 - 1)).getCurWeapon() != null) {
			System.out.println(ZshColor.ANSI_RED + " - Hey. This guy has only one equipped weapon.");			
		}
		else {
			System.out.println(ZshColor.ANSI_RESET + backpack.dispWeapons());
			
			int idx_2 = 0;
			do {
				System.out.println(ZshColor.ANSI_RED + " - Which weapon would you like to use? Please answer its index: ");
				try {
					idx_2 = scan.nextInt();
				} catch (Exception e) {
					System.out.println(ZshColor.ANSI_RED + " - Invalid input!");
					scan.nextLine();
				}
			} while (idx_2 <= 0 || idx_2 > backpack.getWeapons().size());
			scan.nextLine();
			((Heroes) members.get(idx_1 - 1)).changeCurWeapon(backpack.getWeapons().get(idx_2 - 1));
		}
	}
	
	public void changeArmor(Scanner scan, int params) {
		int idx_1 = 0;
		if (params == -1) {
			/*
			 * The Quest
			 */
			System.out.println(ZshColor.ANSI_BLUE + this.toString());
			do {
				System.out.println(ZshColor.ANSI_RED + " - Whose equipped armor would you like to change? Please answer his index: " + ZshColor.ANSI_RESET);
				try {
					idx_1 = scan.nextInt();
				} catch (Exception e) {
					System.out.println(ZshColor.ANSI_RED + " - Invalid input!");
					scan.nextLine();
				}
			} while (idx_1 <= 0 || idx_1 > getNum());
			scan.nextLine();
		}
		else {
			/*
			 * The Quest Of Legends
			 */
			idx_1 = params;
		}
		
		Backpack backpack = ((Heroes) members.get(idx_1 - 1)).getBackpack();
		if (backpack.getArmors().size() == 0) {
			System.out.println(ZshColor.ANSI_RED + " - Hey. This guy has no armors.");
		}
		else if (backpack.getArmors().size() == 1 && ((Heroes) members.get(idx_1 - 1)).getCurArmor() != null) {
			System.out.println(ZshColor.ANSI_RED + " - Hey. This guy has only one equipped armor.");			
		}
		else {
			System.out.println(ZshColor.ANSI_RESET + backpack.dispArmors());
			
			int idx_2 = 0;
			do {
				System.out.println(ZshColor.ANSI_RED + " - Which armor would you like to use? Please answer its index: ");
				try {
					idx_2 = scan.nextInt();
				} catch (Exception e) {
					System.out.println(ZshColor.ANSI_RED + " - Invalid input!");
					scan.nextLine();
				}
			} while (idx_2 <= 0 || idx_2 > backpack.getArmors().size());
			scan.nextLine();
			((Heroes) members.get(idx_1 - 1)).changeCurArmor(backpack.getArmors().get(idx_2 - 1));
		}
	}

}
