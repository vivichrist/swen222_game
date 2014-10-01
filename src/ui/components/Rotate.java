package ui.components;

import java.awt.geom.Point2D;

import javax.media.opengl.GL2;

public class Rotate implements Behavior
{
	private int rotate = 0;
	
	public Rotate( int rotation )
	{
		rotate = rotation;
	}

	@Override
	public void modify( GL2 gl, Point2D.Float pos )
	{
		gl.glTranslatef( pos.x + (GameView.cellsize >> 2), pos.y  + (GameView.cellsize >> 2), 0 );
		gl.glRotatef( rotate, 0f, 0f, 1.f );
		rotate = (rotate + 1) % 360;
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
