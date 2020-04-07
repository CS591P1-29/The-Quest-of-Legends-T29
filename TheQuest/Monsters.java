import java.util.ArrayList;

public class Monsters extends Roles {
	
	protected double Damage;
	protected double Defense;
	protected double Dodge_Chance;
	
	public static ArrayList<Roles> monsters = new ArrayList<Roles> (); 
	
	public Monsters(String Name, int Level, double Damage, double Defense, double Dodge_Chance) {
		super(Name, Level, 100 * Level);
		this.Damage = Damage;
		this.Defense = Defense;
		this.Dodge_Chance = Dodge_Chance;
	}
	
	public double getDamage() {
		return Damage;
	}
	
	public double getDefense() {
		return Defense;
	}
	
	public double getDodgeChance() {
		return Dodge_Chance;
	}
	
	public void reduceDamage() {
		Damage *= 0.9;
	}
	
	public void reduceDefense() {
		Defense *= 0.9;
	}
	
	public void reduceDodgeChance() {
		Dodge_Chance *= 0.9;
	}
	
}
