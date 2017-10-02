import greenfoot.Greenfoot;
import java.io.IOException;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 * Classe représentant un jeu.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class Game {
	public static final int FIRST_LEVEL = 1;
	public static final int LAST_LEVEL = 3;
	
	private PacManWorld pacManWorld; // Monde de jeu actuel
	
	private int levelNumber; // Numéro du niveau actuel
	private int score;
	private int lives; // Nombre de vies restantes après la vie actuelle
	
	private String latestPlayerName; // Pour ne pas avoir à réécrire le nom de joueur à chaque sauvegarde
	
	/**
	 * Constructeur
	 */
	public Game() {
		levelNumber = FIRST_LEVEL - 1;
		score = 0;
		lives = 2;
		
		latestPlayerName = "";
	}
	
	/**
	 * Tirer un nombre aléatoire et tester la correspondance à la probabilité indiquée.
	 * @param inverseProb Inverse de la probabilité
	 * @return Résultat du test
	 */
	public static boolean random(int inverseProb) {
		return Greenfoot.getRandomNumber(inverseProb * 60) == 0;
	}
	
	/**
	 * Redémarrer le jeu.
	 */
	public void restart() {
		levelNumber = 0;
		score = 0;
		Greenfoot.setWorld(new StartScreenWorld());
	}
	
	// GESTION DES NIVEAUX
	
	/**
	 * Obtenir le numéro du niveau actuel.
	 * @return Numéro du niveau
	 */
	public int levelNumber() {
		return levelNumber;
	}
	
	/**
	 * Charger l'écran de transition vers le niveau suivant.
	 */
	public void loadNextLevelScreen() {
		levelNumber++;
		Greenfoot.setWorld(new LevelScreenWorld(this));
	}
	
	/**
	 * Charger le niveau.
	 */
	public void loadLevel() {
		PacManWorld pacManWorld = new PacManWorld(this);
        Greenfoot.setWorld(pacManWorld);
		pacManWorld.loadLevel("level" + levelNumber);
		this.pacManWorld = pacManWorld;
	}
	
	// GESTION DU SCORE
	
	/**
	 * Obtenir le score actuel.
	 * @return Score
	 */
	public int score() {
		return score;
	}
	
	/**
	 * Augmenter le score d'un nombre de points donné.
	 * @param points Nombre de points
	 */
	public void addPoints(int points) {
		score += points;
		
		// Afficher sur le compteur de points spéciaux
		if (points >= 100) {
			pacManWorld.setSpecialPoints(points);
		}
	}
	
	// GESTION DES VIES
	
	/**
	 * Obtenir le nombre de vies restantes.
	 * @return Nombre de vies
	 */
	public int lives() {
		return lives;
	}
	
	/**
	 * Décrémenter le nombre de vies.
	 */
	public void decrementLives() {
		lives--;
	}
	
	// GESTION DE LA SAUVEGARDE
	
	/**
	 * Charger l'écran de sauvegarde.
	 */
	public void loadSaveScreen() {
		if (levelNumber == LAST_LEVEL) {
			loadNextLevelScreen(); // Si c'est la fin du jeu, on ne propose pas de sauvegarder
		}
		else {
			Greenfoot.setWorld(new SaveScreenWorld(this)); // On passe à l'écran de sauvegarde
		}
	}
	
	/**
	 * Afficher une boîte de dialogue d'erreur de sauvegarde.
	 * @param errorMessage Message d'erreur
	 */
	public static void showSaveError(String errorMessage) {
		JOptionPane.showMessageDialog(null, "Sauvegarde impossible : " + errorMessage, "Erreur", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Obtenir le dernier nom de joueur utilisé pour la sauvegarde.
	 * @return Nom de joueur
	 */
	public String latestPlayerName() {
		return latestPlayerName;
	}
	
	/**
	 * Obtenir l'ensemble des noms de joueur utilisés.
	 * @return Ensemble des noms de joueur
	 */
	public static Set<String> playerNames() {
		SavedGamesMap savedGamesMap = new SavedGamesMap();
		
		try {
			savedGamesMap.read();
		}
		catch (ClassNotFoundException e) {
			showSaveError(e.toString());
		}
		catch (IOException e) {
			showSaveError(e.toString());
		}
		
		return savedGamesMap.data().keySet();
	}
	
	/**
	 * Sauvegarder la partie.
	 * @param playerName Nom du joueur
	 */
	public void save(String playerName) {
		latestPlayerName = playerName;
		
		Integer data[] = {levelNumber, score, lives};
		SavedGamesMap savedGamesMap = new SavedGamesMap();
		try {
			savedGamesMap.read();
			savedGamesMap.data().put(playerName, data);
			savedGamesMap.write();
		}
		catch (ClassNotFoundException e) {
			showSaveError(e.toString());
		}
		catch (IOException e) {
			showSaveError(e.toString());
		}
	}
	
	/**
	 * Charger une partie sauvegardée.
	 * @param playerName Nom du joueur
	 */
	public void load(String playerName) {
		SavedGamesMap savedGamesMap = new SavedGamesMap();
		
		try {
			savedGamesMap.read();
		}
		catch (ClassNotFoundException e) {
			showSaveError(e.toString());
		}
		catch (IOException e) {
			showSaveError(e.toString());
		}
		
		levelNumber = savedGamesMap.levelNumber(playerName);
		score = savedGamesMap.score(playerName);
		lives = savedGamesMap.lives(playerName);
		
		loadNextLevelScreen();
	}
}
