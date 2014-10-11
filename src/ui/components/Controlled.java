package ui.components;

import java.awt.geom.Point2D.Float;

import javax.media.opengl.GL2;

public class Controlled implements Behaviour
{

	public Controlled(){}

	@Override
	public void modify( GL2 gl, Float pos )
	{
		
	}

	@Override
	public boolean activate()
	{
		return true;
	}

}
