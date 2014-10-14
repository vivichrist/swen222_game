package ui.components;

import java.awt.geom.Point2D;

import javax.media.opengl.GL2;

/**
 * @author Vivian Stewart
 * Behaviour 
 */
public interface Behaviour
{
	/**
	 * Allows manipulation of the currently rendering mesh every frame
	 * @param gl - OpenGL context
	 * @param pos - position of the GraphicalObject
	 */
	public void modify( GL2 gl, Point2D.Float pos );
	/**
	 * @return
	 */
	public boolean activate();
}
