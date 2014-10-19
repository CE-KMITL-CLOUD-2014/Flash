package test;

public class villager {
	final int INITVOTEDPOINT = 0;
	final String INITSTATUS = "Alive";
	private int votedPoint;
	protected int IDname;
	private int score;
	protected String status ;
	public villager(int IDname ) {
		// TODO Auto-generated constructor stub
		this.IDname = IDname;
		status = INITSTATUS;
		votedPoint = INITVOTEDPOINT;
	}
	public void addVotedPoint(){
		votedPoint++;
	}
	public void vote(villager vi){
		vi.addVotedPoint();
	}
}
