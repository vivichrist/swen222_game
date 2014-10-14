package window.components;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ServerClients.Timers;

public class CountDown extends JPanel
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private int countDownGame = 240;//4mins in seconds
	
	public CountDown()
	{
		super();
		this.paint( getGraphics() );
	}
	
	public void run()
	{
		long currentTime= System.currentTimeMillis();//current system 
		long i=System.currentTimeMillis();
		long temp=currentTime;//assign currentTime to temp
		int gameCycle = 10000;//4mins in milliseconds
		
		while (i-currentTime-2000<gameCycle)
		{     
			if ((i-temp)== 1000 )
			{
				this.paint( getGraphics() );
				countDownGame = (int) (gameCycle/1000-(i-currentTime)/1000);
				temp+=1000;
			}
			i=System.currentTimeMillis();
		}
	}

	@Override
	
	public Dimension getPreferredSize()
	{
		// TODO Auto-generated method stub
		return new Dimension( 50, 50 );
	}

	@Override
	protected void paintComponent( Graphics g )
	{
		// TODO Auto-generated method stub
		super.paintComponent( g );
		g.drawString( (countDownGame / 60) + ":" + (countDownGame % 60), 10, 10 );
	}
	
	public static void main (String[]arg)
	{
		JFrame frame = new JFrame();
		CountDown cd = new CountDown();
		frame.add( cd );
		frame.setVisible( true );
		cd.run();
		cd.setVisible( true );
	}
}
