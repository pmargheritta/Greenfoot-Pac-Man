/**
 * Classe repr�sentant un fant�me rose.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public class PinkGhost extends PathFollowingGhost {
	private Cell targetCell;
	
	/**
     * Constructeur
     * @param initialCell Cellule de d�part
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
	 * Param�trer la cellule cible.
	 * @param nextCell Cellule cible
	 */
	public void setTargetCell(Cell nextCell) {
		this.targetCell = nextCell;
	}
	
	public void updatePath() {
		if (world() != null && targetCell != null) {
			if (targetCell.equals(cell())) {
				// Le fant�me est arriv� � destination : on choisit un nouveau chemin
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
