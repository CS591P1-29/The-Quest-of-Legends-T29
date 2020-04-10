
public interface Fight {
	
	/*
	 * Params: Ability Augmentation
	 * param1: STR *= (1 + param1)
	 * param2: AGI *= (1 + param2)
	 * param3: DEX *= (1 + param3)
	 */
	
	boolean fight(double param1, double param2, double param3);
	
	void singleRound(double param1, double param2, double param3);
	
}
