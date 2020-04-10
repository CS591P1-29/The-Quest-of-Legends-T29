import java.util.Collections;
import java.util.Scanner;

public class TheQuestSimulation extends Simulation {
	
	private TheQuestMap gameMap;
	
	public TheQuestSimulation(Scanner scan) {
		super(scan);
	}
	
	public void simulate() {
		/*
		 * 1. Set the size of the map
		 * 2. Select some imba heroes to build your team
		 * 3. 
		 * 
		 */
		System.out.println(ZshColor.ANSI_RED + "TheQuest ------> Game Start!");
		System.out.println(ZshColor.ANSI_YELLOW + "--------------------------------------------------"
				+ "------------------------------------------------------");
		
		// create a map
		int N = 0, M = 0;
		do {
			System.out.println(ZshColor.ANSI_BLUE + "Please input the size of map (N, M): " + ZshColor.ANSI_RESET);
			try {
				N = scan.nextInt();
				M = scan.nextInt();
				if (N <= 0 || M <= 0) {
					System.out.println("Error! Invalid input. Please enter two positive integers separated by a white space!");
				}
			} catch(Exception e) {
				System.out.println("Error! Invalid input. Please enter two positive integers separated by a white space!");
				scan.nextLine();
			}
		} while (N <= 0 || M <= 0);
		scan.nextLine();
		gameMap = new TheQuestMap(N, M);
		gameMap.printMap();
		
		// simulation
		boolean quitTheGame = false;
		TeamTheQuest team = new TeamTheQuest();
		while (!quitTheGame) {
			// Bulid/Rebuild your team
			if (team.getMembers().size() == 0) {
				System.out.println(ZshColor.ANSI_RED + " - Please build your team first.");
				// Create a team
				team = new TeamTheQuest();
				team.getSize(scan);
				team.buildTeam(scan);
			}
			
			/*
			 * Make a move!
			 * Two conditions that ends the do-while loop
			 *  - 1. Made a valid move like W/A/S/D
			 *  - 2. Player wants to quit the game
			 */
			boolean finishMove = false;
			do {
				System.out.println("\n" + ZshColor.ANSI_RED + " - What would you like to do Now?");
				System.out.println(ZshColor.ANSI_CYAN + " 1. T");
				System.out.println(ZshColor.ANSI_GREEN + "     T->Display status of team members");
				System.out.println(ZshColor.ANSI_CYAN + " 2. C");
				System.out.println(ZshColor.ANSI_GREEN + "     C->Check inventories of current team");
				System.out.println(ZshColor.ANSI_CYAN + " 3. W/A/S/D");
				System.out.println(ZshColor.ANSI_GREEN + "     W->move up, A->move left, S->move down, D->move right");
				System.out.println(ZshColor.ANSI_CYAN + " 4. U/I");
				System.out.println(ZshColor.ANSI_GREEN + "     U->equip/change weapon, I->equip/change armor");
				System.out.println(ZshColor.ANSI_CYAN + " 5. P");
				System.out.println(ZshColor.ANSI_GREEN + "     P->Display the map");
				System.out.println(ZshColor.ANSI_CYAN + " 6. E");
				System.out.println(ZshColor.ANSI_GREEN + "     E->Edit/Rebuild your lineup");
				System.out.println(ZshColor.ANSI_CYAN + " 7. Q");
				System.out.println(ZshColor.ANSI_GREEN + "     Q->quit the game"); //  + ZshColor.ANSI_RESET
				System.out.println(ZshColor.ANSI_RESET);
				String str = scan.nextLine();
				char c = str.charAt(0);
				if (str.length() > 1) {
					// Invalid input
				}
				else if (c == 'Q') {
					quitTheGame = true;
					finishMove = true; // --> break
				}
				else if (c == 'T') {
					System.out.println(ZshColor.ANSI_BLUE + team.toString());
				}
				else if (c == 'U') {
					team.changeWeapon(scan, -1);
				}
				else if (c == 'I') {
					team.changeArmor(scan, -1);
				}
				else if (c == 'P') {
					gameMap.printMap();
				}
				else if (c == 'C') {
					System.out.println(team.getInventories());
				}
				else if (c == 'E') {
					System.out.println(ZshColor.ANSI_RED + " - Let's rebuild your lineup.");
					team = new TeamTheQuest();
					team.getSize(scan);
					team.buildTeam(scan);
				}
				else if (c == 'W' || c == 'A' || c == 'S' || c == 'D') {
					boolean valid = gameMap.move(c); // make move
					if (!valid) {
						// If that move is invalid
						System.out.println(ZshColor.ANSI_RED + "It's an invalid move!");
					}
					else {
						gameMap.printMap();
						Cells currentCell = gameMap.whereIsHeroes();
						if (currentCell instanceof CommonCells) {
							// If heroes stands on a common cell
							if (Probability.estimate(Encounter.Encounter_Rate)) {
								// Encounter rate: 75%
								System.out.println(ZshColor.ANSI_PURPLE + " - You encounter monters!");
								team.generateRivals();
								Rounds rounds = new Rounds(scan, team, 0.05);
								rounds.fight(0, 0, 0);
							}
						}
						else if (currentCell instanceof Markets) {
							// If heroes arrive at a market
							System.out.println(ZshColor.ANSI_PURPLE + " - Welcome to the supermarket!");
							System.out.println(ZshColor.ANSI_RED + " - Tips: Without spells and weapons, you could hardly beat a monster.");
							String doBusiness;
							do {
								System.out.println(ZshColor.ANSI_RED + " - Would you like to buy/sell items?  Yes/No" + ZshColor.ANSI_RESET);
								doBusiness = scan.nextLine();
								if (doBusiness.compareTo("Yes") == 0) {
									Markets.trade(scan, team);
								}
							} while (doBusiness.compareTo("No") != 0);
						}
						finishMove = true; // --> break
					}
				}
			} while (!finishMove);
		}
	}
	
	@Override
	public void initialize(String fileName) {
		// TODO- Text Parse
		
		
	}
	
	@Override
	public void initialize() {
		// heroes
		Heroes.heroes.add(new Warriors("Gaerdal_Ironhand", 100, 700, 500, 600, 1354, 7));
		Heroes.heroes.add(new Warriors("Sehanine_Monnbow", 600, 700, 800, 500, 2500, 8));
		Heroes.heroes.add(new Warriors("Muamman_Duathall", 300, 900, 500, 750, 2546, 6));
		Heroes.heroes.add(new Warriors("Flandal_Steelskin", 200, 750, 650, 700, 2500, 7));
		
		
		Heroes.heroes.add(new Sorcerers("Garl_Glittergold", 700, 550, 600, 500, 2500, 7));
		Heroes.heroes.add(new Sorcerers("Rillifane_Rallathil", 1300, 750, 450, 500, 2500, 9));
		Heroes.heroes.add(new Sorcerers("Segojan_Earthcaller", 900, 800, 500, 650, 2500, 5));
		Heroes.heroes.add(new Sorcerers("Skoraeus_Stonebones", 800, 850, 600, 450, 2500, 6));
		
		Heroes.heroes.add(new Paladins("Solonor_Thelandira", 300, 750, 650, 700, 2500, 7));
		Heroes.heroes.add(new Paladins("Everyone_Safe", 300, 750, 700, 700, 2500, 7));
		Heroes.heroes.add(new Paladins("Wear_Masks", 250, 650, 600, 350, 2500, 4));
		Heroes.heroes.add(new Paladins("Stop_Coronavirus", 100, 600, 500, 400, 2500, 5));
		
		// monsters
		Monsters.monsters.add(new Dragons("Desghidorrah", 3, 300, 400, 35));
		Monsters.monsters.add(new Dragons("Chrysophylax", 2, 200, 500, 20));
		Monsters.monsters.add(new Dragons("BunsenBurner", 4, 400, 500, 45));
		Monsters.monsters.add(new Dragons("Natsunomeryu", 1, 100, 200, 10));
		Monsters.monsters.add(new Dragons("TheScaleless", 7, 700, 600, 75));
		Monsters.monsters.add(new Dragons("Kas-Ethelinh", 5, 600, 500, 60));
		Monsters.monsters.add(new Dragons("Alexstraszan", 10, 1000, 9000, 55));
		Monsters.monsters.add(new Dragons("Phaarthurnax", 6, 600, 700, 60));
		Monsters.monsters.add(new Dragons("D-Maleficent", 9, 900, 950, 85));
		Monsters.monsters.add(new Dragons("TheWeatherbe", 8, 800, 900, 80));
		
		Monsters.monsters.add(new Exoskeletons("Cyrrollalee", 7, 700, 800, 75));
		Monsters.monsters.add(new Exoskeletons("Brandobaris", 3, 350, 450, 30));
		Monsters.monsters.add(new Exoskeletons("BigBad-Wolf", 1, 150, 250, 15));
		Monsters.monsters.add(new Exoskeletons("WickedWitch", 2, 250, 350, 25));
		Monsters.monsters.add(new Exoskeletons("Aasterinian", 4, 400, 500, 45));
		Monsters.monsters.add(new Exoskeletons("Chronepsish", 6, 650, 750, 60));
		Monsters.monsters.add(new Exoskeletons("Kiaransalee", 8, 850, 950, 85));
		Monsters.monsters.add(new Exoskeletons("St-Shargaas", 5, 550, 650, 55));
		Monsters.monsters.add(new Exoskeletons("Merrshaullk", 10, 1000, 900, 55));
		Monsters.monsters.add(new Exoskeletons("St-Yeenoghu", 9, 950, 850, 90));
				
		Monsters.monsters.add(new Spirits("Andrealphus", 2, 600, 500, 40));
		Monsters.monsters.add(new Spirits("Aim-Haborym", 1, 450, 350, 35));
		Monsters.monsters.add(new Spirits("Andromalius", 3, 550, 450, 25));
		Monsters.monsters.add(new Spirits("Chiang-shih", 4, 700, 600, 40));
		Monsters.monsters.add(new Spirits("FallenAngel", 5, 800, 700, 50));
		Monsters.monsters.add(new Spirits("Ereshkigall", 6, 950, 450, 35));
		Monsters.monsters.add(new Spirits("Melchiresas", 7, 350, 150, 75));
		Monsters.monsters.add(new Spirits("Jormunngand", 8, 600, 900, 20));
		Monsters.monsters.add(new Spirits("Rakkshasass", 9, 550, 600, 35));
		Monsters.monsters.add(new Spirits("Taltecuhtli", 10, 300, 200, 50));
		
		// sort all these monsters with respect to their level
		Collections.sort(Monsters.monsters);
		
		// weapons
		
		Markets.weapons.add(new Weapons("Sword", 500, 1, 800 * 5, 1));
		Markets.weapons.add(new Weapons("Bow", 300, 2, 500 * 5, 2));
		Markets.weapons.add(new Weapons("Scythe", 1000, 6, 1100 * 5, 2));
		Markets.weapons.add(new Weapons("Axe", 550, 5, 850 * 5, 1));
		Markets.weapons.add(new Weapons("Shield", 400, 1, 300 * 5, 1));
		Markets.weapons.add(new Weapons("TSwords", 1400, 8, 1600 * 5, 2));
		Markets.weapons.add(new Weapons("Dagger", 200, 1, 400 * 5, 1));
		
		// armors
		
		Markets.armors.add(new Armors("Platinum_Shield", 150, 1, 200));
		Markets.armors.add(new Armors("Breastplate", 350, 3, 600));
		Markets.armors.add(new Armors("Full_Body_Armor", 1000, 8, 1100));
		Markets.armors.add(new Armors("Wizard_Shield", 1200, 10, 1500));
		Markets.armors.add(new Armors("Speed_Boots", 550, 4, 600));
		
		// potions
		
		Markets.potions.add(new Potions("Healing_Potion", 250, 1, 100));
		Markets.potions.add(new Potions("Strength_Potion", 200, 1, 75));
		Markets.potions.add(new Potions("Magic_Potion", 350, 2, 100));
		Markets.potions.add(new Potions("Luck_Elixir", 500, 4, 65));
		Markets.potions.add(new Potions("Mermaid_Tears", 850, 5, 100));
		Markets.potions.add(new Potions("Ambrosia", 1000, 8, 150));
		
		// spells

		Markets.spells.add(new IceSpells("Snow_Canon", 500, 2, 650, 250));
		Markets.spells.add(new IceSpells("Ice_Blade", 250, 1, 450, 100));
		Markets.spells.add(new IceSpells("Frost_Blizzard", 750, 5, 850, 350));
		Markets.spells.add(new IceSpells("Arctic_storm", 700, 6, 800, 300));
		
		Markets.spells.add(new FireSpells("Flame_Tornado", 700, 4, 850, 300));
		Markets.spells.add(new FireSpells("Breath_of_Fire", 350, 1, 450, 100));
		Markets.spells.add(new FireSpells("Heat_Wave", 450, 2, 600, 150));
		Markets.spells.add(new FireSpells("Lava_Commet", 800, 7, 1000, 550));
		
		Markets.spells.add(new LightningSpells("LightningDagger", 400, 1, 500, 150));
		Markets.spells.add(new LightningSpells("Thunder_Blast", 750, 4, 950, 400));
		Markets.spells.add(new LightningSpells("Electric_Arrows", 550, 5, 650, 200));
		Markets.spells.add(new LightningSpells("Spark_Needles", 500, 2, 600, 200));
		
	}
	
}
