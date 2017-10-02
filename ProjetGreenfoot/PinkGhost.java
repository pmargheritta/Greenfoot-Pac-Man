/**
 * Classe représentant un fantôme rose.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class PinkGhost extends PathFollowingGhost {
	private Cell targetCell;
	
	/**
     * Constructeur
     * @param initialCell Cellule de départ
     */
	public PinkGhost(Cell initialCell) {
		super(initialCell);
	}
	
	public void act() {
		super.act();
		updateImage("pinkghost");
		updatePath();	
	}
	
	/**
	 * Paramétrer la cellule cible.
	 * @param nextCell Cellule cible
	 */
	public void setTargetCell(Cell nextCell) {
		this.targetCell = nextCell;
	}
	
	public void updatePath() {
		if (world() != null && targetCell != null) {
			if (targetCell.equals(cell())) {
				// Le fantôme est arrivé à destination : on choisit un nouveau chemin
				path.clear();
				targetCell = null;
			}
			else {
				path = world().level().shortestPath(cell(), targetCell);
				isPathDefined = true;
			}
		}
	}
}
