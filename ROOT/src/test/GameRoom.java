package test;

import java.util.ArrayList;

public class GameRoom {
	User[] user ;
	PlayGame play; 
	//Carreer ca;delete change design
/*	public static void main(String[] args){
		play.start();
	}*/
	GameRoom(){
		init();
	}
	public void init(){
		user = new User[6];
		for(int i = 0 ; i< 6;i++)
			user[i] = null;
		//ca = new Carreer(); delete change design
		play = new PlayGame(user);
		
	}
	public void join(User temp){
		if(!full()) user[indexEmpty()] = temp;
	}
	public boolean full(){
		for(int i = 0 ; i < 6 ; i++){
			if(user[i]==null) return false;
		}
		return true;
	}
	public int indexEmpty(){
		for(int i = 0 ; i < 6 ; i++){
			if(this.user[i]==null) return i;
		}
		return 0;
	}
	public void exit(User userExit){
		for(int i = 0 ; i < 6 ; i++){
			if(user[i]==userExit) user[i] =  null;
		}
	}
}
