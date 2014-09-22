package ui.components;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.media.opengl.GL2;


public class Partition implements GraphicalObject
{
	private boolean 		flat, xaligned = false;
	private Point2D.Float	position;
	private int				cellsize;
	private int				listID;

	public Partition( Type c, Type n, Type e, Type s, Type w
			, Point position, int cellsize )
	{
		if ( n == s && n == Type.WALL )
		{
			if ( w == Type.WALL || e == Type.WALL ) flat = false;
			else { flat = true; } // xaligned is false
		}
		else if ( e == w && e == Type.WALL )
		{
			if ( n == Type.WALL || s == Type.WALL ) flat = false;
			else { flat = true; xaligned = true; }
		}
		else if ( w == Type.WALL || e == Type.WALL )
		{ flat = true; xaligned = true; }
		else { flat = true; } // xaligned is false
		
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
	public boolean makeDisplayList( GL2 gl )
	{
		float wallwidth = (1.0f/3.0f) * cellsize;
		listID = gl.glGenLists( 1 );
		
		gl.glNewList(listID, GL2.GL_COMPILE); 
		// walls come in two types? ...hmmm
		if ( flat ) makeFlatWall( gl, wallwidth );
		else makeIntersectingWall( gl, wallwidth );
		gl.glEndList();
		
		return true;
	}
	
	private void makeIntersectingWall( GL2 gl, float wallwidth )
	{
		gl.glBegin( GL2.GL_QUAD_STRIP );
		gl.glColor3f( .0f, .0f, .0f );
		gl.glVertex3f( position.x, position.y + wallwidth, 0 );
		gl.glVertex3f( position.x, position.y + wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x, position.y + 2 * wallwidth, 0 );
		gl.glVertex3f( position.x, position.y + 2 * wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + wallwidth, position.y + 2 * wallwidth, 0 );
		gl.glVertex3f( position.x + wallwidth, position.y + 2 * wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + wallwidth, position.y + cellsize, 0 );
		gl.glVertex3f( position.x + wallwidth, position.y + cellsize, 2 * cellsize );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + cellsize, 0 );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + cellsize, 2 * cellsize );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + 2 * wallwidth, 0 );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + 2 * wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + cellsize, position.y + 2 * wallwidth, 0 );
		gl.glVertex3f( position.x + cellsize, position.y + 2 * wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + cellsize, position.y + wallwidth, 0 );
		gl.glVertex3f( position.x + cellsize, position.y + wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + wallwidth, 0 );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y, 0 );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y, 2 * cellsize );
		gl.glVertex3f( position.x + wallwidth, position.y, 0 );
		gl.glVertex3f( position.x + wallwidth, position.y, 2 * cellsize );
		gl.glVertex3f( position.x + wallwidth, position.y + wallwidth, 0 );
		gl.glVertex3f( position.x + wallwidth, position.y + wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x, position.y + wallwidth, 0 );
		gl.glVertex3f( position.x, position.y + wallwidth, 2 * cellsize );
		gl.glEnd();
		// gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
		gl.glBegin( GL2.GL_LINE_LOOP );
		gl.glColor3f( 1.0f, 1.0f, 1.0f );
		gl.glVertex3f( position.x, position.y + wallwidth, 0 );
		gl.glVertex3f( position.x, position.y + 2 * wallwidth, 0 );
		gl.glVertex3f( position.x + wallwidth, position.y + 2 * wallwidth, 0 );
		gl.glVertex3f( position.x + wallwidth, position.y + cellsize, 0 );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + cellsize, 0 );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + 2 * wallwidth, 0 );
		gl.glVertex3f( position.x + cellsize, position.y + 2 * wallwidth, 0 );
		gl.glVertex3f( position.x + cellsize, position.y + wallwidth, 0 );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + wallwidth, 0 );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y, 0 );
		gl.glVertex3f( position.x + wallwidth, position.y, 0 );
		gl.glVertex3f( position.x + wallwidth, position.y + wallwidth, 0 );
		gl.glEnd();
		gl.glBegin( GL2.GL_LINE_LOOP );
		gl.glVertex3f( position.x, position.y + wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x, position.y + 2 * wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + wallwidth, position.y + 2 * wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + wallwidth, position.y + cellsize, 2 * cellsize );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + cellsize, 2 * cellsize );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + 2 * wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + cellsize, position.y + 2 * wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + cellsize, position.y + wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y, 2 * cellsize );
		gl.glVertex3f( position.x + wallwidth, position.y, 2 * cellsize );
		gl.glVertex3f( position.x + wallwidth, position.y + wallwidth, 2 * cellsize );
		gl.glEnd();
		gl.glEnd();
		gl.glBegin( GL2.GL_LINES );
		gl.glVertex3f( position.x + wallwidth, position.y + wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + wallwidth, position.y + wallwidth, 0 );
		gl.glVertex3f( position.x + wallwidth, position.y + 2 * wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + wallwidth, position.y + 2 * wallwidth, 0 );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + wallwidth, 0 );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + 2 * wallwidth, 2 * cellsize );
		gl.glVertex3f( position.x + 2 * wallwidth, position.y + 2 * wallwidth, 0 );
		gl.glEnd();
	}
	
	private void makeFlatWall( GL2 gl, float wallwidth )
	{
		if ( xaligned )
		{
			gl.glBegin( GL2.GL_QUAD_STRIP );
			gl.glColor3f( .0f, .0f, .0f );
			gl.glVertex3f( position.x, position.y + wallwidth, 0 );
			gl.glVertex3f( position.x, position.y + wallwidth, 2 * cellsize );
			gl.glVertex3f( position.x, position.y + 2 * wallwidth, 0 );
			gl.glVertex3f( position.x, position.y + 2 * wallwidth, 2 * cellsize );
			gl.glVertex3f( position.x + cellsize, position.y + 2 * wallwidth, 0 );
			gl.glVertex3f( position.x + cellsize, position.y + 2 * wallwidth, 2 * cellsize );
			gl.glVertex3f( position.x + cellsize, position.y + wallwidth, 0 );
			gl.glVertex3f( position.x + cellsize, position.y + wallwidth, 2 * cellsize );
			gl.glVertex3f( position.x, position.y + wallwidth, 0 );
			gl.glVertex3f( position.x, position.y + wallwidth, 2 * cellsize );
			gl.glEnd();
			// gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glColor3f( 1.0f, 1.0f, 1.0f );
			gl.glVertex3f( position.x, position.y + wallwidth, 0 );
			gl.glVertex3f( position.x, position.y + 2 * wallwidth, 0 );
			gl.glVertex3f( position.x + cellsize, position.y + 2 * wallwidth, 0 );
			gl.glVertex3f( position.x + cellsize, position.y + wallwidth, 0 );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glVertex3f( position.x, position.y + wallwidth, 2 * cellsize );
			gl.glVertex3f( position.x, position.y + 2 * wallwidth, 2 * cellsize );
			gl.glVertex3f( position.x + cellsize, position.y + 2 * wallwidth, 2 * cellsize );
			gl.glVertex3f( position.x + cellsize, position.y + wallwidth, 2 * cellsize );
			gl.glEnd();
		}
		else
		{
			gl.glColor3f( .0f, .0f, .0f );
			gl.glBegin( GL2.GL_QUAD_STRIP );
			gl.glVertex3f( position.x + wallwidth, position.y, 0 );
			gl.glVertex3f( position.x + wallwidth, position.y, 2 * cellsize );
			gl.glVertex3f( position.x + 2 * wallwidth, position.y, 0 );
			gl.glVertex3f( position.x + 2 * wallwidth, position.y, 2 * cellsize );
			gl.glVertex3f( position.x + 2 * wallwidth, position.y + cellsize, 0 );
			gl.glVertex3f( position.x + 2 * wallwidth, position.y + cellsize, 2 * cellsize );
			gl.glVertex3f( position.x + wallwidth, position.y + cellsize, 0 );
			gl.glVertex3f( position.x + wallwidth, position.y + cellsize, 2 * cellsize );
			gl.glVertex3f( position.x + wallwidth, position.y, 0 );
			gl.glVertex3f( position.x + wallwidth, position.y, 2 * cellsize );
			gl.glEnd();
			//gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
			gl.glColor3f( 1.0f, 1.0f, 1.0f );
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glVertex3f( position.x + wallwidth, position.y, 0 );
			gl.glVertex3f( position.x + 2 * wallwidth, position.y, 0 );
			gl.glVertex3f( position.x + 2 * wallwidth, position.y + cellsize, 0 );
			gl.glVertex3f( position.x + wallwidth, position.y + cellsize, 0 );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP );
			gl.glVertex3f( position.x + wallwidth, position.y, 2 * cellsize );
			gl.glVertex3f( position.x + 2 * wallwidth, position.y, 2 * cellsize );
			gl.glVertex3f( position.x + 2 * wallwidth, position.y + cellsize, 2 * cellsize );
			gl.glVertex3f( position.x + wallwidth, position.y + cellsize, 2 * cellsize );
			gl.glEnd();
		}
	}

	@Override
	public void clean( GL2 gl )
	{
		gl.glDeleteLists( listID, 1 );
	}

}
