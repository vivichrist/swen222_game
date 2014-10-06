package ui.components;

import java.awt.geom.Point2D.Float;

import javax.media.opengl.GL2;

public class Orientation implements Behavior
{
	private int depth, width; //, count = 0;
	private boolean xaligned, reverse;

	public Orientation(  int width, int depth, boolean xaligned, boolean reverse )
	{
		this.depth = depth * GameView.cellsize;
		this.width = width * GameView.cellsize;
		this.xaligned = xaligned;
		this.reverse = reverse;
	}

	@Override
	public void modify( GL2 gl, Float pos )
	{
		if ( xaligned && reverse )
		{
			gl.glRotatef( 270f, 0f, 0f, 1f );
			gl.glTranslatef( -width, 0f, 0f );
		}
		else if ( xaligned )
		{
			gl.glRotatef( 90f, 0f, 0f, 1f );
			gl.glTranslatef( 0f, -depth, 0f );
		}
		else if ( reverse )
		{
			gl.glRotatef( 180f, 0f, 0f, 1f );
			gl.glTranslatef( -width, -depth, 0f );
		}
		// test code to see all orientations
//		int next = count / 120;
//		++count;
//		if ( next == 0 )
//		{
//			xaligned = false;
//			reverse = false;
//		}
//		else if ( next == 1 )
//		{
//			reverse = true;
//		}
//		else if ( next == 2 )
//		{
//			xaligned = true;
//			reverse = false;
//		}	
//		else if ( next == 3 )
//		{
//			reverse = true;
//			
//		}
//		else count = 0;
	}

	@Override
	public boolean activate()
	{
		return false;
	}

}
