package services;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import customExceptions.BookNotAvailableException;
import customExceptions.RatingIsNotValidException;
import entities.Rating;
public class RatingService extends Rating {
	
	public RatingService() {
	}
	
	public RatingService(int userId, int bookId, float rating) {
		super(userId, bookId, rating);
	}

	public void rating(int uid,String name) throws SQLException{
		//Scanner Class Object
		Scanner scanner = new Scanner(System.in);
		while(true) {
		System.out.println("----------------------------------");
		System.out.println("|                                |");
		System.out.println("|          Rating Page           |");
		System.out.println("|                                |");
		System.out.println("----------------------------------");
		try {
		System.out.println("Enter any above Book id for Give Rate");
		int	bookId = BookNotAvailableException.bookAvailableOrNot(scanner.nextInt());
		if(bookId==0) {
			throw new BookNotAvailableException();
		}
		System.out.println("Enter Rating(1 to 5)");
		float rating = RatingIsNotValidException.ratingIsValidOrNot(scanner.nextFloat());
		if(rating==0) {
			throw new RatingIsNotValidException();
		}
		RatingService ratingservice = new RatingService(uid,bookId,rating);
		if(!ratedOrNot(uid,bookId)) {
		Connection con = ConnectionClass.getConnectionMethod();
		final String query="insert into ratings values(?,?,?)";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, ratingservice.getUserId());
		ps.setInt(2, ratingservice.getBookId());
		ps.setFloat(3,ratingservice.getRating());
		if(ps.executeUpdate()>0) {
			System.out.println("Your Rating Added.");
			BookService bookservice = new BookService();
			bookservice.userViewBooks(uid, name);
			break;
		}
		else {
			System.out.println("Not Added.Please check if Your Input Values correct or not.");
		}
		}
		else {
			Connection con = ConnectionClass.getConnectionMethod();
			final String query="update ratings set rating = ? where userid=? and bookid=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setFloat(1, rating);
			ps.setInt(2, uid);
			ps.setInt(3, bookId);
			if(ps.executeUpdate()>0) {
				System.out.println("Your New Rating Added.");
				BookService bookservice = new BookService();
				bookservice.userViewBooks(uid, name);
				break;
			}
			else {
				System.out.println("Not Added.Please check if Your Input Values correct or not.");
			}
		}
		}catch (BookNotAvailableException e) {
			System.out.println("Invalid BookId. Please Enter a Valid Bookid.");
		}catch(InputMismatchException e) {
			System.out.println("Invalid Input! Please Enter a Valid Input.");
			scanner.next();
		} catch (RatingIsNotValidException e) {
			System.out.println("Invalid Rating. Please Enter a rating Between 1 and 5.");
		}		
	}
	scanner.close();
}
	public boolean ratedOrNot(int uid,int bookId) throws SQLException {
		Connection con = ConnectionClass.getConnectionMethod();
		final String query="select * from ratings where userid=? and bookid=?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, uid);
		ps.setInt(2, bookId);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	
	
	
	
	
}
