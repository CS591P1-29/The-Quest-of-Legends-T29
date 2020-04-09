
public class Roles implements Comparable<Roles> {
	
	public static double eps = 1e-7; 
	
	protected String Name;
	protected int Level;
	protected double Hp;
	/* Position
	 * Used in The Quest Of Legends
	 */
	protected int coorX;
	protected int coorY;	
	
	public Roles(String Name, int Level, double Hp) {
		this.Name = Name;
		this.Level = Level;
		this.Hp = Hp;
	}
	

	@Override
	public int compareTo(Roles o) {
		return this.Level - o.Level;
	}
	
	public String getName() {
		return Name;
	}
	
	public int getLevel() {
		return Level;
	}
	
	public double getHp() {
		return Hp;
	}
	
	public void changeHp(double delta) {
		Hp += delta;
	}
	
	public void setX(int coorX) {
		this.coorX = coorX;
	}
	
	public void setY(int coorY) {
		this.coorY = coorY;
	}
	
	public int getX() {
		return coorX;
	}
	
	public int getY() {
		return coorY;
	}
	
}
