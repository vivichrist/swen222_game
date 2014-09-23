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
		float doorwidth = cellsize/3.0f;
		listID = gl.glGenLists( 1 );
		gl.glNewList(listID, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glTranslatef( position.x, position.y, 0 );
		if ( xaligned )
		{
			gl.glBegin( GL2.GL_QUAD_STRIP );
			gl.glColor3f( .0f, .0f, .0f );
			gl.glVertex3f( 0,  doorwidth, 1.5f * cellsize );
			gl.glVertex3f( 0,  doorwidth, 2 * cellsize );
			gl.glVertex3f( 0,  2 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f( 0,  2 * doorwidth, 2 * cellsize );
			gl.glVertex3f( cellsize,  2 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f( cellsize,  2 * doorwidth, 2 * cellsize );
			gl.glVertex3f( cellsize,  doorwidth, 1.5f * cellsize );
			gl.glVertex3f( cellsize,  doorwidth, 2 * cellsize );
			gl.glVertex3f( 0,  doorwidth, 1.5f * cellsize );
			gl.glVertex3f( 0,  doorwidth, 2 * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glColor3f( 1.0f, 1.0f, 1.0f );
			gl.glVertex3f( 0,  doorwidth, 1.5f * cellsize );
			gl.glVertex3f( 0,  doorwidth, 0 );
			gl.glVertex3f( 0,  2 * doorwidth, 0 );
			gl.glVertex3f( 0,  2 * doorwidth, 1.5f * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glVertex3f( cellsize,  doorwidth, 1.5f * cellsize );
			gl.glVertex3f( cellsize,  doorwidth, 0 );
			gl.glVertex3f( cellsize,  2 * doorwidth, 0 );
			gl.glVertex3f( cellsize,  2 * doorwidth, 1.5f * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glVertex3f( 0,  doorwidth, 1.5f * cellsize );
			gl.glVertex3f( 0,  2 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f(  cellsize,  2 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f(  cellsize,  doorwidth, 1.5f * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glVertex3f( 0,  doorwidth, 2 * cellsize );
			gl.glVertex3f( 0,  2 * doorwidth, 2 * cellsize );
			gl.glVertex3f( cellsize,  2 * doorwidth, 2 * cellsize );
			gl.glVertex3f( cellsize,  doorwidth, 2 * cellsize );
			gl.glEnd();
		}
		else
		{
			gl.glBegin( GL2.GL_QUAD_STRIP );
			gl.glColor3f( .0f, .0f, .0f );
			gl.glVertex3f(  doorwidth, 0, 1.5f * cellsize );
			gl.glVertex3f(  doorwidth, 0, 2 * cellsize );
			gl.glVertex3f(  2 * doorwidth, 0, 1.5f * cellsize );
			gl.glVertex3f(  2 * doorwidth, 0, 2 * cellsize );
			gl.glVertex3f(  2 * doorwidth,  cellsize, 1.5f * cellsize );
			gl.glVertex3f(  2 * doorwidth,  cellsize, 2 * cellsize );
			gl.glVertex3f(  doorwidth,  cellsize, 1.5f * cellsize );
			gl.glVertex3f(  doorwidth,  cellsize, 2 * cellsize );
			gl.glVertex3f(  doorwidth, 0, 1.5f * cellsize );
			gl.glVertex3f(  doorwidth, 0, 2 * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glColor3f( 1.0f, 1.0f, 1.0f );
			gl.glVertex3f( doorwidth, 0, 1.5f * cellsize );
			gl.glVertex3f( doorwidth, 0, 0 );
			gl.glVertex3f( 2 * doorwidth, 0, 0 );
			gl.glVertex3f( 2 * doorwidth, 0, 1.5f * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glVertex3f( doorwidth, cellsize, 1.5f * cellsize );
			gl.glVertex3f( doorwidth, cellsize, 0 );
			gl.glVertex3f( 2 * doorwidth, cellsize, 0 );
			gl.glVertex3f( 2 * doorwidth, cellsize, 1.5f * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glVertex3f(  doorwidth, 0, 1.5f * cellsize );
			gl.glVertex3f(  2 * doorwidth, 0, 1.5f * cellsize );
			gl.glVertex3f(  2 * doorwidth,  cellsize, 1.5f * cellsize );
			gl.glVertex3f(  doorwidth,  cellsize, 1.5f * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glVertex3f(  doorwidth, 0, 2 * cellsize );
			gl.glVertex3f(  2 * doorwidth, 0, 2 * cellsize );
			gl.glVertex3f(  2 * doorwidth,  cellsize, 2 * cellsize );
			gl.glVertex3f(  doorwidth,  cellsize, 2 * cellsize );
			gl.glEnd();
		}
		gl.glPopMatrix();
		gl.glEndList();
		return false;
	}

	@Override
	public void clean( GL2 gl )
	{
		gl.glDeleteLists( listID, 1 );
	}

}
