import java.util.List;

/**
 * Classe abstraite représentant un fantôme pouvant suivre un chemin défini.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public abstract class PathFollowingGhost extends Ghost {
	protected Direction nextDirection;
	protected List<Turn> path;
	protected boolean isPathDefined;
		
	/**
     * Constructeur
     * @param initialCell Cellule de départ
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
		
		// Suivre le chemin sauf en cas de vulnérabilité
		if (isPathDefined && !isVulnerable) {
			followPath();
		}
		else {
			randomMovement();
		}
	}
	
	/**
	 * Mettre à jour le chemin suivi par le fantôme.
	 */
	public abstract void updatePath();
	
	/**
	 * Suivre le chemin défini.
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
