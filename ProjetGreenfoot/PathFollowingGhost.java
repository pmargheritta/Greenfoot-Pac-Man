import java.util.List;

/**
 * Classe abstraite repr�sentant un fant�me pouvant suivre un chemin d�fini.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public abstract class PathFollowingGhost extends Ghost {
	protected Direction nextDirection;
	protected List<Turn> path;
	protected boolean isPathDefined;
		
	/**
     * Constructeur
     * @param initialCell Cellule de d�part
     */
	public PathFollowingGhost(Cell initialCell) {
		super(initialCell);
		direction = Direction.UNDEFINED;
		nextDirection = Direction.UNDEFINED;
		isPathDefined = false;
	}
	
	public void act() {
		super.act();
	}
	
	public void move() {
		super.move();
		
		// Suivre le chemin sauf en cas de vuln�rabilit�
		if (isPathDefined && !isVulnerable) {
			followPath();
		}
		else {
			randomMovement();
		}
	}
	
	/**
	 * Mettre � jour le chemin suivi par le fant�me.
	 */
	public abstract void updatePath();
	
	/**
	 * Suivre le chemin d�fini.
	 */
	public void followPath() {
		speed = maxSpeed();
		
		if (canMove(nextDirection) && !path.isEmpty()) {
    		direction = nextDirection;
    		path.remove(0);
		}
		
		if (path.isEmpty()) {
			isPathDefined = false;
		}
		else {
			nextDirection = path.get(0).direction();
		}
	}
}
