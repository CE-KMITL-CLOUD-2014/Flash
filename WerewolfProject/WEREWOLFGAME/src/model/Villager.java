package model;

public class Villager {
	private String username;
	private int votedPoint;
	private String status;
	private String votedName;
	private String career;

	public Villager(String username, String career, String status, String vote,
			int votedPoint) {
		setCareer(career);
		setStatus(status);
		setUserName(username);
		setVotedName(vote);
		setVotedPoint(votedPoint);
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setVotedPoint(int votedPoint) {
		this.votedPoint = votedPoint;
	}

	public int getVotedPoint() {
		return votedPoint;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setVotedName(String votedName) {
		this.votedName = votedName;
	}

	public String getVotedName() {
		return votedName;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getCareer() {
		return career;
	}
}
