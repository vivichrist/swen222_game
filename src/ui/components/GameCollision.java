package ui.components;
import java.io.FileNotFoundException;
import java.util.Arrays;

enum Type {
	EMPTY, WALL, DOOR, OPENDOOR;
	public static boolean isCollision( Type t )
	{
		return t == Type.WALL || t == Type.DOOR;
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
	
	public GameCollision() throws FileNotFoundException
	{
		// BufferedReader br = new BufferedReader( new FileReader( "map.txt" ) );
		// TODO: load file and create map
	}

	public void setBit( int x, int y, Type type )
	{
		if ( x >= xlimit || y >= ylimit )
			throw new IndexOutOfBoundsException( "Cannot index (" + x + "," + y + ") Bit" );
		map[ x ][ y ] = type;
	}
	
	public boolean isCollidable( float x, float y )
	{
		return isCollidable( (int)(x/GameFrame.cellsize), (int)(y/GameFrame.cellsize) );
	}
	
	public boolean isCollidable( int x, int y )
	{
		if ( x >= xlimit || y >= ylimit )
			throw new IndexOutOfBoundsException( "Cannot index (" + x + "," + y + ") Bit" );
		return Type.isCollision( map[x][y] );
	}
}
