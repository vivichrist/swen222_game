package ui.components;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import world.game.Player;

/**
 * @author Vivian Stewart
 * Centralised data for scene and scene rendering
 */
public class GameViewData
{
	private final ArrayList<StaticRender>		staticScene;
	private final ArrayList<DymanicRender>		dynamicScene;
	private HashMap<Point, GraphicalObject>		gameElements;
	private HashMap<Point, Point>				newPlayerMove;
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
		dynamicScene = new ArrayList<DymanicRender>();
		gameElements = new HashMap<Point, GraphicalObject>();
		newPlayerMove = new HashMap<Point, Point>();
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
	 * @return if successful
	 */
	public boolean moveGameElement( Point current, Point next )
	{
		return gameElements.put( next, gameElements.get( current ) ) != null;
	}

	/**
	 * Buffer the changes to other player positions. To be
	 * updated before the next frame.
	 * @param current position
	 * @param next position
	 */
	public void addNewPlayerMove( Point current, Point next )
	{
		newPlayerMove.put( current, next );
	}
	
	/**
	 * @param old
	 */
	public void removePlayerMove( Point old )
	{
		newPlayerMove.remove( old );
	}
	
	public Map<Point, Point> getOtherPlayerMove()
	{
		return Collections.unmodifiableMap( newPlayerMove );
	}

	public void addAllGameElements( List<Point> ps, GraphicalObject element )
	{
		for ( Point p: ps )
			gameElements.put( p, element );
	}

	public List<StaticRender> getStaticScene()
	{
		return Collections.unmodifiableList( staticScene );
	}

	public boolean addStaticOnly( StaticRender sobject )
	{
		return staticScene.add( sobject );
	}

	public boolean addDynamicOnly( DymanicRender sobject )
	{
		return dynamicScene.add( sobject );
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

	public List<DymanicRender> getDynamicScene()
	{
		return Collections.unmodifiableList( dynamicScene );
	}

	/**
	 * @param p
	 */
	public void remove( Point p )
	{
		GraphicalObject go = gameElements.remove( p );
		dynamicScene.remove( (DymanicRender)go );
	}

	/**
	 * @return
	 */
	public DymanicRender getPreviousSelection()
	{
		return previousSelection;
	}

	/**
	 * @param dyn
	 */
	public void replacePreviousSelection( DymanicRender dyn )
	{
		if ( previousSelection == dyn ) return;
		if ( previousSelection != null )
		{
			previousSelection.setSelectColor( Color.BLACK );
		}
		previousSelection = dyn;
	}

	/**
	 * 
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
	 * @return
	 */
	public DymanicRender getToInitialise()
	{
		return toInitialise;
	}

	/**
	 * @param toInitialise
	 */
	public void setToInitialise( DymanicRender toInitialise )
	{
		this.toInitialise = toInitialise;
	}

	/**
	 * 
	 */
	public void resetToInitialise()
	{
		this.toInitialise = null;
	}
}