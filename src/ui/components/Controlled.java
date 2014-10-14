package ui.components;

import java.awt.Point;
import java.awt.geom.Point2D.Float;

import javax.media.opengl.GL2;

public class Controlled implements Behaviour
{

	public Controlled(){}

	/**
	 * Allow game logic control of this other players position
	 *  (non-Javadoc)
	 * @see ui.components.Behaviour#modify(javax.media.opengl.GL2, java.awt.geom.Point2D.Float)
	 */
	@Override
	public void modify( GL2 gl, Float pos )
	{
		
		GameViewData gdata = GameViewData.instance();
		Point oldLocation = new Point( (int) (pos.x/GameView.cellsize)
									 , (int) (pos.y/GameView.cellsize) );
		Point newLocation = gdata.getOtherPlayerMove().get( oldLocation );
		if ( newLocation == null ) return;
		if ( !gdata.moveGameElement( oldLocation, newLocation ) ) return;
		pos.setLocation( (int)(newLocation.x * GameView.cellsize)
				, (int)(newLocation.y * GameView.cellsize) );
		System.out.println( 
				"move player from:" + oldLocation + "->" + newLocation );
		gdata.removePlayerMove( oldLocation );
	}

	@Override
	public boolean activate()
	{
		return true;
	}

}
