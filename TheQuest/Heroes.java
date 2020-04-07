import java.util.ArrayList;
import java.util.Iterator;

public abstract class Heroes extends Roles implements Regeneration {
	
	protected double Mana;
	protected double Strength;
	protected double Agility;
	protected double Dexterity;
	protected double Money;
	protected double Experience;
	
	protected Items curWeapon = null; // current weapon
	protected Items curArmor = null;
	protected Backpack backpack = new Backpack();
	public static ArrayList<Roles> heroes = new ArrayList<Roles> ();
	
	public Heroes(String Name, double Mana, double Strength, double Agility, 
			double Dexterity, double Starting_Money, double Starting_Experience) {
		super(Name, 1, 100);
		this.Mana = Mana;
		this.Strength = Strength;
		this.Agility = Agility;
		this.Dexterity = Dexterity;
		this.Money = Starting_Money;
		this.Experience = Starting_Experience;
	}
	
	public boolean hasEnoughExp() {
		return (Experience - 10 * Level > Roles.eps) ? true : false;
	}
	
	public void Hp_Mana_Regen() {
		// Health Regeneration
		Hp *= 1.05;
		// Mana Regeneration
		Mana *= 1.05;
	}
	
	public Backpack getBackpack() {
		return backpack;
	}
	
	public double getSTR() {
		return Strength;
	}

	public double getAGI() {
		return Agility;
	}
	
	public double getDEX() {
		return Dexterity;
	}
	
	public double getMana() {
		return Mana;
	}
	
	public double getDodgeChance() {
		return Agility * 0.02;
	}
	
	public void changeMana(double delta) {
		Mana += delta;
	}
	
	public void changeMoney(double delta) {
		Money += delta;
	}
	
	public void changeExperience(double delta) {
		Experience += delta;
	}
	
	public double getMoney() {
		return Money;
	}
	
	public void revive(double param) {
		Hp = Level * 100 * param;
		Money *= param;
	}
	
	public void changeCurWeapon(Items weapon) {
		curWeapon = weapon;
	}
	
	public void changeCurArmor(Items armor) {
		curArmor = armor;
	}
	
	public Items getCurWeapon() {
		return curWeapon;
	}
	
	public Items getCurArmor() {
		return curArmor;
	}
	
	public void drinkAPotion(int idx) {
		Items potion = backpack.getPotions().get(idx);
		if (potion.getName().compareTo(Potions.p_hp) == 0) {
			Hp += ((Potions) potion).getAttributeIncrease();
		}
		else if (potion.getName().compareTo(Potions.p_sp) == 0) {
			Strength += ((Potions) potion).getAttributeIncrease(); 
		}
		else if (potion.getName().compareTo(Potions.p_mp) == 0) {
			Mana += ((Potions) potion).getAttributeIncrease();
		}
		else if (potion.getName().compareTo(Potions.p_le) == 0) {
			Dexterity += ((Potions) potion).getAttributeIncrease();
		}
		else if (potion.getName().compareTo(Potions.p_mt) == 0) {
			Agility += ((Potions) potion).getAttributeIncrease();
		}
		else if (potion.getName().compareTo(Potions.p_a) == 0) {
			Hp += ((Potions) potion).getAttributeIncrease();
			Mana += ((Potions) potion).getAttributeIncrease();
			Strength += ((Potions) potion).getAttributeIncrease();
			Dexterity += ((Potions) potion).getAttributeIncrease();
			Agility += ((Potions) potion).getAttributeIncrease();
		}
		Iterator<Items> it = backpack.getPotions().iterator();
		Items item = null;
		for (int i = 0; i <= idx; i ++) {
			item = it.next();
		}
		System.out.println(ZshColor.ANSI_GREEN + " - " + potion.getName() + " has been used!");
		it.remove();
	}
	
	abstract public void levelUp();
	
}
