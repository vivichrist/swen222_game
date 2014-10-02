package ui.components;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import world.components.CellType;
import world.game.GameBuilder;
import world.game.GameState;

/**
 * @author Vivian Stewart
 *
 */
public class GameScene
{
	private CellType[][] map;
	private HashMap<Point, GraphicalObject> gameElements = new HashMap<Point, GraphicalObject>();
	private int xlimit, ylimit;
	private GameState game;
	
	public GameScene(GameState state)
	{
		//Kalo added
		game = state;
		// read in the map
		// size of map is in the header
		xlimit = game.getMap().getXLimit();
		ylimit = game.getMap().getYLimit();
		System.out.println( "columnss rows:" +xlimit+ ","+ylimit);
		map = game.getMap().getCellTypeMap();
		// map values, a 2D array labelling the viewable scene
	}

	public void setBit( int x, int y, CellType type )
	{
		if ( x >= xlimit || y >= ylimit )
			throw new IndexOutOfBoundsException( "Cannot index (" + x + "," + y + ") Bit" );
		map[ x ][ y ] = type;
	}
	
	public CellType queryBit( int x, int y )
	{
		if ( x >= xlimit || y >= ylimit )
		{
			return CellType.OUTOFBOUNDS;
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
		if ( map[x][y] == CellType.DOOR )
			return ((DoorWay)gameElements.get( new Point( x, y ) )).open();
		return map[x][y] == CellType.WALL;
	}

	public void addSurrounds( ArrayList<GraphicalObject> toDraw, int scale )
	{
		for ( int j = 0; j < ylimit; ++j )
		{
			for ( int i = 0; i < xlimit; ++i )
			{
				CellType north = j - 1 < 0   ? CellType.OUTOFBOUNDS : map[ i ]  [j - 1]
					, east = i + 1 >= xlimit ? CellType.OUTOFBOUNDS : map[i + 1] [ j ]
					, south = j + 1 >= ylimit ? CellType.OUTOFBOUNDS : map[ i ]  [j + 1]
					, west = i - 1 < 0       ? CellType.OUTOFBOUNDS : map[i - 1] [ j ];
				Point p =  new Point( i, j );
				switch( map[i][j] )
				{
				case WALL :
					toDraw.add( new Partition( north, east, south, west, p ) );
					break;
				case DOOR :
					DoorWay door = new DoorWay( north, south, p );
					toDraw.add( door );
					gameElements.put( p, door );
					break;
				case TELEPORT :
					Teleport tele = new Teleport( p );
					toDraw.add( tele );
					gameElements.put( p, tele );
					break;
//				case CONE :
//					Dymanic cone = new Dymanic( CellType.CONE, Behave.ROTATE, p );
//					toDraw.add( door );
//					gameElements.put( p, cone );
//					break;
				default:
					break;
				}
			}
		}
	}
}
