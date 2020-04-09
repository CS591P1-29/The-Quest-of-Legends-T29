import java.util.ArrayList;

public class TeamTheQuestOfLegends extends Team {

	int numOfMonsters;
	private ArrayList<Roles> monsters = new ArrayList<Roles> ();
	
	public TeamTheQuestOfLegends() {
		num = 3;
		numOfMonsters = 0;
	}
	
	public ArrayList<Roles> getMonsters() {
		return this.monsters;
	}
	
	public String getStatus(int idx) {
		String str = "";
		Roles role = members.get(idx);
		str += "   " + String.valueOf(idx) + " " + role.toString() + "\n";
		str += "     Current Location:  (" + ((Heroes) role).getX() + ", " + ((Heroes) role).getY() + ")\n";
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
		return str;
	}
	
	public String getInventories(int idx) {
		String str = ZshColor.ANSI_RED + " - Here are the inventories of your team: \n";
		Backpack backpack = ((Heroes) members.get(idx)).getBackpack();
		str += ZshColor.ANSI_RED + members.get(idx).getName() + ": \n";
		str += backpack.toString() + "\n";
		return str;
	}
	
}
