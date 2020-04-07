import java.util.ArrayList;
import java.util.Random;

public class TheQuestMap extends Map {

	// the location of heroes
	private int coorX = 0;
	private int coorY = 0;
	
	public TheQuestMap(int N, int M) {
		super(N, M);

		// Assume the heros stands on (0, 0) at the very beginning
		
		grid = new ArrayList<ArrayList<Cells>> ();
		for (int i = 0; i < this.N; i ++) {
			ArrayList<Cells> alCell = new ArrayList<Cells> ();
			for (int j = 0; j < this.M; j ++) {
				if (i == 0 && j == 0) {
					alCell.add(new CommonCells());
				}
				else if (i == 0 && j == 1) {
					alCell.add(new Markets());
				}
				else if (i == 1 && j == 0) {
					alCell.add(new Markets());
				}
				else {
					Random rand = new Random();
					int r = rand.nextInt(100) + 1;
					if (r <= 20) {
						alCell.add(new NonAccessibleCells());
					}
					else if (20 < r && r <= 50) {
						alCell.add(new Markets());
					}
					else if (r > 50) {
						alCell.add(new CommonCells());
					}
				}
			}
			grid.add(alCell);
		}
	}
	
	public Cells whereIsHeroes() {
		return grid.get(coorX).get(coorY);
	}
	
	public void printMap() {
		System.out.println(ZshColor.ANSI_RED + " - !!!Map!!!");
		System.out.println(ZshColor.ANSI_RED + " - Notations: H(Heros), XXX(Non-accessible cell), blank(Common cell)");
		System.out.print(ZshColor.ANSI_WHITE);
		for (int i = 0; i < M; i ++) {
			System.out.print("----");
		}
		System.out.println("-");
		
		for (int i = 0; i < N; i ++) {
			for (int j = 0; j < M; j ++) {
				System.out.print(ZshColor.ANSI_WHITE + "|");
				Cells cell = this.getGridAt(i, j);
				if (i == coorX && j == coorY) {
					if (cell instanceof Markets) {
						System.out.print(ZshColor.ANSI_RED + "H " + ZshColor.ANSI_GREEN + "M" + ZshColor.ANSI_WHITE);
					}
					else {
						System.out.print(ZshColor.ANSI_RED + " H " + ZshColor.ANSI_WHITE);
					}
				}
				else if (cell instanceof CommonCells) {
					System.out.print("   ");
				}
				else if (cell instanceof NonAccessibleCells) {
					System.out.print("XXX");
				}
				else if (cell instanceof Markets) {
					System.out.print(ZshColor.ANSI_GREEN + " M ");
				}
			}
			System.out.println(ZshColor.ANSI_WHITE + "|");
			for (int j = 0; j < M; j ++) {
				System.out.print("----");
			}
			if (i != N - 1) {
				System.out.println("|");
			}
			else {
				System.out.println("-");
			}
		}
	}
	
	/*
	 * Make move
	 * If the move is valid, return true
	 * Else return false
	 */
	public boolean move(char c) {
		int cx, cy;
		switch (c) {
			case 'W': cx = -1; cy = 0; break; // move up
			case 'A': cx = 0; cy = -1; break; // move left
			case 'S': cx = +1; cy = 0; break; // move down
			case 'D': cx = 0; cy = +1; break; // move right
			default: return false;
		}
		int nx = coorX + cx, ny = coorY + cy;
		if (nx < 0 || nx >= N || ny < 0 || ny >= M) {
			return false;
		}
		if (this.getGridAt(nx, ny) instanceof NonAccessibleCells) {
			return false;
		}
		coorX = nx;
		coorY = ny;
		return true;
	}
	
}
