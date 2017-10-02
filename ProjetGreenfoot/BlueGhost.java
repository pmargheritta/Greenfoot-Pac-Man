/**
 * Classe repr�sentant un fant�me bleu.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public class BlueGhost extends PathFollowingGhost {
	/**
	 * Constructeur
	 * @param initialCell Cellule de d�part
	 */
	public BlueGhost(Cell initialCell) {
		super(initialCell);
	}

	public void act() {
		super.act();
		updatePath();
		updateImage("blueghost");		
	}
	
	/**
	 * Mettre � jour le chemin suivi par le fant�me.
	 */
	public void updatePath() {
		if (world() != null) {
			path = world().level().shortestPath(cell(), world().pacMan().cell());
			isPathDefined = true;
		}
	}
}
