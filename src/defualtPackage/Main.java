package defualtPackage;
import java.sql.SQLException;
import services.OtherServices;
public class Main {
	public static void main(String[] args){
			OtherServices otherservice = new OtherServices();
			try {
				otherservice.indexMethod();
			} catch (SQLException e) {
				System.out.println(e);
			}
	}
}
