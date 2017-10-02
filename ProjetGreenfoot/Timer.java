/**
 * Classe repr�sentant un d�compte temporel.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public class Timer {
	private int time;
	
	/**
	 * Constructeur
	 * @param time Dur�e du d�compte
	 */
	public Timer(int time) {
		this.time = time;
	}
	
	/**
	 * D�compter d'une unit�.
	 */
	public void count() {
		if (time > 0) {
			time--;
		}
	}
	
	/**
	 * Red�marrer le d�compte.
	 * @param time Dur�e du d�compte
	 */
	public void reset(int time) {
		this.time = time;
	}
	
	/**
	 * Tester si le temps est �coul�.
	 * @return R�sultat du test
	 */
	public boolean hasElapsed() {
		return time == 0;
	}
}
