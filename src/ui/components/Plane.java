package ui.components;
import java.awt.Point;

import javax.media.opengl.GL2;

public class Plane implements GraphicalObject
{
	private int	breadth, level;

	/**
	 * @param width
	 * @param bredth
	 */
	public Plane( Point extents, int level )
	{
		super();
		this.breadth = Math.max( extents.x, extents.y );
		this.level = level * GameView.cellsize;
	}

	@Override
	public boolean initialise( GL2 gl )
	{
		float x, y;
		gl.glBegin( GL2.GL_LINES );
		for ( int i = 0; i <= breadth; ++i )
		{
			x = (i * GameView.cellsize);
			y = (breadth * GameView.cellsize);
			if ( i == breadth / 2 )
			{
				gl.glColor3f( .6f, .3f, .3f );
			} else
			{
				gl.glColor3f( .25f, .25f, .25f );
			}
			gl.glVertex3f( x, 0, level );
			gl.glVertex3f( x, y, level );
			if ( i == breadth / 2 )
			{
				gl.glColor3f( .3f, .3f, .6f );
			} else
			{
				gl.glColor3f( .25f, .25f, .25f );
			}
			gl.glVertex3f( 0, x, level );
			gl.glVertex3f( y, x, level );
		}
		gl.glEnd();
		return true;
	}

	@Override
	public boolean draw( GL2 gl )
	{
//		if ( listID == 0 ) return false;
//		gl.glCallList(listID);
		return true;
	}

	@Override
	public void clean( GL2 gl )
	{
//		gl.glDeleteLists( listID, 1 );
	}
	
	@Override
	public boolean isDynamic()
	{
		return false;
	}

}
