
public abstract class Spells extends Items {
	
	protected double Damage;
	protected double Mana_Cost;
	
	public Spells(String Name, double Price, int Required_Level, double Damage, double Mana_Cost) {
		super(Name, Price, Required_Level);
		this.Damage = Damage;
		this.Mana_Cost = Mana_Cost;
	}
	
	public double getDamage() {
		return Damage;
	}
	
	public double getManaCost() {
		return Mana_Cost;
	}
	
	public String toString() {
		String str;
		str = Name + ",  " + String.valueOf(Price) + ",  " + String.valueOf(Required_Level) + ",  " 
				+ String.valueOf(Damage) + ",  " + String.valueOf(Mana_Cost);
		return str;
	}
	
	abstract public String deterioration();
	abstract public void deteriorate(Roles monster);
	
}
