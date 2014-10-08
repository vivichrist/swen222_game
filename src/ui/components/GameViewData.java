package ui.components;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import world.components.CellType;
import world.components.GameObject;

public class GameViewData
{
	private final ArrayList<StaticRender>		staticScene;
	private final ArrayList<DymanicRender>		dynamicScene;
	private HashMap<Point, GraphicalObject>		gameElements;
	private static GameViewData					instance;

	public static GameViewData instance()
	{
		if ( instance == null ) return new GameViewData();
		return instance;
	}

	public GameViewData()
	{
		staticScene = new ArrayList<StaticRender>();
		dynamicScene = new ArrayList<DymanicRender>();
		gameElements = new HashMap<Point, GraphicalObject>();
	}

	public Map<Point, GraphicalObject> getGameElements()
	{
		return Collections.unmodifiableMap( gameElements );
	}

	public void addGameElements( Point p, GraphicalObject element )
	{
		this.gameElements.put( p, element );
	}

	public List<StaticRender> getStaticScene()
	{
		return Collections.unmodifiableList( staticScene );
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

}