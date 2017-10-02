import java.util.HashMap;

/**
 * Classe représentant le tableau des parties sauvegardées.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class SavedGamesMap extends SavedData<HashMap<String, Integer[]>> {
	public static final String PATH = "data/savedGamesMap.ser";
	
	/**
	 * Constructeur
	 */
	public SavedGamesMap() {
		data = new HashMap<String, Integer[]>();
	}
	
	public String path() {
		return PATH;
	}
	
	/**
	 * Obtenir le niveau actuel d'un joueur donné.
	 * @param playerName Nom du joueur
	 * @return Niveau
	 */
	public int levelNumber(String playerName) {
		return data.get(playerName)[0];
	}
	
	/**
	 * Obtenir le score d'un joueur donné.
	 * @param playerName Nom du joueur
	 * @return Score
	 */
	public int score(String playerName) {
		return data.get(playerName)[1];
	}
	
	/**
	 * Obtenir le nombre de vies d'un joueur donné.
	 * @param playerName Nom du joueur
	 * @return Nombre de vies
	 */
	public int lives(String playerName) {
		return data.get(playerName)[2];
	}
}
