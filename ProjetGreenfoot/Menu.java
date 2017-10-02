import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

public class Menu extends Actor {	
	public static final int KEYBOARD_TIME = 8;
	public static final Color SELECTED_COLOR = Color.BLUE;
	public static final Color UNSELECTED_COLOR = Color.BLACK;
	
	private List<Label> choices;
	private int index;
	private Timer keyboardTimer;
	
	/**
	 * Constructeur
	 */
	public Menu() {
		setImage(new GreenfootImage(1, 1)); // On met une image invisible
		
		choices = new ArrayList<Label>();
		index = 0;
		keyboardTimer = new Timer(KEYBOARD_TIME);
	}
	
	public void act() {		
		keyboardTimer.count();
		
		if (!choices.isEmpty() && keyboardTimer.hasElapsed()) {
			if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("left")) {
				setIndex(index - 1);
			}
			if (Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("right")) {
				setIndex(index + 1);
			}
			keyboardTimer.reset(KEYBOARD_TIME);
		}
	}
	
	public void addedToWorld(World world) {
		showSelected();
	}
	
	/**
	 * Donner l'apparence de s�lection au choix actuel.
	 */
	public void showSelected() {
		selectedChoice().setText("> " + selectedChoice().text());
		selectedChoice().setFontColor(SELECTED_COLOR);
	}
	
	/**
	 * Donner l'apparence de non-s�lection au choix actuel.
	 */
	public void showUnselected() {
		selectedChoice().setText(selectedChoice().text().substring(2));
		selectedChoice().setFontColor(UNSELECTED_COLOR);
	}
	
	/**
	 * Changer l'index actuel pour une valeur donn�e.
	 * @param index Nouvelle valeur d'index
	 */
	public void setIndex(int index) {
		showUnselected();
		this.index = index;
		showSelected();
	}
	
	/**
	 * Obtenir le num�ro du choix actuellement s�lectionn�.
	 * @return Num�ro du choix
	 */
	public int selectedChoiceIndex() {
		return Math.abs(index % choices.size());
	}
	
	/**
	 * Obtenir le choix actuellement s�lectionn�.
	 * @return Choix
	 */
	public Label selectedChoice() {
		return choices.get(selectedChoiceIndex());
	}
	
	/**
	 * Ajouter un choix au menu.
	 * @param choice Choix � ajouter
	 */
	public void addChoice(Label choice) {
		choices.add(choice);
	}
	
	/**
	 * Teste si un choix donn� est choisi.
	 * @param choiceIndex Num�ro du choix
	 * @return R�sultat du test
	 */
	public boolean isChosen(int choiceIndex) {
		return selectedChoiceIndex() == choiceIndex && Greenfoot.isKeyDown("enter");
	}
}
