package ServerClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
/**
 * @author  Zhaojiang Chang
 * 
 * */
public class Client {
	private static final int SERVER_PORT = 3000;
	private Socket socket;
	private PrintStream ps;
	private BufferedReader brServer;
	private BufferedReader brClient;
	
	public void init(){
		try{
			//initialize input from keyboard
			brClient = new BufferedReader(new InputStreamReader(System.in));//get input from game 
			//connect to server
			socket = new Socket("LocalHost", SERVER_PORT);
			//obtain input and output from socket
			ps = new PrintStream(socket.getOutputStream());
			brServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String tip="";
			//request input input player name
			while(true){
				String userName = JOptionPane.showInputDialog(tip+ " Input player name: ");
				ps.println(ServerClientProtocal.playerName + userName +ServerClientProtocal.playerName);
				String response = brServer.readLine();
				//if name already taken by other player, player require to choice another name
				if(response.equals(ServerClientProtocal.playerNameRep)){
					tip = "name already been used, please choose another name! ";
					continue;
				}
				//if server response success connected, break out loop
				if(response.equals(ServerClientProtocal.loginSuccess)){
					break;
				}
			}
			
		}catch(UnknownHostException ex){
			System.out.println("could not find server, start server before client");
		}catch(IOException e){
			System.out.println("unable to connect to server, redo log in");
			closeAll();
			System.exit(1);
			
		}
		new ClientThread(brServer,ps).start();
		
	}
	private void readAndSend(){
		try{
			String line = null;
		
		while((line = brClient.readLine())!=null){
		  ps.println(line);
			
		}
	}
		catch(IOException ex){
			System.out.println("can not connect, redo the log in");
			closeAll();
			System.exit(1);
		}
	}
	private void closeAll() {
		// TODO Auto-generated method stub
		try{
    		if(brServer!=null) brServer.close();
    		if(brClient!=null) brClient.close();
    		if(ps!=null) ps.close();
    		if(socket!=null) socket.close();
    	}catch(IOException ex){
    		ex.printStackTrace();
    	}
	}
	public static void main(String[]args){
		Client client = new Client();
		client.init();
		client.readAndSend();
	}
}
