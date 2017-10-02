/**
 * Classe repr�sentant une cellule.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
 */
public class Cell {
	private int col, row;
	
	/**
	 * Constructeur
	 * @param col Num�ro de colonne
	 * @param row Num�ro de ligne
	 */
	public Cell(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	/**
	 * Obtenir le num�ro de colonne.
	 * @return Num�ro de colonne
	 */
	public int col() {
		return col;
	}
	
	/**
	 * Obtenir le num�ro de ligne.
	 * @return Num�ro de ligne
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
	 * @return R�sultat du test
	 */
	public boolean isInWorld() {
		return (col >= 0) && (col < PacManWorld.COLS) && (row >= 0) && (row < PacManWorld.ROWS);
	}
}
