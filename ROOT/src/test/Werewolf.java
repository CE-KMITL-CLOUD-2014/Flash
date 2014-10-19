package test;

public class Werewolf extends villager{
	public Werewolf(int IDname) {
		super(IDname);
		// TODO Auto-generated constructor stub
		this.IDname = IDname;
	}

	public void kill(villager vi){
		vi.status = "die";
	}
}
