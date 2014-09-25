/**
 * 
 */
package ServerClients;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author  Zhaojiang Chang
 *
 */
public class ClientThread extends Thread {
	BufferedReader brServer = null;
	public ClientThread(BufferedReader brServer) {
		// TODO Auto-generated constructor stub
		this.brServer = brServer;
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

}
