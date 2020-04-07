
public class Armors extends Items {
	
	private double Damage_Reduction;
	
	public Armors(String Name, double Price, int Required_Level, double Damage_Reduction) {
		super(Name, Price, Required_Level);
		this.Damage_Reduction = Damage_Reduction;
	}
	
	public double getDamageReduction() {
		return Damage_Reduction;
	}
	
	public String toString() {
		String str;
		str = Name + ",  Price: " + String.valueOf(Price) + ",  Required_Level: " + String.valueOf(Required_Level) 
			+ ",  Damage_Reduction: " + String.valueOf(Damage_Reduction);
		return str;
	}
	
}
