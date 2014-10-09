package ui.components;
import java.awt.Color;
import java.awt.Point;
import world.components.CellType;
import world.components.Direction;
import world.components.Furniture;
import world.components.GameObject;
import world.components.GameToken;
import world.components.Key;
import world.components.MoveableObject;
import world.components.StationaryObject;
import world.components.TokenType;
import world.components.Torch;
import world.game.GameState;

/**
 * @author Vivian Stewart
 *
 */
public class GameScene
{
	private GameState		game;
	private GameViewData gdata = GameViewData.instance();
	private CellType[][]	map;
	public final int		xlimit, ylimit;

	public GameScene(GameState state)
	{
		//Kalo added
		game = state;
		// size of map is in the header
		xlimit = game.getMap().getXLimit();
		ylimit = game.getMap().getYLimit();
		System.out.println( "columnss rows:" +xlimit+ ","+ylimit);
		// read in the map
		map = game.getMap().getCellTypeMap();
		// print out the map
		for ( CellType[] line: map )
		{
			for ( CellType c: line )
				System.out.print( " " + c.ordinal() );
			System.out.println();
		}
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
		Point p = new Point( x, y );
		if ( map[x][y].ordinal() > CellType.WALL.ordinal() // position is a token, key or torch
			|| gdata.getGameElements().get( p ) != null )
		{	// collect it and remove from data to apear in items
			// System.out.println( "Collide (" + x + "," + y + ")" );
			CellType ct =  gdata.getGameElements().get( new Point( x, y ) ).getType();
			if ( ct == CellType.TELEPORT )
			{return false;}
			else if ( ct.ordinal() > CellType.OUTOFBOUNDS.ordinal()
					&& ct.ordinal() < CellType.CHEST.ordinal() )
			{
				game.pickupObjectAtPoint( game.getPlayer(), p);
				gdata.remove( p );
			} else return ((DymanicRender)gdata.getGameElements().get( p )).collide();
		}
		return map[x][y] == CellType.WALL;
	}

	public void addSurrounds()
	{
		gdata.clear();
		Direction dir;
		Point p;
		DymanicRender dyn;
		for ( int j = 0; j < ylimit; ++j )
		{
			for ( int i = 0; i < xlimit; ++i )
			{
				CellType[] nesw = findNeighbours( i, j );
				p =  new Point( i, j );
				dir = nesw[0] == nesw[2] && nesw[0] == CellType.EMPTY 
						? Direction.NORTH : Direction.EAST;
				switch( map[i][j] )
				{
				case WALL :
					gdata.addStaticOnly( new StaticRender( CellType.WALL, nesw, p ) );
					break;
				case DOOR :
					dyn = DymanicRender.instanceDoor( p, dir );
					StaticRender doorWay = new StaticRender( CellType.DOOR, nesw, p );
					gdata.addStaticOnly( doorWay );
					gdata.addGrapicalObject( dyn );
					break;
				case TELEPORT :
					dyn = DymanicRender.instanceTelePort( p );
					gdata.addStaticOnly( new StaticRender( CellType.EMPTY, nesw, p ) );
					gdata.addGrapicalObject( dyn );
					break;
				default:
					gdata.addStaticOnly( new StaticRender( CellType.EMPTY, nesw, p ) );
					break;
				}
				GameObject go = game.getMap().objectAtPoint(p);
				if ( go == null ) continue;
				if ( go instanceof GameToken )
				{
					if ( ((GameToken)go).getType() == TokenType.CONE )
					{
						dyn = DymanicRender.instanceCone( p, ((GameToken)go).getColor() );
						gdata.addGrapicalObject( dyn );
					}
					else if ( ((GameToken)go).getType() == TokenType.BALL )
					{
						dyn = DymanicRender.instanceBall( p, ((GameToken)go).getColor() );
						gdata.addGrapicalObject( dyn );
					}
					else if ( ((GameToken)go).getType() == TokenType.DIAMOND )
					{
						dyn = DymanicRender.instanceDiamond( p, ((GameToken)go).getColor() );
						gdata.addGrapicalObject( dyn );
					}
					else if ( ((GameToken)go).getType() == TokenType.CUBE )
					{
						dyn = DymanicRender.instanceCube( p, ((GameToken)go).getColor() );
						gdata.addGrapicalObject( dyn );
					}
				}
				else if ( go instanceof MoveableObject )
				{
					if ( go instanceof Key )
					{
						dyn = DymanicRender.instanceKey( p, ((Key)go).getColor() );
						gdata.addGrapicalObject( dyn );
					}
					else if ( go instanceof Torch )
					{
						dyn = DymanicRender.instanceTorch( p, Color.decode( "#880088" ) );
						gdata.addGrapicalObject( dyn );
					}
				}
				else if ( go instanceof StationaryObject )
				{
					if ( go instanceof Furniture )
					{
						dyn = DymanicRender.instanceFurnature( 
								((Furniture)go).getType(), Behave.ORIENTATION, p
								, ((Furniture)go).getFacing(), Color.GRAY );
						gdata.getDynamicScene().add( dyn );
						//gdata.addAllGameElements( ((Furniture)go)., dyn );
					}
				}
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
	private CellType[] findNeighbours( int i, int j )
	{
		return new CellType[]{
			  j - 1 < 0		  ? CellType.OUTOFBOUNDS : map[ i ]   [j - 1]
			, i + 1 >= xlimit ? CellType.OUTOFBOUNDS : map[i + 1] [ j ]
			, j + 1 >= ylimit ? CellType.OUTOFBOUNDS : map[ i ]   [j + 1]
			, i - 1 < 0       ? CellType.OUTOFBOUNDS : map[i - 1] [ j ] };
	}

//	private boolean[] findOrientation( Point p )
//	{
//		int dimX = 0, dimY = 0;
//		boolean[] result = { false, false };
//		int wallN = 0, wallS = 0, wallE = 0, wallW = 0;
//		Point scan = new Point( p.x, p.y );
//		CellType cell = map[p.x][p.y];
//		// scan x+
//		while ( map[scan.x][scan.y] == cell )
//		{
//			++dimX; scan.translate( 1, 0 );
//		}// scan y+
//		scan = new Point( p.x, p.y );
//		while ( map[scan.x][scan.y] == cell )
//		{
//			++dimY; scan.translate( 0, 1 );
//		}
//		if ( dimX >= dimY )
//		{	// assuming that walls totally encase the map
//			scan = new Point( p.x, p.y );
//			while ( map[scan.x][scan.y] == CellType.EMPTY
//					|| map[scan.x][scan.y].ordinal() > CellType.OUTOFBOUNDS.ordinal() )
//			{
//				if (map[scan.x][scan.y] != cell) ++wallE;
//				scan.translate( 1, 0 );
//			}
//			scan = new Point( p );
//			while ( map[scan.x][scan.y] == CellType.EMPTY
//					|| map[scan.x][scan.y].ordinal() > CellType.OUTOFBOUNDS.ordinal() )
//			{
//				if (map[scan.x][scan.y] != cell) ++wallW;
//				scan.translate( -1, 0 );
//			}
//			result[1] = wallE > wallW;
//		}
//		if ( dimX <= dimY )
//		{
//			scan = new Point( p.x, p.y );
//			while ( map[scan.x][scan.y] == CellType.EMPTY
//					|| map[scan.x][scan.y].ordinal() > CellType.OUTOFBOUNDS.ordinal() )
//			{
//				if (map[scan.x][scan.y] != cell) ++wallN;
//				scan.translate( 0, 1 );
//			}
//			scan = new Point( p );
//			while ( map[scan.x][scan.y] == CellType.EMPTY
//					|| map[scan.x][scan.y].ordinal() > CellType.OUTOFBOUNDS.ordinal() )
//			{
//				if (map[scan.x][scan.y] != cell) ++wallS;
//				scan.translate( 0, -1 );
//			}
//			result[0] = true;
//			result[1] = wallN > wallS;
//		}
//		return result;
//	}
	// don't actually need this as, if the gameElements point is already there
//	private boolean leastXYcorner( CellType[] nesw, CellType type )
//	{
//		return nesw[0] != type && nesw[3] != type;
//	}
}
