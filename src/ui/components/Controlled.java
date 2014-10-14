package ui.components;

import java.awt.Point;
import java.awt.geom.Point2D.Float;

import javax.media.opengl.GL2;

public class Controlled implements Behaviour
{

	public Controlled(){}

	@Override
	public void modify( GL2 gl, Float pos )
	{
		GameViewData gdata = GameViewData.instance();
		Point newLocation = gdata.getOtherPlayerMove().get( new Point(
				(int) (pos.x/GameView.cellsize)
				, (int) (pos.y/GameView.cellsize) ) );
		if ( newLocation == null ) return;
		pos.setLocation( (int)(newLocation.x * GameView.cellsize)
				, (int)(newLocation.y * GameView.cellsize) );
	}

	@Override
	public boolean activate()
	{
		return true;
	}

}
