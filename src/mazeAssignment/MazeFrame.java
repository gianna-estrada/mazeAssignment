package mazeAssignment;

import javax.swing.*;
import queue.*;
import stack.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Generates the maze with randomised DFS, then solves it with BFS.
 */
public class MazeFrame extends JFrame {
	int M = 10; // number of rows
	int N = 10; // number of columns
	Cell [][] grid = new Cell[M][N]; // maze grid
	MazeCanvas mazeCanvas = new MazeCanvas(grid); // initialise the maze canvas to call repaint later
	
	Random rand = new Random(); // for randomising the maze
	
	/**
	 * Constructor for the frame of the maze.
	 */
	public MazeFrame() {
		setTitle("My Perfect Maze!");
		setSize(600, 600); // size of the window for the maze GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialiseGrid();
		grid[0][0].left = false; // entry point
		grid[M - 1][N - 1].right = false; // exit point
		generateMazeRandomisedDFS();
		add(mazeCanvas);
		
		// Calls the maze solver method as a thread so that the pause method can be called
		Thread solver = new Thread(() -> mazeSolverBFS());
		solver.start();
	}
	
	/**
	 * The generator for the maze, using Depth First Search. <br>
	 * It removes the wall between the current cell and a random neighbour,
	 * until all cells within the maze grid have been visited. <br>
	 * The stack keeps track of all cells that have been visited, so that it
	 * can easily backtrack once it hits a dead end.
	 */
	private void generateMazeRandomisedDFS() {
		// Use randomised DFS to tear down correct walls to generate a perfect maze
		int rowStart = 0; 
		int colStart = 0;
		StackReferenceBased stack = new StackReferenceBased();
		Cell curr = grid[rowStart][colStart]; // maze start point is top-left
		curr.visited = true;
		stack.push(curr);
		
		while(!stack.isEmpty()) {
			curr = (Cell) stack.peek();
			
			ArrayList<Cell> adj = new ArrayList<Cell>(); // cells adjacent to current
			
			// Check cell above current
			if(curr.row > 0 && !grid[curr.row - 1][curr.col].visited) {
				adj.add(grid[curr.row - 1][curr.col]);
			}
			
			// Check cell to current's right
			if(curr.col < (N - 1) && !grid[curr.row][curr.col + 1].visited) {
				adj.add(grid[curr.row][curr.col + 1]);
			}
			
			// Check cell below current
			if(curr.row < (M - 1) && !grid[curr.row + 1][curr.col].visited) {
				adj.add(grid[curr.row + 1][curr.col]);
			}
			
			// Check cell to current's left
			if(curr.col > 0 && !grid[curr.row][curr.col - 1].visited) {
				adj.add(grid[curr.row][curr.col - 1]);
			}
			
			// Chooses a random adjacent cell if there's any
			if(!adj.isEmpty()) {
				int randomNum = (int)(Math.random() * adj.size());
				Cell next = adj.get(randomNum);
				
				// Get rid of wall between current and next
				// Next is below current
				if(curr.row < next.row) {
					curr.bottom = false;
					next.top = false;
				}
				// Next is above current
				else if(curr.row > next.row) {
					curr.top = false;
					next.bottom = false;
				}
				// Next is to the right of current
				else if(curr.col < next.col) {
					curr.right = false;
					next.left = false;
				}
				// Next is to the left of current
				else if(curr.col > next.col) {
					curr.left = false;
					next.right = false;
				}
				
				next.visited = true;
				stack.push(next);
			}
			
			// Current has no neighbours to visit, so it has to backtrack
			else {
				stack.pop();
			}
		}
		
		// Once the maze is generated, reset each cell's
		// visited status for the maze solver
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				grid[i][j].visited = false;
			}
		}
	}
	
	/**
	 * The solver for the maze, using Breadth First Search. <br>
	 * The queue keeps track of the cells that should be checked later.
	 */
	private void mazeSolverBFS() {
		int entryRow = 0; // row entrance to maze
		int entryCol = 0; // column entrance to maze
		
		QueueArrayBased queue = new QueueArrayBased();
		Cell curr = grid[entryRow][entryCol]; // maze start point is top-left
		
		curr.visited = true;
		queue.enqueue(curr);
		
		while(!queue.isEmpty()) {
			curr = (Cell) queue.dequeue();
			
			// Stop once the exit point is reached
			if(curr == grid[M - 1][N - 1]) {
				break;
			}
			
			// Check cell above current
			if(!curr.top && curr.row > 0) {
				Cell next = grid[curr.row - 1][curr.col];
				
				visitNextCell(next, curr, queue);
			}
			
			// Check cell to current's right
			if(!curr.right && curr.col < (N - 1)) {
				Cell next = grid[curr.row][curr.col + 1];
				
				visitNextCell(next, curr, queue);
			}
			
			// Check cell below current
			if(!curr.bottom && curr.row < (M - 1)) {
				Cell next = grid[curr.row + 1][curr.col];
				
				visitNextCell(next, curr, queue);
			}
			
			// Check cell to current's left
			if(!curr.left && curr.col > 0) {
				Cell next = grid[curr.row][curr.col - 1];
				
				visitNextCell(next, curr, queue);
			}
		}
		
		// Backtracks from the exit to the entry point
		// with the aid of the marked previous cells
		while(curr != null) {
			curr.shortestPath = true;
			mazeCanvas.repaint();
			pause();
			curr = curr.prev;
		}
		
	}
	
	/**
	 * Checks if the next cell has been visited. If not, the cell will be visited and put into
	 * the queue to check its neighbours later. <br>
	 * The maze GUI is updated with this visited cell and then there's a delay before a new
	 * cell is checked.
	 * @param next  the next cell
	 * @param curr  the current cell
	 * @param queue  the queue to keep track of the next cells' neighbours to visit
	 */
	private void visitNextCell(Cell next, Cell curr, QueueArrayBased queue) {
		if(!next.visited) {
			next.visited = true;
			next.prev = curr;
			queue.enqueue(next);
			
			mazeCanvas.repaint();
			pause();
		}
	}
	
	/**
	 * Pauses the thread for a specified amount of milliseconds.
	 */
	private void pause() {
		try {
			Thread.sleep(300);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates the grid for the maze.
	 */
	private void initialiseGrid() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				grid[i][j] = new Cell(i, j);
			}
		}
	}
	
	/**
	 * The main method. Makes the maze GUI visible.
	 * @param args
	 */
	public static void main(String[] args) {
		new MazeFrame().setVisible(true);
	}
}
