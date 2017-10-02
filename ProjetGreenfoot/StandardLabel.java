/**
 * Classe représentant une étiquette de texte standardisée.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class StandardLabel extends Label {
	/**
	 * Constructeur
	 * @param width Largeur
	 */
	public StandardLabel(int width) {
		super(width, 100);
		setFontFamily("Agency FB");
		setFontSize(50);
	}
}
