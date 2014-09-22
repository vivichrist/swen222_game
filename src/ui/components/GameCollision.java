package ui.components;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

enum Type {
	EMPTY, WALL, DOOR, OPENDOOR, OUTOFBOUNDS;
	public static boolean isCollision( Type t )
	{
		return t == WALL;// || t == DOOR;
	}
}

public class GameCollision
{
	private Type[][] map;
	private int xlimit, ylimit;

	public GameCollision( int x, int y )
	{
		map = new Type[ x ][ y ];
		for ( Type[] i : map )
			Arrays.fill( i, Type.EMPTY );
		xlimit = x;
		ylimit = y;
	}
	
	public GameCollision()
	{
		try
		{
			Scanner sc = new Scanner( new BufferedInputStream( new FileInputStream( "map.txt" ) ) );
			// size of map
			xlimit = sc.hasNextInt() ? sc.nextInt() : 0;
			if ( sc.next().indexOf( 'x' ) == -1 || xlimit < 1 )
			{
				sc.close();
				throw new Exception("Format error, header corrupt: " + xlimit + " ?");
			}
			ylimit = sc.hasNextInt() ? sc.nextInt() : 0;
			System.out.println( "width height:" +xlimit+ ","+ylimit);
			map = new Type[ xlimit ][ ylimit ];
			// map values
			for ( int j = 0; j < ylimit; ++j )
			{
				for ( int i = 0; i < xlimit; ++i )
				{
					if ( !sc.hasNext() )
					{
						sc.close();
						throw new Exception("Format error, not enough data");
					}
					map[i][j] = Type.values()[ sc.nextInt() ];
				}
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
		return isCollidable( (int)(x/GameFrame.cellsize), (int)(y/GameFrame.cellsize) );
	}
	
	public boolean isCollidable( int x, int y )
	{
		if ( x >= xlimit || y >= ylimit )
		{
			return false;
			// throw new IndexOutOfBoundsException( "Cannot index (" + x + "," + y + ") Bit" );
		}
		return Type.isCollision( map[x][y] );
	}

	public void addSurrounds( ArrayList<GraphicalObject> toDraw, int scale )
	{
		for ( int j = 0; j < ylimit; ++j )
		{
			for ( int i = 0; i < xlimit; ++i )
			{
				Type center = map[i][j]
					, north = j - 1 < 0       ? Type.OUTOFBOUNDS : map[ i ]  [j - 1]
					, east = i + 1 >= xlimit ? Type.OUTOFBOUNDS : map[i + 1] [ j ]
					, south = j + 1 >= ylimit ? Type.OUTOFBOUNDS : map[ i ]  [j + 1]
					, west = i - 1 < 0       ? Type.OUTOFBOUNDS : map[i - 1] [ j ];
				switch( map[i][j] )
				{
				case WALL :
					toDraw.add( new Partition( center, north, east, south, west, new Point( i, j ), scale ) );
					break;
				case DOOR :
					// TODO: very very bung!!
					//toDraw.add( new DoorWay( north, south, new Point( i, j ), scale ) );
					break;
				case OPENDOOR :
					;
				default:
					break;
				}
			}
		}
	}
}
