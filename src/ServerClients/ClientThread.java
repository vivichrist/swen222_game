/**
 * 
 */
package ServerClients;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author  Zhaojiang Chang
 *
 */
public class ClientThread extends Thread implements KeyListener {
	BufferedReader brServer = null;
	PrintStream ps =null;
	public ClientThread(BufferedReader brServer, PrintStream ps) {
		// TODO Auto-generated constructor stub
		this.brServer = brServer;
		this.ps = ps;
	}
	public void run(){
		try {
			String line = null;
			System.out.println("connect");
			while((line = brServer.readLine())!=null){
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("exception brServer null");
			e.printStackTrace();
		}
		finally{ 
		try{
			if(brServer!=null)
				brServer.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_RIGHT) {													
			ps.write(3);
			//totalSent += 4;
		} else if(code == KeyEvent.VK_LEFT) {				
			ps.write(4);				//totalSent += 4;
		} else if(code == KeyEvent.VK_UP) {				
			ps.write(1);				//totalSent += 4;
		} else if(code == KeyEvent.VK_DOWN) {						
			ps.write(2);				//totalSent += 4;
		}
		ps.flush();

		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
