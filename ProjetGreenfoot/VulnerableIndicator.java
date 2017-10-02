import greenfoot.Actor;

/**
 * Classe repr�sentant un indicateur de vuln�rabilit�.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public class VulnerableIndicator extends Actor {
	public static final int OFFSET = 3 * PacManWorld.CELL_SIZE / 4; // Position relative de l'indicateur par rapport � PacMan
	
	private Ghost ghost;
	
	/**
	 * Constructeur
	 * @param ghost Fant�me associ�
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
