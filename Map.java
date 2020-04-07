import java.util.ArrayList;

public class Map {

	protected int N;
	protected int M;
	
	protected ArrayList<ArrayList<Cells>> grid;
	
	public Map(int N, int M) {
		this.N = N;
		this.M = M;
	}
	
	public Cells getGridAt(int x, int y) {
		return grid.get(x).get(y);
	}
	
	public void printMap() {
		// TODO Auto-generated method stub
		
	}

}
