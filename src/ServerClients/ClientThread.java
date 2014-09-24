/**
 * 
 */
package ServerClients;

import java.io.BufferedReader;

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
	

}
