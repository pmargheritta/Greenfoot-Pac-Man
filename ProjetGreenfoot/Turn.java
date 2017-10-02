/**
 * Classe repr�sentant un virage.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
*/
public class Turn {
	private int x;
	private int y;
	private Direction direction;
	
	/**
	 * Constructeur
	 */
	public Turn() {
		direction = Direction.UNDEFINED;
	}
	
	/**
	 * Constructeur
	 * @param x Abscisse du virage
	 * @param y Ordonn�e du virage
	 * @param direction Direction du virage
	 */
	public Turn(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	/**
	 * Obtenir l'ascisse du virage.
	 * @return Abscisse
	 */
	public int x() {
		return x;
	}
	
	/**
	 * Obtenir l'ordonn�e du virage.
	 * @return Ordonn�e
	 */
	public int y() {
		return y;
	}
	
	/**
	 * Obtenir la direction du virage
	 * @return Direction 
	 */
	public Direction direction() {
		return direction;
	}
}
