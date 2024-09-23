package customExceptions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import services.ConnectionClass;

public class AuthorAlreadyExistException extends Exception{
	public static int getAuthorByName(String authorName) throws SQLException, AuthorAlreadyExistException {
		Connection con = ConnectionClass.getConnectionMethod();
		String query="select * from author where authorname like ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1,"%"+authorName+"%");
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return rs.getInt(1);
		}
		else {
			return 0;
		}
	}
}
