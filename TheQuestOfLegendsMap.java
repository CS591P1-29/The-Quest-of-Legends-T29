import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class TheQuestOfLegendsMap extends Map {
	
	int [][] pos = { { 0, 1 }, { 3, 4 }, { 6, 7 } };
	TeamTheQuestOfLegends team = new TeamTheQuestOfLegends();

	private boolean filledCellsH[][] = new boolean[8][8];
	private boolean filledCellsM[][] = new boolean[8][8];
	
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
						case 1: alCell.add(new CommonCells()); break; // Plain Cells
						case 2: alCell.add(new BushCells()); break;
						case 3: alCell.add(new KoulouCells()); break;
						case 4: alCell.add(new CaveCells()); break;
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

	public void addMonsters() {
		team.generateRivals();
		ArrayList<Roles> monsters = team.getMonsters();
		for (Roles role: monsters) {
			filledCellsM[role.getX()][role.getY()] = true;
		}
	}
	
	public TeamTheQuestOfLegends getTeam() {
		return team;
	}
	
	private String getHeroName(int x, int y) {
		// Get Nickname
		String str = ZshColor.ANSI_RED + "H";
		ArrayList<Roles> members = team.getMembers();
		for (int i = 0; i < 3; i ++) {
			if (members.get(i).getX() == x && members.get(i).getY() == y) {
				str += String.valueOf(i + 1);
				break;
			}
		}
		str += ZshColor.ANSI_RESET;
		return str;
	}
	
	private String getMonsterName(int x, int y) {
		// Get Nickname
		String str = ZshColor.ANSI_GREEN + "M";
		ArrayList<Roles> monsters = team.getMonsters();
		for (int i = 0; i < monsters.size(); i ++) {
			if (monsters.get(i).getX() == x && monsters.get(i).getY() == y) {
				str += String.valueOf(i + 1);
				break;
			}
		}
		str += ZshColor.ANSI_RESET;
		return str;
	}
	
	public void printMap() {
		System.out.println(ZshColor.ANSI_RED + " - !!! Map !!!");
		System.out.println(ZshColor.ANSI_RED + " - Top,    Left  : (0, 0)");
		System.out.println(ZshColor.ANSI_RED + " - Bottom, Right : (7, 7)");
		System.out.print(ZshColor.ANSI_RESET);
		
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
							System.out.print(ZshColor.ANSI_RED + "| " + ZshColor.ANSI_BLUE + "X X X" 
									+ ZshColor.ANSI_RED + " |   " + ZshColor.ANSI_RESET);
						}
						else {
							String strH, strM;
							strH = filledCellsH[i][k] ? getHeroName(i, k) : "  ";
							strM = filledCellsM[i][k] ? getMonsterName(i, k) : "  ";
							if (grid.get(i).get(k) instanceof NexusCells) {
								System.out.print(ZshColor.ANSI_GREEN + "| " + strH + " " + strM 
										+ ZshColor.ANSI_GREEN + " |   " + ZshColor.ANSI_RESET);
							}
							else { 
								System.out.print("| " + strH + " " + strM + " |   ");
							}
						}
					}
				}
				System.out.println("");
			}
			System.out.println("");
		}
		
	}
	
	public Cells whereIsHeroes(int idx) {
		Roles role = team.getMembers().get(idx); 
		return grid.get(role.getX()).get(role.getY());
	}
	
	public Cells whereIsMonsters(int idx) {
		Roles role = team.getMonsters().get(idx);
		return grid.get(role.getX()).get(role.getY());
	}
	
	public boolean move(int idx, char c) {
		int cx, cy;
		switch (c) {
			case 'W': cx = -1; cy = 0; break; // move up
			case 'A': cx = 0; cy = -1; break; // move left
			case 'S': cx = +1; cy = 0; break; // move down
			case 'D': cx = 0; cy = +1; break; // move right
			default: return false;
		}
		
		Roles role = team.getMembers().get(idx);
		int nx = role.getX() + cx;
		int ny = role.getY() + cy;
		if (nx < 0 || nx >= 8 || ny < 0 || ny >= 8) {
			return false;
		}
		if (ny == 2 || ny == 5) {
			// Non-accessible cells
			return false;
		}
		if (filledCellsH[nx][ny]) {
			// A hero already stands at (nx, ny)
			return false;
		}
		else {
			filledCellsH[role.getX()][role.getY()] = false;
			filledCellsH[nx][ny] = true;
			role.setX(nx);
			role.setY(ny);
		}
		
		return true;
	}
	
	public boolean goBack(int idx) {
		Roles role = team.getMembers().get(idx);
		if (role.getX() == 7) {
			// This guy already stands on Nexus cell.
			return true;
		}
		for (int i = 0; i < 3; i ++) {
			if (role.getY() == pos[i][0] || role.getY() == pos[i][1]) {
				if (!filledCellsH[7][pos[i][0]]) {
					filledCellsH[role.getX()][role.getY()] = false;
					filledCellsH[7][pos[i][0]] = true;
					role.setX(7);
					role.setY(pos[i][0]);
					break;
				}
				else if (!filledCellsH[7][pos[i][1]]) {
					filledCellsH[role.getX()][role.getY()] = false;
					filledCellsH[7][pos[i][1]] = true;
					role.setX(7);
					role.setY(pos[i][1]);
					break;
				}
				else {
					return false;
				}
			}
		}
		return true;
	}
	
	public void teleport(Scanner scan, int idx) {
		Roles role = team.getMembers().get(idx);
		
		int cx = 0, cy = 0;
		boolean isValid = false;
		do {
			System.out.println(ZshColor.ANSI_RED + " - Where you want to teleport? Ex: 4, 6 " + ZshColor.ANSI_RESET);
			try {
				cx = scan.nextInt();
				cy = scan.nextInt();
			} catch (Exception e) {
				System.out.println(ZshColor.ANSI_RED + " - Invalid input!");
				scan.nextLine();
			}
			isValid = true;
			if (cy == 2 || cy == 5) {
				System.out.println(ZshColor.ANSI_RED + " - Can't teleport to Non-accessible cells!" + ZshColor.ANSI_RESET);
				isValid = false;
			}
			else if (Math.abs(role.getY() - cy) <= 1) {
				System.out.println(ZshColor.ANSI_RED + " - Can't teleport to the same lane!" + ZshColor.ANSI_RESET);
				isValid = false;
			}
			else if (cx == 0) {
				System.out.println(ZshColor.ANSI_RED + " - Can't teleport to the monsters' Nexus cells directly!" + ZshColor.ANSI_RESET);
				isValid = false;
			}
			else {
				ArrayList<Roles> monsters = team.getMonsters();
				for (int i = 0; i < monsters.size(); i ++) {
					if (Math.abs(monsters.get(i).getY() - role.getY()) <= 1 && monsters.get(i).getX() > cx) {
						System.out.println(ZshColor.ANSI_RED + " - Can't use teleport to pass behind a monster." + ZshColor.ANSI_RESET);
						isValid = false;
						break;
					}
				}
			}
			if (filledCellsH[cx][cy]) {
				System.out.println(ZshColor.ANSI_RED + " - A hero is already there!" + ZshColor.ANSI_RESET);
				isValid = false;
			}
		} while (!isValid);
		scan.nextLine();
		
		filledCellsH[role.getX()][role.getY()] = false;
		filledCellsH[cx][cy] = true;
		role.setX(cx);
		role.setY(cy);
	}
	
	public void checkEncounter(Scanner scan, int idx) {
		Roles hero = team.getMembers().get(idx);
		Roles monster = null;
		
		int index = 0;
		for (Roles role: team.getMonsters()) {
			index ++;
			if (hero.getX() == role.getX() && hero.getY() == role.getY()) {
				monster = role;
				break;
			}
		}
		if (monster == null) {
			return;
		}
		
		TeamTheQuest teamTQ = new TeamTheQuest(hero, monster);
		Rounds rounds = new Rounds(scan, teamTQ, 0.10);
		/*
		 * If hero wins, the monster should be removed from the map
		 * If monster wins, the hero should be moved to a Nexus cell
		 */
		double param1 = 0, param2 = 0, param3 = 0;
		Cells currentCell = grid.get(hero.getX()).get(hero.getY());
		if (currentCell instanceof KoulouCells) {
			param1 += 0.1;
		}
		else if (currentCell instanceof CaveCells) {
			param2 += 0.1;
		}
		else if (currentCell instanceof BushCells) {
			param3 += 0.1;
		}
		
		boolean heroesWin = rounds.fight(param1, param2, param3);
		if (heroesWin) {
			filledCellsM[monster.getX()][monster.getY()] = false;
			Iterator<Roles> it = team.getMonsters().iterator();
			Roles role = null;
			for (int i = 0; i < index; i ++) {
				role = it.next();
			}
			it.remove();
		}
		else {
			filledCellsH[hero.getX()][hero.getY()] = false;
			if (!filledCellsH[7][hero.getY()]) {
				filledCellsH[7][hero.getY()] = true;
				hero.setX(7);
			}
			else {
				for (int i = 0; i < 8; i ++) {
					if (!filledCellsH[7][i]) {
						filledCellsH[7][i] = true;
						hero.setX(i);
					}
				}
			}
		}
	}
	
	public void moveMonster(Scanner scan) {
		ArrayList<Roles> monsters = team.getMonsters();
		for (Roles role: monsters) {
			if (!filledCellsM[role.getX() + 1][role.getY()]) {
				filledCellsM[role.getX()][role.getY()] = false;
				filledCellsM[role.getX() + 1][role.getY()] = true;
				role.setX(role.getX() + 1);
			}
			else {
				int y1 = role.getY() + 1;
				if (y1 == 1 || y1 == 4 || y1 == 7) {
					if (!filledCellsM[role.getX()][y1]) {
						filledCellsM[role.getX()][role.getY()] = false;
						filledCellsM[role.getX()][y1] = false;
						role.setY(y1);
						continue;
					}
				}
				int y2 = role.getY() - 1;
				if (y2 == 0 || y2 == 3 || y2 == 6) {
					if (!filledCellsM[role.getX()][y2]) {
						filledCellsM[role.getX()][role.getY()] = false;
						filledCellsM[role.getX()][y2] = false;
						role.setY(y2);
						continue;
					}
				}
			}
		}
		
		/*
		 * If a monster encounters a hero at a non-nexus cell, --> fight
		 */
		ArrayList<Roles> members = team.getMembers();
		int index = 0;
		for (Roles monster: monsters) {
			index ++;
			if (monster.getX() != 7 && filledCellsH[monster.getX()][monster.getY()]) {
				for (Roles hero: members) {
					if (hero.getX() == monster.getX() && hero.getY() == monster.getY()) {
						TeamTheQuest teamTQ = new TeamTheQuest(hero, monster);
						Rounds rounds = new Rounds(scan, teamTQ, 0.10);
						
						double param1 = 0, param2 = 0, param3 = 0;
						Cells currentCell = grid.get(hero.getX()).get(hero.getY());
						if (currentCell instanceof KoulouCells) {
							param1 += 0.1;
						}
						else if (currentCell instanceof CaveCells) {
							param2 += 0.1;
						}
						else if (currentCell instanceof BushCells) {
							param3 += 0.1;
						}
						
						boolean heroesWin = rounds.fight(param1, param2, param3);
						if (heroesWin) {
							filledCellsM[monster.getX()][monster.getY()] = false;
							Iterator<Roles> it = team.getMonsters().iterator();
							Roles role = null;
							for (int i = 0; i < index; i ++) {
								role = it.next();
							}
							it.remove();
						}
						else {
							filledCellsH[hero.getX()][hero.getY()] = false;
							if (!filledCellsH[7][hero.getY()]) {
								filledCellsH[7][hero.getY()] = true;
								hero.setX(7);
							}
							else {
								for (int i = 0; i < 8; i ++) {
									if (!filledCellsH[7][i]) {
										filledCellsH[7][i] = true;
										hero.setX(i);
									}
								}
							}
						}
						
						break;
					}
				}
			}
		}
	}
	
	public int checkStatus() {
		/*
		 * If hero wins, return 1
		 * If monster wins, return 2
		 * If the game tie, return 3
		 * Otherwise, return 0
		 */
		boolean heroWin = false;
		boolean monsterWin = false;
		for (int i = 0; i < 8; i ++) {
			if (i == 2 || i == 5) {
				continue;
			}
			if (filledCellsH[0][i]) {
				heroWin = true;
			}
			if (filledCellsM[7][i]) {
				monsterWin = true;
			}
		}
		if (heroWin && !monsterWin) {
			return 1;
		}
		if (!heroWin && monsterWin) {
			return 2;
		}
		if (heroWin && monsterWin) {
			return 3;
		}
		return 0;
	}
	
}
