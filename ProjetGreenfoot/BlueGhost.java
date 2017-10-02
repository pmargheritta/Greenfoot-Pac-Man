/**
 * Classe représentant un fantôme bleu.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class BlueGhost extends PathFollowingGhost {
	/**
	 * Constructeur
	 * @param initialCell Cellule de départ
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
	 * Mettre à jour le chemin suivi par le fantôme.
	 */
	public void updatePath() {
		if (world() != null) {
			path = world().level().shortestPath(cell(), world().pacMan().cell());
			isPathDefined = true;
		}
	}
}
