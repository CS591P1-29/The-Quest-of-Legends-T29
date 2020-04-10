import java.util.ArrayList;

public class TeamTheQuestOfLegends extends Team {

	private ArrayList<Roles> monsters = new ArrayList<Roles> ();
	
	public TeamTheQuestOfLegends() {
		num = 3;
	}
	
	public ArrayList<Roles> getMonsters() {
		return this.monsters;
	}
	
	public void generateRivals() {
		// Add 3 new monsters
		int maxLevel = 0;
		for (Roles role: members) {
			maxLevel = Math.max(maxLevel, role.getLevel());
		}
		int pos = 3 * (maxLevel - 1);
		for (int i = 0; i < 3; i ++) {
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
			cp.setX(0);
			cp.setY(3 * i);
			monsters.add(cp);
		}
	}
	
	public String getStatus(int idx) {
		String str = "";
		Roles role = members.get(idx);
		str += "   H" + String.valueOf(idx) + " " + role.toString() + "\n";
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
