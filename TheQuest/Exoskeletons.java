
public class Exoskeletons extends Monsters {

	public Exoskeletons(String Name, int Level, double Damage, double Defense, double Dodge_Chance) {
		super(Name, Level, Damage, Defense, Dodge_Chance);
	}

	public String toString() {
		String str = "Exoskeletons: \"" + Name + "\" , Level: " + Level + ", Hp: " + Hp + ", Damage: " + Damage + ", Defense: " 
				+ Defense + ", Dodge Chance: " + Dodge_Chance;
		return str;
	}
	
}
