package mazeAssignment;

/**
 * The individual squares that make up the grid or maze.
 */
public class Cell {
	public boolean visited; // has the cell been visited before
	int row; // cell position in row
	int col; // cell position in column
	public boolean left, right, top, bottom; // walls of the cell
	public Cell prev; // the cell previous to the current one
	public boolean shortestPath; // is the cell part of the shortest path between maze's exit and entry
	
	/**
	 * Constructor for the cell. <br>
	 * The maze generate and search methods have not been called yet, so visited & shortestPath 
	 * start as false. <br>
	 * All of the walls start as true so they can be removed by the maze generator method.
	 * @param r  row
	 * @param c  column
	 */
	public Cell(int r, int c) {
		visited = false;
		row = r;
		col = c;
		left = right = top = bottom = true;
		prev = null;
		shortestPath = false; 
	}
}
