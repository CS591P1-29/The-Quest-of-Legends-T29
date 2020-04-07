
public class Weapons extends Items {
	
	private double Damage;
	private int Required_Hands;
	
	public Weapons(String Name, double Price, int Required_Level, double Damage, int Required_Hands) {
		super(Name, Price, Required_Level);
		this.Damage = Damage;
		this.Required_Hands = Required_Hands;
	}
	
	public double getDamage() {
		return Damage;
	}
	
	public int getRequiredHands() {
		return Required_Hands;
	}
	
	public String toString() {
		String str;
		str = Name + ",  Price: " + String.valueOf(Price) + ",  Required_Level: " + String.valueOf(Required_Level) + ",  Damage: " 
				+ String.valueOf(Damage) + ",  Required_Hands: " + String.valueOf(Required_Hands);
		return str;
	}
	
}
