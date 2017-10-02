import greenfoot.Actor;

/**
 * Classe représentant un obstacle bloquant PacMan.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
*/
public class Obstacle extends Actor {
	/**
	 * Agir lorsque le personnage donné touche l'obstacle.
	 * @param moving Personnage
	 * @param x0 Abscisse précédente
	 * @param y0 Ordonnée précédente
	 */
    public void touched(Moving moving, int x0, int y0) {
    	// Bloquer le personnage
    	moving.setLocation(x0, y0);
    	moving.setSpeed(0);
    }
}
