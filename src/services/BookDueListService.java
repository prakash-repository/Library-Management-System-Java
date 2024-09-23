package services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;
public class BookDueListService {
	
	
	public boolean confirmGetBook(int uid,int bookId,String date) throws SQLException {
		final String query="insert into booksduelist(userid,bookid,duedate) values(?,?,?)";
		Connection con = ConnectionClass.getConnectionMethod();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, uid);
		ps.setInt(2, bookId);
		ps.setString(3,date);
		if(ps.executeUpdate()>0) {
			System.out.println("Thank You for Get a Book.");
			System.out.println("     Have a Nice Day     ");
			return true;
		}
		else {
			return false;
		}
	}
	
	public void getOverDueDateList(int aid,String name) throws SQLException {
		AdminService adminservice = new AdminService();
		Scanner scanner = new Scanner(System.in);
		final String query="select * from user inner join booksduelist on user.uid=booksduelist.userid inner join book on booksduelist.bookid=book.bookid where duedate < now()";
		Connection con = ConnectionClass.getConnectionMethod();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		System.out.println("________________________________________________________________________________________________________________");
		System.out.println("-------------OverDue Users List--------------");
		while(rs.next()) {
	    System.out.println("---------------------------------------------");
		System.out.println("UserName :"+rs.getString(2));
		System.out.println("PhoneNo  :"+rs.getString(6));
		System.out.println("Address  :"+rs.getString(5));
		System.out.println("District :"+rs.getString(7));
		System.out.println("State    :"+rs.getString(8));
		System.out.println("PinCode  :"+rs.getString(9));
		System.out.println("BookName :"+rs.getString(17));
		System.out.println("DueDate  :"+rs.getString(15));
		System.out.println("---------------------------------------------");
		System.out.println("Enter any key to exit:");
		scanner.next();
		scanner.close();
		adminservice.adminHome(aid, name);
		
		}
	}

}
