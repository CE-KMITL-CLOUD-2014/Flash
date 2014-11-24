package JDBC;

import java.sql.*;

import com.microsoft.sqlserver.jdbc.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.MemberModel;

public class JDBCmember {

	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String connectionUrl = ConnecttionString.getConnectStr();

	public MemberModel SQLselectMember(String username) {
		MemberModel member = new MemberModel(0, "a", "b");
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "SELECT * FROM Member WHERE username='" + username+"'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			if(!rs.next()) 
				return new MemberModel(0, "","");
			else
				member = new MemberModel(rs.getInt(1), rs.getString(2),
					rs.getString(3));
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
		return member;
	}
	
	public ArrayList<MemberModel> listMember() {
		ArrayList<MemberModel> member = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "SELECT * FROM dbo.Member";
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			member = new ArrayList<MemberModel>();
			while (rs.next())
				member.add(new MemberModel(rs.getInt(1), rs.getString(2), rs
						.getString(3),rs.getInt(4)));
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
		return member;
	}

	public void updateMember(MemberModel member) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "UPDATE Member SET username = '"
					+ member.getUserName() + "' ,password = '"
					+ member.getPassword() + "' ,score = " + member.getScore()+
					" ,sesID = "+member.getsesID()+ " WHERE ID=" + member.getID();
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

	public void insertMember(MemberModel member) {
		ArrayList<MemberModel> memberlist = listMember();
		int max = 0;
		for(MemberModel mem : memberlist)
			if(mem.getID()>max)
				max = mem.getID();
		max++;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "INSERT INTO Member (ID, username, password) VALUES("
					+ max + ", '"+ member.getUserName() + "', '"+member.getPassword()+"')";
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
