package mazeAssignment;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class MazeCanvas extends Canvas {
	Color PALE_GREEN = new Color(151, 230, 172);
	Cell [][] grid;
	
	public MazeCanvas(Cell [][] grid_arg) {
		grid = grid_arg;
	}
	
	private void drawCell(Graphics g, Cell c, int top, int left, int cell_dim) {
		top = top + 1;
		
		// Colours in all the cells part of the shortest path
		if(c.shortestPath) {
			g.setColor(PALE_GREEN);
			g.fillRect(left + 1, top + 1, cell_dim - 1, cell_dim - 1);
		}
		
		g.setColor(Color.BLACK); // sets the wall colour
		
		if(c.top)
			g.drawLine(left, top, left + cell_dim, top); // draw top line
		if(c.left)
			g.drawLine(left, top, left, top + cell_dim); // draw left line
		if(c.bottom)
			g.drawLine(left, top + cell_dim, left + cell_dim, top + cell_dim); // draw bottom line
		if(c.right)
			g.drawLine(left + cell_dim, top, left + cell_dim, top + cell_dim); // draw right line
	}
	
	@Override
	public void paint(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		
		int cell_dim; // The size of a cell in pixels when drawn in screen
		
		if(w < h) {
			cell_dim = w / grid.length;
		}
		else {
			cell_dim = h / grid.length;
		}
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				drawCell(g, grid[i][j], i * cell_dim, j * cell_dim, cell_dim);
			}
		}
	}
}
