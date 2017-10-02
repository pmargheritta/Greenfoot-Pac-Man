/**
 * Classe représentant une cellule.
 * @author Sébastien Klopfenstein
 * @author Paul Margheritta
 */
public class Cell {
	private int col, row;
	
	/**
	 * Constructeur
	 * @param col Numéro de colonne
	 * @param row Numéro de ligne
	 */
	public Cell(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	/**
	 * Obtenir le numéro de colonne.
	 * @return Numéro de colonne
	 */
	public int col() {
		return col;
	}
	
	/**
	 * Obtenir le numéro de ligne.
	 * @return Numéro de ligne
	 */
	public int row() {
		return row;
	}
	
	public boolean equals(Object object) {
		if (object.getClass() == Cell.class) {
			Cell cell = (Cell) object;
			return (cell.col() == col) && (cell.row() == row);
		}
		else {
			return false;
		}
	}
	
	/**
	 * Tester si la cellule est dans le monde.
	 * @return Résultat du test
	 */
	public boolean isInWorld() {
		return (col >= 0) && (col < PacManWorld.COLS) && (row >= 0) && (row < PacManWorld.ROWS);
	}
}
