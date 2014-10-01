package ui.components;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Vivian Stewart
 * every type of entity that can occupy one square of the game
 */
enum Type {
	EMPTY, WALL, DOOR, KEY, OUTOFBOUNDS;
}

/**
 * @author Vivian Stewart
 *
 */
public class GameScene
{
	private Type[][] map;
	private HashMap<Point, GraphicalObject> gameElements = new HashMap<Point, GraphicalObject>();
	private int xlimit, ylimit;
	
	public GameScene()
	{
		try
		{// read in the map
			Scanner sc = new Scanner( new BufferedInputStream( new FileInputStream( "map.txt" ) ) );
			// size of map is in the header
			xlimit = sc.hasNextInt() ? sc.nextInt() : 0;
			if ( sc.next().indexOf( 'x' ) == -1 || xlimit < 1 )
			{
				sc.close();
				throw new RuntimeException("Format error, header corrupt: columns = " + xlimit + " ?");
			}
			ylimit = sc.hasNextInt() ? sc.nextInt() : 0;
			if ( ylimit < 1 )
			{
				sc.close();
				throw new RuntimeException("Format error, header corrupt: rows = " + ylimit + " ?");
			}
			System.out.println( "columnss rows:" +xlimit+ ","+ylimit);
			map = new Type[ xlimit ][ ylimit ];
			// map values, a 2D array labelling the viewable scene
			for ( int j = 0; j < ylimit; ++j )
			{
				for ( int i = 0; i < xlimit; ++i )
				{
					if ( !sc.hasNext() )
					{
						sc.close();
						throw new RuntimeException("Format error, not enough data");
					}
					map[i][j] = Type.values()[ sc.nextInt() ];
				}
			}
			if ( sc.hasNext() )
			{
				sc.close();
				throw new RuntimeException("Format error, overflow of data");
			}
			sc.close();
		} catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void setBit( int x, int y, Type type )
	{
		if ( x >= xlimit || y >= ylimit )
			throw new IndexOutOfBoundsException( "Cannot index (" + x + "," + y + ") Bit" );
		map[ x ][ y ] = type;
	}
	
	public Type queryBit( int x, int y )
	{
		if ( x >= xlimit || y >= ylimit )
		{
			return Type.OUTOFBOUNDS;
		}
		return map[ x ][ y ];
	}
	
	public Point mapsize()
	{
		return new Point( xlimit, ylimit );
	}
	
	public boolean isCollidable( float x, float y )
	{
		return isCollidable( (int)(x/GameView.cellsize), (int)(y/GameView.cellsize) );
	}
	
	public boolean isCollidable( int x, int y )
	{
		if ( x >= xlimit || y >= ylimit )
		{
			return false;
			// throw new IndexOutOfBoundsException( "Cannot index (" + x + "," + y + ") Bit" );
		}
		if ( map[x][y] == Type.DOOR )
			return ((DoorWay)gameElements.get( new Point( x, y ) )).open();
		return map[x][y] == Type.WALL;
	}

	public void addSurrounds( ArrayList<GraphicalObject> toDraw, int scale )
	{
		for ( int j = 0; j < ylimit; ++j )
		{
			for ( int i = 0; i < xlimit; ++i )
			{
				Type north = j - 1 < 0       ? Type.OUTOFBOUNDS : map[ i ]  [j - 1]
					, east = i + 1 >= xlimit ? Type.OUTOFBOUNDS : map[i + 1] [ j ]
					, south = j + 1 >= ylimit ? Type.OUTOFBOUNDS : map[ i ]  [j + 1]
					, west = i - 1 < 0       ? Type.OUTOFBOUNDS : map[i - 1] [ j ];
				switch( map[i][j] )
				{
				case WALL :
					toDraw.add( new Partition( north, east, south, west
							, new Point( i, j ) ) );
					break;
				case DOOR :
					DoorWay door = new DoorWay( north, south, new Point( i, j ) );
					toDraw.add( door );
					gameElements.put( new Point( i, j ), door );
					break;
				default:
					break;
				}
			}
		}
	}
}