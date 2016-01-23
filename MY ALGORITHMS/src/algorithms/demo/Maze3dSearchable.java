package algorithms.demo;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.CommonSearchable;
import algorithms.search.State;

/**
 * object adapter,contains maze3D and inherit searchable
* @author Chen Zissu
 * @version 1.0
 * @since 2015-11-15
 */
public class Maze3dSearchable extends CommonSearchable<Position> {
	
	
	Maze3d maze;
	/**
	 * constructor with one parameter
	 * @param maze maze3D
	 */
	public Maze3dSearchable(Maze3d maze) {
		super();
		
		this.maze = maze;
	}
	/**
	 * get the starting state of the maze
	 */
	@Override
	public State<Position> getStartState() 
	{
		if(!checkIfSerachableIsValid())
		{
			return null;
		}
		State<Position> s=new State<Position>(maze.getStartPosition());
		return s;
	}
	/**
	 * getting the goal state of the maze
	 */
	@Override
	public State<Position> getGoalState()
	{
		if(!checkIfSerachableIsValid())
		{
			return null;
		}
		return new State<Position>(maze.getGoalPosition());
	}
	/**
	 * get all the possible state from current point in the maze
	 */
	@Override
	public ArrayList<State<Position>> getAllPossibleStates(State<Position> s) 
	{
		if(!checkIfSerachableIsValid())
		{
			return null;
		}
		ArrayList<State<Position>> AL=new ArrayList<State<Position>>();
		ArrayList<Position> ALP=maze.getAllPosibleStates(s.getState());
		for(int i=0;i<ALP.size();i++)
		{
			AL.add(new State<Position>(ALP.get(i)));
		}
		return AL;
	}
	/**
	 * get the cost between two states in the maze
	 */
	@Override
	public double getPassageCost(State<Position> a, State<Position> b) 
	{
		if(!checkIfSerachableIsValid())
		{
			return -1;
		}
		return 1;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkIfSerachableIsValid() {
		if(maze==null)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	

}
