import java.util.ArrayList;
import java.util.Random;

public class TheQuestOfLegendsMap extends Map {
	
	public TheQuestOfLegendsMap() {
		super(8, 8);
		
		/*
		 * Generate the board/map
		 * The type of each cell is randomly assigned
		 */
		grid = new ArrayList<ArrayList<Cells>> ();
		for (int i = 0; i < 8; i ++) {
			ArrayList<Cells> alCell = new ArrayList<Cells> ();
			for (int j = 0; j < 8; j ++) {
				if (i == 0 || i == 7) {
					if (j == 2 || j == 5) {
						alCell.add(new NonAccessibleCells());
					}
					else {
						alCell.add(new NexusCells()); // Market
					}
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
	}

	public void printMap() {
		System.out.println(ZshColor.ANSI_RED + " - !!!Map!!!");
		System.out.println(ZshColor.ANSI_RED + " - Notations: H(Heros), XXX(Non-accessible cell), blank(Common cell)");
		System.out.print(ZshColor.ANSI_WHITE);
		
		for (int i = 0; i < 9; i ++) {
			for (int j = 0; j < 3; j ++) {
				
				if (j == 0) {
					for (int k = 0; k < 9; k ++) {
						System.out.print(grid.get(i).get(k));
					}
				}
				else if (j == 1) {
					for (int k = 0; k < 9; k ++) {
						 
					}
				}
				else if (j == 2) {
					for (int k = 0; k < 9; k ++) {
						
					}
				}
				
			}
		}
		
	}
	
}
