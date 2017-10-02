/**
 * Classe représentant un fantôme rouge.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class RedGhost extends Ghost {
	private boolean isChasing;
	private Turn nextTurn;
	
	/**
     * Constructeur
     * @param initialCell Cellule de départ
     */
	public RedGhost(Cell initialCell) {
		super(initialCell);
		isChasing = false;
	}
	
	public void act() {
		super.act();
		updateImage("redghost");
	}
	
	public void move() {
		super.move();
		
		// Activer la poursuite de PacMan dès qu'il est en vue
		if (seesPacMan() && !isVulnerable) {
			isChasing = true;
		}
		
		// Relancer PacMan en cas de blocage de la poursuite
		if (speed == 0) {
			isChasing = false;
		}
		
		// Mouvement aléatoire sauf en cas de poursuite
		if (isChasing && !isVulnerable) {
			chase();
		}
		else {
			randomMovement();
		}
	}
	
	/**
	 * Tester si PacMan est en vue.
	 * @return Résultat du test
	 */
	public boolean seesPacMan() {
		int cellSize = PacManWorld.CELL_SIZE;
		int i = 1;
		boolean isObstacle = false, isPacMan = false, inWorld = true;
		
		while (!isPacMan && !isObstacle && inWorld) {
			int dx = i * cellSize * dx(direction), dy = i * cellSize * dy(direction);
			isObstacle = (getOneObjectAtOffset(dx, dy, Obstacle.class) != null);
			isPacMan = (getOneObjectAtOffset(dx, dy, PacMan.class) != null);
			inWorld = (getX() + dx >= 0) && (getX() + dx < PacManWorld.WIDTH) &&
					  (getY() + dy >= 0) && (getY() + dy < PacManWorld.HEIGHT);
			i++;
		}
		
		return isPacMan;
	}
	
	/**
	 * Paramétrer le prochain virage du fantôme.
	 * @param nextTurn Prochain virage
	 */
	public void setNextTurn(Turn nextTurn) {
		if (seesPacMan()) {
			this.nextTurn = nextTurn;
		}
		else {
			isChasing = false;
		}
	}
	
	/**
	 * Poursuivre PacMan.
	 */
	public void chase() {
		if (nextTurn != null && getX() == nextTurn.x() && getY() == nextTurn.y()) {
			direction = nextTurn.direction();
		}
	}
}
