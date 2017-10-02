import greenfoot.Greenfoot;
import java.util.Map;
import java.util.HashMap;

/**
 * Classe représentant un vie supplémentaire.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class Bonus extends Edible {
	// Association points / probabilités
	public static final Map<Integer, Integer> PROBS = new HashMap<Integer, Integer>();
	static {
		PROBS.put(100, 50);
		PROBS.put(300, 25);
		PROBS.put(500, 10);
		PROBS.put(700, 5);
		PROBS.put(1000, 4);
		PROBS.put(2000, 3);
		PROBS.put(3000, 2);
		PROBS.put(5000, 1);
	}
	
	private int points;
	
	/**
	 * Constructeur
	 */
	public Bonus() {
		points = points();
	}
	
	/**
	 * Tirer au sort le nombre de points associé au bonus.
	 * @return Nombre de points
	 */
	public int points() {
		int random = Greenfoot.getRandomNumber(100);
		
		int i = 0;
		for (Map.Entry<Integer, Integer> entry: PROBS.entrySet()) {
			if (random < i + entry.getValue()) {
				return entry.getKey();
			}
			i += entry.getValue();
		}
		
		return 0;
	}
	
	public void touched() {		
		world().game().addPoints(points);
		super.touched();
	}
}
