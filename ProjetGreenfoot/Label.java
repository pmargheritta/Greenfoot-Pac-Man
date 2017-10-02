import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.awt.Color;
import java.awt.Font;

/**
 * Classe repr�sentant une �tiquette de texte.
 * @author S�bastien Klopfenstein
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
	 * Obtenir le texte affich�.
	 * @return Texte
	 */
	public String text() {
		return text;
	}
	
	/**
	 * D�finir le texte � afficher.
	 * @param text Texte
	 */
	public void setText(String text) {
		this.text = text;
		image.clear();
		image.setColor(fontColor);
		image.drawString(text, 0, (image.getHeight() + fontSize) / 2);
	}
	
	/**
	 * D�finir la fonte � utiliser.
	 * @param fontFamily Nom de la fonte
	 */
	public void setFontFamily(String fontFamily) {
		image.setFont(new Font(fontFamily, fontStyle, fontSize));
		this.fontFamily = fontFamily;
	}
	
	/**
	 * D�finir le style de texte � utiliser.
	 * @param fontStyle Style du texte
	 */
	public void setFontStyle(int fontStyle) {
		image.setFont(new Font(fontFamily, fontStyle, fontSize));
		this.fontStyle = fontStyle;
	}
	
	/**
	 * D�finir la taille de texte � utiliser.
	 * @param fontSize Taille du texte
	 */
	public void setFontSize(int fontSize) {
		image.setFont(new Font(fontFamily, fontStyle, fontSize));
		this.fontSize = fontSize;
	}
	
	/**
	 * D�finir la couleur du texte � utiliser.
	 * @param fontColor Couleur du texte
	 */
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
		setText(text);
	}
}
