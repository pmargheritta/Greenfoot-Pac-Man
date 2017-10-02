import greenfoot.Greenfoot;
import java.util.Map;
import java.util.HashMap;

/**
 * Classe abstraite représentant un fantôme ennemi.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public abstract class Ghost extends Moving {
	public static final int SPEED = 2;
	public static final int VULNERABLE_SPEED = 1;
	
	public static final int HALF_CYCLE = 5; // Demi-cycle pour l'animation des sprites
	public static final int START_TIME = 60; // Temps avant le démarrage du fantôme
	public static final int VULNERABLE_TIME = 600; // Durée de la phase de vulnérabilité
	public static final int POINTS = 200;
		
	protected boolean isVulnerable;
	protected Timer vulnerableTimer;
	protected VulnerableIndicator vulnerableIndicator;
	protected Timer startTimer;
	
	/**
     * Constructeur
     * @param initialCell Cellule de départ
     */
	public Ghost(Cell initialCell) {
		super(initialCell);
		
		direction = Direction.DOWN;
		speed = maxSpeed();
		stepCounter = new StepCounter(HALF_CYCLE);
		
		isVulnerable = false;
		vulnerableTimer = new Timer(VULNERABLE_TIME);
		vulnerableIndicator = new VulnerableIndicator(this);
		startTimer = new Timer(START_TIME);
	}

	public void act() {		
		if (startTimer.hasElapsed()) {
			move();
		}
		updateTimers();
		touching();
	}
	
	// GESTION DU MOUVEMENT
	
	public int maxSpeed() {
		if (isVulnerable) {
			return VULNERABLE_SPEED;
		}
		else {
			return SPEED;
		}
	}
	
	public void move() {
		super.move();
	}
	
	/**
	 * Se déplacer de manière alétoire.
	 */
	public void randomMovement() {
		if (isInCell()) {
			speed = maxSpeed();
			
			int dx = getX() - world().pacMan().getX(), dy = getY() - world().pacMan().getY();
			int probX = dx * dx * 70, probY = dy * dy * 70;
			Direction directionX = Direction.LEFT.oriented(dx), directionY = Direction.UP.oriented(dy);
			
			Map<Direction, Integer> directions = new HashMap<Direction, Integer>();
			int sum = 0;
			if (canMove(directionX)) {
				directions.put(directionX, probX);
				sum += probX;
			}
			if (canMove(directionY)) {
				directions.put(directionY, probY);
				sum += probY;
			}
			if (canMove(directionX.reverse())) {
				directions.put(directionX.reverse(), (dx*dx + dy*dy) * 15);
				sum += (dx*dx + dy*dy) * 15;
			}
			if (canMove(directionY.reverse())) {
				directions.put(directionY.reverse(), (dx*dx + dy*dy) * 15);
				sum += (dx*dx + dy*dy) * 15;
			}
			
			// Ne pas se déplacer dans la direction opposée sauf si c'est la seule possibilité
			if (directions.size() > 1 && directions.containsKey(direction.reverse())) {
				sum -= directions.get(direction.reverse()) / 4;
				directions.put(direction.reverse(), directions.get(direction.reverse()) / 4);
			}
			
			if (directions.size() > 0) {
				for (Map.Entry<Direction, Integer> entry: directions.entrySet()) {
					int random = Greenfoot.getRandomNumber(sum);
					if (random < entry.getValue()) {
						direction = entry.getKey();
					}
				}
			}
		}
	}
	
	// GESTION DE LA VULNÉRABILITÉ
	
	/**
	 * Tester si le fantôme est vulnérable.
	 * @return Résultat du test
	 */
	public boolean isVulnerable() {
		return isVulnerable;
	}
	
	/**
	 * Rendre le fantôme vulnérable à PacMan.
	 */
	public void setVulnerable() {
		isVulnerable = true;
		vulnerableTimer.reset(VULNERABLE_TIME);
		world().addObject(vulnerableIndicator, getX(), getY() - VulnerableIndicator.OFFSET);

		direction = direction.reverse();
		speed = maxSpeed();
	}
	
	/**
	 * Rendre le fantôme offensif.
	 * @param world Monde actuel
	 */
	public void setOffensive(PacManWorld world) {
		isVulnerable = false;
		vulnerableTimer.reset(VULNERABLE_TIME);
		world.removeObject(vulnerableIndicator);
		
		speed = maxSpeed();
	}
	
	public void respawn(PacManWorld world) {
		startTimer.reset(START_TIME);
		super.respawn(world);
	}
	
	/**
	 * Mettre à jour les décomptes de temps pour le démarrage et la vulnérabilité.
	 */
	public void updateTimers() {
		startTimer.count(); // On décompte le temps pour le démarrage du fantôme
		
		// On décompte le temps dès que le fantôme est vulnérable
		if (isVulnerable) {
			vulnerableTimer.count();
		}
		
		// On arrête la vulnérabilité lorsque le décompte est terminé
		if (vulnerableTimer.hasElapsed()) {
			setOffensive(world());
		}
	}
	
	// GESTION DES COLLISIONS
	
	/**
     * Gérer la rencontre du fantôme avec PacMan.
     */
    public void touching() {
        PacMan pacMan = (PacMan) getOneObjectAtOffset(0, 0, PacMan.class);
        if (pacMan != null && !isVulnerable) {
            pacMan.touched();
        }
    }
    
    /**
	 * Agir lorsque le fantôme est touché par PacMan.
	 */
	public void touched() {
		if (isVulnerable) {
			PacManWorld world = world();
			
			// Points
			world.setGhostsInSuccession(world.ghostsInSuccession() + 1);
			world.game().addPoints((int) Math.scalb(POINTS, world.ghostsInSuccession() - 1)); // 200, 400, 800 et 1600
			if (world.ghostsInSuccession() == 4) {
				world.setGhostsInSuccession(0);
			}
			
			world.removeObject(this);
			world.deadMovings().add(this);
			setOffensive(world);
		}
	}
} 
