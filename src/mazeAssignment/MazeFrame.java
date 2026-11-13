package mazeAssignment;

import javax.swing.*;
import queue.*;
import stack.*;
import java.util.ArrayList;
import java.util.Random;

public class MazeFrame extends JFrame {
	int M = 8; // number of rows
	int N = 8; // number of columns
	Cell [][] grid = new Cell[M][N];
	MazeCanvas mazeCanvas = new MazeCanvas(grid); // initialise the canvas to call repaint later
	
	Random rand = new Random(); // for randomising the maze
	
	public MazeFrame() {
		setTitle("My Perfect Maze!");
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialiseGrid();
		grid[0][0].left = false; // entry point
		grid[M - 1][N - 1].right = false; // exit point
		generateMazeRandomisedDFS();
		add(mazeCanvas);
		
		// Call the maze solver method as a thread
		Thread solver = new Thread(() -> mazeSolverBFS());
		solver.start();
	}
	
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
			
			// Choose a random adjacent cell if there's any
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
	
	
	private void mazeSolverBFS() {
		int entryRow = 0; // row entrance to maze
		int entryCol = 0; // column entrance to maze
		
		QueueArrayBased queue = new QueueArrayBased();
		Cell curr = grid[entryRow][entryCol];
		
		curr.explored = true;
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
				
				if(!next.visited) {
					next.visited = true;
					next.explored = true;
					next.prev = curr;
					queue.enqueue(next);
					
					mazeCanvas.repaint();
					pause();
				}
			}
			
			// Check cell to current's right
			if(!curr.right && curr.col < (N - 1)) {
				Cell next = grid[curr.row][curr.col + 1];
				
				if(!next.visited) {
					next.visited = true;
					next.explored = true;
					next.prev = curr;
					queue.enqueue(next);
					
					mazeCanvas.repaint();
					pause();
				}
			}
			
			// Check cell below current
			if(!curr.bottom && curr.row < (M - 1)) {
				Cell next = grid[curr.row + 1][curr.col];
				
				if(!next.visited) {
					next.visited = true;
					next.explored = true;
					next.prev = curr;
					queue.enqueue(next);
					
					mazeCanvas.repaint();
					pause();
				}
			}
			
			// Check cell to current's left
			if(!curr.left && curr.col > 0) {
				Cell next = grid[curr.row][curr.col - 1];
				
				if(!next.visited) {
					next.visited = true;
					next.explored = true;
					next.prev = curr;
					queue.enqueue(next);
					
					mazeCanvas.repaint();
					pause();
				}
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
	
	private void pause() {
		try {
			Thread.sleep(300);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	private void initialiseGrid() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				grid[i][j] = new Cell(i, j);
			}
		}
	}
	
	public static void main(String[] args) {
		new MazeFrame().setVisible(true);
	}
}
