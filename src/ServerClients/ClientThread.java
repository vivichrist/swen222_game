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
			ps.println(ServerClientProtocal.KeyListeners+3+ServerClientProtocal.KeyListeners);
			//totalSent += 4;
		} else if(code == KeyEvent.VK_LEFT) {				
			ps.println(ServerClientProtocal.KeyListeners+4+ServerClientProtocal.KeyListeners);
		} else if(code == KeyEvent.VK_UP) {
			System.out.println("moveUp");

			ps.println(ServerClientProtocal.KeyListeners+1+ServerClientProtocal.KeyListeners);
		} else if(code == KeyEvent.VK_DOWN) {						
			ps.println(ServerClientProtocal.KeyListeners+2+ServerClientProtocal.KeyListeners);
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
