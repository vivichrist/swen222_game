/**
 * 
 */
package ServerClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author Zhaojiang Chang
 * socket for individual client  
 */
public class ServerThread extends Thread {
    
    private Socket socket;
    BufferedReader br = null;
    PrintStream ps = null;
    //Constructor a socket to create ServerThread thread
    public ServerThread(Socket socket){
        this.socket = socket;
        
    }
    public void run(){
        try{
            //get input stream from socket
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //get output stream from scoket
            ps = new PrintStream(socket.getOutputStream());
            String line = null;
          
            while((line = br.readLine())!=null){
                String userName = br.readLine();//get client name
                if(Server.clients.containsKey(userName)){
                	System.out.println("Duplicate username!");
                	ps.println(ServerClientProtocal.playerNameRep);
                }
                else {
                	System.out.println("success connected");
                	ps.println(ServerClientProtocal.loginSuccess);
                	Server.clients.put(userName, ps);
                }
            }
            
        }catch(IOException e){
        	Server.clients.removeByValue(ps);
        	System.out.println(Server.clients.size());
        	try{
        		if(br!=null) br.close();
        		if(ps!=null) ps.close();
        		if(socket!=null) socket.close();
        	}catch(IOException ex){
        		ex.printStackTrace();
        	}
        }
    }
    
}
