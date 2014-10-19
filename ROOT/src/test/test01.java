//import static java.lang.System.*;
package test;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;



public class test01 {
	static int time = 0;
	static Timer timer;
	/*public static void main(String[] args) {
		
		System.out.println("a");
		new JFrame().setVisible( true );
	    ActionListener actionListener = new ActionListener() {
	            public void actionPerformed(ActionEvent actionEvent) {   //timer 3 sec
	                System.out.println( time++ );
	                //timer.stop();
	                if(time == 10){
	                	timer.stop();
	                //	timer.
	                }
	            }
	    };
	    timer = new Timer( 1000, actionListener );

	    timer.start();
	}
}*/
	public static void main(String[] args){
		User[] testUser = new User[6];
		for(int i = 0 ; i < 6 ;i++)
		testUser[i] = new User();
		villager a = new villager(1234);
		//System.out.println(testUser);
		//System.out.println(a);
		testUser[0].IDnum = 1234;
		testUser[1].IDnum = 2345;
		testUser[2].IDnum = 3456;
		testUser[3].IDnum = 4567;
		testUser[4].IDnum = 5678;
		testUser[5].IDnum = 6789;
		
		GameRoom testRoom = new GameRoom();
		testRoom.join(testUser[0]);
		//System.out.println(testUser[0]);
		//System.out.println(testRoom.user[0]);
	}
}