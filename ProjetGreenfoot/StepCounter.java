/**
 * Classe représentant un compteur de pas utile pour animer les personnages déplaçables.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class StepCounter {
	private int halfCycle; // Nombre de sprites composant un mode de l'animation
	private int up, down, left, right; // Compteurs de pas dans chaque direction
	
	/**
	 * Constructeur
	 * @param halfCycle Nombre de sprites composant un mode de l'animation
	 */
	public StepCounter(int halfCycle) {
		this.halfCycle = halfCycle;
		up = 0;
		down = 0;
		left = 0;
		right = 0;
	}
	
	/**
	 * Obtenir le compteur pour une direction donnée.
	 * @param direction Direction
	 * @return Compteur
	 */
	public int counter(Direction direction) {
		switch (direction) {
			case UP:
				return up;
			case DOWN:
				return down;
			case LEFT:
				return left;
			case RIGHT:
				return right;
			default:
				return 0;
		}
	}
	
	/**
	 * Faire un pas dans une direction donnée.
	 * @param direction Direction de déplacement
	 */
	public void step(Direction direction) {
		switch (direction) {
			case UP:
				up++; break;
			case DOWN:
				down++; break;
			case LEFT:
				left++; break;
			case RIGHT:
				right++; break;
			default:
				break;
		}
	}
	
	/**
	 * Calculer le numéro du mode de l'animation.
	 * @param count Compteur de pas
	 * @return Numéro du mode
	 */
	public int cycle(int count) {
		if (count % (2*halfCycle) < halfCycle) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	/**
	 * Obtenir le numéro du mode de l'animation pour une direction donnée.
	 * @param direction Direction de déplacement
	 * @return Numéro du mode
	 */
	public int mode(Direction direction) {
		return cycle(counter(direction));
	}
}
