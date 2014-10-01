package ui.components;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.media.opengl.GL2;


/**
 * @author Vivian Stewart
 * All wall meshes including corner walls, T walls and intersecting walls
 */
public class Partition implements GraphicalObject
{
	private Point2D.Float	position;
	private boolean	north, east, south, west;

	/**
	 * @param n, @param e, @param s, @param w indicate what this wall is
	 * surrounded by and hence what shape of wall this is.
	 * @param position
	 * @param GameView.cellsize
	 */
	public Partition( Type n, Type e, Type s, Type w
			, Point position )
	{	// walls connect with other walls or doors
		north = n == Type.WALL || n == Type.DOOR;
		east = e == Type.WALL || e == Type.DOOR;
		west = w == Type.WALL || w == Type.DOOR;
		south = s == Type.WALL || s == Type.DOOR;

		this.position = new Point2D.Float( position.x * GameView.cellsize
				, position.y * GameView.cellsize );
	}

	/* (non-Javadoc)
	 * @see ui.components.GraphicalObject#draw(javax.media.opengl.GL2)
	 */
	@Override
	public boolean draw( GL2 gl )
	{
//		if ( listID == 0 ) return false;
//		gl.glCallList(listID);
		return true;
	}

	/* (non-Javadoc)
	 * @see ui.components.GraphicalObject#initialise(javax.media.opengl.GL2)
	 */
	@Override
	public boolean initialise( GL2 gl )
	{
		float wallwidth = GameView.cellsize/3.0f; // wall spacing
		
		gl.glPushMatrix();
		gl.glTranslatef( position.x, position.y, 0 );
		makeWall( gl, wallwidth );
		gl.glPopMatrix();

		return true;
	}

	/**
	 * Initialise the displaylist for this wall, to be called later.
	 * @param gl
	 * @param wallwidth
	 */
	private void makeWall( GL2 gl, float wallwidth )
	{
		gl.glBegin( GL2.GL_QUAD_STRIP ); // filled polygons of wall
		gl.glColor3f( .0f, .0f, .0f );
		gl.glVertex3f( wallwidth, wallwidth, 0 );
		gl.glVertex3f( wallwidth, wallwidth, 2 * GameView.cellsize );
		if ( west )
		{
			gl.glVertex3f( 0, wallwidth, 0 );
			gl.glVertex3f( 0, wallwidth, 2 * GameView.cellsize );
			gl.glVertex3f( 0, 2 * wallwidth, 0 );
			gl.glVertex3f( 0, 2 * wallwidth, 2 * GameView.cellsize );
		}
		gl.glVertex3f( wallwidth, 2 * wallwidth, 0 );
		gl.glVertex3f( wallwidth, 2 * wallwidth, 2 * GameView.cellsize );
		if ( south )
		{
			gl.glVertex3f( wallwidth, GameView.cellsize, 0 );
			gl.glVertex3f( wallwidth, GameView.cellsize, 2 * GameView.cellsize );
			gl.glVertex3f( 2 * wallwidth, GameView.cellsize, 0 );
			gl.glVertex3f( 2 * wallwidth, GameView.cellsize, 2 * GameView.cellsize );
		}
		gl.glVertex3f( 2 * wallwidth, 2 * wallwidth, 0 );
		gl.glVertex3f( 2 * wallwidth, 2 * wallwidth, 2 * GameView.cellsize );
		if ( east )
		{
			gl.glVertex3f( GameView.cellsize, 2 * wallwidth, 0 );
			gl.glVertex3f( GameView.cellsize, 2 * wallwidth, 2 * GameView.cellsize );
			gl.glVertex3f( GameView.cellsize, wallwidth, 0 );
			gl.glVertex3f( GameView.cellsize, wallwidth, 2 * GameView.cellsize );
		}
		gl.glVertex3f( 2 * wallwidth, wallwidth, 0 );
		gl.glVertex3f( 2 * wallwidth, wallwidth, 2 * GameView.cellsize );
		if ( north )
		{
			gl.glVertex3f( 2 * wallwidth, 0, 0 );
			gl.glVertex3f( 2 * wallwidth, 0, 2 * GameView.cellsize );
			gl.glVertex3f( wallwidth, 0, 0 );
			gl.glVertex3f( wallwidth, 0, 2 * GameView.cellsize );
		}
		gl.glVertex3f( wallwidth, wallwidth, 0 );
		gl.glVertex3f( wallwidth, wallwidth, 2 * GameView.cellsize );
		gl.glEnd();
		gl.glBegin( GL2.GL_LINE_LOOP ); // lines around the bottom edges
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
			gl.glVertex3f( wallwidth, GameView.cellsize, 0 );
			gl.glVertex3f( 2 * wallwidth, GameView.cellsize, 0 );
		}
		gl.glVertex3f( 2 * wallwidth, 2 * wallwidth, 0 );
		if ( east )
		{
			gl.glVertex3f( GameView.cellsize, 2 * wallwidth, 0 );
			gl.glVertex3f( GameView.cellsize, wallwidth, 0 );
		}
		gl.glVertex3f( 2 * wallwidth, wallwidth, 0 );
		if ( north )
		{
			gl.glVertex3f( 2 * wallwidth, 0, 0 );
			gl.glVertex3f( wallwidth, 0, 0 );
		}
		gl.glEnd();
		gl.glBegin( GL2.GL_LINE_LOOP ); // lines around the top edges
		gl.glVertex3f( wallwidth, wallwidth, 2 * GameView.cellsize );
		if ( west )
		{
			gl.glVertex3f( 0, wallwidth, 2 * GameView.cellsize );
			gl.glVertex3f( 0, 2 * wallwidth, 2 * GameView.cellsize );
		}
		gl.glVertex3f( wallwidth, 2 * wallwidth, 2 * GameView.cellsize );
		if ( south )
		{
			gl.glVertex3f( wallwidth, GameView.cellsize, 2 * GameView.cellsize );
			gl.glVertex3f( 2 * wallwidth, GameView.cellsize, 2 * GameView.cellsize );
		}
		gl.glVertex3f( 2 * wallwidth, 2 * wallwidth, 2 * GameView.cellsize );
		if ( east )
		{
			gl.glVertex3f( GameView.cellsize, 2 * wallwidth, 2 * GameView.cellsize );
			gl.glVertex3f( GameView.cellsize, wallwidth, 2 * GameView.cellsize );
		}
		gl.glVertex3f( 2 * wallwidth, wallwidth, 2 * GameView.cellsize );
		if ( north )
		{
			gl.glVertex3f( 2 * wallwidth, 0, 2 * GameView.cellsize );
			gl.glVertex3f( wallwidth, 0, 2 * GameView.cellsize );
		}
		gl.glEnd();
		// vertical lines along the z-axis in the corners of the room
		gl.glBegin( GL2.GL_LINES ); 
		if ( west && north )
		{
			gl.glVertex3f( wallwidth - 0.1f, wallwidth - 0.1f, 2 * GameView.cellsize );// 1, 1
			gl.glVertex3f( wallwidth - 0.1f, wallwidth - 0.1f, 0 );
		} if ( west && south )
		{
			gl.glVertex3f( wallwidth - 0.1f, 2 * wallwidth + 0.1f, 2 * GameView.cellsize );// 1, 2
			gl.glVertex3f( wallwidth - 0.1f, 2 * wallwidth + 0.1f, 0 );
		} if ( north && east )
		{
			gl.glVertex3f( 2 * wallwidth + 0.1f, wallwidth - 0.1f, 2 * GameView.cellsize );// 2, 1
			gl.glVertex3f( 2 * wallwidth + 0.1f, wallwidth - 0.1f, 0 );
		} if ( east && south )
		{
			gl.glVertex3f( 2 * wallwidth + 0.1f, 2 * wallwidth + 0.1f, 2 * GameView.cellsize );// 2, 2
			gl.glVertex3f( 2 * wallwidth + 0.1f, 2 * wallwidth + 0.1f, 0 );
		}
		gl.glEnd();
	}

	/* (non-Javadoc)
	 * @see ui.components.GraphicalObject#clean(javax.media.opengl.GL2)
	 */
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
