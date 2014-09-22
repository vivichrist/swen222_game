package ui.components;
import javax.media.opengl.GL2;


public interface GraphicalObject
{
	public boolean draw( GL2 gl );
	public boolean makeDisplayList( GL2 gl );
	public void clean( GL2 gl );
}
