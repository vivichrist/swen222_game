package ui.components;

import java.awt.geom.Point2D;

import javax.media.opengl.GL2;

public interface Behavior
{
	public void modify( GL2 gl, Point2D.Float pos );
}
