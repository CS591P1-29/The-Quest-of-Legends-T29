
public class Items {
	
	protected String Name;
	protected double Price;
	protected int Required_Level;
	
	public Items(String Name, double Price, int Required_Level) {
		this.Name = Name;
		this.Price = Price;
		this.Required_Level = Required_Level;
	}
	
	public String getName() {
		return Name;
	}
	
	public double getPrice() {
		return Price;
	}
	
	public int getRequiredLevel() {
		return Required_Level;
	}
	
}
