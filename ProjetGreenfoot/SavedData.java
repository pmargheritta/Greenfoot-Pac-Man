import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Classe abstraite représentant des données sauvegardées.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public abstract class SavedData<T> {	
	protected T data;
	
	/**
	 * Donner le chemin d'accès aux données.
	 * @return Chemin d'accès
	 */
	public abstract String path();
	
	/**
	 * Obtenir les données.
	 * @return Données
	 */
	public T data() {
		return data;
	}

	/**
	 * Lire le fichier de sauvegarde.
	 * @throws ClassNotFoundException Classe de l'objet lu non identifiable
	 * @throws IOException Erreur de lecture du fichier
	 */
	@SuppressWarnings("unchecked")
	public void read() throws ClassNotFoundException, IOException {
		InputStream fileIn = null;

		try {
			try {
				fileIn = new FileInputStream("ProjetGreenfoot/" + path()); // Dans Eclipse
			}
			catch (IOException e) {
				fileIn = new FileInputStream(path()); // Dans Greenfoot
			}
			ObjectInputStream in = new ObjectInputStream(fileIn);
			data = (T) in.readObject();
			in.close();
		}
		catch (EOFException e) {
			// Lorsque le fichier est vide
		}
		finally {
			if (fileIn != null) {
				fileIn.close();
			}
		}
	}
	
	/**
	 * Écrire dans le fichier de sauvegarde.
	 * @throws IOException Erreur de lecture du fichier
	 */
	public void write() throws IOException {
		OutputStream fileOut = null;
		
		try {
			try {
				fileOut = new FileOutputStream("ProjetGreenfoot/" + path()); // Dans Eclipse
			}
			catch (IOException e) {
				fileOut = new FileOutputStream(path()); // Dans Greenfoot
			}
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(data);
			out.close();
		}
		finally {
			if (fileOut != null) {
				fileOut.close();
			}
		}
	}
}
