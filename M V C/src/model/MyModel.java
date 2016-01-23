package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattenDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.demo.Maze3dSearchable;
import controller.Controller;
import algorithms.io.MyCompressorOutputStream;
import algorithms.io.MyDecompressorInputStream;
/**
 * generating the code behind each command
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-16
 */

public class MyModel extends CommonModel {
	HashMap<String, Maze3d> mazeCollection;//hash map with key-name of maze,value-maze 3d object
	ExecutorService threadPool;
	HashMap<String, String> mazeToFile;//hash map with key-name of maze,value-the file name where the maze is saved
	
	//hash map that hold the solution for mazes
	//We generated hashCode in maze3d,in order to find solution we already have
	//(if we got the same maze the hash code will find it,and the program will not need to solve them again)
	HashMap<Maze3d, Solution<Position>> mazeSolutions;

	/**
	 * {@inheritDoc}
	 */
	public MyModel(Controller c) {
		super(c);
		mazeCollection = new HashMap<String, Maze3d>();
		//Creates a thread pool that creates 10 new threads as needed (exit shut it down)
		threadPool = Executors.newFixedThreadPool(10);
		mazeToFile=new HashMap<String,String>();
		mazeSolutions=new HashMap<Maze3d, Solution<Position>>();
	}

	/**
	 * {@inheritDoc}
	 */
	public void HandleDirPath(String[] path) {
		if (path.length != 1) // path does'nt entered/contain to much parameters
		{
			c.passError("Invalid path please try again");
			return;
		}
		File f = new File(path[0].toString());

		if ((f.list() != null) && (f.list().length > 0)) {
			c.passDirPath(f.list());
		} else if (f.list() == null) // invalid path
		{
			c.passError("Invalid path please try again");
			return;
		} else // if there is nothing in the list
		{
			c.passError("Empty folder");
			return;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void handleGenerate3dMaze(String[] paramArray) {
		if (paramArray.length != 5) {
			{
				if(paramArray.length < 5)
				c.passError("not enough parameters please try again");
				else
				c.passError("too much parameters please try again");
			}
			return;
		}
		try {
			//As we got the parameters from the user as a string the parseInt method
			//Parses the string argument as a signed decimal integer
			if ((Integer.parseInt(paramArray[1]) <= 0) || (Integer.parseInt(paramArray[2]) <= 0)|| (Integer.parseInt(paramArray[3]) <= 0)) 
			{// checking if the sizes of the maze passed valid
				c.passError("Invalid parameters please try again");
				return;
			}
			//Thrown to indicate that the application has attempted to convert a string to one of the numeric types,
			//but that the string does not have the appropriate format
		} catch (NumberFormatException e) {
			c.passError("Invalid parameters enter numbers in x, y and z");
			return;
		}

		threadPool.execute(new Runnable() {
			public void run() 
			{
				Maze3dGenerator m3dg;
				if (paramArray[paramArray.length - 1].intern() == "simple") {
					m3dg = new SimpleMaze3dGenerator();
				} else if (paramArray[paramArray.length - 1].intern() == "prim") {
					m3dg = new MyMaze3dGenerator();
				} else {
					c.passError("You didn't chose simple/prim please try again");
					return;
				}

				if (mazeCollection.containsKey(paramArray[0].toString())) {
					c.passError("This name already exists,choose another one.");
					return;
				}

				mazeCollection.put(paramArray[0].toString(), m3dg.generate(Integer.parseInt(paramArray[1]),
						Integer.parseInt(paramArray[2]), Integer.parseInt(paramArray[3])));
				// generate maze with specified algorithm ,with specified sizes.

				c.passGenerate3dMaze("maze " + paramArray[0].toString() + " is ready");
				
			}
		});
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void handleDisplayName(String[] name) {
		if (name.length != 1) {
			{
				if(name.length < 1)
				c.passError("You didn't enter a name please try again");
				else
				c.passError("too much parameters please try again");
			}
		}

		
		if (!mazeCollection.containsKey(name[0].toString())) {
			c.passError("Maze doesn't exists");
			return;
		}

		c.passDisplayName(mazeCollection.get(name[0].toString()).toByteArray());
		// getting the maze from maze collection
		

	}

	/**
	 * {@inheritDoc}
	 */
	public void handleDisplayCrossSectionBy(String[] paramArray) 
	{
		if ((paramArray.length != 4) || (paramArray[2].intern() != "for")) 
		{

			if(paramArray.length < 4)
			c.passError("not enough parameters please try again");
			else if((paramArray.length > 4))
			c.passError("too much parameters please try again");
			else if (paramArray[2].intern() != "for")
				c.passError("You forgot typing for after writing the index try again");
		}

		if (!mazeCollection.containsKey(paramArray[3].toString())) // checking if maze is in the collection
		{
			c.passError("This maze doesn't exists");
			return;
		}

		
		if (paramArray[0].intern() == "x") {
			Maze3d maze = mazeCollection.get(paramArray[3].toString());
			try {
				int[][] crossSection = maze.getCrossSectionByX(Integer.parseInt(paramArray[1]));
				c.passDisplayCrossSectionBy(crossSection);
				return;
			} 
			//Thrown to indicate that the application has attempted to convert a string to one of the numeric types,
			//but that the string does not have the appropriate format
			catch (NumberFormatException e) {
				c.passError("Invalid parameters");
				return;
			} catch (IndexOutOfBoundsException e) {
				c.passError("Invalid x coordinate");
				return;
			}

		} else if (paramArray[0].intern() == "y") 
		{
			Maze3d maze = mazeCollection.get(paramArray[3].toString());
			try 
			{
				int[][] crossSection = maze.getCrossSectionByY(Integer.parseInt(paramArray[1]));
				c.passDisplayCrossSectionBy(crossSection);
				return;
			} 
			catch (NumberFormatException e) 
			{
				c.passError("Invalid parameters");
				return;
			} 
			catch (IndexOutOfBoundsException e) 
			{
				c.passError("Invalid y coordinate");
				return;
			}
		} 
		else if (paramArray[0].intern() == "z") 
		{
			Maze3d maze = mazeCollection.get(paramArray[3].toString());
			try {
				int[][] crossSection = maze.getCrossSectionByZ(Integer.parseInt(paramArray[1]));
				c.passDisplayCrossSectionBy(crossSection);
				return;
			} catch (NumberFormatException e) {
				c.passError("Invalid parameters");
				return;
			} catch (IndexOutOfBoundsException e) {
				c.passError("Invalid z coordinate");
				return;
			}
		} else {
			c.passError("Invalid parameters");
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void handleSaveMaze(String[] paramArray) 
	{

		if (paramArray.length != 2) 
		{

			if(paramArray.length < 2)
			c.passError("not enough parameters please try again");
			else 
			c.passError("too much parameters please try again");
			return;
		}

		

		if (!(mazeCollection.containsKey(paramArray[0]))) 
		{
			c.passError("maze doesn't exists");
			return;
		}
		//saving the name of the maze,with the file where the maze is saved,in order to use it later in file size command
		mazeToFile.put(paramArray[0], paramArray[1]);

		Maze3d maze = mazeCollection.get(paramArray[0].toString());

		int size = maze.toByteArray().length;
		
		try 
		{
			//using our i.o class MyCompressorOutputStream. init it and using it's write method to save the file
			MyCompressorOutputStream out = new MyCompressorOutputStream(new FileOutputStream(paramArray[1].toString()));
			//the byteBuffer will allocate 4 bytes for int size.
			out.write(ByteBuffer.allocate(4).putInt(size).array());
			out.write(maze.toByteArray());
			c.passSaveMaze(paramArray[0].toString() + " has been saved");
			out.close();
			return;
		} 
		catch (FileNotFoundException e) 
		{
			c.passError("File not found");
			return;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void handleLoadMaze(String[] paramArray)
	{
		if (paramArray.length != 2) 
		{
			if(paramArray.length < 2)
			c.passError("not enough parameters please try again");
			else 
			c.passError("too much parameters please try again");
			return;
		}
		//checking if there are already maze with the same name
		if (mazeCollection.containsKey(paramArray[1])) 
		{
			c.passError("Invalid name,this name is taken");
			return;
		}
		
		if(mazeToFile.containsKey(paramArray[1]))
		{
			if(mazeToFile.get(paramArray[1])!=paramArray[0])
			{
				c.passError("This name already exists");
				return;
			}
		}
		mazeToFile.put(paramArray[1], paramArray[0]);
			
		try 
		{	
			MyDecompressorInputStream in = new MyDecompressorInputStream(new FileInputStream(paramArray[0].toString()));

			// The ByteArrayOutputStream class stream creates a buffer in memory
			// and all the data sent to the stream is stored in the buffer.
			ByteArrayOutputStream ByteOutputStream = new ByteArrayOutputStream();

			// reading 4 bytes from file,which shows the size of the maze
			ByteOutputStream.write(in.read());
			ByteOutputStream.write(in.read());
			ByteOutputStream.write(in.read());
			ByteOutputStream.write(in.read());

			// creates input stream from a buffer initialize in bracelet
			ByteArrayInputStream inByte = new ByteArrayInputStream(ByteOutputStream.toByteArray());// taking out the buffer I wrote to
			DataInputStream dis = new DataInputStream(inByte);// easier to read data from stream(can read primitive types)

			// What we did:created an input stream and wrote there my 4 bytes from file.
			// than created input stream,where we put our byte array,than read from this buffer integer.
			
			// construct an array of byte,in the size that was readen from file.
			byte[] byteArr = new byte[dis.readInt()];
			in.read(byteArr);
			mazeCollection.put(paramArray[1], new Maze3d(byteArr));
			c.passLoadMaze(paramArray[1] + " has been loaded from file: " + paramArray[0]);
			in.close();
			return;
		} 
		catch (FileNotFoundException e) 
		{
			c.passError("File not found");
			return;
		} 
		catch (IOException e) 
		{

			e.printStackTrace();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void handleMazeSize(String[] name) 
	{
		if (name.length != 1) 
		{
			if(name.length < 1)
			c.passError("You didn't write the maze's name please try again");
			else 
			c.passError("too much parameters please try again");
			return;
		}
		if (!mazeCollection.containsKey(name[0])) 
		{
			c.passError("maze doesn't exists");
			return;
		}

		Maze3d maze = mazeCollection.get(name[0]);

		
		c.passMazeSize(maze.toByteArray().length);
		
	}
	/**
	 * {@inheritDoc}
	 */
	public void handleFileSize(String[] name)
	{
		if(name.length!=1)
		{
			if(name.length<1) 
			c.passError("You didn't write the maze's name please try again");
			else 
			c.passError("too much parameters please try again");
			return;
		}
		
		//checking if there is a file that have this maze
		if(!(mazeToFile.containsKey(name[0])))
		{
			c.passError("maze doesn't exists in file");
			return;
		}
		
		try
		{
			//taking the file name from the right hashmap
			File file=new File(mazeToFile.get(name[0]));
			c.passFileSize(file.length());
			return;
		}
		catch (NullPointerException e)
		{
			c.passError("you didn't entered path name");
			return;
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void handleSolve(String[] paramArray)
	{
		if(paramArray.length!=4)
		{
			if(paramArray.length!=2)
			{
				c.passError("invalid amount of parameters");
				return;
			}
		}
			
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() 
			{
				//using StringBuilder instead of stringBuffer as it will be faster under most implementations.
				StringBuilder algo=new StringBuilder();
				for(int i=1;i<paramArray.length;i++)
				{
					if(i==paramArray.length-1)
					{
						//Appends the current string to the algorithm
						algo.append(paramArray[i]);
					}
					else
					{
						//Appends space to the algorithm name
						algo.append(paramArray[i]+" ");
					}
					

				}
				//check if i generated maze with this name
				if(!(mazeCollection.containsKey(paramArray[0])))
				{
					c.passError("Maze doesn't exist");
					return;
				}
				//if we have a solution for this maze
				if(mazeSolutions.containsKey(mazeCollection.get(paramArray[0])))
				{
					c.passSolve("Solution for "+paramArray[0]+ " is ready");
					return;
				}
				
				
				
				if(algo.toString().equals("bfs") || algo.toString().equals("BFS"))
				{
					//Get the maze's name out of the hashmap
					Maze3dSearchable ms=new Maze3dSearchable(mazeCollection.get(paramArray[0]));
					Searcher<Position> bfs=new BFS<Position>();
					Solution<Position> sol=bfs.search(ms);
					//Enter the solution to the hashmap
					mazeSolutions.put(mazeCollection.get(paramArray[0]), sol);
					
					c.passSolve("Solution for "+paramArray[0]+ " is ready");
					return;
				}
				else if(algo.toString().equals("astar air distance") || algo.toString().equals("Astar air distance"))
				{
					//Get the maze's name out of the hashmap
					Maze3dSearchable ms=new Maze3dSearchable(mazeCollection.get(paramArray[0]));
					Searcher<Position> AstarAir=new AStar<Position>(new MazeAirDistance());
					Solution<Position> sol=AstarAir.search(ms);
					//Enter the solution to the hashmap
					mazeSolutions.put(mazeCollection.get(paramArray[0]), sol);
					
					c.passSolve("Solution for "+paramArray[0]+ " is ready");
					return;
				}
				else if(algo.toString().equals("astar manhatten distance") || algo.toString().equals("Astar manhatten distance"))
				{
					//Get the maze's name out of the hashmap
					Maze3dSearchable ms=new Maze3dSearchable(mazeCollection.get(paramArray[0]));
					Searcher<Position> AStarMan=new AStar<Position>(new MazeManhattenDistance());
					Solution<Position> sol=AStarMan.search(ms);
					//Enter the solution to the hashmap
					mazeSolutions.put(mazeCollection.get(paramArray[0]), sol);
					
					
					c.passSolve("Solution for "+paramArray[0]+ " is ready");
					return;
				}
				else
				{
					c.passError("This algorithm isn't from the list bellow bfs, astar manhatten distance, astar air distance");
					return;
				}
				
			}
		});
	}
	/**
	 * {@inheritDoc}
	 */
	public void handleDisplaySolution(String[] name)
	{
		if(name.length!=1)
		{
			c.passError("Invalid amount of parameters");
			return;
		}
		
		if(!(mazeCollection.containsKey(name[0])))
		{
			c.passError("This maze with this name doesn't exists");
			return;
		}
		
		if(mazeSolutions.containsKey(mazeCollection.get(name[0])))
		{
			//take the maze from maze3d,and pass it to the maze Solution,and it return the solution
			c.passDisplaySolution(mazeSolutions.get(mazeCollection.get(name[0])));
			return;
		}
		else
		{
			c.passError("Solution doesn't exists(use solve command first)");
			return;
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public void handleExitCommand(String[] emptyArr)
	{
		//exit the program, no new tasks will be accepted
		threadPool.shutdown();
		try 
		{
			//boolean method to check if this executor terminated or the timeout elapsed before termination
			while(!(threadPool.awaitTermination(10, TimeUnit.SECONDS)));
		} 
		catch (InterruptedException e) {

			e.printStackTrace();
		}
		
	}
	
}