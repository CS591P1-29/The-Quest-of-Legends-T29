import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TheQuestOfLegendsMap extends Map {
	
	TeamTheQuestOfLegends team = new TeamTheQuestOfLegends();

	private Boolean filledCellsH[][] = new Boolean[8][8];
	private Boolean filledCellsM[][] = new Boolean[8][8];
	
	public TheQuestOfLegendsMap(Scanner scan) {
		// Size of board
		super(8, 8);
		/*
		 * Generate the board/map
		 * The type of each cell is randomly assigned
		 */
		grid = new ArrayList<ArrayList<Cells>> ();
		for (int i = 0; i < 8; i ++) {
			ArrayList<Cells> alCell = new ArrayList<Cells> ();
			for (int j = 0; j < 8; j ++) {
				filledCellsH[i][j] = false;
				filledCellsM[i][j] = false;
				if (j == 2 || j == 5) {
					alCell.add(new NonAccessibleCells());
				}
				else if (i == 0 || i == 7) {
					alCell.add(new NexusCells()); // Market
				}
				else {
					Random rand = new Random();
					int r = rand.nextInt(4) + 1;
					switch (r) {
						case 1:
							alCell.add(new CommonCells()); // Plain Cells
							break;
						case 2:
							alCell.add(new BushCells());
							break;
						case 3:
							alCell.add(new KoulouCells());
							break;
						case 4:
							alCell.add(new CaveCells());
							break;
					}
				}
			}
			grid.add(alCell);
		}
		/*
		 * Select 3 heroes to build your team
		 * Assign them to 3 default positions (7, 0), (7, 3), (7, 6)
		 */
		team.buildTeam(scan);
		ArrayList<Roles> members = team.getMembers();
		members.get(0).setX(7); members.get(0).setY(0);
		members.get(1).setX(7); members.get(1).setY(3);
		members.get(2).setX(7); members.get(2).setY(6);
		filledCellsH[7][0] = true;
		filledCellsH[7][3] = true;
		filledCellsH[7][6] = true;
	}

	private String getHeroName(int x, int y) {
		// Get Nickname
		String str = "H";
		ArrayList<Roles> members = team.getMembers();
		for (int i = 0; i < 3; i ++) {
			if (members.get(i).getX() == x && members.get(i).getY() == y) {
				str += String.valueOf(i + 1);
				break;
			}
		}
		return str;
	}
	
	private String getMonsterName(int x, int y) {
		// Get Nickname
		String str = "M";
		ArrayList<Roles> monsters = team.getMonsters();
		for (int i = 0; i < monsters.size(); i ++) {
			if (monsters.get(i).getX() == x && monsters.get(i).getY() == y) {
				str += String.valueOf(i + 1);
				break;
			}
		}
		return str;
	}
	
	public void printMap() {
		System.out.println(ZshColor.ANSI_RED + " - !!!Map!!!");
		System.out.print(ZshColor.ANSI_WHITE);
		
		for (int i = 0; i < 8; i ++) {
			for (int j = 0; j < 3; j ++) {
				
				if (j == 0 || j == 2) {
					for (int k = 0; k < 8; k ++) {
						System.out.print(grid.get(i).get(k));
					}
				}
				else if (j == 1) {
					for (int k = 0; k < 8; k ++) {
						if (grid.get(i).get(k) instanceof NonAccessibleCells) {
							System.out.print("| X X X |   ");
						}
						else {
							String strH, strM;
							strH = filledCellsH[i][k] ? getHeroName(i, k) : "  ";
							strM = filledCellsM[i][k] ? getMonsterName(i, k) : "  ";
							System.out.print("| " + strH + " " + strM + " |   ");
						}
					}
				}
				System.out.println("");
			}
			System.out.println("");
		}
		
	}
	
}
