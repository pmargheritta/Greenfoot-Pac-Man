import greenfoot.Greenfoot;

/**
 * Classe représentant le personnage PacMan.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class PacMan extends Moving {
	public static final int HALF_CYCLE = 5; // Demi-cycle pour l'animation des sprites
	
    private boolean isStopped; // Pour empêcher les changements de direction sur place à la fin du niveau
    
    /**
     * Constructeur
     * @param initialCell Cellule de départ
     */
    public PacMan(Cell initialCell) {
    	super(initialCell);
    	
    	direction = Direction.DOWN;
    	speed = 0;
    	stepCounter = new StepCounter(HALF_CYCLE);

    	nextDirection = Direction.UNDEFINED;
    	isStopped = false;
    }
    
    public void act() {    	
		updateImage("pacman");
    	waitDirection();
    	move();
        touching();
    }
    
	/**
	 * Arrêter PacMan.
	 */
	public void stop() {
		speed = 0; // S'arrêter avec le bon sprite
		isStopped = true;
	}
    
    // GESTION DU MOUVEMENT
    
    public int maxSpeed() {
    	return 2;
    }
    
	public void move() {
		if (!isStopped) {
	    	super.move();
	    	
	    	// Le personnage change de direction dès que c'est possible
	    	if (nextDirection != direction && canMove(nextDirection)) {
	    		direction = nextDirection;
	    		
	    		// Communication avec les fantômes rouges
	    		for (RedGhost redGhost: world().actors(RedGhost.class)) {
	    			redGhost.setNextTurn(new Turn(getX(), getY(), nextDirection));
	    		}
	    		
	            setTargetCell(); // Communication avec les fantômes roses
	    	}
		}
    }
    
    // GESTION DE LA DIRECTION
    
    /**
     * Lire une direction entrée au clavier.
     */
    public void waitDirection() {
    	if (Greenfoot.isKeyDown("up")) {
    		setDirection(Direction.UP);
    	}
    	else if (Greenfoot.isKeyDown("down")) {
    		setDirection(Direction.DOWN);
    	}
    	else if (Greenfoot.isKeyDown("left")) {
    		setDirection(Direction.LEFT);
    	}
    	else if (Greenfoot.isKeyDown("right")) {
    		setDirection(Direction.RIGHT);
    	}
    }
    
    // GESTION DES COLLISIONS
    
    /**
     * Gérer la rencontre de PacMan avec un objet consommable ou un fantôme.
     */
    public void touching() {
    	// Objet consommable
    	Edible edible = (Edible) getOneObjectAtOffset(0, 0, Edible.class);
        if (edible != null) {
            edible.touched(); return;
        }
        
        // Fantôme
        Ghost ghost = (Ghost) getOneObjectAtOffset(0, 0, Ghost.class);
        if (ghost != null) {
        	ghost.touched();
        }
    }
    
	/**
	 * Agir lorsque PacMan est touché par un fantôme.
	 */
	public void touched() {
		PacManWorld world = world();
		world.removeObject(this);
		world.game().decrementLives();
		
		if (world.game().lives() < 0) {
			world.removeGhosts();
		}
		else {
			world.respawnAll(this);
		}
	}
	
	// GESTION DE LA RÉSURRECTION
	
	public void respawn(PacManWorld world) {
		world.removeLifeIndicator();
		super.respawn(world);
	}
	
	/**
	 * Déterminer le prochain virage et le communiquer aux fantômes roses.
	 */
	public void setTargetCell() {
		int i = 1;
		int cellSize = PacManWorld.CELL_SIZE;
		int dx = dx(direction), dy = dy(direction);
		
		Cell cell = cell(), nextCell = new Cell(cell().col() + dx, cell().row() + dy);
		boolean isObstacle1 = true, isObstacle2 = true, isObstacle3 = false;
		
		while (isObstacle1 && isObstacle2 && !isObstacle3 && nextCell.isInWorld()) {
			cell = nextCell;
			
			if (dx == 0) {
				isObstacle1 = getOneObjectAtOffset(cellSize, cellSize*dy*i, Obstacle.class) != null;
				isObstacle2 = getOneObjectAtOffset(-cellSize, cellSize*dy*i, Obstacle.class) != null;
			}
			if (dy == 0) {
				isObstacle1 = getOneObjectAtOffset(cellSize*dx*i, cellSize, Obstacle.class) != null;
				isObstacle2 = getOneObjectAtOffset(cellSize*dx*i, -cellSize, Obstacle.class) != null;
			}
			
			isObstacle3 = getOneObjectAtOffset(cellSize*dx*i, cellSize*dy*i, Obstacle.class) != null;
			
			i++;
			nextCell = new Cell(cell().col() + dx*i, cell().row() + dy*i);
		}
		
		// Cas d'un cul-de-sac
		if (isObstacle3) {
			cell = cell();
		}
		
		// Communication avec les fantômes roses
		for (PinkGhost pinkGhost: world().actors(PinkGhost.class)) {
			pinkGhost.setTargetCell(cell);
		}
	}
}
