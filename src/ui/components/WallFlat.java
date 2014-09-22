package ui.components;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.media.opengl.GL2;


public class WallFlat implements GraphicalObject
{

	private Point2D.Float	position;
	private int				cellsize;
	private int	listID;
	private boolean	xaligned;

	public WallFlat( Point position, int cellsize, boolean xaligned, GameCollision gc )
	{
		this.xaligned = xaligned;
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
		listID = gl.glGenLists( 1 );
		gl.glNewList(listID, GL2.GL_COMPILE); 
		float wallwidth = (1.0f/3.0f) * cellsize;
		
		
		
		gl.glEndList();
		return true;
	}

	@Override
	public void clean( GL2 gl )
	{
		gl.glDeleteLists( listID, 1 );
	}

}
