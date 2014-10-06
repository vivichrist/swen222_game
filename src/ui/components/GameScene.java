package ui.components;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import world.components.CellType;
import world.game.GameState;

/**
 * @author Vivian Stewart
 *
 */
public class GameScene
{
	private CellType[][] map;
	private HashMap<Point, GraphicalObject> gameElements = new HashMap<Point, GraphicalObject>();
	public final int xlimit, ylimit;
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
		System.out.println( "map:\n" + map );
		// map values, a 2D array labelling the viewable scene
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
			return ((DymanicRender)gameElements.get( new Point( x, y ) ))
					.collide();
		return map[x][y] == CellType.WALL;
	}

	public void addSurrounds( ArrayList<DymanicRender> dynamicScene, ArrayList<StaticRender> staticScene )
	{
		boolean xaligned;
		Point p;
		for ( int j = 0; j < ylimit; ++j )
		{
			for ( int i = 0; i < xlimit; ++i )
			{
				CellType[] nesw = {
					  j - 1 < 0		  ? CellType.OUTOFBOUNDS : map[ i ]   [j - 1]
					, i + 1 >= xlimit ? CellType.OUTOFBOUNDS : map[i + 1] [ j ]
					, j + 1 >= ylimit ? CellType.OUTOFBOUNDS : map[ i ]   [j + 1]
					, i - 1 < 0       ? CellType.OUTOFBOUNDS : map[i - 1] [ j ] };
				p =  new Point( i, j );
				xaligned = nesw[0] == nesw[2] && nesw[0] == CellType.EMPTY;
				switch( map[i][j] )
				{
				case EMPTY :
					staticScene.add( new StaticRender( map[i][j], nesw, p ) );
					break;
				case WALL :
					staticScene.add( new StaticRender( map[i][j], nesw, p ) );
					break;
				case DOOR :
					DymanicRender door = new DymanicRender( map[i][j], Behave.OPEN_CLOSE, p
							, xaligned, false, Color.GREEN );
					StaticRender doorWay = new StaticRender( map[i][j], nesw, p );
					staticScene.add( doorWay );
					dynamicScene.add( door );
					gameElements.put( p, door );
					break;
				case TELEPORT :
					DymanicRender tele = new DymanicRender( map[i][j], Behave.NONE, p
							, xaligned, false, Color.ORANGE );
					dynamicScene.add( tele );
					gameElements.put( p, tele );
					break;
				default:
					break;
				}
			}
		}
		p =  new Point( 4, 4 );
		DymanicRender dyn = new DymanicRender( CellType.CONE, Behave.ROTATE, p, false, false, Color.CYAN );
		dynamicScene.add( dyn );
		gameElements.put( p, dyn );
		p =  new Point( 4, 8 );
		dyn = new DymanicRender( CellType.BALL, Behave.ROTATE, p, false, false, Color.RED );
		dynamicScene.add( dyn );
		gameElements.put( p, dyn );
		p =  new Point( 4, 12 );
		dyn = new DymanicRender( CellType.CUBE, Behave.ROTATE, p, false, false, Color.YELLOW );
		dynamicScene.add( dyn );
		gameElements.put( p, dyn );
		p =  new Point( 4, 16 );
		dyn = new DymanicRender( CellType.BED, Behave.ORIENTATION, p, true, true, Color.decode( "#667788" ) );
		dynamicScene.add( dyn );
		gameElements.put( p, dyn );
		p =  new Point( 4, 20 );
		dyn = new DymanicRender( CellType.TABLE, Behave.ORIENTATION, p, false, false, Color.decode( "#991155" ) );
		dynamicScene.add( dyn );
		gameElements.put( p, dyn );
		p =  new Point( 9, 20 );
		dyn = new DymanicRender( CellType.TORCH, Behave.ROTATE, p, false, false, Color.decode( "#552299" ) );
		dynamicScene.add( dyn );
		gameElements.put( p, dyn );
		p =  new Point( 12, 20 );
		dyn = new DymanicRender( CellType.KEY, Behave.ROTATE, p, false, false, Color.decode( "#ffaa11" ) );
		dynamicScene.add( dyn );
		gameElements.put( p, dyn );
		p =  new Point( 14, 20 );
		dyn = new DymanicRender( CellType.COUCH, Behave.ORIENTATION, p, true, true, Color.decode( "#112233" ) );
		dynamicScene.add( dyn );
		gameElements.put( p, dyn );
		p =  new Point( 16, 20 );
		dyn = new DymanicRender( CellType.DRAWERS, Behave.ORIENTATION, p, false, false, Color.decode( "#77ff22" ) );
		dynamicScene.add( dyn );
		gameElements.put( p, dyn );
		p =  new Point( 18, 20 );
		dyn = new DymanicRender( CellType.CHEST, Behave.ORIENTATION, p, false, false, Color.decode( "#dd6655" ) );
		dynamicScene.add( dyn );
		gameElements.put( p, dyn );
	}
}
