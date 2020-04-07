
public class Potions extends Items {

	public static String p_hp = "Healing_Potion";
	public static String p_sp = "Strength_Potion";
	public static String p_mp = "Magic_Potion";
	public static String p_le = "Luck_Elixir";
	public static String p_mt = "Mermaid_Tears";
	public static String p_a = "Ambrosia";
	
	private double Attribute_Increase;
	
	public Potions(String Name, double Price, int Required_Level, double Attribute_Increase) {
		super(Name, Price, Required_Level);
		this.Attribute_Increase = Attribute_Increase;
	}
	
	public double getAttributeIncrease() {
		return Attribute_Increase;
	}
	
	public String toString() {
		String str;
		str = Name + ",  " + String.valueOf(Price) + ",  " + String.valueOf(Required_Level) + ",  " 
				+ String.valueOf(Attribute_Increase);
		return str;
	}
	
}
