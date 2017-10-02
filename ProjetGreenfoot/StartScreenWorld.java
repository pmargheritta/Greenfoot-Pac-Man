import greenfoot.Actor;
import greenfoot.World;
import javax.swing.JOptionPane;

/**
 * Classe représentant l'écran de démarrage du jeu.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class StartScreenWorld extends World {
	private Menu menu;
	
	/**
	 * Constructeur
	 */
	public StartScreenWorld() {
        super(PacManWorld.WIDTH, PacManWorld.HEIGHT, 1);
        
        // Titre
        Label title = new Label(300, 150);
        title.setFontFamily("Agency FB");
        title.setFontSize(120);
        title.setText("PacMan");
        addObject(title, 50, 50);
        
        // Nouvelle partie
        Label newGame = new StandardLabel(getWidth());
        newGame.setText("Nouvelle partie");
        addObject(newGame, 50, 200);
        
        // Charger une partie
        Label loadGame = new StandardLabel(getWidth());
        loadGame.setText("Charger une partie");
        addObject(loadGame, 50, 250);
        
        // Menu
        menu = new Menu();
        menu.addChoice(newGame);
        menu.addChoice(loadGame);
        addObject(menu, 0, 0);
 	}
	
	public void act() {		
		start();
	}
	
	public void addObject(Actor actor, int x, int y) {
		// Ajouter un objet depuis le coin en haut à gauche plutôt que le centre
		super.addObject(actor, x + actor.getImage().getWidth() / 2, y + actor.getImage().getHeight() / 2);
	}
	
	/**
	 * Démarrer le jeu.
	 */
	public void start() {
		Game game = new Game();
		
		if (menu.isChosen(0)) {
			game.loadNextLevelScreen(); // Nouvelle partie
		}
		if (menu.isChosen(1)) {
			showLoadPrompt(game); // Charger une partie
		}
	}
	
	/**
	 * Afficher la boîte de dialogue de chargement
	 * @param game Jeu
	 */
	private void showLoadPrompt(Game game) {
		if (Game.playerNames().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Aucune partie sauvegardée.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
		else {
			Object input = JOptionPane.showInputDialog(null, "Entrer le nom du joueur :",
					"Charger une partie", JOptionPane.PLAIN_MESSAGE, null, Game.playerNames().toArray(), "");
		
			if (input != null) {
				game.load((String) input);
			}
		}
	}
}
