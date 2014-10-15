package ui.components;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import world.components.CellType;
import world.components.Direction;
import world.game.Player;

/**
 * @author Vivian Stewart
 * Centralised data for scene and scene rendering
 */
public class GameViewData
{
	private final List<StaticRender>			staticScene;
	private final List<DymanicRender>			dynamicScene;
	private final Map<Point, GraphicalObject>	gameElements;
	private final Map<Point, Point>				newPlayerMove;
	private DymanicRender						toInitialise;
	private DymanicRender 						previousSelection = null;
	private static GameViewData					instance = null;

	/**
	 * Singleton Patterned for ease of access
	 * @return the single instance of this class
	 */
	public static GameViewData instance()
	{
		if ( instance == null )
			instance = new GameViewData();
		return instance;
	}

	private GameViewData()
	{
		staticScene = new ArrayList<StaticRender>();
		// preemptive synchronisation for concurrent modification
		dynamicScene = Collections.synchronizedList( new ArrayList<DymanicRender>() );
		gameElements = Collections.synchronizedMap( new HashMap<Point, GraphicalObject>() );
		newPlayerMove = Collections.synchronizedMap( new HashMap<Point, Point>() );
	}

	/**
	 * @return an unmodifiable map of collidables an selectables
	 */
	public Map<Point, GraphicalObject> getGameElements()
	{
		return Collections.unmodifiableMap( gameElements );
	}

	/**
	 * @param current - position of element
	 * @param next - position to move element to
	 */
	public void moveGameElement( Point current, Point next )
	{
		GraphicalObject go = gameElements.remove( current );
		if ( go == null )
			throw new RuntimeException(
					"No Object at Point:" + current + " -> null" );
		gameElements.put( next, go );
	}

	/**
	 * Buffer the changes to other player positions. To be
	 * updated before the next frame.
	 * @param current position
	 * @param next position
	 */
	public void addNewPlayerMove( Point current, Point next )
	{
		if ( current == null )
		{
			DymanicRender dyn = DymanicRender.instancePlayer(
					Behave.CONTROLLED, next, Direction.NORTH, Color.darkGray );
			dynamicScene.add( dyn );
			toInitialise = dyn;
			gameElements.put( next, dyn );
			return;
		}
		newPlayerMove.put( current, next );
	}

	/**
	 * This Method a buffered "other player" move from the newPlayerMove map
	 * because it has been moved.
	 * @param old is the point the player has moved from, and also the
	 * reference key
	 */
	public void removePlayerMove( Point old )
	{
		newPlayerMove.remove( old );
	}

	/**
	 * @return the buffer of other player moves as unmodifiable map
	 */
	public Map<Point, Point> getOtherPlayerMove()
	{
		return Collections.unmodifiableMap( newPlayerMove );
	}

	/**
	 * An element that takes up more than one point on the map must add point
	 * references to that element to select and collide on many squares.
	 * @param points
	 * @param element
	 */
	public void addPointsToGameElement( List<Point> points, GraphicalObject element )
	{
		for ( Point p: points )
			gameElements.put( p, element );
	}

	/**
	 * @return an unmodifiable list of solid collidable objects that never change
	 */
	public List<StaticRender> getStaticScene()
	{
		return Collections.unmodifiableList( staticScene );
	}

	/**
	 * @param staticObject - solid collidable objects that never change to
	 * queue for rendering.
	 * @return if successful
	 */
	public boolean addStaticOnly( StaticRender staticObject )
	{
		return staticScene.add( staticObject );
	}

	/**
	 * @param dynamicObject - object that can move or change
	 * @return if successful
	 */
	public boolean addDynamicOnly( DymanicRender dynamicObject )
	{
		return dynamicScene.add( dynamicObject );
	}

	/**
	 * Adds the graphical object to both the game map for collision detection
	 * and mouse selection, and also to the rendering list. Items added at some
	 * point in time after initialisation need to be assigned to the
	 * toInitialise field to be initialised before the next frame.
	 * @param graphical object
	 * @return successfully added to renderer
	 */
	public boolean addGrapicalObject( GraphicalObject gobject )
	{
		gameElements.put( gobject.getLocation(), gobject );
		if ( gobject instanceof StaticRender )
		{
			return staticScene.add( (StaticRender)gobject );
		}
		return dynamicScene.add( (DymanicRender)gobject );
	}

	/**
	 * @return an unmodifiable list of possibly moving or changing object
	 */
	public List<DymanicRender> getDynamicScene()
	{
		return Collections.unmodifiableList( dynamicScene );
	}

	/**
	 * @param point of GraphicalObject to remove from the dynamic scene
	 * and collision/selection
	 */
	public void remove( Point p )
	{
		DymanicRender go = (DymanicRender)gameElements.remove( p );
		if ( go == null )
			throw new RuntimeException(
					"No Object at Point:" + p + " -> null" );
		dynamicScene.remove( go );
	}

	/**
	 * @return the last element clicked on by the player
	 */
	public DymanicRender getPreviousSelection()
	{
		return previousSelection;
	}

	/**
	 * This means that the player has selected something else in the scene.
	 * @param dynamicObject - item to replace with the current selection item
	 */
	public void replacePreviousSelection( DymanicRender dynamicObject )
	{
		if ( previousSelection == dynamicObject ) return;
		if ( previousSelection != null )
		{
			previousSelection.setSelectColor( Color.BLACK );
		}
		previousSelection = dynamicObject;
	}

	/**
	 * completely wipe all data of this level. Usually in response to teleport
	 * to a new floor and hence new map.
	 */
	public void clear()
	{
		previousSelection = null;
		dynamicScene.clear();
		newPlayerMove.clear();
		staticScene.clear(); // must clear staticID from opengl
		gameElements.clear();
		toInitialise = null;
	}

	/**
	 * @return the dynamic element to be initialised because it has been added
	 * late after mass initialisation happened (to load vertices and indices)
	 */
	public DymanicRender getToInitialise()
	{
		return toInitialise;
	}

	/**
	 * @param toInitialise - new GraphicalObject to be initialised before
	 * rendering because of being added to scene after  mass initialisation.
	 */
	public void setToInitialise( DymanicRender toInitialise )
	{
		this.toInitialise = toInitialise;
	}

	/**
	 * Once the GraphicalObject has been initialised it is removed from the
	 * toInitialise field.
	 */
	public void resetToInitialise()
	{
		this.toInitialise = null;
	}
}