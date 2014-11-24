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

public class JDBCchat {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String connectionUrl = ConnecttionString.getConnectStr();
	
	public ArrayList<String> getChat(int idRoom){
		ArrayList<String> chat = new ArrayList<String>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			String SQL = "SELECT * FROM chat WHERE idRoom = " + idRoom;
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			while(rs.next()){
				chat.add(rs.getString(2));
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
		return chat;
	}
	
	public void insertChat(int idRoom, String str){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			int max = 1;
			String SQLid = "SELECT ID FROM chat";
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQLid);
			while(rs.next()){
				if(rs.getInt(1)>max) max = rs.getInt(1);
			}
			String SQL = "INSERT INTO chat (idRoom, talk ,ID) VALUES("+idRoom+", '"+str+"', "+(max+1)+")";
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
