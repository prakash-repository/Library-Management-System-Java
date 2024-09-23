package services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import entities.Admin;

public class AdminService extends Admin{
	
	public void adminLogin() throws SQLException {
//      create object for  Console class
//		Console console = System.console();
		System.out.println("________________________________________________________________________________________________________________");
		System.out.println("---------------------------");
		System.out.println("|                         |");
		System.out.println("|       WELCOME TO        |");
		System.out.println("|    LIBRARY MANAGEMENT   |");
		System.out.println("|         SYSTEM          |");
		System.out.println("|                         |");
		System.out.println("|      Admin  Login       |");
		System.out.println("|          Page           |");
		System.out.println("|      ____________       |");
		System.out.println("|                         |");
		System.out.println("---------------------------");
		while(true) {//loop starts
			//create object for Scanner Class
			Scanner scanner=new Scanner(System.in);
			
		System.out.println("Enter a UserName:");
		//Object creation for AdminrService class:
		AdminService adminservice = new AdminService();
		adminservice.setUserName(scanner.next());
		System.out.println("Enter a PassWord:");
		adminservice.setPassWord(scanner.next());
		final String query="select * from admin where username=? AND password=?";
		Connection con = ConnectionClass.getConnectionMethod();
		PreparedStatement ps = con.prepareStatement(query);		
		ps.setString(1, adminservice.getUserName());
		ps.setString(2, adminservice.getPassWord());
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			adminservice.setAid(rs.getInt(1));
			adminservice.setName(rs.getString(2));
			System.out.println("Login Success.");
			adminHome(adminservice.getAid(),adminservice.getName());
			break;
		}else {
			try {
			System.out.println("Invalid Username and Password.");
			System.out.println("   1.Try again     2.Exit     ");
			int option=scanner.nextInt();
			if(option == 1) {
				continue;
			}
			else if(option == 2) {
				OtherServices otherservices = new OtherServices();
				otherservices.indexMethod();
				scanner.close();
				break;
			}
			else {
				System.out.println("Invalid option!!!. Please select a valid option.");
//				continue;
			}
			}catch(InputMismatchException e) {
				System.out.println("Invalid option! Please Select a Valid Option.");
//				OtherServices otherservices = new OtherServices();
//				otherservices.indexMethod();
//				scanner.close();
//				break;
			}
		}
		}//loop ends		
	}
	
	public void adminHome(int aid, String name) throws SQLException {
		//create object for Scanner Class
		Scanner scanner=new Scanner(System.in);
		final int adminId=aid;
		final String adminName=name;
		final String userCountQuery = "select count(uid) from user";
		final String bookCountQuery = "select count(bookid) from book";
		final String languageCountQuery = "select count(languageid) from language";
		final String authorCountQuery = "select count(authorid) from author";
		Connection con = ConnectionClass.getConnectionMethod();
		PreparedStatement userCountQuerY = con.prepareStatement(userCountQuery);
		ResultSet userCount=userCountQuerY.executeQuery();
		userCount.next();
		PreparedStatement bookCountQuerY = con.prepareStatement(bookCountQuery);
		ResultSet bookCount=bookCountQuerY.executeQuery();
		bookCount.next();
		PreparedStatement languageCountQuerY = con.prepareStatement(languageCountQuery);
		ResultSet languageCount=languageCountQuerY.executeQuery();
		languageCount.next();
		PreparedStatement authorCountQuerY = con.prepareStatement(authorCountQuery);
		ResultSet authorCount = authorCountQuerY.executeQuery();
		authorCount.next();
		
		while(true) {
			System.out.println("________________________________________________________________________________________________________________");
			System.out.println("---------------------------");
			System.out.println("|      ____________       |");
			System.out.println("|                         |");
			System.out.println("|          Admin          |");
			System.out.println("|        DashBoard        |");
			System.out.println("|      ____________       |");
			System.out.println("|                         |");
			System.out.println("---------------------------");
			System.out.println("Dear "+name);
			System.out.println("---------------------------");
			System.out.println("        <INVENTORY>        ");
			System.out.println("       --------------      ");
			System.out.println(" Total Users    : "+userCount.getInt(1));
			System.out.println(" Total Books    : "+bookCount.getInt(1));
			System.out.println(" Total Languages: "+languageCount.getInt(1));
			System.out.println(" Total Authors  : "+authorCount.getInt(1));
			System.out.println("---------------------------");
			System.out.println("|1.add books              |");
			System.out.println("|2.add Authors            |");
			System.out.println("|3.add Languages          |");
			System.out.println("|4.view Books             |");
			System.out.println("|5.view Authors           |");
			System.out.println("|6.view Languages         |");
			System.out.println("|7.view DueDate List      |");
			System.out.println("|8.Logout                 |");
			System.out.println("---------------------------");
			try{//try block starts	
				System.out.print("Enter a Option:");
			    int option = scanner.nextInt();
			 	 if(option == 1) {
					 BookService bookservice = new BookService();
					 bookservice.addBook(adminId,adminName);
					 break;
				 }
				 else if(option == 2) {
					 AuthorService authorservice = new AuthorService();
					 authorservice.addAuthor(adminId,adminName);
		             break;
				 }
				 else if(option == 3) {
					 LanguageService languageservice = new LanguageService();
					 languageservice.addLanguage(adminId, adminName);
					 break;
				 }
				 else if(option == 4) {
					 BookService bookservice = new BookService();
					 bookservice.adminViewBooks(adminId, adminName);
					 break;
				 }
				 else if(option == 5) {
					 AuthorService authorservice = new AuthorService();
					 authorservice.viewAuthors(adminId, adminName);
					 break;
				 }
				 else if(option == 6) {
					 LanguageService languageservice = new LanguageService();
					 languageservice.viewLanguages(adminId, adminName);
					 break;
				 }
				 else if(option == 7) {
					 BookDueListService bookduelistservice = new BookDueListService();
					 bookduelistservice.getOverDueDateList(adminId, adminName);
					 break;
				 }
				 else if(option == 8) {
					 OtherServices otherservice = new OtherServices();
					 otherservice.indexMethod();
					 break;
				 }
				 else {
					System.out.println("Invalid option!!! Please Select a Valid Option."); 
				 }
//			 	scanner.close();
			}//try block ends
			catch(InputMismatchException e) {
				System.out.println("Invalid option! Please Select a Valid Option.");
				scanner.next();
			}
		}
		scanner.close();
	}
	
	
	
	
	
	
}
