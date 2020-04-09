import java.util.ArrayList;

public class TeamTheQuestOfLegends extends Team {

	int numOfMonsters;
	private ArrayList<Roles> monsters = new ArrayList<Roles> ();
	
	public TeamTheQuestOfLegends() {
		num = 3;
		numOfMonsters = 0;
	}
	
	public ArrayList<Roles> getMonsters() {
		return this.monsters;
	}
	
}
