import greenfoot.Actor;

/**
 * Classe abstraite représentant un personnage capable de se déplacer.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public abstract class Moving extends Actor {
	public static final int RESPAWN_TIME = 120;
	
	protected Cell initialCell; // Mémoriser la position initiale pour la résurrection
	protected Direction direction;
	protected Direction nextDirection;
	protected int speed;
	
	protected StepCounter stepCounter;
	protected Timer respawnTimer;
	
	/**
	 * Constructeur
	 * @param initialCell Cellule de départ
	 */
	public Moving(Cell initialCell) {
		this.initialCell = initialCell;
		respawnTimer = new Timer(RESPAWN_TIME);
	}
	
	/**
	 * Obtenir le monde dans lequel se déplace le personnage.
	 * @return Monde
	 */
	public PacManWorld world() {
		return (PacManWorld) getWorld();
	}
	
	// GESTION DU MOUVEMENT
	
	/**
	 * Donner la vitesse du personnage lorsqu'il n'est pas à l'arrêt.
	 * @return Vitesse du personnage
	 */
	public abstract int maxSpeed();
	
	/**
	 * Déterminer le déplacement élémentaire en x dans une direction donnée.
	 * @param direction Direction de déplacement
	 * @return Déplacement élémentaire
	 */
	public static int dx(Direction direction) {
		switch (direction) {
			case LEFT:
				return -1;
			case RIGHT:
				return 1;
			default:
				return 0;
		}
	}
	
	/**
	 * Déterminer le déplacement élémentaire en y dans une direction donnée.
	 * @param direction Direction de déplacement
	 * @return Déplacement élémentaire
	 */
	public static int dy(Direction direction) {
		switch (direction) {
			case UP:
				return -1;
			case DOWN:
				return 1;
			default:
				return 0;
		}
	}
	
	/**
	 * Régler la vitesse du personnage à une vitesse donnée.
	 * @param speed Nouvelle vitesse
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Déplacer le personnage vers une position donnée en signalant les éventuels obstacles.
	 * @param x Abscisse de la position
	 * @param y Ordonnée de la position
	 */
	public void go(int x, int y) {    	
        int x0 = getX(), y0 = getY();
        setLocation(x, y);
        
        // Rencontre avec le bord du monde
        if (isAtEdge()) {
        	world().atEdge(this, x0, y0);
        }
        
        // Rencontre avec un obstacle
        Obstacle obstacle = (Obstacle) getOneIntersectingObject(Obstacle.class);
        if (obstacle != null) {
        	obstacle.touched(this, x0, y0);
        }
        
    	// On anime le personnage dès qu'il bouge
    	if (speed > 0) {
    		stepCounter.step(direction);
    	}
	}
	
	/**
	 * Se déplacer en permanence dans la direction actuelle à la vitesse actuelle.
	 */
	public void move() {				
		if (speed == 1) {
			// Cas particulier : on fait deux pas, une fois sur deux
			if (stepCounter.counter(direction) % 2 == 0) {
				go(getX() + dx(direction)*2, getY() + dy(direction)*2);
			}
			else {
				stepCounter.step(direction);
			}
		}
		else {
			go(getX() + dx(direction)*speed, getY() + dy(direction)*speed);
		}
	}
	
	/**
	 * Tester si le personnage peut se déplacer dans une direction donnée.
	 * @param direction Direction de déplacement
	 * @return Résultat du test
	 */
	public boolean canMove(Direction direction) {
		if (direction == Direction.UNDEFINED) {
			return false;
		}
		else {
			int cellSize = PacManWorld.CELL_SIZE;
			int dCol = speed / maxSpeed() * dx(direction), dRow = speed / maxSpeed() * dy(direction);
			
			// Vérifier que la cellule cible existe
			Cell targetCell = new Cell(cell().col() + dCol, cell().row() + dRow);
			boolean cellExists = targetCell.isInWorld();
			
			// Vérifier que la cellule cible est sans obstacle
			boolean isFree = getOneObjectAtOffset(dCol * cellSize, dRow * cellSize, Obstacle.class) == null;
			
			return isInCell() && cellExists && isFree;
		}
	}
	
	// GESTION DE LA DIRECTION
	
    /**
     * Obtenir la prochaine direction à prendre.
     * @return Prochaine direction
     */
    public Direction nextDirection() {
    	return nextDirection;
    }
	
	/**
     * Programmer la prochaine direction du personnage.
     * @param direction Prochaine direction
     */
    public void setDirection(Direction direction) {
    	nextDirection = direction;
    	speed = maxSpeed();
    }
	
	// GESTION DE LA POSITION
	
	/**
	 * Obtenir la cellule dans laquelle le personnage se situe actuellement.
	 * @return Cellule
	 */
	public Cell cell() {
		return new Cell(getX() / PacManWorld.CELL_SIZE, getY() / PacManWorld.CELL_SIZE);
	}
	
	/**
	 * Tester si le personnage est au bord du monde.
	 * @return Résultat du test
	 */
	public boolean isAtEdge() {
		int cellSize = PacManWorld.CELL_SIZE;
		return (getX() - cellSize/2 < 0) || (getX() + cellSize/2 > PacManWorld.WIDTH) ||
				(getY() - cellSize/2 < 0) || (getY() + cellSize/2 > PacManWorld.HEIGHT);
	}
	
	/**
	 * Teste si le personnage est dans une cellule.
	 * @return Résultat du test
	 */
	public boolean isInCell() {
		int cellSize = PacManWorld.CELL_SIZE;
		return (getX() % cellSize == cellSize / 2) && (getY() % cellSize == cellSize / 2);
	}
	
	// GESTION DE L'ANIMATION
	
    /**
     * Mettre à jour le sprite du personnage.
     * @param prefix Préfixe du sprite
     */
    public void updateImage(String prefix) {
    	// On garde le sprite de base tant qu'aucune direction n'est définie
    	if (direction != Direction.UNDEFINED) {
    		// On anime le personnage dès qu'il bouge
    		int mode = 0;
    		if (speed > 0) {
    			mode = stepCounter.mode(direction);
    		}
    		
	    	String name = prefix + "_" + direction.toString().toLowerCase() + mode + ".png";
	    	setImage(name);
    	}
    }
    
    // GESTION DE LA RÉSURRECTION
    
	/**
	 * Mettre à jour le décompte de temps pour la résurrection.
	 * @param world Monde dans lequel le personnage doit réapparaître
	 */
	public void updateRespawnTimer(PacManWorld world) {
		// On décompte le temps dès que le personnage est mort
		respawnTimer.count();
		
		// On fait réapparaître le personnage lorsque le compteur est terminé
		if (respawnTimer.hasElapsed()) {
			respawn(world);
		}
	}
	
	/**
	 * Redémarrer le décompte de temps pour la résurrection.
	 */
	public void resetRespawnTimer() {
		respawnTimer.reset(RESPAWN_TIME);
	}
    
	/**
	 * Ressusciter en reprenant la position initiale.
	 * @param world Monde dans lequel ressusciter
	 */
	public void respawn(PacManWorld world) {
		direction = Direction.DOWN; // Réapparaître avec le bon sprite
		nextDirection = Direction.UNDEFINED;
		speed = 0;
		
		world.deadMovings().remove(this);
		world.addObjectInCell(this, initialCell);		
		respawnTimer.reset(RESPAWN_TIME);
	}
}
