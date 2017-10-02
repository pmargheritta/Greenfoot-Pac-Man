/**
 * Classe repr�sentant un fant�me orange.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public class OrangeGhost extends Ghost {
	/**
     * Constructeur
     * @param initialCell Cellule de d�part
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
