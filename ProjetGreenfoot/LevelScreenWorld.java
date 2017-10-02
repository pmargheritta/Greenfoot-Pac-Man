import greenfoot.World;

/**
 * Classe repr�sentant un �cran de transition vers un niveau.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public class LevelScreenWorld extends World {
	public static final int LEVEL_SCREEN_TIME = 120;
	
	private Game game;
	private Timer levelScreenTimer;
	
	/**
	 * Constructeur
	 * @param game Jeu associ�
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
        	levelLabel.setText("Gagn� !"); // Le joueur gagne
        }
        else {
            levelLabel.setText("Niveau " + game.levelNumber()); // Num�ro du niveau
        }
        addObject(levelLabel, getWidth() / 2, getHeight() / 2);
	}
	
	public void act() {
		levelScreenTimer.count(); // On d�compte le temps d�s que l'�cran appara�t
		
		// On lance le niveau d�s que le d�compte est termin�
		if (levelScreenTimer.hasElapsed()) {
			if (game.levelNumber() > Game.LAST_LEVEL || game.lives() < 0) {
				game.restart(); // Le jeu est termin� : on red�marre
			}
			else {
				game.loadLevel(); // On charge le niveau suivant
			}
		}
	}
}
