package model;

/**
 * the model interface perform all the background calculations,than pass them to the controller.
 * this is the facade of the model
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-16
 */

public interface Model {
	/**
	 * Handling the command :dir path
	 * @param args Array of strings,containing one string with path
	 */
	public void HandleDirPath(String[] args);
	/**
	 * Handling the command:generate 3d maze name x y z algorithm
	 * name-name of the maze,x-amount of floors in maze,y-amount of rows,z-amount of columns,
	 * algorithm-prim(using MyMaze3dGenerator class)/simple (using simpleMaze3dGenerator class)
	 * @param paramArray Array of strings with the parameters i mentioned above
	 */
	public void handleGenerate3dMaze(String[] paramArray);
	/**
	 * Handling the command:display the maze name(name of the maze)
	 * @param name String Array with one string, the name of the maze that needs to be displayed
	 */
	public void handleDisplayName(String[] name);
	/**
	 * Handling the command:display cross section by (x,y,z) index for name
	 * @param paramArray Array of strings containing the axis(x, y or z), floor of the maze we created 
	 */
	public void handleDisplayCrossSectionBy(String[] paramArray);
	/**
	 * Handling the command:save maze name file name
	 * name-maze name generated before,file name-the name of the file to save maze to
	 * @param paramArray array of strings with the parameters of name and storage
	 */
	public void handleSaveMaze(String[] paramArray);
	/**
	 * handling command:load maze file name name
	 * loading maze from the file specified
	 * @param paramArray array of strings with file name and maze name
	 */
	public void handleLoadMaze(String[] paramArray);
	/**
	 * handling command:maze size name
	 * measure the maze's size in memory
	 * @param name array of one string with the name of the maze
	 */
	public void handleMazeSize(String[] name);
	/**
	 * handling command:file size name
	 * measure the size of maze constructed with bytes array in a file
	 * @param name array of one string with the name of the file
	 */
	public void handleFileSize(String[] name);
	/**
	 * handle command:solve name algorithm
	 * solves the maze specified,with specified algorithm bfs/AstarAirDistance/AstarManhattenDistance
	 * @param paramArray array of 2 strings:cell 0-name of the maze,1-the algorithm's name to find the solution of this maze
	 */
	public void handleSolve(String[] paramArray);
	/**
	 * handle command:display solution name
	 * display the path from the start position and the goal position
	 * @param name string array of the name of the maze
	 */
	public void handleDisplaySolution(String[] name);
	/**
	 * closing the running threads
	 * @param exit send back "bye bye"
	 */
	public void handleExitCommand(String[] exit);
}