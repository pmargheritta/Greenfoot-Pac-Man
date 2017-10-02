import greenfoot.Actor;

/**
 * Classe représentant un indicateur de vulnérabilité.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class VulnerableIndicator extends Actor {
	public static final int OFFSET = 3 * PacManWorld.CELL_SIZE / 4; // Position relative de l'indicateur par rapport à PacMan
	
	private Ghost ghost;
	
	/**
	 * Constructeur
	 * @param ghost Fantôme associé
	 */
	public VulnerableIndicator(Ghost ghost) {
		this.ghost = ghost;
	}
	
	public void act() {
		if (ghost.isVulnerable()) {
			setLocation(ghost.getX(), ghost.getY() - OFFSET);
		}
	}
}
