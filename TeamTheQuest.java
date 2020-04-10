import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TeamTheQuest extends Team implements Encounter {
	
	private ArrayList<Roles> rivals = new ArrayList<Roles> ();
	
	public ArrayList<Roles> getRivals() {
		return this.rivals;
	}
	
	public TeamTheQuest() {
		
	}
	
	public TeamTheQuest(Roles hero, Roles monster) {
		num = 1;
		members.add(hero);
		rivals.add(monster);
	}
	
	public void getSize(Scanner scan) {
		// Get the size of your team
		num = 0;
		do {
			System.out.println(ZshColor.ANSI_RED + " - How many heroes you'd like to select? (1 or 2 or 3)" + ZshColor.ANSI_RESET);
			try {
				num = scan.nextInt();
			} catch (Exception e) {
				System.out.println(ZshColor.ANSI_RED + "Invalid input!");
				scan.nextLine();
			}
		} while (num <= 0 || num > 3);
	}
	
	@Override
	public void generateRivals() {
		rivals.clear();
		// Deep Copy
		int maxLevel = 0;
		for (Roles role: members) {
			maxLevel = Math.max(maxLevel, role.getLevel());
		}
		int pos = 3 * (maxLevel - 1);
		for (int i = 0; i < num; i ++) {
			Roles role = Monsters.monsters.get(pos + i);
			Roles cp;
			if (role instanceof Dragons) {
				cp = new Dragons(role.getName(), role.getLevel(), ((Dragons) role).getDamage(), 
						((Dragons) role).getDefense(), ((Dragons) role).getDodgeChance());
			}
			else if (role instanceof Exoskeletons) {
				cp = new Exoskeletons(role.getName(), role.getLevel(), ((Exoskeletons) role).getDamage(), 
						((Exoskeletons) role).getDefense(), ((Exoskeletons) role).getDodgeChance());
			}
			else { //if (role instanceof Spirits) {
				cp = new Spirits(role.getName(), role.getLevel(), ((Spirits) role).getDamage(), 
						((Spirits) role).getDefense(), ((Spirits) role).getDodgeChance());
			}
			rivals.add(cp);
		}
	}

	public String getInventories() {
		String str = ZshColor.ANSI_RED + " - Here are the inventories of your team: \n";
		for (Roles role: members) {
			Backpack backpack = ((Heroes) role).getBackpack();
			str += ZshColor.ANSI_RED + role.getName() + ": \n";
			str += backpack.toString() + "\n";
		}
		return str;
	}
	
	public String getRivalStatus() {
		String str = " - The status of those monsters: \n";
		int idx = 0;
		for (Roles role: rivals) {
			idx ++;
			str += "   " + String.valueOf(idx) + " " + role.toString() + "\n";
		}
		return str;
	}
	
	public String toString() {
		String str = " - The status of your team member: \n";
		int idx = 0;
		for (Roles role: members) {
			idx ++;
			str += "   " + String.valueOf(idx) + " " + role.toString() + "\n";
			if (((Heroes) role).getCurWeapon() == null) {
				str += "     Current weapon:  NULL\n";
			}
			else {
				str += "     Current weapon:  " + ((Heroes) role).getCurWeapon().toString() + "\n";
			}
			if (((Heroes) role).getCurArmor() == null) {
				str += "     Current armor:  NULL\n";				
			}
			else {
				str += "     Current armor:  " + ((Heroes) role).getCurArmor().toString() + "\n";;
			}
			
		}
		return str;
	}
}
