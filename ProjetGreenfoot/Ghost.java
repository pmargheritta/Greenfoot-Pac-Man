import greenfoot.Greenfoot;
import java.util.Map;
import java.util.HashMap;

/**
 * Classe abstraite repr�sentant un fant�me ennemi.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public abstract class Ghost extends Moving {
	public static final int SPEED = 2;
	public static final int VULNERABLE_SPEED = 1;
	
	public static final int HALF_CYCLE = 5; // Demi-cycle pour l'animation des sprites
	public static final int START_TIME = 60; // Temps avant le d�marrage du fant�me
	public static final int VULNERABLE_TIME = 600; // Dur�e de la phase de vuln�rabilit�
	public static final int POINTS = 200;
		
	protected boolean isVulnerable;
	protected Timer vulnerableTimer;
	protected VulnerableIndicator vulnerableIndicator;
	protected Timer startTimer;
	
	/**
     * Constructeur
     * @param initialCell Cellule de d�part
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
	 * Se d�placer de mani�re al�toire.
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
			
			// Ne pas se d�placer dans la direction oppos�e sauf si c'est la seule possibilit�
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
	
	// GESTION DE LA VULN�RABILIT�
	
	/**
	 * Tester si le fant�me est vuln�rable.
	 * @return R�sultat du test
	 */
	public boolean isVulnerable() {
		return isVulnerable;
	}
	
	/**
	 * Rendre le fant�me vuln�rable � PacMan.
	 */
	public void setVulnerable() {
		isVulnerable = true;
		vulnerableTimer.reset(VULNERABLE_TIME);
		world().addObject(vulnerableIndicator, getX(), getY() - VulnerableIndicator.OFFSET);

		direction = direction.reverse();
		speed = maxSpeed();
	}
	
	/**
	 * Rendre le fant�me offensif.
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
	 * Mettre � jour les d�comptes de temps pour le d�marrage et la vuln�rabilit�.
	 */
	public void updateTimers() {
		startTimer.count(); // On d�compte le temps pour le d�marrage du fant�me
		
		// On d�compte le temps d�s que le fant�me est vuln�rable
		if (isVulnerable) {
			vulnerableTimer.count();
		}
		
		// On arr�te la vuln�rabilit� lorsque le d�compte est termin�
		if (vulnerableTimer.hasElapsed()) {
			setOffensive(world());
		}
	}
	
	// GESTION DES COLLISIONS
	
	/**
     * G�rer la rencontre du fant�me avec PacMan.
     */
    public void touching() {
        PacMan pacMan = (PacMan) getOneObjectAtOffset(0, 0, PacMan.class);
        if (pacMan != null && !isVulnerable) {
            pacMan.touched();
        }
    }
    
    /**
	 * Agir lorsque le fant�me est touch� par PacMan.
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
