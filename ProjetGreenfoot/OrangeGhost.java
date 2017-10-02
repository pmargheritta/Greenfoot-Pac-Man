/**
 * Classe représentant un fantôme orange.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class OrangeGhost extends Ghost {
	/**
     * Constructeur
     * @param initialCell Cellule de départ
     */
	public OrangeGhost(Cell initialCell) {
		super(initialCell);
	}
	
	public void act() {
		super.act();
		updateImage("orangeghost");
	}
	
	public void move() {
		super.move();
		randomMovement();
	}
}
