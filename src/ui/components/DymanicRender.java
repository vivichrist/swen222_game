package ui.components;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

import javax.media.opengl.GL2;

import world.components.CellType;

public class DymanicRender implements GraphicalObject
{
	private Point2D.Float	position;
	private float[]			meshColor, selectColor;
	private CellType		type;
	private Behavior		anim;
	private List<float[]>	vertices;
	private List<int[]>		indices;
	private boolean			xaligned = false;

	public DymanicRender( CellType type, Behave act, Point position
			, boolean xaligned, Color meshColor )
	{
		this.meshColor = meshColor.getRGBColorComponents( null );
		this.selectColor = Color.BLACK.getRGBColorComponents( null );
		this.type = type;
		if ( type.ordinal() > CellType.OUTOFBOUNDS.ordinal()
				&& type.ordinal() < CellType.CHEST.ordinal() )
			this.position = new Point2D.Float(
					  position.x * GameView.cellsize - (GameView.cellsize/2f)
					, position.y * GameView.cellsize + (GameView.cellsize/2f) );
		else
			this.position = new Point2D.Float(
				  position.x * GameView.cellsize
				, position.y * GameView.cellsize );
		this.xaligned = xaligned;
		switch ( act )
		{
		case ROTATE: anim = new Rotate( 0 ); break;
		case OPEN_CLOSE: anim = new OpenClose(); break;
		default: anim = null; break;
		}
	}

	@Override
	public boolean draw( GL2 gl )
	{
		if ( indices == null || vertices == null )
		{
			System.out.println( "Empty Vertices and Indices:" + type );
			System.exit( 1 );
		}
		gl.glPushMatrix();
		gl.glTranslatef( position.x, position.y, 0 );
		if ( !xaligned )
		{
			gl.glTranslatef( GameView.cellsize, 0f, 0f );
			gl.glRotatef( 90.0f, 0f, 0f, 1.f );
		}
		if ( anim != null ) anim.modify( gl, position );
		gl.glScalef( GameView.cellsize, GameView.cellsize, GameView.cellsize );
		renderMesh( gl );
		gl.glPopMatrix();
		return true;
	}

	public boolean collide()
	{
		if ( anim == null ) return false;
		return anim.activate();
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
			switch ( i.length )
			{
			case 4:
				gl.glBegin( GL2.GL_QUADS );
				gl.glColor3fv( selectColor, 0 );
				//gl.glColor3f( .0f, .0f, .0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glVertex3fv( vertices.get( i[2] ), 0 );
				gl.glVertex3fv( vertices.get( i[3] ), 0 );
				gl.glEnd();
				gl.glBegin( GL2.GL_LINE_LOOP );
				gl.glColor3fv( meshColor ,0 );
				// gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glVertex3fv( vertices.get( i[2] ), 0 );
				gl.glVertex3fv( vertices.get( i[3] ), 0 );
				gl.glEnd();
				break;
			case 3:
				gl.glBegin( GL2.GL_TRIANGLES );
				// gl.glColor3f( .0f, .0f, .0f );
				gl.glColor3fv( selectColor, 0 );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glVertex3fv( vertices.get( i[2] ), 0 );
				gl.glEnd();
				gl.glBegin( GL2.GL_LINE_LOOP );
				// gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glColor3fv( meshColor ,0 );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glVertex3fv( vertices.get( i[2] ), 0 );
				gl.glEnd();
				break;
			case 2:
				gl.glBegin( GL2.GL_LINES );
				// gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glColor3fv( meshColor ,0 );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glVertex3fv( vertices.get( i[1] ), 0 );
				gl.glEnd();
				break;
			case 1:
				gl.glBegin( GL2.GL_POINTS );
				// gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glColor3fv( meshColor ,0 );
				gl.glVertex3fv( vertices.get( i[0] ), 0 );
				gl.glEnd();
				break;
			default: // n-gons
				gl.glBegin( GL2.GL_TRIANGLES );
				gl.glColor3fv( selectColor, 0 );
				// gl.glColor3f( .0f, .0f, .0f );
				// make triangle fan out of polygon
				int end = i.length - 1;
				for ( int j = 0; j < end - 1; ++j )
				{
					gl.glVertex3fv( vertices.get( end ), 0 );
					gl.glVertex3fv( vertices.get( i[j] ), 0 );
					gl.glVertex3fv( vertices.get( i[j + 1] ), 0 );
				}
				gl.glEnd();
				gl.glBegin( GL2.GL_LINE_LOOP );
				// gl.glColor3f( 1.0f, 1.0f, 1.0f );
				gl.glColor3fv( meshColor ,0 );
				for ( int j: i )
					gl.glVertex3fv( vertices.get( j ), 0 );
				gl.glEnd();
				break;
			}
		}
	}


	@Override
	public void clean( GL2 gl )
	{

	}

//	public Color getSelectColor()
//	{
//		return new Color( selectColor.getRGB() );
//	}
//
//	public void setSelectColor( Color selectColor )
//	{
//		this.selectColor = new Color( selectColor.getRGB() );
//	}

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
