import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

public class TheQuestOfLegendsSimulation extends Simulation {

	private final int Turns = 8; // Every 8 rounds 3 monsters spawned
	private TheQuestOfLegendsMap gameMap;
	
	public TheQuestOfLegendsSimulation(Scanner scan) {
		super(scan);
	}

	@Override
	public void simulate() {
		
		System.out.println(ZshColor.ANSI_RED + "TheQuest ------> Game Start!");
		System.out.println(ZshColor.ANSI_YELLOW + "--------------------------------------------------"
				+ "------------------------------------------------------");
		
		// simulation
		gameMap = new TheQuestOfLegendsMap(scan);
		
		boolean quitTheGame = false;
		int T = 0;
		while (!quitTheGame) {
			
			T ++;
			TeamTheQuestOfLegends team = gameMap.getTeam();
			ArrayList<Roles> members = team.getMembers();
			if (T % Turns == 1) {
				gameMap.addMonsters();
			}
			gameMap.printMap();
			
			/*
			 * Check the status of the whole game!
			 */
			int points = gameMap.checkStatus();
			if (points == 0) {
				// Keep playing the game
			}
			else if (points == 1) {
				System.out.println(ZshColor.ANSI_RED + " - Congratulation! Heroes Win!" + ZshColor.ANSI_RESET);
				break;
			}
			else if (points == 2) {
				System.out.println(ZshColor.ANSI_RED + " - Ooops! Monsters Win!" + ZshColor.ANSI_RESET);
				break;
			}
			else if (points == 3) {
				System.out.println(ZshColor.ANSI_RED + " - Tie game!" + ZshColor.ANSI_RESET);
				break;
			}
			
			// Assume that hero won't fight with monster when they encounter at a Nexus cell
			for (int i = 0; i < 3; i ++) {
				gameMap.printMap();
				Cells currentCell = gameMap.whereIsHeroes(i);
				String strName = "H" + String.valueOf(i + 1) + "(" + members.get(i).getName() + ")";
				if (currentCell instanceof NexusCells) {
					// If heroes arrive at a market
					System.out.println(ZshColor.ANSI_PURPLE + " - Hi! " + strName + "  Welcome to the supermarket!");
					System.out.println(ZshColor.ANSI_RED + " - Tips: Without spells and weapons, you could hardly beat a monster.");
					String doBusiness;
					do {
						System.out.println(ZshColor.ANSI_RED + " - Would you like to buy/sell items?  Yes/No" + ZshColor.ANSI_RESET);
						doBusiness = scan.nextLine();
						if (doBusiness.compareTo("Yes") == 0) {
							// Markets.trade(scan, team);
							Markets.trade(scan, members.get(i));
						}
					} while (doBusiness.compareTo("No") != 0);
				}
				
				boolean finishMove = false;
				do {
					System.out.println("\n" + ZshColor.ANSI_RED + " - It's " + strName + "'s turn!");
					System.out.println(ZshColor.ANSI_CYAN + " 1. #");
					System.out.println(ZshColor.ANSI_GREEN + "     #->Display status of " + strName);
					System.out.println(ZshColor.ANSI_CYAN + " 2. C");
					System.out.println(ZshColor.ANSI_GREEN + "     C->Check inventory");
					System.out.println(ZshColor.ANSI_CYAN + " 3. W/A/S/D");
					System.out.println(ZshColor.ANSI_GREEN + "     W->move up, A->move left, S->move down, D->move right");
					System.out.println(ZshColor.ANSI_CYAN + " 4. T");
					System.out.println(ZshColor.ANSI_GREEN + "     T->Teleport  | Only input T");
					System.out.println(ZshColor.ANSI_CYAN + " 5. B");
					System.out.println(ZshColor.ANSI_GREEN + "     B->Go back to Nexus");
					System.out.println(ZshColor.ANSI_CYAN + " 6. U/I");
					System.out.println(ZshColor.ANSI_GREEN + "     U->equip/change weapon, I->equip/change armor");
					System.out.println(ZshColor.ANSI_CYAN + " 7. P");
					System.out.println(ZshColor.ANSI_GREEN + "     P->Display the map");
					System.out.println(ZshColor.ANSI_CYAN + " 8. Q");
					System.out.println(ZshColor.ANSI_GREEN + "     Q->quit the game"); //  + ZshColor.ANSI_RESET
					System.out.println(ZshColor.ANSI_RESET);
					String str = scan.nextLine();
					char c = str.charAt(0);
					if (str.length() > 1) {
						// Invalid input
					}
					else if (c == '#') {
						System.out.println(ZshColor.ANSI_BLUE + team.getStatus(i));
					}
					else if (c == 'C') {
						System.out.println(ZshColor.ANSI_BLUE + team.getInventories(i));
					}
					else if (c == 'W' || c == 'A' || c == 'S' || c == 'D') {
						boolean valid = gameMap.move(i, c); // make move
						if (!valid) {
							// If that move is invalid
							System.out.println(ZshColor.ANSI_RED + "It's an invalid move!");
						}
						else {
							// battle
							currentCell = gameMap.whereIsHeroes(i);
							if (!(currentCell instanceof NexusCells)) {
								gameMap.checkEncounter(scan, i);
							}
							finishMove = true;
						}
					}
					else if (c == 'T') {
						gameMap.teleport(scan, i);
						currentCell = gameMap.whereIsHeroes(i);
						if (!(currentCell instanceof NexusCells)) {
							gameMap.checkEncounter(scan, i);
						}
						finishMove = true;
					}
					else if (c == 'B') {
						boolean valid = gameMap.goBack(i);
						if (!valid) {
							System.out.println(ZshColor.ANSI_RED + "It's an invalid move!");
						}
						else {
							gameMap.printMap();
							finishMove = true;
						}
					}
					else if (c == 'U') {
						team.changeWeapon(scan, i + 1);
					}
					else if (c == 'I') {
						team.changeArmor(scan, i + 1);
					}
					else if (c == 'P') {
						gameMap.printMap();
					}
					else if (c == 'Q') {
						quitTheGame = true;
						finishMove = true; // --> break
					}
				} while (!finishMove);
			}

			/*
			 * Monster's turn: 
			 * Monsters' moving rules
			 * - If he can move down, just do it
			 * - If he can't 
			 *   - If he can move left/right, just do it
			 *   - If he can't, stay here and do nothing
			 *   
			 *   
			 * - If a monster encounters a hero, FIGHT!
			 */
			gameMap.moveMonster(scan);
			
		}
		
	}
	
	private Iterator<String> getFileIterator(String path) {
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<String> itr = lines.iterator();
		itr.next(); // get past the headings
		return itr;
	}
	
	private void initializeCharacters(String path, String type) {
		Iterator<String> itr = getFileIterator(path);
		while (itr.hasNext()) {
			String next = itr.next();
			String[] split = next.split("\\s+");
			switch (type) {
				case "warriors":
					Heroes.heroes.add(new Warriors(split[0], Double.parseDouble(split[1]), Double.parseDouble(split[2]),
							Double.parseDouble(split[3]), Double.parseDouble(split[4]), Double.parseDouble(split[5]),
									Double.parseDouble(split[6])));
					break;
				case "paladins":
					Heroes.heroes.add(new Paladins(split[0], Double.parseDouble(split[1]), Double.parseDouble(split[2]),
							Double.parseDouble(split[3]), Double.parseDouble(split[4]), Double.parseDouble(split[5]),
							Double.parseDouble(split[6])));
					break;
				case "sorcerers":
					Heroes.heroes.add(new Sorcerers(split[0], Double.parseDouble(split[1]), Double.parseDouble(split[2]),
							Double.parseDouble(split[3]), Double.parseDouble(split[4]), Double.parseDouble(split[5]),
							Double.parseDouble(split[6])));
					break;
				case "dragons":
					Monsters.monsters.add(new Dragons(split[0], Integer.parseInt(split[1]), Double.parseDouble(split[2]),
							Double.parseDouble(split[3]), Double.parseDouble(split[4])));
					break;
				case "exoskeletons":
					Monsters.monsters.add(new Exoskeletons(split[0], Integer.parseInt(split[1]), Double.parseDouble(split[2]),
							Double.parseDouble(split[3]), Double.parseDouble(split[4])));
					break;
				case "spirits":
					Monsters.monsters.add(new Spirits(split[0], Integer.parseInt(split[1]), Double.parseDouble(split[2]),
							Double.parseDouble(split[3]), Double.parseDouble(split[4])));
					break;
			}
		}
	}

	private void initializeMarketItems(String path, String type) {
		Iterator<String> itr = getFileIterator(path);
		while (itr.hasNext()) {
			String next = itr.next();
			String[] split = next.split("\\s+");
			switch (type) {
				case "weapons":
					Markets.weapons.add(new Weapons(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]),
							Double.parseDouble(split[3]), Integer.parseInt(split[4])));
					break;
				case "armors":
					Markets.armors.add(new Armors(split[0], Double.parseDouble(split[1]), Integer.parseInt(split[2]),
							Double.parseDouble(split[3])));
					break;
				case "potions":
					Markets.potions.add(new Potions(split[0], Double.parseDouble(split[1]), Integer.parseInt(split[2]),
							Double.parseDouble(split[3])));
					break;
				case "ice_spells":
					Markets.spells.add(new IceSpells(split[0], Double.parseDouble(split[1]), Integer.parseInt(split[2]),
							Double.parseDouble(split[3]), Double.parseDouble(split[3])));
					break;
				case "fire_spells":
					Markets.spells.add(new FireSpells(split[0], Double.parseDouble(split[1]), Integer.parseInt(split[2]),
							Double.parseDouble(split[3]), Double.parseDouble(split[3])));
					break;
				case "lightning_spells":
					Markets.spells.add(new LightningSpells(split[0], Double.parseDouble(split[1]), Integer.parseInt(split[2]),
							Double.parseDouble(split[3]), Double.parseDouble(split[3])));
					break;
			}
		}
	}

	@Override
	public void initialize(){
		initializeCharacters("./Warriors.txt", "warriors");
		initializeCharacters("./Paladins.txt", "paladins");
		initializeCharacters("./Sorcerers.txt", "sorcerers");

		initializeCharacters("./Dragons.txt", "dragons");
		initializeCharacters("./Exoskeletons.txt", "exoskeletons");
		initializeCharacters("./Spirits.txt", "spirits");
		// sort all these monsters with respect to their level
		Collections.sort(Monsters.monsters);
		
		// Market Items
		initializeMarketItems("./Weaponry.txt", "weapons");
		initializeMarketItems("./Armory.txt", "armors");
		initializeMarketItems("./Potions.txt", "potions");
		initializeMarketItems("./IceSpells.txt", "ice_spells");
		initializeMarketItems("./FireSpells.txt", "fire_spells");
		initializeMarketItems("./LightningSpells.txt", "lightning_spells");
	}

}
