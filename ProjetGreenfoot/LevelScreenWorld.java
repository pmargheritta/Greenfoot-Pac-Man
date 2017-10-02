import greenfoot.World;

/**
 * Classe représentant un écran de transition vers un niveau.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class LevelScreenWorld extends World {
	public static final int LEVEL_SCREEN_TIME = 120;
	
	private Game game;
	private Timer levelScreenTimer;
	
	/**
	 * Constructeur
	 * @param game Jeu associé
	 */
	public LevelScreenWorld(Game game) {
        super(PacManWorld.WIDTH, PacManWorld.HEIGHT, 1);
        
        this.game = game;
        levelScreenTimer = new Timer(LEVEL_SCREEN_TIME);
        
        Label levelLabel = new StandardLabel(140);
        if (game.lives() < 0) {
        	levelLabel.setText("Perdu !"); // Le joueur perd
        }
        else if (game.levelNumber() > Game.LAST_LEVEL) {
        	levelLabel.setText("Gagné !"); // Le joueur gagne
        }
        else {
            levelLabel.setText("Niveau " + game.levelNumber()); // Numéro du niveau
        }
        addObject(levelLabel, getWidth() / 2, getHeight() / 2);
	}
	
	public void act() {
		levelScreenTimer.count(); // On décompte le temps dès que l'écran apparaît
		
		// On lance le niveau dès que le décompte est terminé
		if (levelScreenTimer.hasElapsed()) {
			if (game.levelNumber() > Game.LAST_LEVEL || game.lives() < 0) {
				game.restart(); // Le jeu est terminé : on redémarre
			}
			else {
				game.loadLevel(); // On charge le niveau suivant
			}
		}
	}
}
