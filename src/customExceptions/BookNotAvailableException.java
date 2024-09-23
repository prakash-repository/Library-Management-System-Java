package customExceptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.ConnectionClass;

public class BookNotAvailableException extends Exception{
		public static int bookAvailableOrNot(int bookid) throws SQLException, BookNotAvailableException {
			Connection con = ConnectionClass.getConnectionMethod();
			PreparedStatement ps =con.prepareStatement("select * from book where bookid=?");
			ps.setInt(1, bookid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			else {
				return 0;
			}
			
		}
}
