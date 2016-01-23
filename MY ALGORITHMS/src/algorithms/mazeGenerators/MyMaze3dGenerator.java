package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class which generate maze with Prim's algorithm
* @author Chen Zissu
 * @version 1.0
 * @since 2015-11-15
 */
public class MyMaze3dGenerator extends CommonMaze3dGenarator {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Maze3d generate(int size_x, int size_y, int size_z) 
	{
		Maze3d myMaze;
		if(size_x<4)
		{
			myMaze=new Maze3d(4, size_y, size_z);
			size_x=4;
		}
		if(size_y<4)
		{
			myMaze=new Maze3d(size_x, 4, size_z);
			size_y=4;
		}
		if(size_z<4)
		{
			myMaze=new Maze3d(size_x, size_y, 4);
			size_z=4;
		}
		else
		{
			myMaze=new Maze3d(size_x, size_y, size_z);
		}
		//I used Prim's algorithm
		
		ArrayList<Position> AL=new ArrayList<Position>();//array of walls
		int i,j,n;
		int randomCell=0;
		Position entrance=new Position();
		Position exit=new Position();
		Position currentPos=new Position();
		
		Random rand=new Random();
		myMaze.fillWithWalls();//fill 1 all over the maze
		
		
		
		//I am starting with a random cell,this cell is for sure not in the shell
		i=rand.nextInt(size_x-2)+1;
		j=rand.nextInt(size_y-2)+1;
		n=rand.nextInt(size_z-2)+1;
		
		myMaze.setValueInPlace(new Position(i,j,n), 0);//put in this cell zero
		this.fillArrayOfWalls(new Position(i,j,n), myMaze, AL);//placing all the walls around the cell into array of walls
		
		while (AL.isEmpty()==false)
		{
			randomCell=rand.nextInt(AL.size());//take a random wall from array of walls
			
			currentPos=AL.get(randomCell);//take the content of the cell,the current position we will be working at.
			
			if(countNumberOfZerosAroundCell(currentPos, myMaze)==1)//if the amount of spaces aroud the current cell is 1
			{
				if(isAShell(currentPos, myMaze)==false)
				{
					myMaze.setValueInPlace(currentPos, 0);//place zero in current cell
					fillArrayOfWalls(currentPos, myMaze, AL);
					//take the walls that sround it and put it in array of walls
				}
			}
			AL.remove(randomCell);
		}
		
		//till now we don't have any exits	
		entrance=findExits(myMaze);

		do
		{
			exit=findExits(myMaze);
		}while(isClose(entrance, exit));
		
		myMaze.setValueInPlace(entrance, 0);
		myMaze.setValueInPlace(exit, 0);
		
		myMaze.setStartPosition(entrance);
		myMaze.setGoalPosition(exit);
		
		return myMaze;
	}
	/**
	 * checking if two positions are close
	 * @param a position a
	 * @param b position b
	 * @return true-if close(one cell distance),false-if far
	 */
	private Boolean isClose(Position a,Position b)
	{
		int xa=a.getX();
		int ya=a.getY();
		int za=a.getZ();
		
		int xb=b.getX();
		int yb=b.getY();
		int zb=b.getZ();
		
		if((xa==xb)&&(ya==yb)&&(za==zb))
		{
			return true;
		}
//		else if((xa+1==xb)||(xa-1==xb))
//		{
//			return true;
//		}
//		else if((ya-1==yb)||(ya-1==yb))
//		{
//			return true;
//		}
//		else if((za+1==zb)||(za-1==zb))
//		{
//			return true;
//		}
		else 
		{
			return false;
		}
	}
	/**
	 * generating exits in builded maze
	 * @param myMaze the maze we generate there the exits
	 * @return the position of the exit
	 */
	private Position findExits(Maze3d myMaze)
	{
		Random rand=new Random();
		int i,j,n;

		Position entrance=new Position();
		int size_x=myMaze.getSize_x();
		int size_y=myMaze.getSize_y();
		int size_z=myMaze.getSize_z();
		
		int wall=rand.nextInt(3);//choose the sides that will have the exits 
		switch (wall) {
		case 0:
			n=rand.nextInt(2)*(size_x-1);//choose the entrance wall
			for(i=0;i<size_y;i++)
			{
				for(j=0;j<size_z;j++)
				{
					if(countNumberOfZerosAroundCell(new Position(n,i,j), myMaze)==1)//check if there is a an empty space so we can insert the entrance nearby
					{
						
						entrance=new Position(n,i,j);
						return entrance;
						
					}
				}

			}
			break;
		case 1:
			n=rand.nextInt(2)*(size_y-1);
			for(i=0;i<size_x;i++)
			{
				for(j=0;j<size_z;j++)
				{
					if(countNumberOfZerosAroundCell(new Position(i,n,j), myMaze)==1)
					{
						
						entrance=new Position(i,n,j);
						return entrance;
					}
				}

			}
			break;
		case 2:
			n=rand.nextInt(2)*(size_z-1);
			for(i=0;i<size_x;i++)
			{
				for(j=0;j<size_y;j++)
				{
					if(countNumberOfZerosAroundCell(new Position(i,j,n), myMaze)==1)
					{
						
						entrance=new Position(i,j,n);
						return entrance;
					}
				}

			}
			break;
		default:
			n=rand.nextInt(2)*(size_x-1);
			for(i=0;i<size_y;i++)
			{
				for(j=0;j<size_z;j++)
				{
					if(countNumberOfZerosAroundCell(new Position(n,i,j), myMaze)==1)
					{
						
						entrance=new Position(n,i,j);
						return entrance;
					}
				}

			}
			break;
		}
		return entrance;
	}
	
	
	/**
	 * count the number of empty cells around position
	 * @param p position we check
	 * @param maze the maze we work with
	 * @return the number of empty cells around position
	 */
	private int countNumberOfZerosAroundCell(Position p,Maze3d maze)
	{
		
		int i=p.getX();
		int j=p.getY();
		int n=p.getZ();
		
		
		//count how much zeros there are around the cell
		int count=0;
		if((i-1>=0)&&(maze.getValueIn(i-1, j, n)==0))
		{
			count++;
		}
		if((i+1<maze.getSize_x())&&(maze.getValueIn(i+1, j, n)==0))
		{
			count++;
		}
		if((j-1>=0)&&(maze.getValueIn(i, j-1, n)==0))
		{
			count++;
		}
		if((j+1<maze.getSize_y())&&(maze.getValueIn(i, j+1, n)==0))
		{
			count++;
		}
		if((n-1>=0)&&(maze.getValueIn(i, j, n-1)==0))
		{
			count++;
		}
		if((n+1<maze.getSize_z())&&(maze.getValueIn(i, j, n+1)==0))
		{
			count++;
		}
		return count;
	}
}
