import java.util.Random;

public class Probability {

	/*
	 * 
	 * @percent: the probability of an event
	 * Estimate if a event will occur
	 * 
	 */
	public static boolean estimate(double percent) {
		Random rand = new Random();
		int r = rand.nextInt(100) + 1;
		if (r <= 100 * percent) {
			return true;
		}
		return false;
	}
	
}
