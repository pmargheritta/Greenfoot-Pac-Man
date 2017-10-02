import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.awt.Color;
import java.awt.Font;

/**
 * Classe représentant une étiquette de texte.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class Label extends Actor {
	private GreenfootImage image;
	
	private String text;
	private String fontFamily;
	private int fontStyle;
	private int fontSize;
	private Color fontColor;
	
	/**
	 * Constructeur
	 * @param width Largeur de l'image
	 * @param height Hauteur de l'image
	 */
	public Label(int width, int height) {
		image = new GreenfootImage(width, height);
		setImage(image);
		
		text = "";
		fontFamily = Font.SANS_SERIF;
		fontStyle = Font.PLAIN;
		fontSize = height;
		fontColor = Color.BLACK;
		image.setFont(new Font(fontFamily, fontStyle, fontSize));
	}
	
	/**
	 * Obtenir le texte affiché.
	 * @return Texte
	 */
	public String text() {
		return text;
	}
	
	/**
	 * Définir le texte à afficher.
	 * @param text Texte
	 */
	public void setText(String text) {
		this.text = text;
		image.clear();
		image.setColor(fontColor);
		image.drawString(text, 0, (image.getHeight() + fontSize) / 2);
	}
	
	/**
	 * Définir la fonte à utiliser.
	 * @param fontFamily Nom de la fonte
	 */
	public void setFontFamily(String fontFamily) {
		image.setFont(new Font(fontFamily, fontStyle, fontSize));
		this.fontFamily = fontFamily;
	}
	
	/**
	 * Définir le style de texte à utiliser.
	 * @param fontStyle Style du texte
	 */
	public void setFontStyle(int fontStyle) {
		image.setFont(new Font(fontFamily, fontStyle, fontSize));
		this.fontStyle = fontStyle;
	}
	
	/**
	 * Définir la taille de texte à utiliser.
	 * @param fontSize Taille du texte
	 */
	public void setFontSize(int fontSize) {
		image.setFont(new Font(fontFamily, fontStyle, fontSize));
		this.fontSize = fontSize;
	}
	
	/**
	 * Définir la couleur du texte à utiliser.
	 * @param fontColor Couleur du texte
	 */
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
		setText(text);
	}
}
