package ui.components;
import javax.media.opengl.GL2;


/**
 * @author Vivian Stewart
 * A visible 3D object occupying one square (cube) in the 3D environment
 * and game map.
 */
public interface GraphicalObject
{
	/**
	 * Called every frame to draw this graphical object
	 * @param gl - opengl drawing context for openGL 2.0
	 * @return if successful
	 */
	public boolean draw( GL2 gl );
	/**
	 * initialise any resources need to draw the Graphical Object
	 * i.e displaylists and meshes.
	 * @param gl - opengl drawing context
	 * @return if successful
	 */
	public boolean initialise( GL2 gl );
	/**	
	 * remove the displaylist from video memory when we are finished with it
	 * @param gl - opengl drawing context
	 */
	public void clean( GL2 gl );
}
