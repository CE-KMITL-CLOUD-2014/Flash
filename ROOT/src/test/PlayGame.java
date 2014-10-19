package test;

public class PlayGame {
	private Carreer tem;
	private User[] userlist;
	
	public PlayGame(User[] userlist) {
		// TODO Auto-generated constructor stub
		this.userlist = userlist;
	}
	
	
	public void start(){
		Carreer carreer = new Carreer(userlist);
		while(!isWin()){
			Night night = new Night();
			night.killState();
			Day day = new Day();
			day.showWhoDie();
			day.chatAndVoteState();
			day.showWhoVoteWho();
		}
	}
	boolean isWin(){
		return tem.getWerewolfNum() == 0 && tem.getVillagerNum() == 0;
	}
	String getWinner()
	{
		if(tem.getVillagerNum() == 0) return "werewolf";
		else if(tem.getWerewolfNum() == 0) return "villagerNum";
		else return ""; 
	}
}
