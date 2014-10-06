package ui.components;

import java.awt.geom.Point2D.Float;

import javax.media.opengl.GL2;

public class Oreintation implements Behavior
{
	private int depth, width;
	private boolean xaligned, reverse;

	public Oreintation(  int depth, int width, boolean xaligned, boolean reverse )
	{
		this.depth = depth;
		this.width = width;
		this.xaligned = xaligned;
		this.reverse = reverse;
	}

	@Override
	public void modify( GL2 gl, Float pos )
	{
		if ( reverse )
		{
			gl.glTranslatef( width, depth, 0f );
			gl.glRotatef( 180f, 0f, 0f, 1f );
		}
		if ( xaligned )
		{
			gl.glTranslatef( depth, width, 0f );
			gl.glRotatef( 90f, 0f, 0f, 1f );
		}
	}

	@Override
	public boolean activate()
	{
		return false;
	}

}
