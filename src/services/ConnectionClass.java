package services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionClass {
//	private static final String className="com.mysql.cj.jdbc.Driver";
	private static final String url="jdbc:mysql://localhost/librarymanagementsystem";
	private static final String userName ="root";
	private static final String passWord="prakash_bsc.cs2002";
	
	public static Connection getConnectionMethod() throws SQLException {
		return DriverManager.getConnection(url,userName,passWord);
	}
}

