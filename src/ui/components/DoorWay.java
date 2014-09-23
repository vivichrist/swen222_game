package ui.components;

import java.awt.Point;
import java.awt.geom.Point2D;

import javax.media.opengl.GL2;

public class DoorWay implements GraphicalObject
{

	private boolean	xaligned = true;
	private Point2D.Float	position;
	private int	cellsize;
	private int	listID;

	public DoorWay( Type north, Type south, Point position, int scale )
	{
		if ( north == south && north == Type.WALL ) xaligned = false;
		this.position = new Point2D.Float( position.x * scale
				, position.y * scale );
		cellsize = scale;
	}

	@Override
	public boolean draw( GL2 gl )
	{
		if ( listID == 0 ) return false;
		gl.glCallList(listID);
		return true;
	}

	@Override
	public boolean makeDisplayList( GL2 gl )
	{
		float doorwidth = (1.0f/2.0f) * cellsize;
		listID = gl.glGenLists( 1 );
		gl.glNewList(listID, GL2.GL_COMPILE);
		if ( xaligned )
		{
			gl.glBegin( GL2.GL_QUAD_STRIP );
			gl.glColor3f( .0f, .0f, .0f );
			gl.glVertex3f( position.x, position.y + 2 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f( position.x, position.y + 2 * doorwidth, 2 * cellsize );
			gl.glVertex3f( position.x, position.y + 3 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f( position.x, position.y + 3 * doorwidth, 2 * cellsize );
			gl.glVertex3f( position.x + cellsize, position.y + 3 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f( position.x + cellsize, position.y + 3 * doorwidth, 2 * cellsize );
			gl.glVertex3f( position.x + cellsize, position.y + 2 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f( position.x + cellsize, position.y + 2 * doorwidth, 2 * cellsize );
			gl.glVertex3f( position.x, position.y + 2 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f( position.x, position.y + 2 * doorwidth, 2 * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glColor3f( 1.0f, 1.0f, 1.0f );
			gl.glVertex3f( position.x, position.y + 2 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f( position.x, position.y + 3 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f( position.x + cellsize, position.y + 3 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f( position.x + cellsize, position.y + 2 * doorwidth, 1.5f * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glVertex3f( position.x, position.y + 2 * doorwidth, 2 * cellsize );
			gl.glVertex3f( position.x, position.y + 3 * doorwidth, 2 * cellsize );
			gl.glVertex3f( position.x + cellsize, position.y + 3 * doorwidth, 2 * cellsize );
			gl.glVertex3f( position.x + cellsize, position.y + 2 * doorwidth, 2 * cellsize );
			gl.glEnd();
		}
		else
		{
			gl.glColor3f( .0f, .0f, .0f );
			gl.glBegin( GL2.GL_QUAD_STRIP );
			gl.glVertex3f( position.x + 2 * doorwidth, position.y, 1.5f * cellsize );
			gl.glVertex3f( position.x + 2 * doorwidth, position.y, 2 * cellsize );
			gl.glVertex3f( position.x + 3 * doorwidth, position.y, 1.5f * cellsize );
			gl.glVertex3f( position.x + 3 * doorwidth, position.y, 2 * cellsize );
			gl.glVertex3f( position.x + 3 * doorwidth, position.y + cellsize, 1.5f * cellsize );
			gl.glVertex3f( position.x + 3 * doorwidth, position.y + cellsize, 2 * cellsize );
			gl.glVertex3f( position.x + 2 * doorwidth, position.y + cellsize, 1.5f * cellsize );
			gl.glVertex3f( position.x + 2 * doorwidth, position.y + cellsize, 2 * cellsize );
			gl.glVertex3f( position.x + 2 * doorwidth, position.y, 1.5f * cellsize );
			gl.glVertex3f( position.x + 2 * doorwidth, position.y, 2 * cellsize );
			gl.glEnd();
			gl.glColor3f( 1.0f, 1.0f, 1.0f );
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glVertex3f( position.x + 2 * doorwidth, position.y, 1.5f * cellsize );
			gl.glVertex3f( position.x + 3 * doorwidth, position.y, 1.5f * cellsize );
			gl.glVertex3f( position.x + 3 * doorwidth, position.y + cellsize, 1.5f * cellsize );
			gl.glVertex3f( position.x + 2 * doorwidth, position.y + cellsize, 1.5f * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glVertex3f( position.x + 2 * doorwidth, position.y, 2 * cellsize );
			gl.glVertex3f( position.x + 3 * doorwidth, position.y, 2 * cellsize );
			gl.glVertex3f( position.x + 3 * doorwidth, position.y + cellsize, 2 * cellsize );
			gl.glVertex3f( position.x + 2 * doorwidth, position.y + cellsize, 2 * cellsize );
			gl.glEnd();
		}
		gl.glEndList();
		return false;
	}

	@Override
	public void clean( GL2 gl )
	{
		gl.glDeleteLists( listID, 1 );
	}

}
