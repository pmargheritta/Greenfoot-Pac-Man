import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedGraph;

/**
 * Classe repr�sentant un niveau.
 * @author S�bastien Klopfenstein
 * @author Paul Margheritta
*/
public class Level {
	private String mapName;
	private Cell[][] vertices;
	private SimpleDirectedGraph<Cell, Turn> graph; // Repr�senter une direction par un Turn pour garantir l'unicit� de chaque ar�te
	private PacManWorld world;
	
	/**
	 * Constructeur
	 * @param mapName Nom de la carte du niveau
	 * @param world Monde associ� au niveau
	 */
	public Level(String mapName, PacManWorld world) {
		this.mapName = mapName;
		vertices = new Cell[PacManWorld.COLS][PacManWorld.ROWS-1]; // Une ligne r�serv�e pour les afficheurs
		this.world = world;
	}
	
	/**
	 * Charger le niveau dans le monde � partir de la carte.
	 * @throws IOException Erreur d'ouverture de la carte du niveau
	 */
	public void load() throws IOException {
		InputStream in = null;
    	
    	try {
    		// Ouverture du fichier en lecture
    		try {
    			in = new FileInputStream("ProjetGreenfoot/levels/" + mapName); // Dans Eclipse
    		}
    		catch (IOException e) {
    			in = new FileInputStream("levels/" + mapName); // Dans Greenfoot
    		}
    		InputStreamReader reader = new InputStreamReader(in);
    		
    		// D�codage des symboles un par un
    		int symbol;
    		int col = 0, row = 0;
    		while ((symbol = reader.read()) != -1) {
    			decode(symbol, new Cell(col, row));
    			buildVertices(symbol, new Cell(col, row));
    			
    			col++;
				if (symbol == '\n') {
					row++;
					col = 0;
				}
			}
    		
    		reader.close();
    	}
    	finally {
    		if (in != null) {
    			in.close(); // Fermeture du fichier
    			buildGraph();
    		}
    	}
	}
	
	/**
     * D�coder un symbole de la carte du niveau en pla�ant l'objet associ� dans le monde.
     * @param symbol Symbole � d�coder
     * @param cell Cellule
     */
    public void decode(int symbol, Cell cell) {    	
    	switch (symbol) {
	    	case 'o':
	    		// Ajouter PacMan seulement s'il n'y est pas d�j�
	    		if (world.getObjects(PacMan.class).isEmpty()) {
	    			world.addObjectInCell(new PacMan(cell), cell);
	    		}
	    		break;
	    	case 'R':
	    		world.addObjectInCell(new RedGhost(cell), cell); break;
	    	case 'O':
	    		world.addObjectInCell(new OrangeGhost(cell), cell); break;
	    	case 'B':
	    		world.addObjectInCell(new BlueGhost(cell), cell); break;
	    	case 'P':
	    		world.addObjectInCell(new PinkGhost(cell), cell); break;
	    	case 'x':
	    		world.addObjectInCell(new Obstacle(), cell); break;
	    	case '.':
	    		world.addObjectInCell(new Dot(), cell); break;
	    	case '*':
	    		world.addObjectInCell(new Energizer(), cell); break;
	    	case '~':
	    		world.addObjectInCell(new Teleporter(), cell); break;
    	}
    }
    
    /**
     * Remplir une nouvelle case du tableau des n�uds selon le symbole correspondant � la cellule actuelle.
     * @param symbol Symbole
     * @param cell Cellule
     */
    public void buildVertices(int symbol, Cell cell) {
    	if (cell.col() < PacManWorld.COLS && cell.row() < PacManWorld.ROWS) {
	    	if (symbol == 'x') {
	    		vertices[cell.col()][cell.row()] = null; // C'est un obstacle : pas de n�ud
	    	}
	    	else {
	    		vertices[cell.col()][cell.row()] = cell;
	    	}
    	}
    }
    
    /**
     * Construire le graphe repr�sentant la carte du niveau.
     */
    public void buildGraph() {
    	SimpleDirectedGraph<Cell, Turn> graph = new SimpleDirectedGraph<Cell, Turn>(Turn.class);
    	
    	for (int i = 0; i < PacManWorld.COLS; i++) {
    		for (int j = 0; j < PacManWorld.ROWS-1; j++) {
    			if (vertices[i][j] != null) {
    				graph.addVertex(vertices[i][j]); // Cr�ation d'un n�ud
    				
    				// Cr�ation des ar�tes
    				if (i - 1 >= 0 && vertices[i-1][j] != null) {
    					graph.addEdge(vertices[i-1][j], vertices[i][j], new Turn(i, j, Direction.RIGHT));
    					graph.addEdge(vertices[i][j], vertices[i-1][j], new Turn(i, j, Direction.LEFT));
    				}
    				if (j - 1 >= 0 && vertices[i][j-1] != null) {
    					graph.addEdge(vertices[i][j-1], vertices[i][j], new Turn(i, j, Direction.DOWN));
    					graph.addEdge(vertices[i][j], vertices[i][j-1], new Turn(i, j, Direction.UP));
    				}
    			}
    		}
    	}
    	
    	this.graph = graph;
    }
    
    /**
     * Obtenir le graphe du niveau.
     * @return Graphe
     */
    public SimpleDirectedGraph<Cell, Turn> graph() {
    	return graph;
    }
    
    /**
     * Obtenir le plus court chemin entre deux cellules donn�es.
     * @param start Cellule de d�part
     * @param end Cellule d'arriv�e
     * @return Liste de d�placements
     */
    public List<Turn> shortestPath(Cell start, Cell end) {
    	return DijkstraShortestPath.findPathBetween(graph, vertices[start.col()][start.row()], vertices[end.col()][end.row()]);
    }
}
