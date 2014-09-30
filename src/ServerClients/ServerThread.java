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
	public Timer timer;
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
				if(line.startsWith(ServerClientProtocal.KeyListeners)&&line.endsWith(ServerClientProtocal.KeyListeners)){
					// read direction event from client.
					int keyCode = Integer.parseInt(getRealMsg(line));
					System.out.println(keyCode);



					switch(keyCode) {
					case 1:
						//board.player(uid).moveUp();
						System.out.println("moveUp");
						break;
					case 2:
						//board.player(uid).moveDown();
						System.out.println("moveDown");

						break;
					case 3:
						//board.player(uid).moveRight();
						System.out.println("moveRight");

						break;
					case 4:
						//board.player(uid).moveLeft();
						System.out.println("moveLeft");

						break;
					}
					ps.flush();
				} 

				// Now, broadcast the state of the board to client
				//byte[] state = board.toByteArray(); 
				//output.writeInt(state.length);
				//output.write(state);
				//
				//Thread.sleep(broadcastClock);


				if(line.startsWith(ServerClientProtocal.playerName)
						&&line.endsWith(ServerClientProtocal.playerName)){
					String userName = getRealMsg(line);;//get client name
					if(Server.clients.containsKey(userName)){
						System.out.println("Duplicate username!");
						ps.println(ServerClientProtocal.playerNameRep);
					}
					else {
						System.out.println("success connected");
						ps.println(ServerClientProtocal.loginSuccess);
						Server.clients.put(userName, ps);
						timer = new Timer();
						timer.timerWaiting();//waiting for all clients join the game
						
						while(timer.getCountDownPre()>0){
							ps.println(timer.getCountDownPre());
						}
						timer.timerGameStart();
						while(timer.getCountDownGame()>0){
							ps.println(timer.getCountDownGame());
						}
					}
				}
				else{
					for(PrintStream clientPs: Server.clients.valueSet()){
						clientPs.println(Server.clients.getKeyByValue(ps)+" say: "+ line);
					}
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
	private String getRealMsg(String line) {
		// TODO Auto-generated method stub
		return line.substring(ServerClientProtocal.PROTOCOL_LEN, line.length() -ServerClientProtocal.PROTOCOL_LEN );
	}

}
