package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Properties;


/**
 * defines the  command line interface
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-28
 */
public class CLI extends CommonView 
{
	BufferedReader in;
	PrintWriter out;
	boolean canBeClosed;
	String line;
	
	/**
	 * constructor with input and output stream parametrs
	 * @param in input stream source
	 * @param out output stream source
	 */
	public CLI(BufferedReader in, PrintWriter out) 
	{
		this.in=in;
		this.out=out;
		this.canBeClosed=false;
		this.line=new String();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() 
	{
		out.println("Hello,Welcome to my command line interface!\nPlease Enter command:\nfor help press,help");
		out.flush();
		new Thread(new Runnable() 
		{
			public void run() 
			{
				
				while(canBeClosed==false)
				{
					try 
					{
						
						line=in.readLine();
						if(!line.isEmpty())
						{
							setChanged();
							notifyObservers();
						}
					} 
					
					catch (IOException e) {
						
						e.printStackTrace();
					}
				}

			}
		}).start();
		
	}
	/**
	 * {@inheritDoc}
	 */
	public String getUserCommand()
	{
		return line;
	}
	/**
	 * {@inheritDoc}
	 */
	public void showError(String error)
	{
		out.println(error);
		out.flush();
	}
	/**
	 * {@inheritDoc}
	 */
	public void showDirPath(String[] list)
	{
		out.println("The files and directories in this folder are:");
		out.flush();
		for(String s:list)
		{
			out.println(s);
			out.flush();
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public void showGenerate3dMaze(String message)
	{
		out.println(message);
		out.flush();
	}
	/**
	 * {@inheritDoc}
	 */
	public void showDisplayName(byte[] byteArr)
	{
		try {
			Maze3d maze3d=new Maze3d(byteArr);
			int size_x=maze3d.getSize_x();
			int size_y=maze3d.getSize_y();
			int size_z=maze3d.getSize_z();
			int[][][] maze=maze3d.getMaze();
			
			
			StringBuilder aString=new StringBuilder();
			
			for(int i=0;i<size_x;i++)
			{
				aString.append("floor "+i+":\n\n");
				for(int j=0;j<size_y;j++)
				{

					for(int n=0;n<size_z;n++)
					{
						if(n==size_z-1)
						{
							aString.append(maze[i][j][n]);
						}
						else
						{
							aString.append(maze[i][j][n]+" ");
						}
						
					}
					aString.append("\n");
				}
				aString.append("\n\n");
			}
		
			out.println(aString);
			out.flush();
			out.println("The start position: ("+maze3d.getStartPosition().getX()+","+maze3d.getStartPosition().getY()+","+maze3d.getStartPosition().getZ()+")");
			out.println("The goal position:  ("+maze3d.getGoalPosition().getX()+","+maze3d.getGoalPosition().getY()+","+maze3d.getGoalPosition().getZ()+")");
			out.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public void showDisplayCrossSectionBy(int[][] crossSection)
	{
		for(int[] a:crossSection)
		{
			for(int b:a)
			{
				out.print(b+" ");
			}
			out.println();
		}
		out.flush();
	}
	/**
	 * {@inheritDoc}
	 */
	public void showSaveMaze(String message)
	{
		out.println(message);
		out.flush();
	}
	/**
	 * {@inheritDoc}
	 */
	public void showLoadMaze(String message)
	{
		out.println(message);
		out.flush();
	}
	/**
	 * {@inheritDoc}
	 */
	public void showMazeSize(int size)
	{
		out.println("The size of the maze is: "+ size);
		out.flush();
	}
	/**
	 * {@inheritDoc}
	 */
	public void showFileSize(long size)
	{
		out.println("The size of the maze inside the file is: "+ size);
		out.flush();
	}
	/**
	 * {@inheritDoc}
	 */
	public void showSolveMaze(String message)
	{
		out.println(message);
		out.flush();
	}
	/**
	 * {@inheritDoc}
	 */
	public void showDisplaySolution(Solution<Position> solution)
	{
		ArrayList<State<Position>> al=solution.getAL();
		String s="";
		for(int i=0;i<al.size();i++)
		{
			if(i==al.size()-1)
			{
				s=s+al.get(i).getState()+"\n ";
			}
			else
			{
				s=s+al.get(i).getState()+", ";
			}
			
		}
		out.println(s);
		out.flush();
	}
	
	
	
	/**
	 * {@inheritDoc}
	 */
	public void showExit()
	{
		canBeClosed=true;
		out.println("Bye bye!");
		out.flush();
	}
	/**
	 * {@inheritDoc}
	 */
	public void showHelp()
	{
		
		out.println("Help Center:");
		out.println("dir <path>                                           -display the files and directories in this specific path.");
		out.println("generate 3d maze <name> <x> <y> <z> <algorithm>      -generating maze with the specified name,with xyz dimensions with algorith:simple/prim");
		out.println("display <name>                                       -display the specified maze");
		out.println("display cross section by {x,y,z} <index> for <name>  -diplaying cross section(x,y or z,chose one) in the index specified for maze with this name");
		out.println("save maze <name> <file name>                         -save maze in file name specified");
		out.println("load maze <file name> <name>                         -load maze from file specified");
		out.println("maze size <name>                                     -display the size of maze in ram");
		out.println("file size <name>                                     -display the size of maze in file");
		out.println("solve <name> <algorithm>                             -solve maze with specified algorithm:bfs/Astar manhatten distance/astar air distance");
		out.println("display solution <name>                              -solve the maze and show the solution");
		out.println("exit                                                 -exit the program");
		out.println();
		out.println("<> -You have to write the requested string inside,{} -choose one of the fallwing and write inside the brackets");
		out.flush();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showSolveFrom(String message) {}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showDisplayHalfSolution(Solution<Position> solution) {}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showLoadXML(Properties p) {}
}
