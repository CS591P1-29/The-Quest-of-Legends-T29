
public class IceSpells extends Spells {

	public IceSpells(String Name, double Price, int Required_Level, double Damage, double Mana_Cost) {
		super(Name, Price, Required_Level, Damage, Mana_Cost);
	}

	@Override
	public void deteriorate(Roles monster) {
		// TODO Auto-generated method stub
		((Monsters) monster).reduceDamage();
	}

	@Override
	public String deterioration() {
		// TODO Auto-generated method stub
		return "The damage range of that monster has been reduced.";
	}
	
}
