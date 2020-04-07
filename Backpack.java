import java.util.ArrayList;
import java.util.Iterator;

public class Backpack {

	private ArrayList<Items> weapons = new ArrayList<Items> ();
	private ArrayList<Items> armors = new ArrayList<Items> ();
	private ArrayList<Items> potions = new ArrayList<Items> ();
	private ArrayList<Items> spells = new ArrayList<Items> ();
	
	public ArrayList<Items> getWeapons() {
		return weapons;
	}
	
	public ArrayList<Items> getArmors() {
		return armors;
	}
	
	public ArrayList<Items> getPotions() {
		return potions;
	}
	
	public ArrayList<Items> getSpells() {
		return spells;
	}
	
	public void addWeapons(Items weapon) {
		weapons.add(new Weapons(weapon.getName(), weapon.getPrice(), weapon.getRequiredLevel(), 
				((Weapons) weapon).getDamage(), ((Weapons) weapon).getRequiredHands()));
	}
	
	public void addArmors(Items armor) {
		armors.add(new Armors(armor.getName(), armor.getPrice(), armor.getRequiredLevel(), 
				((Armors) armor).getDamageReduction()));
	}
	
	public void addPotions(Items potion) {
		potions.add(new Potions(potion.getName(), potion.getPrice(), potion.getRequiredLevel(), 
				((Potions) potion).getAttributeIncrease()));
	}
	
	public void addSpells(Items spell) {
		if (spell instanceof IceSpells) {
			spells.add(new IceSpells(spell.getName(), spell.getPrice(), spell.getRequiredLevel(), 
					((IceSpells) spell).getDamage(), ((IceSpells) spell).getManaCost()));
		}
		else if (spell instanceof FireSpells) {
			spells.add(new FireSpells(spell.getName(), spell.getPrice(), spell.getRequiredLevel(), 
					((FireSpells) spell).getDamage(), ((FireSpells) spell).getManaCost()));
		}
		else if (spell instanceof LightningSpells) {
			spells.add(new LightningSpells(spell.getName(), spell.getPrice(), spell.getRequiredLevel(), 
					((LightningSpells) spell).getDamage(), ((LightningSpells) spell).getManaCost()));
		}
	}
	
	public String dispWeapons() {
		String str = "";
		str += ZshColor.ANSI_RED + " - Here are all the weapons in his backpack:\n";
		str += ZshColor.ANSI_YELLOW + " - Format:  Name/cost/level/damage/required hands\n" + ZshColor.ANSI_RESET;
		int idx = 0; 
		for (Items item: weapons) {
			idx ++;
			str += String.valueOf(idx)+ "  " + ((Weapons) item).toString() + "\n";
		}
		if (weapons.size() == 0) {
			str += "   None\n";
		}
		return str;
	}
	
	public String dispArmors() {
		String str = "";
		str += ZshColor.ANSI_RED + " - Here are all the armors in his backpack:\n";
		str += ZshColor.ANSI_YELLOW + " - Format:  Name/cost/level/damage reduction\n" + ZshColor.ANSI_RESET;
		int idx = 0; 
		for (Items item: armors) {
			idx ++;
			str += String.valueOf(idx)+ "  " + ((Armors) item).toString() + "\n";
		}
		if (armors.size() == 0) {
			str += "   None\n";
		}
		return str;
	}
	
	public String dispPotions() {
		String str = "";
		str += ZshColor.ANSI_RED + " - Here are all the potions in his backpack:\n";
		str += ZshColor.ANSI_YELLOW + " - Format:  Name/cost/required level/attribute increase\n" + ZshColor.ANSI_RESET;
		int idx = 0;
		for (Items item: potions) {
			idx ++;
			str += String.valueOf(idx)+ "  " + ((Potions) item).toString() + "\n";
		}
		return str;
	}
	
	public String dispSpells() {
		String str = "";
		str += ZshColor.ANSI_RED + " - Here are all the spells he has learned:\n";
		str += ZshColor.ANSI_YELLOW + " - Format:  Name/cost/required level/damage/mana cost\n" + ZshColor.ANSI_RESET;
		int idx = 0;
		for (Items item: spells) {
			idx ++;
			str += String.valueOf(idx)+ "  " + ((Spells) item).toString() + "\n";
		}
		return str;
	}
	
	public String toString() {
		String str = "";
		
		str += ZshColor.ANSI_RED + " - Weapons:\n";
		str += ZshColor.ANSI_YELLOW + " - Format:  Name/cost/level/damage/required hands\n" + ZshColor.ANSI_RESET;
		for (Items item: weapons) {
			str += "   " + ((Weapons) item).toString() + "\n";
		}
		if (weapons.size() == 0) {
			str += "   None\n";
		}
		
		str += ZshColor.ANSI_RED + " - Armors:\n";
		str += ZshColor.ANSI_YELLOW + " - Format:  Name/cost/required level/damage reduction\n" + ZshColor.ANSI_RESET;
		for (Items item: armors) {
			str += "   " + ((Armors) item).toString() + "\n";
		}
		if (armors.size() == 0) {
			str += "   None\n";
		}
		
		str += ZshColor.ANSI_RED + " - Potions:\n";
		str += ZshColor.ANSI_YELLOW + " - Format:  Name/cost/required level/attribute increase\n" + ZshColor.ANSI_RESET;
		for (Items item: potions) {
			str += "   " + ((Potions) item).toString() + "\n";
		}
		if (potions.size() == 0) {
			str += "   None\n";
		}
		
		str += ZshColor.ANSI_RED + " - Spells:\n";
		str += ZshColor.ANSI_YELLOW + " - Format:  Type/Name/cost/required level/attribute increase\n" + ZshColor.ANSI_RESET;
		for (Items item: spells) {
			if (item instanceof IceSpells) {
				str += ZshColor.ANSI_WHITE + "   IceSpell,  ";
			}
			else if (item instanceof FireSpells) {
				str += ZshColor.ANSI_WHITE + "   FireSpell,  ";
			}
			else if (item instanceof LightningSpells) {
				str += ZshColor.ANSI_WHITE + "   LightningSpell,  ";
			}
			str += ((Spells) item).toString() + "\n";
		}
		if (spells.size() == 0) {
			str += "   None\n";
		}
		
		return str;
	}
	
}
