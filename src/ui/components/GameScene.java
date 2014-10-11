package ui.components;
import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.Map.Entry;

import world.components.CellType;
import world.components.Direction;
import world.components.Furniture;
import world.components.GameObject;
import world.components.GameToken;
import world.components.Key;
import world.components.MoveableObject;
import world.components.TokenType;
import world.components.Torch;
import world.game.GameState;
import world.game.Player;

/**
 * @author Vivian Stewart
 *
 */
public class GameScene
{
	private GameState		game;
	private boolean 		loadNewLevel = false
							, teleport = false; 
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
		System.out.println( "columns rows:" +xlimit+ ","+ylimit);
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


	/**
	 * @param x - continuous x position value
	 * @param y - continuous y position value
	 * @return if the position is inside a collidable square
	 */
	public boolean isCollidable( float x, float y )
	{
		return isCollidable( (int)(x/GameView.cellsize), (int)(y/GameView.cellsize) );
	}

	/**
	 * @param x - row select
	 * @param y - column select
	 * @return if this cell has a collidable object in it
	 */
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
			CellType ct =  gdata.getGameElements().get( p ).getType();
			if ( ct == CellType.RINGS )
			{
				if ( !teleport )
				{
					loadNewLevel = true;
					teleport = true;
					return false;
				} else
				{
					teleport = false;
					return teleport;
				}
			}
			if ( ct == CellType.KEYDOOR )
			{	if ( game.canOpenDoor( game.getPlayer(), p ) )
					return ((DymanicRender)gdata.getGameElements().get( p )).collide();
				return true;
			}
			else if ( ct.toString() == game.getPlayer().getType().toString()
					|| ct == CellType.KEY || ct == CellType.TORCH )
			{
				game.pickupObjectAtPoint( game.getPlayer(), p );
				gdata.remove( p );
				
			} else return ((DymanicRender)gdata.getGameElements().get( p )).collide();
		}
		return map[x][y] == CellType.WALL;
	}

	/**
	 * Setup Scene with all the GraphicalObjects, Static and Dynamic
	 */
	public void addSurrounds()
	{
		gdata.clear(); // need to destroy staticID too. for new floor
		Direction dir;
		Point p;
		DymanicRender dyn;
		GameObject go;
		Furniture furn;
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
				case KEYDOOR :
					dyn = DymanicRender.instanceKeyDoor( p, dir );
					StaticRender keydoorWay = new StaticRender( CellType.KEYDOOR, nesw, p );
					gdata.addStaticOnly( keydoorWay );
					gdata.addGrapicalObject( dyn );
					break;
				case TELEPORT :
					dyn = DymanicRender.instanceTelePort( p );
					gdata.addStaticOnly( new StaticRender( CellType.TELEPORT, nesw, p ) );
					gdata.addGrapicalObject( dyn );
					break;
				default:
					gdata.addStaticOnly( new StaticRender( CellType.EMPTY, nesw, p ) );
					break;
				}
				go = game.getMap().objectAtPoint(p);
				if ( go == null ) continue;
				if ( go instanceof GameToken )
				{ // Tokens to be collected
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
				} // items that enable other items to be collected or triggered
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
				} else
				{ // load furnature int the scene
					furn = game.getMap().furnitureAtPoint(p);
					if ( furn != null )
					{
						dyn = DymanicRender.instanceFurnature(
								furn.getType(), Behave.ORIENTATION, p
								, furn.getFacing(), Color.GRAY );
						gdata.addDynamicOnly( dyn );
						List<Point> lp = furn.getPoints();
						System.out.println( "Number of points in Furature:" + lp.size() );
						for ( Point pt: lp )
							System.out.println( "Point:" + pt.toString() );
						gdata.addAllGameElements( furn.getPoints(), dyn );
					}
				}
			}
		}
		// load other players into the scene
		List<Player> players = game.getPlayers();
		for ( Player pl: players )
		{
			if ( game.getPlayer() != pl && pl.getFloor() == game.getPlayer().getFloor() )
			{
				dyn = DymanicRender.instancePlayer( Behave.CONTROLLED
					, pl.getPosition(), pl.getFacing(), Color.darkGray );
				gdata.addGrapicalObject( dyn );
			}
		}
		
		// testing 
		for ( Entry<Point, GraphicalObject> kv: gdata.getGameElements().entrySet() )
			System.out.println( "GameElement at:" + kv.getKey().toString()
					+ " -> " + kv.getValue().getType().toString() );
	}
	private CellType[] findNeighbours( int i, int j )
	{
		return new CellType[] {
			  j - 1 < 0		  ? CellType.OUTOFBOUNDS : map[ i ]   [j - 1]
			, i + 1 >= xlimit ? CellType.OUTOFBOUNDS : map[i + 1] [ j ]
			, j + 1 >= ylimit ? CellType.OUTOFBOUNDS : map[ i ]   [j + 1]
			, i - 1 < 0       ? CellType.OUTOFBOUNDS : map[i - 1] [ j ]
		};
	}


	public boolean loadNewLevel()
	{
		boolean result = loadNewLevel;
		loadNewLevel = false;
		return result;
	}
}
