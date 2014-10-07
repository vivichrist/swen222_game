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
		if ( map[x][y].ordinal() > CellType.WALL.ordinal() )
		{
			System.out.println("collide:" + map[x][y]);
			return ((DymanicRender)gameElements.get( new Point( x, y ) )).collide();
		}
		return map[x][y] == CellType.WALL;
	}

	public void addSurrounds( ArrayList<DymanicRender> dynamicScene, ArrayList<StaticRender> staticScene )
	{
		boolean xaligned;
		Point p;
		DymanicRender dyn;
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
				case WALL :
					staticScene.add( new StaticRender( map[i][j], nesw, p ) );
					break;
				case DOOR :
					DymanicRender door = new DymanicRender( map[i][j], Behave.OPEN_CLOSE, p
							, xaligned, false, Color.decode( "#008800" ) );
					StaticRender doorWay = new StaticRender( map[i][j], nesw, p );
					staticScene.add( doorWay );
					dynamicScene.add( door );
					gameElements.put( p, door );
					break;
				case TELEPORT : // TODO: Teleporting behaviour
					dyn = new DymanicRender( CellType.TELEPORT, Behave.NONE, p
							, false, false, Color.ORANGE );
					dynamicScene.add( dyn );
					gameElements.put( p, dyn );
					break;
				case CONE:
					dyn = new DymanicRender( CellType.CONE, Behave.ROTATE, p
							, false, false, Color.CYAN );
					dynamicScene.add( dyn );
					gameElements.put( p, dyn );
					break;
				case BALL:
					dyn = new DymanicRender( CellType.BALL, Behave.ROTATE, p
							, false, false, Color.RED );
					dynamicScene.add( dyn );
					gameElements.put( p, dyn );
					break;
				case DIAMOND:
					dyn = new DymanicRender( CellType.DIAMOND, Behave.ROTATE, p
							, false, false, Color.WHITE );
					dynamicScene.add( dyn );
					gameElements.put( p, dyn );
					break;
				case CUBE:
					dyn = new DymanicRender( CellType.CUBE, Behave.ROTATE, p
							, false, false, Color.YELLOW );
					dynamicScene.add( dyn );
					gameElements.put( p, dyn );
					break;
				case KEY:
					dyn = new DymanicRender( CellType.KEY, Behave.ROTATE, p
							, false, false, Color.decode( "#ffaa11" ) );
					dynamicScene.add( dyn );
					gameElements.put( p, dyn );
					break;
				case COUCH:
					if ( leastXYcorner( nesw, CellType.COUCH ) )
					{
						boolean[] ori = findOrientation( p );
						dyn = new DymanicRender( CellType.COUCH, Behave.ORIENTATION, p, ori[0], ori[1], Color.GRAY );
						dynamicScene.add( dyn );
						gameElements.put( p, dyn );
					}
					break;
				case BED:
					if ( leastXYcorner( nesw, CellType.BED ) )
					{
						boolean[] ori = findOrientation( p );
						dyn = new DymanicRender( CellType.BED, Behave.ORIENTATION, p, ori[0], ori[1], Color.GRAY );
						dynamicScene.add( dyn );
						if ( ori[0] )
						{
							for ( int x = 0; i < 3; ++i )
								for ( int y = 0; j < 2; ++j )
									gameElements.put( new Point( p.x + x, p.y + y ), dyn );
						}
						else {
							for ( int x = 0; i < 2; ++i )
								for ( int y = 0; j < 3; ++j )
									gameElements.put( new Point( p.x + x, p.y + y ), dyn );
						}
					}
					break;
				default:
					break;
				}
				staticScene.add( new StaticRender( CellType.EMPTY, nesw, p ) );
			}
		}
//		p =  new Point( 4, 16 );
//		dyn = new DymanicRender( CellType.BED, Behave.ORIENTATION, p, true, true, Color.decode( "#667788" ) );
//		dynamicScene.add( dyn );
//		gameElements.put( p, dyn );
//		p =  new Point( 4, 20 );
//		dyn = new DymanicRender( CellType.TABLE, Behave.ORIENTATION, p, false, false, Color.decode( "#991155" ) );
//		dynamicScene.add( dyn );
//		gameElements.put( p, dyn );
//		p =  new Point( 9, 20 );
//		dyn = new DymanicRender( CellType.COUCH, Behave.ORIENTATION, p, true, true, Color.decode( "#112233" ) );
//		dynamicScene.add( dyn );
//		gameElements.put( p, dyn );
//		p =  new Point( 12, 20 );
//		dyn = new DymanicRender( CellType.DRAWERS, Behave.ORIENTATION, p, false, false, Color.decode( "#77ff33" ) );
//		dynamicScene.add( dyn );
//		gameElements.put( p, dyn );
//		p =  new Point( 18, 20 );
//		dyn = new DymanicRender( CellType.CHEST, Behave.ORIENTATION, p, false, false, Color.decode( "#dd6655" ) );
//		dynamicScene.add( dyn );
//		gameElements.put( p, dyn );
	}

	private boolean[] findOrientation( Point p )
	{
		int dimX = 0, dimY = 0;
		boolean[] result = { false, false };
		int wallN = 0, wallS = 0, wallE = 0, wallW = 0;
		Point scan = new Point( p.x, p.y );
		CellType cell = map[p.x][p.y];
		// scan right
		while ( map[scan.x][scan.y] == cell )
		{
			++dimX; scan.translate( 1, 0 );
		}// scan up
		scan = new Point( p.x, p.y );
		while ( map[scan.x][scan.y] == cell )
		{
			++dimY; scan.translate( 0, 1 );
		}
		if ( dimX >= dimY )
		{	// assuming that walls totally encase the map
			scan = new Point( p.x, p.y );
			while ( map[scan.x][scan.y] == CellType.EMPTY
					|| map[scan.x][scan.y].ordinal() > CellType.OUTOFBOUNDS.ordinal() )
			{
				if (map[scan.x][scan.y] != cell) ++wallE;
				scan.translate( 1, 0 );
			}
			scan = new Point( p );
			while ( map[scan.x][scan.y] == CellType.EMPTY
					|| map[scan.x][scan.y].ordinal() > CellType.OUTOFBOUNDS.ordinal() )
			{
				if (map[scan.x][scan.y] != cell) ++wallW;
				scan.translate( -1, 0 );
			}
			result[1] = wallE > wallW;
		}
		if ( dimX <= dimY )
		{
			scan = new Point( p.x, p.y );
			while ( map[scan.x][scan.y] == CellType.EMPTY
					|| map[scan.x][scan.y].ordinal() > CellType.OUTOFBOUNDS.ordinal() )
			{
				if (map[scan.x][scan.y] != cell) ++wallN;
				scan.translate( 0, 1 );
			}
			scan = new Point( p );
			while ( map[scan.x][scan.y] == CellType.EMPTY
					|| map[scan.x][scan.y].ordinal() > CellType.OUTOFBOUNDS.ordinal() )
			{
				if (map[scan.x][scan.y] != cell) ++wallS;
				scan.translate( 0, -1 );
			}
			result[0] = true;
			result[1] = wallN > wallS;
		}
		return result;
	}

	private boolean leastXYcorner( CellType[] nesw, CellType type )
	{
		return nesw[0] != type && nesw[3] != type;
	}
}
