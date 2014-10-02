package ui.components;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

import javax.media.opengl.GL2;

import world.components.CellType;

public class Dymanic implements GraphicalObject
{
	private Point2D.Float	position;
	private CellType		type;
	private Behavior anim;
	private List<float[]> vertices;
	private List<int[]> indices;
	
	public Dymanic( CellType type, Behave act, Point position )
	{
		this.type = type;
		if ( type == CellType.BALL || type == CellType.CONE
				|| type == CellType.CUBE || type == CellType.DIAMOND
				|| type == CellType.KEY )
			this.position = new Point2D.Float(
					  position.x * GameView.cellsize + (GameView.cellsize >> 2)
					, position.y * GameView.cellsize + (GameView.cellsize >> 2) );
		else this.position = new Point2D.Float(
				  position.x * GameView.cellsize
				, position.y * GameView.cellsize );
		if ( act == Behave.ROTATE )
			anim = new Rotate( 0 );
		else anim = null;
	}

	@Override
	public boolean draw( GL2 gl )
	{
		//if ( indices == null || vertices == null ) throw new RuntimeException( "indices or vertices not initialised" );
		gl.glPushMatrix();
		gl.glTranslatef( position.x + (GameView.cellsize >> 2), position.y  + (GameView.cellsize >> 2), 0 );
		anim.modify( gl, position );
		gl.glScalef( GameView.cellsize, GameView.cellsize, GameView.cellsize );
		renderMesh( gl );
		gl.glPopMatrix();
		return true;
	}

	@Override
	public boolean initialise( GL2 gl )
	{
		MeshStore m = MeshStore.instance();
		Mesh mesh = m.getMesh( type );
		vertices = mesh.getVertices();
		indices = mesh.getIndices();
		return true;
	}
	
	private void renderMesh( GL2 gl )
	{
		for ( int[] i: indices )
		{
			if ( i.length == 4 )
			{
				gl.glBegin( GL2.GL_QUADS );
				gl.glColor3f( .0f, .0f, .0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glVertex3fv( vertices.get( i[2] ), 0 );
				gl.glVertex3fv( vertices.get( i[3] ), 0 );
				gl.glEnd();
				gl.glBegin( GL2.GL_LINE_LOOP );
				gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glVertex3fv( vertices.get( i[2] ), 0 );
				gl.glVertex3fv( vertices.get( i[3] ), 0 );
				gl.glEnd();
			}
			if ( i.length == 3 )
			{
				gl.glBegin( GL2.GL_TRIANGLES );
				gl.glColor3f( .0f, .0f, .0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glVertex3fv( vertices.get( i[2] ), 0 );
				gl.glEnd();
				gl.glBegin( GL2.GL_LINE_LOOP );
				gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glVertex3fv( vertices.get( i[2] ), 0 );
				gl.glEnd();
			}
			if ( i.length == 2 )
			{
				gl.glBegin( GL2.GL_LINE );
				gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glEnd();
			}
			if ( i.length == 1 )
			{
				gl.glBegin( GL2.GL_POINTS );
				gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glEnd();
			}
		}
	}


	@Override
	public void clean( GL2 gl )
	{
		
	}

	@Override
	public boolean isDynamic()
	{
		return true;
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		
	}

}
