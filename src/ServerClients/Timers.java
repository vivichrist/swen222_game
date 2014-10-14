package ServerClients;

import java.awt.Canvas;

import javax.swing.JPanel;

import window.components.UsefulItemsCanvas;

/**
 * @author  Zhaojiang Chang
 * 
 * four mins count down 
 * this class will call from server class, once clients size >=3 will start timing.
 * */
public class Timers {
	
	int countDownGame = 240;//4mins in seconds
	int countDownPre = 10;//wait 10senconds before start game
	

	
	
	
		public Timers(){
			timerWaiting();
		}
		/**
		 * timerGameStart method is going to count down 4 mins for each client
		 * */
		
		public void timerGameStart(){
			long currentTime= System.currentTimeMillis();//current system 
			long i=System.currentTimeMillis();
			long temp=currentTime;//assign currentTime to temp 
			int gameCycle = 240000;//4mins in milliseconds

		while (i-currentTime-2000<gameCycle)
		{     

			if ((i-temp)== 1000 )
			{
				System.out.println(" you got "+countDownGame+" second to collect all the items");
				countDownGame = (int) (gameCycle/1000-(i-currentTime)/1000);
				temp+=1000;
			}
			i=System.currentTimeMillis();
		}

		//System.out.println("Stopped!!");

	}
		/**
		 * timerWaiting it will wait for 10 second then start the game
		 * 
		 * */
		public void timerWaiting(){
			long currentTime= System.currentTimeMillis();//current system 
			long i=System.currentTimeMillis();
			long temp=currentTime;//assign currentTime to temp 
			int gameCycle = 10000;//10seconds in milliseconds
			
			while (i-currentTime-2000<gameCycle)
			{     

				if ((i-temp)== 1000 )
				{
					System.out.println(" Game will start in "+countDownPre+" second");
					countDownPre = (int) (gameCycle/1000-(i-currentTime)/1000);
					temp+=1000;
				}
				i=System.currentTimeMillis();
			}

			//System.out.println("Stopped!!");

		}
		public int getCountDownGame() {
			return countDownGame;
		}
		public int getCountDownPre() {
			return countDownPre;
		}
		public static void main (String[]arg){
			//Timers t = new Timers();
			//while(t.getCountDownGame()>0){
			//System.out.println(t);
		//	}
		}
}
