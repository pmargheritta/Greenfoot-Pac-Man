import greenfoot.World;
import javax.swing.JOptionPane;

/**
 * Classe représentant un écran de sauvegarde.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class SaveScreenWorld extends World {
	private Game game;
	private Menu menu;
	
	/**
	 * Constructeur
	 * @param game Jeu associé
	 */
	public SaveScreenWorld(Game game) {
        super(PacManWorld.WIDTH, PacManWorld.HEIGHT, 1);
        
        this.game = game;
        
        // Bravo
        Label congratsLabel = new StandardLabel(110);
        congratsLabel.setText("Bravo !");
        addObject(congratsLabel, getWidth() / 2, getHeight() / 4);
        
        // Continuer
        Label continueLabel = new StandardLabel(getWidth());
        continueLabel.setText("Continuer sans sauvegarder");
        addObject(continueLabel, 50 + getWidth()/2, 200);
        
        // Sauvegarder
        Label saveLabel = new StandardLabel(getWidth());
        saveLabel.setText("Sauvegarder la partie");
        addObject(saveLabel, 50 + getWidth()/2, 250);
        
        // Menu
        menu = new Menu();
        menu.addChoice(continueLabel);
        menu.addChoice(saveLabel);
        addObject(menu, 0, 0);
	}
	
	public void act() {
		if (menu.isChosen(0)) {
			game.loadNextLevelScreen(); // Continuer
		}
		if (menu.isChosen(1)) {
			showSavePrompt(); // Sauvegarder
		}
	}
	
	/**
	 * Afficher la boîte de dialogue de sauvegarde.
	 */
	public void showSavePrompt() {
		Object input = JOptionPane.showInputDialog(null, "Entrer le nom du joueur :",
				"Sauvegarder la partie", JOptionPane.PLAIN_MESSAGE, null, null, game.latestPlayerName());
		
		if (input != null) {
			String playerName = (String) input;
			
			if (playerName.isEmpty()) {
				 JOptionPane.showMessageDialog(null, "Le nom de joueur ne doit pas être vide.",
						 "Sauvegarder la partie", JOptionPane.ERROR_MESSAGE);
				 showSavePrompt();
			}
			else {
				game.save(playerName);
				game.loadNextLevelScreen();
			}
		}
	}
}
