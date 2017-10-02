/**
 * Classe repr�sentant un compteur de pas utile pour animer les personnages d�pla�ables.
 * @author S�bastien Klopfenstein
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
	 * Obtenir le compteur pour une direction donn�e.
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
	 * Faire un pas dans une direction donn�e.
	 * @param direction Direction de d�placement
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
	 * Calculer le num�ro du mode de l'animation.
	 * @param count Compteur de pas
	 * @return Num�ro du mode
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
	 * Obtenir le num�ro du mode de l'animation pour une direction donn�e.
	 * @param direction Direction de d�placement
	 * @return Num�ro du mode
	 */
	public int mode(Direction direction) {
		return cycle(counter(direction));
	}
}
