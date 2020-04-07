import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Markets extends Cells implements BuyOrSell {

	private static final double sellParam = 0.5;
	public static ArrayList<Items> weapons = new ArrayList<Items> ();
	public static ArrayList<Items> armors = new ArrayList<Items> ();
	public static ArrayList<Items> potions = new ArrayList<Items> ();
	public static ArrayList<Items> spells = new ArrayList<Items> ();
	
	public static void trade(Scanner scan, Team team) {
		
		System.out.println(ZshColor.ANSI_PURPLE + team);
		int p = 0;
		// Which hero wants to do business?
		do {
			System.out.println(ZshColor.ANSI_RED + " - Which hero wants to do business? Please just input one index like 1 or 2 or 3" + ZshColor.ANSI_RESET);
			try {
				p = scan.nextInt();
			} catch (Exception e) {
				System.out.println(ZshColor.ANSI_RED + "Invalid input!");
				scan.nextLine();
			}
			
		} while (p < 1 || p > team.getNum());
		scan.nextLine();
		
		// Buy or Sell?
		String tradeType = "";
		do {
			System.out.println(ZshColor.ANSI_RED + " - What kind of business? Buy or Sell?" + ZshColor.ANSI_RESET);
			tradeType = scan.nextLine();
		} while (tradeType.compareTo("Buy") != 0 && tradeType.compareTo("Sell") != 0);
		
		if (tradeType.compareTo("Buy") == 0) {
			buy(scan, team.getMembers().get(p - 1));
		}
		else {
			sell(scan, team.getMembers().get(p - 1));
		}
		
	}
	
	private static void sell(Scanner scan, Roles role) {
		Backpack backpack = ((Heroes) role).getBackpack();
		int bpSize = backpack.getWeapons().size() + backpack.getArmors().size() + 
				backpack.getPotions().size() + backpack.getSpells().size();
		if (bpSize == 0) {
			System.out.println(ZshColor.ANSI_RED + "Hey! You have nothing to sell!");
		}
		else {
			// Display all items in the backpack
			displayAllItems(backpack.getWeapons(), backpack.getArmors(), backpack.getPotions(), backpack.getSpells(), 1);
			int idx = 0;
			do {
				System.out.println(ZshColor.ANSI_RED + " - Which Item you would like to sell? Please input its index" + ZshColor.ANSI_RESET);
				try {
					idx = scan.nextInt();
				} catch (Exception e) {
					System.out.println(ZshColor.ANSI_RED + "Invalid input!");
					scan.nextLine();
				}
			} while (idx > bpSize || idx <= 0);
			scan.nextLine();
			
			//
			System.out.println(ZshColor.ANSI_RED + " - Before selling");
			System.out.println(ZshColor.ANSI_BLUE + role.toString());
			
			// delete the idx-th item
			if (idx <= backpack.getWeapons().size()) {
				((Heroes) role).changeMoney(sellParam * deleteItem(idx, backpack.getWeapons(), role));
			}
			else {
				int idx_2 = idx - backpack.getWeapons().size();
				if (idx_2 <= backpack.getArmors().size()) {
					((Heroes) role).changeMoney(sellParam * deleteItem(idx_2, backpack.getArmors(), role));
				}
				else {
					int idx_3 = idx_2 - backpack.getArmors().size();
					if (idx_3 <= backpack.getPotions().size()) {
						((Heroes) role).changeMoney(sellParam * deleteItem(idx_3, backpack.getPotions(), role));
					}
					else {
						int idx_4 = idx_3 - backpack.getPotions().size();
						((Heroes) role).changeMoney(sellParam * deleteItem(idx_4, backpack.getSpells(), role));
					}
				}
			}
			
			//
			System.out.println(ZshColor.ANSI_RED + " - After selling");
			System.out.println(ZshColor.ANSI_BLUE + role.toString());
			
		}
		
	}

	private static void buy(Scanner scan, Roles role) {
		displayAllItems(weapons, armors, potions, spells, 0);
		int bpSize = weapons.size() + armors.size() + potions.size() + spells.size();
		int idx = 0;
		do {
			System.out.println(ZshColor.ANSI_RED + " - Which Item you would like to buy? Please input its index" + ZshColor.ANSI_RESET);
			try {
				idx = scan.nextInt();
			} catch (Exception e) {
				System.out.println(ZshColor.ANSI_RED + " - Invalid input!");
				scan.nextLine();
			}
		} while (idx > bpSize || idx <= 0);
		scan.nextLine();
		
		Items item = null;
		if (idx <= weapons.size()) {
			item = findItem(idx, weapons);
		}
		else {
			int idx_2 = idx - weapons.size();
			if (idx_2 <= armors.size()) {
				item = findItem(idx_2, armors);
			}
			else {
				int idx_3 = idx_2 - armors.size();
				if (idx_3 <= potions.size()) {
					item = findItem(idx_3, potions);
				}
				else {
					int idx_4 = idx_3 - potions.size();
					item = findItem(idx_4, spells);
				}
			}
		}
		
		if (item.getPrice() - ((Heroes) role).getMoney() > Roles.eps) {
			System.out.println(ZshColor.ANSI_RED + " - Hey! You don't have enough money!");
		}
		else if (((Heroes) role).getLevel() < item.getRequiredLevel()) {
			System.out.println(ZshColor.ANSI_RED + " - Hey! You don't meet the level requirement!");
		}
		else {
			((Heroes) role).changeMoney(- item.getPrice());
			if (item instanceof Weapons) {
				((Heroes) role).getBackpack().addWeapons(item);
			}
			else if (item instanceof Armors) {
				((Heroes) role).getBackpack().addArmors(item);
			}
			else if (item instanceof Potions) {
				((Heroes) role).getBackpack().addPotions(item);
			}
			else if (item instanceof Spells) {
				((Heroes) role).getBackpack().addSpells(item);
			}
			System.out.println(ZshColor.ANSI_GREEN + " - That item has been shipped to your backpack!");
		}
	}
	
	private static Items findItem(int idx, ArrayList<Items> items) {
		Iterator<Items> it = items.iterator();
		Items item = null;
		for (int i = 0; i < idx; i ++) {
			item = it.next();
		}
		return item;
	}
	
	private static double deleteItem(int idx, ArrayList<Items> items, Roles role) {
		Iterator<Items> it = items.iterator();
		Items item = null;
		for (int i = 0; i < idx; i ++) {
			item = it.next();
		}
		it.remove();
		// If A equiped weapon or armor is sold,
		// the curWeapon or curArmor should be set to null.
		// .equal() --> we compare the difference based on their address
		if (item.equals(((Heroes) role).getCurWeapon())) {
			((Heroes) role).changeCurWeapon(null);		
		}
		else if (item.equals(((Heroes) role).getCurArmor())) {
			((Heroes) role).changeCurArmor(null);
		}
		return item.getPrice();
	}
	
	public static void displayAllItems(ArrayList<Items> dWeapons, ArrayList<Items> dArmors, 
			ArrayList<Items> dPotions, ArrayList<Items> dSpells, int param) {
		if (param == 0) {
			System.out.println(ZshColor.ANSI_RED + " - Here is the list of all commodities:");
		}
		else if (param == 1) {
			System.out.println(ZshColor.ANSI_RED + " - Here are the items in your backpack:");
		}
		int idx = 0;
		// All weapons
		System.out.println(ZshColor.ANSI_RED + " - Weapons:");
		System.out.println(ZshColor.ANSI_YELLOW + " - Format:  Name/cost/level/damage/required hands" + ZshColor.ANSI_RESET);
		for (Items item: dWeapons) {
			idx ++;
			System.out.print("   " + String.valueOf(idx) + " ");
			System.out.println((Weapons) item);
		}
		if (dWeapons.size() == 0) {
			System.out.println("   None\n");
		}
		
		// All armors
		System.out.println(ZshColor.ANSI_RED + " - Armors:");
		System.out.println(ZshColor.ANSI_YELLOW + " - Format:  Name/cost/required level/damage reduction" + ZshColor.ANSI_RESET);
		for (Items item: dArmors) {
			idx ++;
			System.out.print("   " + String.valueOf(idx) + " ");
			System.out.println((Armors) item);
		}
		if (dArmors.size() == 0) {
			System.out.println("   None\n");
		}
		
		// All potions
		System.out.println(ZshColor.ANSI_RED + " - Potions:");
		System.out.println(ZshColor.ANSI_YELLOW + " - Format:  Name/cost/required level/attribute increase" + ZshColor.ANSI_RESET);
		for (Items item: dPotions) {
			idx ++;
			System.out.print("   " + String.valueOf(idx) + " ");
			System.out.println((Potions) item);
		}
		if (dPotions.size() == 0) {
			System.out.println("   None\n");
		}
		
		// All spells
		System.out.println(ZshColor.ANSI_RED + " - Spells:");
		System.out.println(ZshColor.ANSI_YELLOW + " - Format:  Type/Name/cost/required level/attribute increase" + ZshColor.ANSI_RESET);
		for (Items item: dSpells) {
			idx ++;
			System.out.print("   " + String.valueOf(idx) + " ");
			if (item instanceof IceSpells) {
				System.out.print(ZshColor.ANSI_WHITE + "IceSpell,  ");
			}
			else if (item instanceof FireSpells) {
				System.out.print(ZshColor.ANSI_WHITE + "FireSpell,  ");
			}
			else if (item instanceof LightningSpells) {
				System.out.print(ZshColor.ANSI_WHITE + "LightningSpell,  ");
			}
			System.out.println((Spells) item);
		}
		if (dSpells.size() == 0) {
			System.out.println("   None\n");
		}
	}
	
	
	// 换装 卖了
	// 打架
}
