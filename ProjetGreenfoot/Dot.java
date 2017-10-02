/**
 * Classe représentant une gomme consommable par PacMan.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class Dot extends Edible {
	public static final int POINTS = 10;
	
	public void touched() {
		world().game().addPoints(POINTS);
		super.touched();
	}
}
