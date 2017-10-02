import java.awt.Font;

/**
 * Classe repr�sentant une �tiquette de texte pour un score.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public class ScoreLabel extends Label {
	/**
	 * Constructeur
	 */
	public ScoreLabel() {
		super(4 * PacManWorld.CELL_SIZE, PacManWorld.CELL_SIZE);
		setFontFamily("Agency FB");
        setFontStyle(Font.BOLD);
        setFontSize(PacManWorld.CELL_SIZE);
	}
}
