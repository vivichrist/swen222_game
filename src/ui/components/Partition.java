package ui.components;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.media.opengl.GL2;


public class Partition implements GraphicalObject
{
	private Point2D.Float	position;
	private int				cellsize;
	private int				listID;
	private boolean	north, east, south, west;

	public Partition( Type n, Type e, Type s, Type w
			, Point position, int cellsize )
	{
		north = n == Type.WALL || n == Type.DOOR;
		east = e == Type.WALL || e == Type.DOOR;
		west = w == Type.WALL || w == Type.DOOR;
		south = s == Type.WALL || s == Type.DOOR;

		this.position = new Point2D.Float( position.x * cellsize
				, position.y * cellsize );
		this.cellsize = cellsize;
	}

	@Override
	public boolean draw( GL2 gl )
	{
		if ( listID == 0 ) return false;
		gl.glCallList(listID);
		return true;
	}

	@Override
	public boolean initialise( GL2 gl )
	{
		float wallwidth = cellsize/3.0f;
		listID = gl.glGenLists( 1 );
		gl.glNewList(listID, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glTranslatef( position.x, position.y, 0 );
		makeWall( gl, wallwidth );
		gl.glPopMatrix();
		gl.glEndList();

		return true;
	}

	private void makeWall( GL2 gl, float wallwidth )
	{
		gl.glBegin( GL2.GL_QUAD_STRIP );
		gl.glColor3f( .0f, .0f, .0f );
		gl.glVertex3f( wallwidth, wallwidth, 0 );
		gl.glVertex3f( wallwidth, wallwidth, 2 * cellsize );
		if ( west )
		{
			gl.glVertex3f( 0, wallwidth, 0 );
			gl.glVertex3f( 0, wallwidth, 2 * cellsize );
			gl.glVertex3f( 0, 2 * wallwidth, 0 );
			gl.glVertex3f( 0, 2 * wallwidth, 2 * cellsize );
		}
		gl.glVertex3f( wallwidth, 2 * wallwidth, 0 );
		gl.glVertex3f( wallwidth, 2 * wallwidth, 2 * cellsize );
		if ( south )
		{
			gl.glVertex3f( wallwidth, cellsize, 0 );
			gl.glVertex3f( wallwidth, cellsize, 2 * cellsize );
			gl.glVertex3f( 2 * wallwidth, cellsize, 0 );
			gl.glVertex3f( 2 * wallwidth, cellsize, 2 * cellsize );
		}
		gl.glVertex3f( 2 * wallwidth, 2 * wallwidth, 0 );
		gl.glVertex3f( 2 * wallwidth, 2 * wallwidth, 2 * cellsize );
		if ( east )
		{
			gl.glVertex3f( cellsize, 2 * wallwidth, 0 );
			gl.glVertex3f( cellsize, 2 * wallwidth, 2 * cellsize );
			gl.glVertex3f( cellsize, wallwidth, 0 );
			gl.glVertex3f( cellsize, wallwidth, 2 * cellsize );
		}
		gl.glVertex3f( 2 * wallwidth, wallwidth, 0 );
		gl.glVertex3f( 2 * wallwidth, wallwidth, 2 * cellsize );
		if ( north )
		{
			gl.glVertex3f( 2 * wallwidth, 0, 0 );
			gl.glVertex3f( 2 * wallwidth, 0, 2 * cellsize );
			gl.glVertex3f( wallwidth, 0, 0 );
			gl.glVertex3f( wallwidth, 0, 2 * cellsize );
		}
		gl.glVertex3f( wallwidth, wallwidth, 0 );
		gl.glVertex3f( wallwidth, wallwidth, 2 * cellsize );
		gl.glEnd();
		// gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
		gl.glBegin( GL2.GL_LINE_LOOP );
		gl.glColor3f( 1.0f, 1.0f, 1.0f );
		gl.glVertex3f( wallwidth, wallwidth, 0 );
		if ( west )
		{
			gl.glVertex3f( 0, wallwidth, 0 );
			gl.glVertex3f( 0, 2 * wallwidth, 0 );
		}
		gl.glVertex3f( wallwidth, 2 * wallwidth, 0 );
		if ( south )
		{
			gl.glVertex3f( wallwidth, cellsize, 0 );
			gl.glVertex3f( 2 * wallwidth, cellsize, 0 );
		}
		gl.glVertex3f( 2 * wallwidth, 2 * wallwidth, 0 );
		if ( east )
		{
			gl.glVertex3f( cellsize, 2 * wallwidth, 0 );
			gl.glVertex3f( cellsize, wallwidth, 0 );
		}
		gl.glVertex3f( 2 * wallwidth, wallwidth, 0 );
		if ( north )
		{
			gl.glVertex3f( 2 * wallwidth, 0, 0 );
			gl.glVertex3f( wallwidth, 0, 0 );
		}
		gl.glEnd();
		gl.glBegin( GL2.GL_LINE_LOOP );
		gl.glVertex3f( wallwidth, wallwidth, 2 * cellsize );
		if ( west )
		{
			gl.glVertex3f( 0, wallwidth, 2 * cellsize );
			gl.glVertex3f( 0, 2 * wallwidth, 2 * cellsize );
		}
		gl.glVertex3f( wallwidth, 2 * wallwidth, 2 * cellsize );
		if ( south )
		{
			gl.glVertex3f( wallwidth, cellsize, 2 * cellsize );
			gl.glVertex3f( 2 * wallwidth, cellsize, 2 * cellsize );
		}
		gl.glVertex3f( 2 * wallwidth, 2 * wallwidth, 2 * cellsize );
		if ( east )
		{
			gl.glVertex3f( cellsize, 2 * wallwidth, 2 * cellsize );
			gl.glVertex3f( cellsize, wallwidth, 2 * cellsize );
		}
		gl.glVertex3f( 2 * wallwidth, wallwidth, 2 * cellsize );
		if ( north )
		{
			gl.glVertex3f( 2 * wallwidth, 0, 2 * cellsize );
			gl.glVertex3f( wallwidth, 0, 2 * cellsize );
		}
		gl.glEnd();
		gl.glEnd();
		gl.glBegin( GL2.GL_LINES );
		if ( west && north )
		{
			gl.glVertex3f( wallwidth - 0.1f, wallwidth - 0.1f, 2 * cellsize );// 1, 1
			gl.glVertex3f( wallwidth - 0.1f, wallwidth - 0.1f, 0 );
		} if ( west && south )
		{
			gl.glVertex3f( wallwidth - 0.1f, 2 * wallwidth + 0.1f, 2 * cellsize );// 1, 2
			gl.glVertex3f( wallwidth - 0.1f, 2 * wallwidth + 0.1f, 0 );
		} if ( north && east )
		{
			gl.glVertex3f( 2 * wallwidth + 0.1f, wallwidth - 0.1f, 2 * cellsize );// 2, 1
			gl.glVertex3f( 2 * wallwidth + 0.1f, wallwidth - 0.1f, 0 );
		} if ( east && south )
		{
			gl.glVertex3f( 2 * wallwidth + 0.1f, 2 * wallwidth + 0.1f, 2 * cellsize );// 2, 2
			gl.glVertex3f( 2 * wallwidth + 0.1f, 2 * wallwidth + 0.1f, 0 );
		}
		gl.glEnd();
	}

	@Override
	public void clean( GL2 gl )
	{
		gl.glDeleteLists( listID, 1 );
	}

}
