package ui.components;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

public class GameListener implements KeyListener, MouseListener
{
	public float			direction = 0f; // accumulated direction
	private boolean			keyUpdate = false;
	private boolean			wKey = false, aKey = false, sKey = false
						  , dKey = false, ctrl = false;
	private final float		speed = 0.5f; // forward reverse constant
	private final float		turnSpeed = 0.05f; //
	private Point2D.Float	position; // accumulated position
	private Point			click = null;
	private GameScene	map;

	public void setKeyUpdate( boolean keyUpdate )
	{
		this.keyUpdate = keyUpdate;
	}

	public boolean isKeyUpdate()
	{
		return keyUpdate;
	}

	// REQUIRES: x < map.xlimit and y < map.ylimit
	public void setLocation( float x, float y )
	{
		this.position.setLocation( x, y );
	}

	public GameListener( Point2D.Float position, float direction, GameScene map)
	{
		this.position = new Point2D.Float( position.x, position.y );
		this.direction = direction;
		this.map = map;
	}

	public float getNewX()
	{
		return position.x;
	}
	public float getNewY()
	{
		return position.y;
	}
	public float getDirection()
	{
		return direction;
	}

	@Override
	public void keyTyped( KeyEvent arg0 ){}

	@Override
	public void keyReleased( KeyEvent key )
	{
		if ( key.getKeyCode() == KeyEvent.VK_W )
		{
			wKey = false;
		}
		else if ( key.getKeyCode() == KeyEvent.VK_S )
		{
			sKey = false;
		}
		else if ( key.getKeyCode() == KeyEvent.VK_A )
		{
			aKey = false;
		}
		else if ( key.getKeyCode() == KeyEvent.VK_D )
		{
			dKey = false;
		}
		if ( !key.isControlDown() ) ctrl = false;
	}

	@Override
	public void keyPressed( KeyEvent key )
	{
		if ( key.getKeyCode() == KeyEvent.VK_W || (!keyUpdate && wKey) )
		{
			movePos( (float) (direction - Math.PI) );
			wKey = true;
		}
		if ( key.getKeyCode() == KeyEvent.VK_S || (!keyUpdate && sKey) )
		{
			movePos( direction );
			sKey = true;
		}
		if ( key.getKeyCode() == KeyEvent.VK_A || (!keyUpdate && aKey) )
		{
			if ( key.isControlDown() )
			{
				movePos( (float) ( direction + 0.5f * Math.PI ) );
				ctrl = true;
			}
			else
				addToDirection( -turnSpeed );
			aKey = true;
		}
		if ( key.getKeyCode() == KeyEvent.VK_D || (!keyUpdate && dKey) )
		{
			if ( key.isControlDown() )
			{
				movePos( (float) ( direction - 0.5f * Math.PI ) );
				ctrl = true;
			}
			else
				addToDirection( turnSpeed );
			dKey = true;
		}
		keyUpdate = true;
	}

/**
 * keep moving and turning even if there are no key press or release events,
 * but only if keys have not been released and there has not been a recent
 * update.
 */
	public void update()
	{
		if ( !keyUpdate )
    	{
	    	if ( wKey )
			{
	    		movePos( (float) (direction - Math.PI) );
			}
			if ( sKey )
			{
				movePos( direction );
			}

			if ( aKey )
			{
				if ( ctrl ) movePos( (float) (direction + 0.5f * Math.PI) );
				else addToDirection( -turnSpeed );
			}
			if ( dKey )
			{
				if ( ctrl ) movePos( (float) (direction - 0.5f * Math.PI) );
				else addToDirection( turnSpeed );
			}
		}
	}
	
	public Point getClick()
	{
		Point p = click;
		click = null;
		return p;
	}
	
	public void reset()
	{
		keyUpdate = false;
		wKey = false;
		aKey = false;
		sKey = false;
		dKey = false;
		ctrl = false;
	}

/**
 * Buffer a new player position to be updated when display() is called.
 * @param backward
 */
	private void movePos( float dir )
	{
		float newx = (float) ( position.x
				+ Math.sin( dir ) * speed );
		float newy = (float) ( position.y
				+ Math.cos( dir ) * speed );
		boolean xcross = (int)(newx / GameView.cellsize)
				!= (int)(position.x / GameView.cellsize);
		boolean ycross = (int)(newy / GameView.cellsize) 
				!= (int)(position.y / GameView.cellsize);
		// collision detection
		if ( ( xcross || ycross )
				&& map.isCollidable( newx, newy ) )
		{
			if ( xcross && !ycross )
				position.setLocation( position.x, newy );
			else if ( ycross && !xcross )
				position.setLocation( newx, position.y );
		}
		else position.setLocation( newx, newy );
	}

	private void addToDirection( float f )
	{
		direction += f;
		direction %= GameView.PI2;
	}

	@Override
	public void mouseClicked( MouseEvent e )
	{
		System.out.println("Mouse Clicked");
		click = e.getPoint();
	}

	@Override
	public void mousePressed( MouseEvent e ){}

	@Override
	public void mouseReleased( MouseEvent e ){}

	@Override
	public void mouseEntered( MouseEvent e ){}

	@Override
	public void mouseExited( MouseEvent e ){}
}