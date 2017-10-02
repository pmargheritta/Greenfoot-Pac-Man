/**
 * Classe représentant un décompte temporel.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class Timer {
	private int time;
	
	/**
	 * Constructeur
	 * @param time Durée du décompte
	 */
	public Timer(int time) {
		this.time = time;
	}
	
	/**
	 * Décompter d'une unité.
	 */
	public void count() {
		if (time > 0) {
			time--;
		}
	}
	
	/**
	 * Redémarrer le décompte.
	 * @param time Durée du décompte
	 */
	public void reset(int time) {
		this.time = time;
	}
	
	/**
	 * Tester si le temps est écoulé.
	 * @return Résultat du test
	 */
	public boolean hasElapsed() {
		return time == 0;
	}
}
