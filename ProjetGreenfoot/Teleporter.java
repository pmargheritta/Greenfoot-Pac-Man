import greenfoot.Actor;
import greenfoot.Greenfoot;
import java.util.List;

/**
 * Classe repr�sentant un t�l�porteur.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public class Teleporter extends Actor {
	private boolean isActive; // Pour �viter le va-et-vient constant entre t�l�porteurs
	
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
	 * D�sactiver le t�l�porteur.
	 */
	public void turnOff() {
		isActive = false;
	}
	
	/**
	 * T�l�porter les personnages vers un t�l�porteur al�atoire.
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
