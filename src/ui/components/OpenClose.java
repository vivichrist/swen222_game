package ui.components;

import java.awt.geom.Point2D;
import javax.media.opengl.GL2;

public class OpenClose implements Behavior
{
	private float open = 0f, openSpeed = 0f;
	private float	threshhold = GameView.cellsize * 0.9f;
	
	// called every frame
	@Override
	public void modify( GL2 gl, Point2D.Float pos )
	{
		if ( openSpeed == 0 ) return;
		open += openSpeed;
		if ( open > GameView.cellsize * 1.5f ) openSpeed = -openSpeed;
		if ( open < 0 )
		{
			openSpeed = 0f;
			open = 0f;
		}
		gl.glTranslatef( -(Math.min(open, threshhold)), 0f, 0f );
	}
	// called upon collision, returns false if no collision
	@Override
	public boolean activate()
	{
		if ( open > threshhold ) return false;
		if ( open > 0 ) return true;
		openSpeed = 0.7f / GameView.cellsize;
		return true;
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{

	}

}
