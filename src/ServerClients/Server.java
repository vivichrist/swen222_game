/**
 * 
 */
package ServerClients;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author  Zhaojiang Chang
 *
 */
public class Server {
	private static final int SERVER_PORT = 3000;
	
	public static clientNameMap<String, PrintStream> clients = new clientNameMap<String, PrintStream>();
	public HashMap<String, String>my = new HashMap<String,String>();
	public void init(){
        
        try{
            //create a new serverScoket
            ServerSocket ss = new ServerSocket(SERVER_PORT);
            
            while(true){
                Socket socket = ss.accept();
                new ServerThread(socket).start();
            }
        }catch(IOException e){
            System.out.println("Server start fail, is it port "+ SERVER_PORT+" been used by other server?");
        }
    }
    public static void main(String[] args){
        Server server = new Server();
        server.init();
    }
}
