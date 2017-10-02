/**
 * �num�ration repr�sentant une direction.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public enum Direction {
	UP,
	DOWN,
	LEFT,
	RIGHT,
	UNDEFINED; // Utile � l'initialisation du jeu par exemple
	
	/**
	 * Obtenir la direction inverse.
	 * @return Direction inverse
	 */
	public Direction reverse() {
		switch (this) {
			case UP:
				return DOWN;
			case DOWN:
				return UP;
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			default:
				return UNDEFINED;
		}
	}
	
	/**
	 * Obtenir la direction alg�brique orient�e par un facteur donn�.
	 * @param factor Facteur d'orientation
	 * @return Direction orient�e
	 */
	public Direction oriented(int factor) {
		if (factor > 0) {
			return this;
		}
		else if (factor == 0) {
			return UNDEFINED;
		}
		else {
			return reverse();
		}
	}
}
