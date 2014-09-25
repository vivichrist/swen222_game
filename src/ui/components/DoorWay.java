package ui.components;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

import javax.media.opengl.GL2;

/**
 * @author Vivian Stewart
 * The arch above a doorway.
 */
public class DoorWay implements GraphicalObject
{

	private boolean	xaligned = true;
	private Point2D.Float	position;
	private int	cellsize;
	private int	listID;
	private float open = 0f, openSpeed = 0f;
	private List<float[]> vertices;
	private List<int[]> indices;

	/**
	 * @param north and @param south indicate if the door is aligned
	 * along the x-axis or y-axis.
	 * @param position - the position from the map where this doorway should be
	 * drawn. (Needs to be scaled)
	 * @param scale
	 */
	public DoorWay( Type north, Type south, Point position, int scale )
	{
		if ( north == south && north == Type.WALL ) xaligned = false;
		this.position = new Point2D.Float( position.x * scale
				, position.y * scale );
		cellsize = scale;
		MeshStore m = MeshStore.instance();
		Mesh mesh = m.getMesh( Type.DOOR );
		vertices = mesh.getVertices();
		indices = mesh.getIndices();
	}
	
	public void open()
	{
		openSpeed = 0.7f / cellsize;
	}

	/* (non-Javadoc)
	 * @see ui.components.GraphicalObject#draw(javax.media.opengl.GL2)
	 */
	@Override
	public boolean draw( GL2 gl )
	{
		if ( listID == 0 ) return false;
		gl.glCallList(listID);
		drawDynamic( gl );
		return true;
	}

	private void drawDynamic( GL2 gl )
	{
		open += openSpeed;
		if ( open > 2 * cellsize ) openSpeed = -openSpeed;
		if ( open < 0 )
		{
			openSpeed = 0f;
			open = 0f;
		}
		gl.glPushMatrix();
		gl.glTranslatef(
				xaligned ? position.x - Math.min(open, cellsize * 0.9f) : position.x
			  , xaligned ? position.y : position.y - Math.min(open, cellsize * 0.9f), 0 );
		
		if ( !xaligned )
		{
			gl.glTranslatef( cellsize, 0f, 0f );
			gl.glRotatef( 90.0f, 0f, 0f, 1.f );
		}
		gl.glScalef( cellsize, cellsize, cellsize );
		for ( int[] i: indices )
		{
			if ( i.length == 4 )
			{
				gl.glBegin( GL2.GL_QUADS );
				gl.glColor3f( .0f, .0f, .0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glVertex3fv( vertices.get( i[2] ), 0 );
				gl.glVertex3fv( vertices.get( i[3] ), 0 );
				gl.glEnd();
				gl.glBegin( GL2.GL_LINE_LOOP );
				gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glVertex3fv( vertices.get( i[2] ), 0 );
				gl.glVertex3fv( vertices.get( i[3] ), 0 );
				gl.glEnd();
			}
			if ( i.length == 3 )
			{
				gl.glBegin( GL2.GL_TRIANGLES );
				gl.glColor3f( .0f, .0f, .0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glVertex3fv( vertices.get( i[2] ), 0 );
				gl.glEnd();
				gl.glBegin( GL2.GL_LINE_LOOP );
				gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glVertex3fv( vertices.get( i[2] ), 0 );
				gl.glEnd();
			}
			if ( i.length == 2 )
			{
				gl.glBegin( GL2.GL_LINE );
				gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glEnd();
			}
			if ( i.length == 1 )
			{
				gl.glBegin( GL2.GL_POINTS );
				gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glEnd();
			}
		}
		gl.glPopMatrix();
	}

	/**
	 * Setup drawing of the arch above the door using a displaylist.
	 * @see ui.components.GraphicalObject#initialise(javax.media.opengl.GL2)
	 **/
	@Override
	public boolean initialise( GL2 gl )
	{
		float doorwidth = cellsize/3.0f;
		listID = gl.glGenLists( 1 );
		gl.glNewList(listID, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glTranslatef( position.x, position.y, 0 );
		if ( xaligned )
		{
			gl.glBegin( GL2.GL_QUAD_STRIP ); // arch of doorway
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
			gl.glBegin( GL2.GL_LINE_LOOP ); // side of doorway
			gl.glColor3f( 1.0f, 1.0f, 1.0f );
			gl.glVertex3f( 0,  doorwidth, 1.5f * cellsize );
			gl.glVertex3f( 0,  doorwidth, 0 );
			gl.glVertex3f( 0,  2 * doorwidth, 0 );
			gl.glVertex3f( 0,  2 * doorwidth, 1.5f * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP ); // other side of doorway
			gl.glVertex3f( cellsize,  doorwidth, 1.5f * cellsize );
			gl.glVertex3f( cellsize,  doorwidth, 0 );
			gl.glVertex3f( cellsize,  2 * doorwidth, 0 );
			gl.glVertex3f( cellsize,  2 * doorwidth, 1.5f * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP ); // line across the top of the arch
			gl.glVertex3f( 0,  doorwidth, 1.5f * cellsize );
			gl.glVertex3f( 0,  2 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f(  cellsize,  2 * doorwidth, 1.5f * cellsize );
			gl.glVertex3f(  cellsize,  doorwidth, 1.5f * cellsize );
			gl.glEnd();
			gl.glBegin( GL2.GL_LINE_LOOP ); // lines underneath the arch
			gl.glVertex3f( 0,  doorwidth, 2 * cellsize );
			gl.glVertex3f( 0,  2 * doorwidth, 2 * cellsize );
			gl.glVertex3f( cellsize,  2 * doorwidth, 2 * cellsize );
			gl.glVertex3f( cellsize,  doorwidth, 2 * cellsize );
			gl.glEnd();
		}
		else
		{// same order as above but different orientation
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

	/* (non-Javadoc)
	 * @see ui.components.GraphicalObject#clean(javax.media.opengl.GL2)
	 */
	@Override
	public void clean( GL2 gl )
	{
		gl.glDeleteLists( listID, 1 );
	}

}
