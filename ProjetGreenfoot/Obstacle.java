import greenfoot.Actor;

/**
 * Classe repr�sentant un obstacle bloquant PacMan.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
*/
public class Obstacle extends Actor {
	/**
	 * Agir lorsque le personnage donn� touche l'obstacle.
	 * @param moving Personnage
	 * @param x0 Abscisse pr�c�dente
	 * @param y0 Ordonn�e pr�c�dente
	 */
    public void touched(Moving moving, int x0, int y0) {
    	// Bloquer le personnage
    	moving.setLocation(x0, y0);
    	moving.setSpeed(0);
    }
}
