package customExceptions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import services.ConnectionClass;

public class LanguageAlreadyExistException extends Exception{
	
	public static int getLanguageByName(String languageName) throws SQLException,LanguageAlreadyExistException{
		Connection con = ConnectionClass.getConnectionMethod();
		String query="select * from language where languagename like ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1,"%"+languageName+"%");
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return rs.getInt(1);
		}
		else {
			return 0;		
		}
	}
	
}
