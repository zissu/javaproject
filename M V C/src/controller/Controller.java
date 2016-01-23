package controller;
import model.Model;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import view.View;
/**
 * the controller links between the view and the model,which not familiar with each other
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-16
 */
public interface Controller {
	/**
	 * sets the controller's model in order to initialize the controller in the main
	 * @param model Model
	 */
	void setM(Model model);
	/**
	 * sets the controller's view in order to initialize the controller in the main
	 * @param view View
	 */
	void setV(View view);
	
	

	/**
	 * transfer the names of files and directories to the view
	 * @param path array of strings containing the names of files and directories in the specified path
	 */
	public void passDirPath(String[] path);
	/**
	 * In an error case passes the error to view
	 * @param eror the error string
	 */
	public void passError(String eror);
	/**
	 * passes the message:the maze you generated is ready
	 * @param ready maze is ready
	 */
	public void passGenerate3dMaze(String ready);
	/**
	 * passes the generated maze to view which display it.
	 * It passes as byte array in order to compress it.
	 * @param byteArr byte array containing the maze
	 */
	public void passDisplayName(byte[] byteArr);
	/**
	 * passes cross section to view
	 * @param crossSection cross section 2d array
	 */
	public void passDisplayCrossSectionBy(int[][] crossSection);
	/**
	 * pass the string that saying the maze has been saved
	 * @param message string that tells-maze has been saved
	 */
	public void passSaveMaze(String message);
	/**
	 * pass the message:the maze has been loaded
	 * @param message maze has been loaded
	 */
	public void passLoadMaze(String message);
	/**
	 * passing the maze size to view to display
	 * @param size maze size in bytes
	 */
	public void passMazeSize(int size);
	/**
	 * passing the maze size in file to view for display
	 * @param size maze size in file in bytes
	 */
	public void passFileSize(long size);
	/**
	 * passing a message that the maze has been solved
	 * @param message string with the phrase maze has been solved
	 */
	public void passSolve(String message);
	/**
	 * passing the solution of the maze
	 * @param sol solution of the maze
	 */
	public void passDisplaySolution(Solution<Position> sol);
}