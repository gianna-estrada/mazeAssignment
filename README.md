# Maze Assignment 
This is my project to create a perfect maze and solve it, using data structures and algorithms I learned recently.

The main files are in the mazeAssignment folder. The other two folders, queue and stack, contain my lecturer's code for those data structures.

The MazeFrame class contains the Main function.

## Project Description
I made a 10x10 perfect maze that starts from the top left corner (entry point is at grid[0][0]) and ends in the bottom right corner (exit point is at grid[M-1][N-1]).
This was to make it simple and not overcomplicate it by adding a randomiser.

Whenever the maze solver algorithm visits a cell, that cell is coloured in lavender. Once the algorithm finds the exit, the shortest path is coloured in pale green
from the exit point to the entry point.

I split up the main parts of the project into three classes: **Cell**, **MazeCanvas**, and **MazeFrame**. The Cell class would hold the code for the *individual
squares* that make up the maze grid, the MazeCanvas class would hold the code for the *maze's GUI*, and the MazeFrame class would hold the code for *generating 
and solving the maze*.

## How to Run
### Eclipse
If you are using Eclipse, make sure to add JavaSE-1.7 or JavaSE-1.8. This is to ensure that the Java.swing imports work.
1. Right-click on the project.
2. Select Build Path > Add Libraries.
3. Select JRE System Library, then click Next.
4. Select Execution library and choose JavaSE-1.7 or JavaSE-1.8 (whichever appears for you).
5. Click Finish.

You can then run it.

### Terminal
Here are the steps to run it via the terminal:
1. Cd into the project (e.g. cd Downloads/mazeAssignment-master).
2. Type in **java -cp bin mazeAssignment.MazeFrame**

It should then open a window where you can see the maze being solved.

## Editing the Project
This section is what you can edit to change some bits of the maze. The code mentioned here is all in the *MazeFrame class*.

If you want to increase or decrease the number of rows in the maze, change the number in **int M = 10**. The same goes for the columns in the
maze, except you'll want to change the number in **int N = 10**. Just be wary that the maze may not display well with certain combinations of rows and columns.

If you want to change the initial window size of the GUI, change the numbers within **setSize(600, 600)** in the *MazeFrame constructor*.

If you want to change the delay between each cell being explored or coloured in, change the number within **Thread.sleep(300)** in the *pause() method*. It's
counted in milliseconds, so by default it pauses for 0.3 seconds.
