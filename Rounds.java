import java.util.ArrayList;
import java.util.Scanner;

public class Rounds implements Fight {
	private TeamTheQuest team = null;
	private Scanner scan;
	private ArrayList<Roles> members = null;
	private ArrayList<Roles> rivals = null;
	private double paramRegen;
	
	public Rounds(Scanner scan, TeamTheQuest team, double paramRegen) {
		this.scan = scan;
		this.team = team;
		this.members = team.getMembers();
		this.rivals = team.getRivals();
		this.paramRegen = paramRegen;
	}
	
	public boolean areHeroesLose() {
		for (Roles role: members) {
			if (role.getHp() > 0) { // Hp > 0
				return false;
			}
		}
		return true;
	}
	
	public boolean areHeroesWin() {
		for (Roles role: rivals) {
			if (role.getHp() > 0) { // Hp > 0
				return false;
			}
		}
		return true;
	}
	
	public void displayStatus() {
		System.out.println(ZshColor.ANSI_BLUE + team.toString() + "\n");
		System.out.println(ZshColor.ANSI_BLUE + team.getRivalStatus() + "\n");
	}
	
	/*
	 * 1. Heroes take actions first
	 *    - Drink a potion
	 *    - Cast a spell
	 *    - Attack
	 *    - Change Weapon/Armor
	 * 2. then monsters
	 * 
	 * 3. Ability Augmentation
	 * param1: STR *= (1 + param1)
	 * param2: AGI *= (1 + param2)
	 * param3: DEX *= (1 + param3)
	 */
	public void singleRound(double param1, double param2, double param3) {
		
		// Heroes' turn
		System.out.println(ZshColor.ANSI_YELLOW + "--------------------------------------------------"
				+ "------------------------------------------------------");
		System.out.println(ZshColor.ANSI_RED + " - Welcome to a new round.");
		for (int i = 0; i < team.getNum(); i ++) {
			displayStatus();
			if (members.get(i).getHp() <= 0) {
				// if this hero is faint
				continue;
			}
			String str = "";
			do {
				System.out.println(ZshColor.ANSI_RED + " - Hello, " + members.get(i).getName());
				System.out.println(ZshColor.ANSI_RED + " - It's your turn. What would you like to do?");
				System.out.println(ZshColor.ANSI_RED + " - Any following action will consume your action point. "
						+ "---> You can only take one move this term.");
				System.out.println(ZshColor.ANSI_CYAN + " 1. A");
				System.out.println(ZshColor.ANSI_GREEN + "     A->Attack");
				System.out.println(ZshColor.ANSI_CYAN + " 2. C");
				System.out.println(ZshColor.ANSI_GREEN + "     C->Cast a spell");
				System.out.println(ZshColor.ANSI_CYAN + " 3. D");
				System.out.println(ZshColor.ANSI_GREEN + "     D->Drink a potion");
				System.out.println(ZshColor.ANSI_CYAN + " 4. U/I");
				System.out.println(ZshColor.ANSI_GREEN + "     U->equip/change weapon, I->equip/change armor");
				System.out.println(ZshColor.ANSI_CYAN + " 5. Disp");
				System.out.println(ZshColor.ANSI_GREEN + "     Disp->Display ");
				System.out.println(ZshColor.ANSI_RED + " - Warning: If a hero has no potions but want to drink, "
						+ "he will lose the chance to make move at this round.");
				System.out.println(ZshColor.ANSI_RED + " - Warning: If a hero has never learned any spells but want to cast one, "
						+ "he will lose the chance to make move at this round.");
				System.out.println(ZshColor.ANSI_RED + " - Warning: If a hero wants to cast a spell without enough Mana, "
						+ "he will lose the chance to make move at this round.");
				System.out.println(ZshColor.ANSI_RESET);
				str = scan.nextLine();
			} while (str.compareTo("A") != 0 && str.compareTo("C") != 0 && str.compareTo("D") != 0 
					&& str.compareTo("U") != 0 && str.compareTo("I") != 0 && str.compareTo("Disp") != 0);
			
			Backpack backpack = ((Heroes) members.get(i)).getBackpack();
			if (str.compareTo("U") == 0) {
				team.changeWeapon(scan, -1);
			}
			else if (str.compareTo("I") == 0) {
				team.changeArmor(scan, -1);
			}
			else if (str.compareTo("Disp") == 0) {
				displayStatus();
			}
			else if (str.compareTo("D") == 0 ) {
				// Drink a potion
				int size_t = backpack.getPotions().size();
				if (size_t == 0) {
					System.out.println(ZshColor.ANSI_GREEN  + " - Hey! This guy has no potions.");
				}
				else {
					// Display all potions he has
					System.out.println(ZshColor.ANSI_BLUE + backpack.dispPotions() + ZshColor.ANSI_RESET);
					int idx = 0;
					do {
						System.out.println(ZshColor.ANSI_RED + " - Which do you gonna drink?");
						try {
							idx = scan.nextInt();
						} catch (Exception e) {
							System.out.println(ZshColor.ANSI_RED + " - Invalid input!");
							scan.nextLine();
						}
					} while (idx <= 0 || idx > size_t);
					scan.nextLine();
					((Heroes) members.get(i)).drinkAPotion(idx - 1);
				}
			}
			else {
				// Get the target that should be attacked or casted spells
				int target = 0;
				do {
					System.out.println(ZshColor.ANSI_RED + " - Which monster is your target? Please input its index: \n"
							+ "Please don't aim at a monster that is already dead!" + ZshColor.ANSI_RESET);
					try {
						target = scan.nextInt();
					} catch (Exception e) {
						System.out.println(ZshColor.ANSI_RED + " - Invalid input!");
						scan.nextLine();
					}
				} while (target <= 0 || target > rivals.size() || (rivals.get(target - 1).getHp() <= 0));
				scan.nextLine();
				
				if (str.compareTo("A") == 0) {
					// Attack
					if (Probability.estimate(0.01 * ((Monsters) rivals.get(target - 1)).getDodgeChance())) {
						// if the monster dodges the attack
						System.out.println(ZshColor.ANSI_GREEN + " - The monster dodges your attack!");
						continue;
					}
					// Calculate the damage of one single hit
					double oneHit = ((Heroes) members.get(i)).getSTR() * 0.05 * (1 + param1);
					if (((Heroes) members.get(i)).getCurWeapon() != null) {
						oneHit += ((Weapons) ((Heroes) members.get(i)).getCurWeapon()).getDamage() * 0.05;
					}
					oneHit -= ((Monsters) rivals.get(target - 1)).getDefense();
					if (oneHit <= 0) {
						// if oneHit <= 0
						oneHit = 0;
					}
					rivals.get(target - 1).changeHp(-oneHit);
					System.out.println(ZshColor.ANSI_GREEN + " - The attack cause " + String.valueOf(oneHit) + " damage.");
				}
				else if (str.compareTo("C") == 0) {
					// Cast a spell
					int size_t = backpack.getSpells().size();
					if (size_t == 0) {
						System.out.println(ZshColor.ANSI_GREEN + " - Hey! He doesn't know any spells.");
					}
					else {
						// Display all spells he's able to use
						System.out.println(ZshColor.ANSI_BLUE + backpack.dispSpells());
						int idx = 0;
						do {
							System.out.println(ZshColor.ANSI_RED + " - Which do you gonna cast?" + ZshColor.ANSI_RESET);
							try {
								idx = scan.nextInt();
							} catch (Exception e) {
								System.out.println(ZshColor.ANSI_RED + " - Invalid input!");
								scan.nextLine();
							}
						} while (idx <= 0 || idx > size_t);
						scan.nextLine();
						if (((Heroes ) members.get(i)).getMana() >= ((Spells) backpack.getSpells().get(idx - 1)).getManaCost()) {
							// if the hero has enough Mana
							
							if (Probability.estimate(0.01 * ((Monsters) rivals.get(target - 1)).getDodgeChance())) {
								// if the monster dodges the spell
								System.out.println(ZshColor.ANSI_GREEN + " - The monster dodges your spell!");
								continue;
							}
							
							// Calculate the damage of one single hit
							double oneHit = ((Spells) backpack.getSpells().get(idx - 1)).getDamage() 
									* (1 + ((Heroes ) members.get(i)).getDEX() * (1 + param3) / 10000);
							rivals.get(target - 1).changeHp(-oneHit);
							((Spells) backpack.getSpells().get(idx - 1)).deteriorate(rivals.get(target - 1));
							((Heroes ) members.get(i)).changeMana(-((Spells) backpack.getSpells().get(idx - 1)).getManaCost());
							System.out.println(ZshColor.ANSI_GREEN + " - The spell cause " + String.valueOf(oneHit) + " damage.");
							System.out.println(((Spells) backpack.getSpells().get(idx - 1)).deterioration() + ZshColor.ANSI_RESET);
						}
						else {
							System.out.println(ZshColor.ANSI_GREEN + " - Sorry. That hero doesn't have enough Mana.");
						}
					}
				}
			}
		}
		
		/*
		 * Monsters' turn:
		 * The i-th monster only attack the i-th hero when the Hp of that hero is > 0
		 * The i-th monster will attack other targets after making the i-th hero faint
		 */
		if (!areHeroesWin()) {
			// If at least one monster survive, we need to output combat log
			System.out.println(ZshColor.ANSI_GREEN + "\n - Combat Log for Monsters:");
		}
		for (int i = 0; i < team.getNum(); i ++) {
			if (rivals.get(i).getHp() <= 0) {
				// if this monster is not alive
				continue;
			}
			if (members.get(i).getHp() > 0) {
				// if the Hp of the i-th hero is greater than 0, just attack him
				if (Probability.estimate(0.01 * ((Heroes) members.get(i)).getDodgeChance() * (1 + param2))) {
					// if the hero dodges the attack
					System.out.println(ZshColor.ANSI_GREEN + " - " + members.get(i).getName() 
							+ " dodges an attack from " + rivals.get(i).getName() + "!");
					continue;
				}
				double oneHit = ((Monsters) rivals.get(i)).getDamage();
				if (((Heroes) members.get(i)).getCurArmor() != null) {
					oneHit -= ((Armors) ((Heroes) members.get(i)).getCurArmor()).getDamageReduction();
				}
				if (oneHit <= 0) {
					// if oneHit <= 0
					oneHit = 0;
				}
				members.get(i).changeHp(-oneHit);
				System.out.println(ZshColor.ANSI_GREEN + " - " + rivals.get(i).getName() 
						+ " cause " + String.valueOf(oneHit) + " damage to " + members.get(i).getName() + "!");
			}
			else {
				// find another target
				int target = 0;
				for (int j = 0; j < team.getNum(); j ++) {
					if (j == i) {
						continue;
					}
					else if (members.get(j).getHp() > 0) {
						target = j;
						break;
					}
				}
				double oneHit = ((Monsters) rivals.get(i)).getDamage();
				if (((Heroes) members.get(target)).getCurArmor() != null) {
					oneHit -= ((Armors) ((Heroes) members.get(target)).getCurArmor()).getDamageReduction();
				}
				if (oneHit <= 0) {
					// if oneHit <= 0
					oneHit = 0;
				}
				members.get(target).changeHp(-oneHit);
				System.out.println(ZshColor.ANSI_GREEN + " - " + rivals.get(i).getName() 
						+ " cause " + String.valueOf(oneHit) + " damage to " + members.get(target).getName() + "!");
			}
		}
			
	}
	
	public void regeneration() {
		// Only heroes who are not faint cound get Hp/Mana regen
		for (Roles role: members) {
			if (role.getHp() > 0) { // Hp > 0
				((Heroes) role).Hp_Mana_Regen(paramRegen);
			}
		}
	}
	
	public boolean fight(double param1, double param2, double param3) {
		boolean gameOver = false;
		boolean heroesWin = false;
		do {
			if (areHeroesLose()) {
				System.out.println(ZshColor.ANSI_YELLOW + "\n - You got beated by monsters!\n"
						+ " - 60% of your money are lost.\n"
						+ " - All the heroes currently in your team will get back 40 % of Hp\n"
						+ " - Try to get more powerful weapons and spells!");
				// Rules: 
				// Angel helps revive all dead heroes
				// Heroes lose half of their money and only get back 40% of their Hp
				// They won't get any Exp
				for (Roles role: members) {
					((Heroes) role).revive(0.4);
				}
				gameOver = true;
			}
			else if (areHeroesWin()) {
				int level = rivals.get(0).getLevel();
				for (Roles role: members) {
					if (role.getHp() > 0) { // Hp > 0
						((Heroes) role).changeMoney(100.0 * level);
						((Heroes) role).changeExperience(2);
					}
					else {
						((Heroes) role).revive(0.5);
					}
				}
				for (Roles role: members) {
					if (((Heroes) role).hasEnoughExp()) {
						((Heroes) role).levelUp();
						System.out.println("\n - " + role.getName() + " LEVEL UP!\n");
					}
				}
				System.out.println(ZshColor.ANSI_YELLOW + "\n - Congratulation! Your team defeat the monters!\n"
						+ " - Bonuses are added to specific heroes.");
				gameOver = true;
				heroesWin = true;
			}
			else {
				// this fight is still ongoing
				singleRound(param1, param2, param3);
				regeneration();
			}
			
		} while (!gameOver);
		return heroesWin;
	}
	
}
