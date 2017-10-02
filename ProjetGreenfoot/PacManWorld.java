import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.World;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Classe repr�sentant le monde dans lequel �volue PacMan.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public class PacManWorld extends World {
	// Constantes pour la construction de la grille
	public static final int WIDTH = 1056, HEIGHT = 386;
	public static final int CELL_SIZE = 32;
	public static final int COLS = WIDTH / CELL_SIZE, ROWS = HEIGHT / CELL_SIZE;
	
	public static final int BONUS_PROB = 32;
	public static final int SPECIAL_POINTS_TIME = 120;
	public static final int END_LEVEL_TIME = 60;
	public static final int END_GAME_TIME = 60;
	
	private Game game;
	private Level level;
	private Label scoreLabel, specialPointsLabel;
    private Timer specialPointsTimer, endLevelTimer, endGameTimer;

    private CopyOnWriteArrayList<Moving> deadMovings; // Liste concurrente pour lire et supprimer � la fois
    private int ghostsInSuccession; // Nombre de fant�mes tu�s � la suite
	
	/**
	 * Constructeur
	 * @param game Jeu associ�
	 */
    public PacManWorld(Game game) {    	
        super(WIDTH, HEIGHT, 1);
        setPaintOrder(Label.class, VulnerableIndicator.class, Ghost.class, PacMan.class);
        
        this.game = game;
    	endLevelTimer = new Timer(END_LEVEL_TIME);
    	endGameTimer = new Timer(END_GAME_TIME);
    	
    	// Score
        scoreLabel = new ScoreLabel();
        scoreLabel.setText("" + game.score());
        addObject(scoreLabel, 2 * CELL_SIZE, HEIGHT - CELL_SIZE/2);
        
        // Points sp�ciaux
        specialPointsLabel = new ScoreLabel();
        specialPointsLabel.setFontColor(Color.RED);
        addObject(specialPointsLabel, 7 * CELL_SIZE, HEIGHT - CELL_SIZE/2);
        specialPointsTimer = new Timer(0);
        
        // Vies
        for (int i = 0; i < game.lives(); i++) {
        	addObjectInCell(new LifeIndicator(), new Cell(WIDTH/CELL_SIZE - 1 - i, HEIGHT/CELL_SIZE - 1));
        }
        
    	deadMovings = new CopyOnWriteArrayList<Moving>();
    	ghostsInSuccession = 0;
    }
    
    public void act() {
    	addBonus();
    	updateScore();
    	updateSpecialPointsTimer();
        updateRespawnTimers();
        waitLevelEnd();
        waitGameEnd();
    }
    
	/**
	 * Obtenir le jeu associ� au monde.
	 * @return Jeu
	 */
	public Game game() {
		return game;
	}
	
	// GESTION DES ACTEURS DU MONDE
    
    /**
     * Obtenir le personnage PacMan.
     * @return PacMan
     */
    public PacMan pacMan() {
    	return (PacMan) getObjects(PacMan.class).get(0);
    }
    
    /**
     * Agir lorsque le personnage donn� est au bord du monde.
     * @param moving Personnage
     * @param x0 Abscisse pr�c�dente
     * @param y0 Ordonn�e pr�c�dente
     */
    public void atEdge(Moving moving, int x0, int y0) {
    	moving.setLocation(x0, y0);
    	moving.setSpeed(0);
    }
    
    /**
     * Obtenir une liste des acteurs d'une classe donn�e (version am�lior�e de getObjects de Greenfoot).
     * @param <T> Type des acteurs
     * @param clazz Classe d'acteurs
     * @return Liste des acteurs
     */
    @SuppressWarnings("unchecked")
	public <T extends Actor> List<T> actors(Class<T> clazz) {
    	List<T> actors = new ArrayList<T>();
    	for (Object actor: getObjects(clazz)) {
			actors.add((T) actor);
    	}
    	return actors;
    }
    
    /**
     * Faire dispara�tre tous les fant�mes.
     */
    public void removeGhosts() {
    	removeObjects(getObjects(VulnerableIndicator.class));
    	removeObjects(getObjects(Ghost.class));
    	deadMovings.clear();
    }
        
    /**
     * Ajouter un objet dans une cellule.
     * @param actor Objet � ajouter
     * @param cell Cellule
     */
    public void addObjectInCell(Actor actor, Cell cell) {
    	// Coordonn�es du centre de la cellule
    	int x = cell.col()*CELL_SIZE + CELL_SIZE/2;
    	int y = cell.row()*CELL_SIZE + CELL_SIZE/2;
    	
    	addObject(actor, x, y);
    }
    
    /**
	 * Faire appara�tre ou dispara�tre un bonus.
	 */
	public void addBonus() {
		if (Game.random(BONUS_PROB)) {
			if (getObjects(Bonus.class).isEmpty()) {
				List<Dot> dots = actors(Dot.class);
				
				if (dots.size() > 1) {
					// Retirer une des gommes restantes
					Dot dot = dots.get(Greenfoot.getRandomNumber(dots.size()));
					int x = dot.getX(), y = dot.getY();
					removeObject(dot);
					
					addObject(new Bonus(), x, y); // Remplacer par un bonus
				}
			}
			else {
				// Remplacer un bonus par une gomme
				Bonus bonus = actors(Bonus.class).get(0);
				addObject(new Dot(), bonus.getX(), bonus.getY());
				removeObject(bonus);
			}
		}
	}
    
    // GESTION DES NIVEAUX
	
	public Level level() {
		return level;
	}
    
    /**
     * Charger un niveau � partir d'une carte donn�e.
     * @param mapName Nom de la carte
     */
    public void loadLevel(String mapName) {
    	try {
    		level = new Level(mapName, this);
    		level.load();
    	}
    	catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * D�clencher la fin du niveau.
     */
	public void waitLevelEnd() {
        if (getObjects(Dot.class).isEmpty()) {
        	removeGhosts();
        	
        	// Faire une pause avant le niveau suivant
        	if (pacMan().isInCell()) {
	        	endLevelTimer.count();
	        	pacMan().stop();
	        	
	        	if (endLevelTimer.hasElapsed()) {
	        		game.loadSaveScreen();
	        	}
        	}
        }
    }
    
	// GESTION DE LA R�SURRECTION
	
    /**
	 * Mettre � jour les d�comptes de temps pour la r�surrection.
	 */
	public void updateRespawnTimers() {
        for (Moving moving: deadMovings) {
        	moving.updateRespawnTimer(this);
        }
	}
	
	/**
	 * Obtenir la liste des personnages morts.
	 * @return Liste des personnages morts
	 */
	public CopyOnWriteArrayList<Moving> deadMovings() {
		return deadMovings;
	}
	
	/**
	 * R�initialiser les fant�mes et PacMan apr�s la mort de PacMan.
	 * @param pacMan PacMan
	 */
	public void respawnAll(PacMan pacMan) {
		// R�initialisation des fant�mes
		for (Ghost ghost: actors(Ghost.class)) {
			removeObject(ghost);
			deadMovings().add(ghost);
			ghost.setOffensive(this);
		}
		
		// R�initialisation des d�comptes pour la r�surrection
		for (Moving moving: deadMovings()) {
			moving.resetRespawnTimer();
		}
		
		deadMovings().add(pacMan); // R�initialisation de PacMan
	}
	
	// GESTION DU SCORE
	
	/**
	 * Obtenir le compteur de fant�mes tu�s � la suite.
	 * @return Compteur de fant�mes
	 */
	public int ghostsInSuccession() {
		return ghostsInSuccession;
	}
	
	/**
	 * Param�trer le compteur de fant�mes tu�s � la suite.
	 * @param ghostsInSuccession Compteur de fant�mes
	 */
	public void setGhostsInSuccession(int ghostsInSuccession) {
		this.ghostsInSuccession = ghostsInSuccession;
	}
	
	/**
	 * Mettre � jour l'afficheur de score.
	 */
	public void updateScore() {
    	scoreLabel.setText("" + game.score());
	}
	
	/**
	 * Param�trer l'afficheur de points sp�ciaux.
	 * @param points Points sp�ciaux
	 */
	public void setSpecialPoints(int points) {
		specialPointsTimer.reset(SPECIAL_POINTS_TIME);
		specialPointsLabel.setText("+" + points + " !");
	}
	
	/**
	 * Mettre � jour le d�compte de l'afficheur de points sp�ciaux.
	 */
	public void updateSpecialPointsTimer() {
        specialPointsTimer.count();
        if (specialPointsTimer.hasElapsed()) {
        	specialPointsLabel.setText("");
        }
	}
	
	// GESTION DES VIES
	
	/**
	 * D�clencher la fin du jeu.
	 */
	public void waitGameEnd() {
		// On d�compte d�s que le jeu est termin�
		if (game.lives() < 0) {
			endGameTimer.count();
		}
		
		// On lance l'�cran de fin lorsque le d�compte est termin�
		if (endGameTimer.hasElapsed()) {
			game.loadNextLevelScreen();
		}
	}
	
	/**
	 * Retirer un indicateur de vie.
	 */
	public void removeLifeIndicator() {
		List<LifeIndicator> lifeIndicators = actors(LifeIndicator.class);
		removeObject(lifeIndicators.get(lifeIndicators.size() - 1));
	}
}
