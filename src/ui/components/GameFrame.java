package ui.components;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * A minimal program that draws with JOGL in a Swing JFrame using the AWT GLJPanel.
 *
 * @author Wade Walker
 */
@SuppressWarnings( "serial" )
public class GameFrame extends JFrame 
{
	private ArrayList<GraphicalObject> toDraw = new ArrayList<GraphicalObject>();
	public static final int		cellsize = 10;
	public static final double
					PI2 = Math.PI * 2;
	public static final float
					DEG = 180.0f / (float) Math.PI;
	private float	newdir, direction = newdir = 0.0f ; // newdir is a more current alias to direction
	private float	speed = 0.5f; // forward reverse constant
	private boolean keyUpdate = false, w = false, a = false, s = false, d = false;
	private Point2D.Float position, newpos = 
			new Point2D.Float();// newpos is a buffer alias to position
	private GameCollision map = new GameCollision();
	public final Point	extents = map.mapsize();
	private float	turnSpeed = 0.05f;
	
    public GameFrame( String str )
    {
    	super(str);
    	GLProfile.initSingleton();
    	GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        GLJPanel gameView = new GLJPanel( glcapabilities );
        position = new Point2D.Float( 
				(extents.x/ 2.0f) * cellsize, (extents.y / 2.0f) * cellsize );
        newpos.setLocation( position );
        gameView.addGLEventListener( new GLEventListener() {
            
            @Override
            public void reshape( GLAutoDrawable glautodrawable
            		, int x, int y, int width, int height ) {
            	GL2 gl2 = glautodrawable.getGL().getGL2();
            	GLU glu = GLU.createGLU( gl2 );
            	gl2.glViewport( 0, 0, width, height );
            	gl2.glClearColor( 0.0f, 0.0f, 0.01f, 0.0f );
            	gl2.glMatrixMode( GL2.GL_PROJECTION );
            	gl2.glLoadIdentity();
            	glu.gluPerspective( 65.0f, (float)width/(float)height, 1.0f, 1000.0f );
            }
            
            @Override
            public void init( GLAutoDrawable glautodrawable ) {
            	glautodrawable.getGL().setSwapInterval(1);
            	GL2 gl2 = glautodrawable.getGL().getGL2();
            	gl2.glLineWidth( 3.0f );
            	gl2.glEnable( GL.GL_LINE_SMOOTH );
            	gl2.glHint( GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
            	gl2.glHint( GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST );
            	// Antialias lines
            	gl2.glEnable( GL.GL_BLEND );
            	gl2.glPointSize( 10.0f );
            	gl2.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA );
            	gl2.glClearColor( 0.0f, 0.0f, 0.01f, 0.0f );
            	gl2.glEnable( GL.GL_DEPTH_TEST );
            	gl2.glDepthFunc(GL.GL_LEQUAL);
                gl2.glShadeModel(GL2.GL_SMOOTH);
            	toDraw.add( new Plane( extents, 0, cellsize ) );
            	toDraw.add( new Plane( extents, 2, cellsize ) );
            	map.addSurrounds( toDraw, cellsize );
            	for( GraphicalObject go: toDraw )
                	go.makeDisplayList( gl2 );
            }
            
            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            	GL2 gl = glautodrawable.getGL().getGL2();
            	for ( GraphicalObject g: toDraw )
            		g.clean( gl );
            }
            
            @Override
            public void display( GLAutoDrawable glautodrawable ) {
            	update();
            	GL2 gl2 = glautodrawable.getGL().getGL2();
            	GLU glu = GLU.createGLU( gl2 );
            	gl2.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
                // Draw some rotating stuff
            	gl2.glMatrixMode( GL2.GL_MODELVIEW );
            	gl2.glLoadIdentity();

            	glu.gluLookAt( 0.0f, 0.0f, 0.0f   // position
            				 , 0.0f,-1.0f, 0.0f   // direction
            				 , 0.0f, 0.0f, 1.0f );// up
            	gl2.glRotatef( direction, 0 ,0 ,1.0f );
            	gl2.glTranslatef( -position.x, -position.y, -10.0f );
                render( gl2 );
            }
        });
        gameView.addKeyListener( new KeyListener()
		{
			@Override
			public void keyTyped( KeyEvent arg0 )
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased( KeyEvent key )
			{
				if ( key.getKeyCode() == KeyEvent.VK_W )
				{
					w = false;
				}
				else if ( key.getKeyCode() == KeyEvent.VK_S )
				{
					s = false;
				}
				else if ( key.getKeyCode() == KeyEvent.VK_A )
				{
					a = false;
				}
				else if ( key.getKeyCode() == KeyEvent.VK_D )
				{
					d = false;
				}
			}
			
			@Override
			public void keyPressed( KeyEvent key )
			{
				if ( key.getKeyCode() == KeyEvent.VK_W || (!keyUpdate && w) )
				{
					movePos( true );
				}
				if ( key.getKeyCode() == KeyEvent.VK_S || (!keyUpdate && s) )
				{
					movePos( false );
				}
				if ( key.getKeyCode() == KeyEvent.VK_A || (!keyUpdate && a) )
				{
					newdir -= turnSpeed;
					newdir %= PI2;
					keyUpdate = true;
					a = true;
				}
				if ( key.getKeyCode() == KeyEvent.VK_D || (!keyUpdate && d) )
				{
					newdir += turnSpeed;
					newdir %= PI2;
					keyUpdate = true;
					d = true;
				}
			}

			private void movePos(boolean b)
			{
				int invert = b ? -1 : 1;
				float newx = (float) ( newpos.x
						+ invert * Math.sin( newdir ) * speed );
				float newy = (float) ( newpos.y
						+ invert * Math.cos( newdir ) * speed );
				// TODO: collision detection
				if ( map.isCollidable( newx, newy ) )
				{
					if ( !map.isCollidable( newpos.x, newy ) )
						newpos.setLocation( newpos.x, newy );
					if ( !map.isCollidable( newx, newpos.y ) )
						newpos.setLocation( newx, newpos.y );
				}
				else newpos.setLocation( newx, newy );
				keyUpdate = true;
				if ( b ) w = true;
				else s = true;
			}
		} );
        
        addWindowListener( new WindowAdapter()
        {
            public void windowClosing( WindowEvent windowevent )
            {
                dispose();
                System.exit( 0 );
            }
        });
        
        FPSAnimator animator = new FPSAnimator(60);
        animator.add( gameView );
        animator.start();

        getContentPane().add( gameView, BorderLayout.CENTER );
        setSize( 800, 600 );
        setVisible( true );
    }
    
    private void update()
	{
    	if ( !keyUpdate )
    	{
	    	if ( w )
			{
	    		float newx = (float) ( newpos.x
						- Math.sin( newdir ) * speed );
				float newy = (float) ( newpos.y
						- Math.cos( newdir ) * speed );
				// TODO: collision detection
				if ( map.isCollidable( newx, newy ) )
				{
					if ( !map.isCollidable( newpos.x, newy ) )
						newpos.setLocation( newpos.x, newy );
					if ( !map.isCollidable( newx, newpos.y ) )
						newpos.setLocation( newx, newpos.y );
				}
				else newpos.setLocation( newx, newy );
			}
			if ( s )
			{
				float newx = (float) ( newpos.x
						+ Math.sin( newdir ) * speed );
				float newy = (float) ( newpos.y
						+ Math.cos( newdir ) * speed );
				// TODO: collision detection
				if ( map.isCollidable( newx, newy ) )
				{
					if ( !map.isCollidable( newpos.x, newy ) )
						newpos.setLocation( newpos.x, newy );
					if ( !map.isCollidable( newx, newpos.y ) )
						newpos.setLocation( newx, newpos.y );
				}
				else newpos.setLocation( newx, newy );
			}
			if ( a )
			{
				newdir -= turnSpeed;
				newdir %= PI2;
			}
			if ( d )
			{
				newdir += turnSpeed;
				newdir %= PI2;
			}
		}
    	direction = newdir * DEG;
    	position.setLocation( newpos );
    	keyUpdate = false;
    }

	private void render( GL2 gl2 )
	{
        for( GraphicalObject go: toDraw )
        	go.draw( gl2 );
    }

	public static void main( String [] args ) {
		new GameFrame( "Swing GLJPanel" );
	}
}