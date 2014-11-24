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

	public GameRoom selectDataInGame(int idRoom) {
		GameRoom gameRoom = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "SELECT * FROM dbo.dataInGame WHERE idRoom=" + idRoom;
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

	public int updatePlayer(String username, String creater) {// join game
		int idRoom = 0;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "";
			for (int i = 2; i <= 6; i++) {
				String SQLfind = "SELECT player" + i
						+ " FROM dbo.dataInGame WHERE creater='" + creater
						+ "'";
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (rs.getString(1) == null) {
					// b=rs.getString(1);
					SQL = "UPDATE dataInGame SET player" + i + " = '"
							+ username + "'" + " WHERE creater='" + creater
							+ "'";
					break;
				}
			}
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			addPlayerInRoom(creater);
			SQL = "SELECT idRoom FROM dbo.dataInGame WHERE creater='" + creater
					+ "'";
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			rs.next();
			idRoom = rs.getInt(1);
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
		return idRoom;
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
			String SQL = "INSERT INTO dataInGame (idRoom, player1, creater ,numPlayer,state) VALUES("
					+ max
					+ ", '"
					+ username
					+ "', '"
					+ username
					+ "', 1, 'standby')";
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

	public void deleteRoom(int idRoom) {
		try {
			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "DELETE FROM dataInGame WHERE idRoom=" + idRoom;
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

	public void deletePlayer(String username, int idRoom) {// out room
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "";
			for (int i = 2; i <= 6; i++) {
				String SQLfind = "SELECT player" + i
						+ " FROM dbo.dataInGame WHERE idRoom=" + idRoom;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (!(rs.getString(1) == null)) {
					if (rs.getString(1).equals(username)) {
						// b=rs.getString(1);
						SQL = "UPDATE dataInGame SET player" + i + " = " + null
								+ " WHERE idRoom=" + idRoom;
						break;
					}
				}
			}
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			decrePlayerInRoom(idRoom);
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

	public String startGameInit(int idRoom, int wereWolfNum) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "";
			for (int i = 2; i <= 6; i++) {
				String SQLfind = "SELECT player" + i
						+ " FROM dbo.dataInGame WHERE idRoom=" + idRoom;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (!(rs.getString(1) == null)) {

				} else
					return "notFull";
			}
			for (int i = 1; i <= 6; i++) {
				if (i == wereWolfNum) {
					SQL = "UPDATE dataInGame SET career" + i
							+ " = 'werewolf' ,status" + i + "='alive' ,votedP"
							+ i + "=0 ,submit" + i + "=0 WHERE idRoom="
							+ idRoom;
					stmt = con.createStatement();
					stmt.executeUpdate(SQL);
				} else {
					SQL = "UPDATE dataInGame SET career" + i
							+ " = 'villager' ,status" + i + "='alive' ,votedP"
							+ i + "=0,submit" + i + "=0 WHERE idRoom=" + idRoom;
					stmt = con.createStatement();
					stmt.executeUpdate(SQL);
				}
			}
			SQL = "UPDATE dataInGame SET state = 'kill' WHERE idRoom = "
					+ idRoom;
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
		return idRoom + "sucsees";
	}

	public void updateKillPlayer(String username, int idRoom) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "";
			for (int i = 1; i <= 6; i++) {
				String SQLfind = "SELECT player" + i
						+ " FROM dbo.dataInGame WHERE idRoom=" + idRoom;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (!(rs.getString(1) == null)) {
					if (rs.getString(1).equals(username)) {
						SQL = "UPDATE dataInGame SET status" + i
								+ " = 'die' WHERE idRoom=" + idRoom;
						updateChangeState(idRoom, "chat");
						break;
					}
				}
			}
			con = DriverManager.getConnection(connectionUrl);
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

	public void updateVotePlayer(String voter, int idRoom, String voted) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "";
			for (int i = 1; i <= 6; i++) {// for update vote villager
				String SQLfind = "SELECT player" + i
						+ " FROM dbo.dataInGame WHERE idRoom=" + idRoom;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (!(rs.getString(1) == null)) {
					if (rs.getString(1).equals(voter)) {
						updateSubmit(voter, idRoom);
						SQL = "UPDATE dataInGame SET vote" + i + " = '" + voted
								+ "' WHERE idRoom=" + idRoom;
						break;
					}
				}
			}
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);

			for (int i = 1; i <= 6; i++) {
				String SQLfind = "SELECT player" + i + ", votedP" + i
						+ " FROM dbo.dataInGame WHERE idRoom=" + idRoom;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (!(rs.getString(1) == null)) {
					if (rs.getString(1).equals(voted)) {
						// b=rs.getString(1);
						SQL = "UPDATE dataInGame SET votedP" + i + " = "
								+ (rs.getInt(2) + 1) + " WHERE idRoom="
								+ idRoom;
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

	public void addPlayerInRoom(int idRoom) {// Increase numPlayer
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "Select numPlayer FROM  dbo.dataInGame WHERE idRoom="
					+ idRoom;
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			rs.next();
			SQL = "UPDATE dataInGame SET numPlayer = " + (rs.getInt(1) + 1)
					+ " WHERE idRoom=" + idRoom;
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

	public void addPlayerInRoom(String creater) {// Increase numPlayer
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "Select numPlayer FROM  dbo.dataInGame WHERE creater='"
					+ creater + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			rs.next();
			SQL = "UPDATE dataInGame SET numPlayer = " + (rs.getInt(1) + 1)
					+ " WHERE creater='" + creater + "'";
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

	public void decrePlayerInRoom(int idRoom) {// Decrease numPlayer
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "Select numPlayer FROM  dbo.dataInGame WHERE idRoom="
					+ idRoom;
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			rs.next();
			SQL = "UPDATE dataInGame SET numPlayer = " + (rs.getInt(1) - 1)
					+ " WHERE idRoom=" + idRoom;
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

	public void updateChangeState(int idRoom, String toState) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "UPDATE dataInGame SET state = '" + toState
					+ "' WHERE idRoom=" + idRoom;
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
			for (int i = 1; i <= 6; i++) {
				SQL = "SELECT votedP" + i
						+ " FROM dbo.dataInGame WHERE idRoom=" + idRoom;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQL);
				rs.next();
				if (max < rs.getInt(1)) {
					max = rs.getInt(1);
					maxNum = i;
				}
			}
			SQL = "UPDATE dataInGame SET status" + maxNum
					+ " ='die' WHERE idRoom=" + idRoom;
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

	public String getState(int idRoom) {
		String state = "";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "SELECT state FROM dbo.dataInGame WHERE idRoom="
					+ idRoom;
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			rs.next();
			state = rs.getString(1);
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
		return state;
	}

	public ArrayList<Integer> getSubmit(int idRoom) {
		ArrayList<Integer> submit = new ArrayList<Integer>();
		String SQL;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			for (int i = 1; i <= 6; i++) {
				SQL = "SELECT submit" + i + ",status" + i
						+ " FROM dbo.dataInGame WHERE idRoom = " + idRoom;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQL);
				rs.next();
				if ((rs.getString(2).equals("die")) || (rs.getInt(1) == 1))
					submit.add(1);
				else
					submit.add(0);
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

		return submit;
	}

	public void resetSubmit(int idRoom) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL;
			for (int i = 1; i <= 6; i++) {
				SQL = "UPDATE dataInGame SET submit" + i + "=0 WHERE idRoom = "
						+ idRoom;
				stmt = con.createStatement();
				stmt.executeUpdate(SQL);
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
	}

	public void updateSubmit(String username, int idRoom) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "";
			for (int i = 1; i <= 6; i++) {
				String SQLfind = "SELECT player" + i
						+ " FROM dbo.dataInGame WHERE idRoom=" + idRoom;
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQLfind);
				rs.next();
				if (!(rs.getString(1) == null)) {
					if (rs.getString(1).equals(username)) {
						SQL = "UPDATE dataInGame SET submit" + i
								+ " = 1 WHERE idRoom=" + idRoom;
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

	public void resetVote(int idRoom) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL;
			for (int i = 1; i <= 6; i++) {
				SQL = "UPDATE dataInGame SET votedP" + i + "=0, vote" + i
						+ " = " + null + " WHERE idRoom = " + idRoom;
				stmt = con.createStatement();
				stmt.executeUpdate(SQL);
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
	}

	public void end(int idRoom, String winner) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			for (int i = 1; i < 6; i++) {
				String SQL = "SELECT player" + i
						+ " FROM dataInGame WHERE career" + i + " = '" + winner
						+ "'";
				stmt = con.createStatement();
				rs = stmt.executeQuery(SQL);
				if ((rs.next())) {
					SQL = "UPDATE Member SET score = score+1 WHERE username='"
							+ rs.getString(1) + "'";
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
	}
}
