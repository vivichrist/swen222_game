package ui.components;
import java.awt.Point;

import javax.media.opengl.GL2;

public class Plane implements GraphicalObject
{
	private int	breadth, level, cellsize, listID;

	/**
	 * @param width
	 * @param bredth
	 */
	public Plane( Point extents, int level, int cellsize )
	{
		super();
		this.breadth = Math.max( extents.x, extents.y );
		this.cellsize = cellsize;
		this.level = level * cellsize;
	}

	@Override
	public boolean makeDisplayList( GL2 gl )
	{
		float x, y;
		listID = gl.glGenLists( 1 );
		gl.glNewList(listID, GL2.GL_COMPILE); 
		gl.glBegin( GL2.GL_LINES );
		for ( int i = 0; i <= breadth; ++i )
		{
			x = (i * cellsize);
			y = (breadth * cellsize);
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
		gl.glEndList();
		return true;
	}

	@Override
	public boolean draw( GL2 gl )
	{
		if ( listID == 0 ) return false;
		gl.glCallList(listID);
		return true;
	}

	@Override
	public void clean( GL2 gl )
	{
		gl.glDeleteLists( listID, 1 );
	}

}
