package ui.components;

import java.awt.geom.Point2D;
import javax.media.opengl.GL2;

public class OpenClose implements Behavior
{
	private float open = 0f, openSpeed = 0f;
	private boolean	xaligned = false;
	
	public OpenClose(boolean xaligned)
	{
		this.xaligned = xaligned;
	}

	@Override
	public void modify( GL2 gl, Point2D.Float pos )
	{
		if ( openSpeed == 0 ) return;
		open += openSpeed;
		if ( open > 2 * GameView.cellsize ) openSpeed = -openSpeed;
		if ( open < 0 )
		{
			openSpeed = 0f;
			open = 0f;
		}
		gl.glTranslatef( -(Math.min(open, GameView.cellsize * 0.9f)), 0f, 0f );
	}

	@Override
	public boolean activate()
	{
		if ( open > GameView.cellsize * 0.9f ) return false;
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