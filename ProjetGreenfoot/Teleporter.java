import greenfoot.Actor;
import greenfoot.Greenfoot;
import java.util.List;

/**
 * Classe représentant un téléporteur.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class Teleporter extends Actor {
	private boolean isActive; // Pour éviter le va-et-vient constant entre téléporteurs
	
	/**
	 * Constructeur
	 */
	public Teleporter() {
		isActive = true;
	}
	
	public void act() {
		teleport();
	}
	
	/**
	 * Désactiver le téléporteur.
	 */
	public void turnOff() {
		isActive = false;
	}
	
	/**
	 * Téléporter les personnages vers un téléporteur aléatoire.
	 */
	public void teleport() {
		Moving moving = (Moving) getOneObjectAtOffset(0, 0, Moving.class);
		
		if (moving == null) {
			isActive = true;
		}
		else if (isActive) {
			List<Teleporter> teleporters = ((PacManWorld) getWorld()).actors(Teleporter.class);
			teleporters.remove(this);
			
			if (!teleporters.isEmpty()) {
				Teleporter teleporter = teleporters.get(Greenfoot.getRandomNumber(teleporters.size()));
				teleporter.turnOff();
				moving.go(teleporter.getX(), teleporter.getY());
			}
		}
	}
}
