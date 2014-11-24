package JDBC;

public class ConnecttionString {
	private static String connect = "jdbc:sqlserver://rk8ajd6xbw.database.windows.net:1433;database"
			+ "=WerewolfBase;user=deknarog@rk8ajd6xbw;password=WbxvFxxR5501;";

	public static String getConnectStr(){
		return connect;
	}
}
