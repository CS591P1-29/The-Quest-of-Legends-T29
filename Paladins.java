
public class Paladins extends Heroes {

	public Paladins(String Name, double Mana, double Strength, double Agility, 
			double Dexterity, double Starting_Money, double Starting_Experience) {
		super(Name, Mana, Strength, Agility, Dexterity, 
				Starting_Money, Starting_Experience);
	}
		
	@Override
	public void levelUp() {
		// TODO Auto-generated method stub
		Experience -= 10 * Level;
		Level ++;
		Hp = Level * 100;
		Mana *= 1.1;
		Strength *= 1.1;
		Agility *= 1.05;
		Dexterity *= 1.1;
	}
	
	public String toString() {
		String str = "Paladins: \"" + Name + "\", Level: " + Level + ", Hp: " + Hp  + ", Mana: " + Mana + ", STR: " + Strength + ", AGI: " 
				+ Agility + ", DEX: " + Dexterity + ", Money: " + Money + ", Exp: " + Experience;
		return str;
	}
	
}
