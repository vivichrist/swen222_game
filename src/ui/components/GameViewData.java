package ui.components;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameViewData
{
	private final ArrayList<StaticRender>		staticScene;
	private final ArrayList<DymanicRender>		dynamicScene;
	private HashMap<Point, GraphicalObject>		gameElements;
	private DymanicRender 						previousSelection	= null;
	private static GameViewData					instance;

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
	}

	public Map<Point, GraphicalObject> getGameElements()
	{
		return Collections.unmodifiableMap( gameElements );
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

	public void remove( Point p )
	{
		GraphicalObject go = gameElements.remove( p );
		dynamicScene.remove( (DymanicRender)go );
	}

	public DymanicRender getPreviousSelection()
	{
		return previousSelection;
	}

	public void replacePreviousSelection( DymanicRender dyn )
	{
		if ( previousSelection == dyn ) return;
		if ( previousSelection != null )
		{
			previousSelection.setSelectColor( Color.BLACK );
		}
		previousSelection = dyn;
	}

	public void clear()
	{
		previousSelection = null;
		dynamicScene.clear();
		staticScene.clear(); // must clear staticID from opengl
		gameElements.clear();
	}
}