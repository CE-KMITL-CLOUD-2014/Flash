package JDBC;

import java.util.ArrayList;

import model.*;

import java.sql.*;

import com.microsoft.sqlserver.jdbc.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCdataRoom {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String connectionUrl = ConnecttionString.getConnectStr();

	public GameRoom selectDataInGame(int roomID) {
		GameRoom gameRoom = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "SELECT * FROM dbo.dataInGame WHERE roomID=" + roomID;
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			rs.next();
			ArrayList<Villager> vi = new ArrayList<Villager>();
			for (int i = 0; i < 6; i++)
				vi.add(new Villager(rs.getString(5 * i + 2), rs
						.getString(5 * i + 3), rs.getString(5 * i + 4), rs
						.getString(5 * i + 5), rs.getInt(5 * i + 6)));
			gameRoom = new GameRoom(vi);
			gameRoom.setRoomID(rs.getInt(1));
			gameRoom.setState(rs.getString(32));
			gameRoom.setCreater(rs.getString(33));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		return gameRoom;
	}

	public int updatePlayer(String username, int roomID) {// join game
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "";
			for (int i = 2; i <= 6; i++) {
				String SQLfind = "SELECT player" + i
						+ " FROM dbo.dataInGame WHERE roomID=" + roomID;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (rs.getString(1) == null) {
					// b=rs.getString(1);
					SQL = "UPDATE dataInGame SET player" + i + " = '"
							+ username + "'" + " WHERE roomID=" + roomID;
					break;
				}
			}
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			addPlayerInRoom(roomID);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		return roomID;
	}

	public int insertRoom(String username) { // create room
		ArrayList<GameRoom> roomlist = roomlist();
		int max = 0;
		for (GameRoom room : roomlist)
			if (room.getRoomID() > max)
				max = room.getRoomID();
		max++;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "INSERT INTO dataInGame (roomID, player1, creater ,numPlayer) VALUES("
					+ max + ", '" + username + "', '" + username + "', 1)";
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		return max;
	}

	public ArrayList<GameRoom> roomlist() {
		ArrayList<GameRoom> roomlist = new ArrayList<GameRoom>();
		GameRoom gameRoom = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "SELECT * FROM dbo.dataInGame";
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				ArrayList<Villager> vi = new ArrayList<Villager>();
				for (int i = 0; i < 6; i++)
					vi.add(new Villager(rs.getString(5 * i + 2), rs
							.getString(5 * i + 3), rs.getString(5 * i + 4), rs
							.getString(5 * i + 5), rs.getInt(5 * i + 6)));
				gameRoom = new GameRoom(vi);
				gameRoom.setRoomID(rs.getInt(1));
				gameRoom.setState(rs.getString(32));
				gameRoom.setCreater(rs.getString(33));
				gameRoom.setNumPlayer(rs.getInt(34));
				roomlist.add(gameRoom);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		return roomlist;
	}

	public void deleteRoom(int roomID) {
		try {
			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "DELETE FROM dataInGame WHERE roomID=" + roomID;
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	public void deletePlayer(String username, int roomID) {// out room
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "";
			for (int i = 2; i <= 6; i++) {
				String SQLfind = "SELECT player" + i
						+ " FROM dbo.dataInGame WHERE roomID=" + roomID;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (!(rs.getString(1) == null)) {
					if (rs.getString(1).equals(username)) {
						// b=rs.getString(1);
						SQL = "UPDATE dataInGame SET player" + i + " = " + null
								+ " WHERE roomID=" + roomID;
						break;
					}
				}
			}
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			decrePlayerInRoom(roomID);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	public String startGameInit(int roomID, int wereWolfNum) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "";
			for (int i = 2; i <= 6; i++) {
				String SQLfind = "SELECT player" + i
						+ " FROM dbo.dataInGame WHERE roomID=" + roomID;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (!(rs.getString(1) == null)) {

				} else
					return "not Full";
			}
			for (int i = 1; i <= 6; i++) {
				if (i == wereWolfNum) {
					SQL = "UPDATE dataInGame SET career" + i
							+ " = 'werewolf' ,status" + i + "='alive' ,votedP"
							+ i + "=0 WHERE roomID=" + roomID;
					stmt = con.createStatement();
					stmt.executeUpdate(SQL);
				} else {
					SQL = "UPDATE dataInGame SET career" + i
							+ " = 'villager' ,status" + i + "='alive' ,votedP"
							+ i + "=0 WHERE roomID=" + roomID;
					stmt = con.createStatement();
					stmt.executeUpdate(SQL);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		return roomID + "sucsees";
	}

	public void updateKillPlayer(String username, int roomID) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "";
			for (int i = 1; i <= 6; i++) {
				String SQLfind = "SELECT player" + i
						+ " FROM dbo.dataInGame WHERE roomID=" + roomID;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (!(rs.getString(1) == null)) {
					if (rs.getString(1).equals(username)) {
						// b=rs.getString(1);
						SQL = "UPDATE dataInGame SET status" + i
								+ " = 'die' WHERE roomID=" + roomID;
						break;
					}
				}
			}
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	public void updateVotePlayer(String voter, int roomID, String voted) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "";
			for (int i = 1; i <= 6; i++) {//for update vote villager
				String SQLfind = "SELECT player" + i
						+ " FROM dbo.dataInGame WHERE roomID=" + roomID;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (!(rs.getString(1) == null)) {
					if (rs.getString(1).equals(voter)) {
						// b=rs.getString(1);
						SQL = "UPDATE dataInGame SET vote" + i + " = '" + voted
								+ "' WHERE roomID=" + roomID;
						break;
					}
				}
			}
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			
			for (int i = 1; i <= 6; i++) {
				String SQLfind = "SELECT player" + i
						+ ", votedP"+i+" FROM dbo.dataInGame WHERE roomID=" + roomID;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (!(rs.getString(1) == null)) {
					if (rs.getString(1).equals(voted)) {
						// b=rs.getString(1);
						SQL = "UPDATE dataInGame SET votedP" + i + " = " + (rs.getInt(2)+1)
								+ " WHERE roomID=" + roomID;
						break;
					}
				}
			}
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}

	}
	
	public void addPlayerInRoom(int roomID){// Increase numPlayer
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "Select numPlayer FROM  dbo.dataInGame WHERE roomID=" + roomID;
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			rs.next();
			SQL = "UPDATE dataInGame SET numPlayer = "+(rs.getInt(1)+1)+" WHERE roomID="+roomID;
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}
	
	public void decrePlayerInRoom(int roomID){//Decrease numPlayer
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "Select numPlayer FROM  dbo.dataInGame WHERE roomID=" + roomID;
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			rs.next();
			SQL = "UPDATE dataInGame SET numPlayer = "+(rs.getInt(1)-1)+" WHERE roomID="+roomID;
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}
	
	public void updateChangeState(int idRoom , String toState)
	{
		try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		con = DriverManager.getConnection(connectionUrl);
		String SQL = "UPDATE dataInGame SET state = '"+toState+"' WHERE roomID="+idRoom;
		stmt = con.createStatement();
		stmt.executeUpdate(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	public void killByVote(int idRoom) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL;
			int max = 0;
			int maxNum = 0;
			for(int i = 1 ; i <= 6 ; i++ ){
				SQL = "SELECT votedP"+i+" dataInGame WHERE idNum="+idRoom;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQL);
				if(max < rs.getInt(1)){
					max = rs.getInt(1);
					maxNum = i ;
				}
			}
			SQL = "UPDATE dataInGame SET status"+maxNum+" ='die' WHERE roomID="+idRoom;
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (rs != null)
					try {
						rs.close();
					} catch (Exception e) {
					}
				if (stmt != null)
					try {
						stmt.close();
					} catch (Exception e) {
					}
				if (con != null)
					try {
						con.close();
					} catch (Exception e) {
					}
			}
	}
}
