/**
 * Classe représentant un bonus consommable par PacMan.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class Energizer extends Edible {
	public static final int POINTS = 50;
	
	public void touched() {
		for (Ghost ghost: world().actors(Ghost.class)) {
			ghost.setVulnerable();
		}
		
		world().setGhostsInSuccession(0);
		world().game().addPoints(POINTS);
		
		super.touched();
	}
}
