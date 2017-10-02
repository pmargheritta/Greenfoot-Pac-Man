import greenfoot.Actor;

/**
 * Classe abstraite représentant un objet mangeable par PacMan.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public abstract class Edible extends Actor {
	/**
	 * Obtenir le monde dans lequel se déplace le personnage.
	 * @return Monde
	 */
	public PacManWorld world() {
		return (PacManWorld) getWorld();
	}
	
	/**
	 * Agir lorsque l'objet est touché par un personnage.
	 */
	public void touched() {
		world().removeObject(this);
	}
}
