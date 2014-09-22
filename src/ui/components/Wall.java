package ui.components;
import java.awt.Point;
import java.awt.geom.Point2D;
import javax.media.opengl.GL2;

public class Wall implements GraphicalObject
{
	private Point2D.Float	begin, end, doorB, doorE;
	private boolean			hasDoor	= true;
	private int				height;
	private int	listID;

	public Wall( Point begin, Point end, int door, int height, GameCollision gc )
	{
		if ( begin.x != end.x && begin.y != end.y )
			throw new RuntimeException( "Walls must be at Perpendicular" );
		if ( begin.x > end.x || begin.y > end.y )
			throw new RuntimeException( "Walls must be x1<=x2 y1<=y2 (ordered)" );
		// height is also cell size
		this.begin = new Point2D.Float( (begin.x + 0.5f) * height,
				(begin.y + 0.5f) * height );
		this.end = new Point2D.Float( (end.x + 0.5f) * height, (end.y + 0.5f)
				* height );
		this.height = height * 2;
		setupCollisions( begin, end, door, gc );
		if ( begin.distance( end ) - 1 <= door )
		{
			// Doors must be inside Wall Bounds unless there is no door
			hasDoor = false;
			return;
		}
		if ( begin.x == end.x )
		{
			doorB = new Point2D.Float( this.begin.x, (begin.y + door)
					* height );
			doorE = new Point2D.Float( this.begin.x, (begin.y + door + 1)
					* height );
		} else
		{
			doorB = new Point2D.Float( (begin.x + door) * height,
					this.begin.y );
			doorE = new Point2D.Float( (begin.x + door + 1) * height,
					this.begin.y );
		}
	}

	private void setupCollisions( Point start, Point to, int door, GameCollision gc )
	{
		if ( start.x == to.x )
		{
			for ( int i = start.y; i < to.y; ++i )
				if ( start.y + door != i ) gc.setBit( start.x, i, Type.WALL );
		}
		else
			for ( int i = start.x; i < to.x; ++i )
				if ( start.x + door != i ) gc.setBit( i, start.y, Type.WALL );
	}

	@Override
	public boolean makeDisplayList( GL2 gl )
	{
		listID = gl.glGenLists( 1 );
		gl.glNewList(listID, GL2.GL_COMPILE); 
		gl.glColor3f( .0f, .0f, .0f );
		gl.glBegin( GL2.GL_QUADS );
		gl.glVertex3f( begin.x, begin.y, height );
		gl.glVertex3f( begin.x, begin.y, 0 );
		if ( hasDoor )
		{
			gl.glVertex3f( doorB.x, doorB.y, 0 );
			gl.glVertex3f( doorB.x, doorB.y, height );
			gl.glVertex3f( doorB.x, doorB.y, height );
			gl.glVertex3f( doorB.x, doorB.y, height * 0.8f );
			gl.glVertex3f( doorE.x, doorE.y, height * 0.8f );
			gl.glVertex3f( doorE.x, doorE.y, height );
			gl.glVertex3f( doorE.x, doorE.y, height );
			gl.glVertex3f( doorE.x, doorE.y, 0 );
		}
		gl.glVertex3f( end.x, end.y, 0 );
		gl.glVertex3f( end.x, end.y, height );
		// and the reverse side
		gl.glVertex3f( begin.x, begin.y, 0 );
		gl.glVertex3f( begin.x, begin.y, height );
		if ( hasDoor )
		{
			gl.glVertex3f( doorB.x, doorB.y, height );
			gl.glVertex3f( doorB.x, doorB.y, 0 );
			// top of door
			gl.glVertex3f( doorB.x, doorB.y, height * 0.8f );
			gl.glVertex3f( doorB.x, doorB.y, height );
			gl.glVertex3f( doorE.x, doorE.y, height );
			gl.glVertex3f( doorE.x, doorE.y, height * 0.8f );
			// rest of wall
			gl.glVertex3f( doorE.x, doorE.y, 0 );
			gl.glVertex3f( doorE.x, doorE.y, height );
		}
		gl.glVertex3f( end.x, end.y, height );
		gl.glVertex3f( end.x, end.y, 0 );
		gl.glEnd();
		// then lines
		gl.glColor3f( 1.0f, 1.0f, 1.0f );
		gl.glBegin( GL2.GL_LINE_LOOP );
		gl.glVertex3f( begin.x, begin.y, height );
		gl.glVertex3f( begin.x, begin.y, 0 );
		if ( hasDoor )
		{
			gl.glVertex3f( doorB.x, doorB.y, 0 );
			gl.glVertex3f( doorB.x, doorB.y, height * 0.8f );
			gl.glVertex3f( doorE.x, doorE.y, height * 0.8f );
			gl.glVertex3f( doorE.x, doorE.y, 0 );
		}
		gl.glVertex3f( end.x, end.y, 0 );
		gl.glVertex3f( end.x, end.y, height );
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
