import greenfoot.Actor;

/**
 * Classe abstraite repr�sentant un objet mangeable par PacMan.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public abstract class Edible extends Actor {
	/**
	 * Obtenir le monde dans lequel se d�place le personnage.
	 * @return Monde
	 */
	public PacManWorld world() {
		return (PacManWorld) getWorld();
	}
	
	/**
	 * Agir lorsque l'objet est touch� par un personnage.
	 */
	public void touched() {
		world().removeObject(this);
	}
}
