import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Team {

	protected int num = 0;
	protected ArrayList<Roles> members = new ArrayList<Roles> ();
	
	public ArrayList<Roles> getMembers() {
		return this.members;
	}
	
	public void buildTeam(Scanner scan) {
		System.out.println(ZshColor.ANSI_RED + " - Here are the lists: " + ZshColor.ANSI_RESET);
		int idx = 0;
		for (Roles role: Heroes.heroes) {
			idx ++;
			System.out.println("Index: " + idx + "   " + role.toString());
		}
		System.out.println(ZshColor.ANSI_RED + " - Please select " + num + " heros.");
		System.out.println(" - Just enter the indexes like 3 6, which means you select the 3rd and 6th heroes." + ZshColor.ANSI_RESET);
		
		ArrayList<Integer> alist = new ArrayList<Integer> ();
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer> ();
		boolean completeReading = false;
		do {
			try {
				alist.clear();
				map.clear();
				for (int i = 0; i < num; i ++) {
					idx = scan.nextInt();
					alist.add(idx - 1);
				}
				boolean hasReplicate = false;
				for (int i = 0; i < num; i ++) {
					if (map.get(alist.get(i)) == null && 0 <= alist.get(i) && alist.get(i) < Heroes.heroes.size()) {
						map.put(alist.get(i), 1);
					}
					else {
						hasReplicate = true;
					}
				}
				if (hasReplicate) {
					System.out.println(ZshColor.ANSI_RED + " - One hero can only be select once in one team.\n" 
							 + " - Index should be valid." + ZshColor.ANSI_RESET);
				}
				else {
					completeReading = true;
				}
			} catch (Exception e) {
				System.out.println(ZshColor.ANSI_RED + " - Invalid input!" + ZshColor.ANSI_RESET);
				scan.nextLine();
			}
		} while (!completeReading);
		for (int i = 0; i < num; i ++) {
			members.add(Heroes.heroes.get(alist.get(i)));
		}
		scan.nextLine();
	}

}
