package services;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import entities.User;

public class UserService extends User{
	//Constructor
	public UserService(){

	}
	//Constructor
	public UserService(String name,String userName,String passWord) {
		super(name,userName,passWord);
	}
	//Constructor
	public UserService(int uid, String name, int age, String gender, String address, String phoneNo, String district,
			String state, int pinCode) {
		super(uid,name,age,gender,address,phoneNo,district,state,pinCode);
	}
	
	public void userRegister() throws SQLException {
		//create object for Scanner Class
	    Scanner scanner=new Scanner(System.in);
		System.out.println("________________________________________________________________________________________________________________");
		System.out.println("---------------------------");
		System.out.println("|                         |");
		System.out.println("|       WELCOME TO        |");
		System.out.println("|    LIBRARY MANAGEMENT   |");
		System.out.println("|         SYSTEM          |");
		System.out.println("|                         |");
		System.out.println("|      User  Register     |");
		System.out.println("|          Page           |");
		System.out.println("|      ____________       |");
		System.out.println("|                         |");
		System.out.println("---------------------------");
		//Object creation for UserServices class:
		UserService userservice=new UserService();
		    System.out.println("Enter a Name:");
		    userservice.setName(scanner.next());
			System.out.println("Enter a UserName:");
			userservice.setUserName(scanner.next());
			System.out.println("Enter a PassWord:");
			userservice.setPassWord(scanner.next());
			final String query="insert into user(name,username,password) values(?,?,?)";
			Connection con = ConnectionClass.getConnectionMethod();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1,userservice.getName());
			ps.setString(2,userservice.getUserName());
			ps.setString(3,userservice.getPassWord());
			if(ps.executeUpdate()>0) {
				System.out.println("Successfully Registered");
				OtherServices otherservice = new OtherServices();
				otherservice.indexMethod();
			}else {
				System.out.println("Registration Failed");
			}
			scanner.close();
	}

	
	
	public void userLogin() throws SQLException {
		System.out.println("________________________________________________________________________________________________________________");
		System.out.println("---------------------------");
		System.out.println("|                         |");
		System.out.println("|       WELCOME TO        |");
		System.out.println("|    LIBRARY MANAGEMENT   |");
		System.out.println("|         SYSTEM          |");
		System.out.println("|                         |");
		System.out.println("|       User  Login       |");
		System.out.println("|          Page           |");
		System.out.println("|      ____________       |");
		System.out.println("|                         |");
		System.out.println("---------------------------");
		//Object creation for UserServices class:
		UserService userservice=new UserService();
		while(true) {
			//create object for Scanner Class
			Scanner scanner=new Scanner(System.in);
		System.out.println("Enter a UserName:");
		userservice.setUserName(scanner.next());
		System.out.println("Enter a PassWord:");
		userservice.setPassWord(scanner.next());
		final String query="select * from user where username=? AND password=?";
		Connection con = ConnectionClass.getConnectionMethod();
		PreparedStatement ps = con.prepareStatement(query);		
		ps.setString(1, userservice.getUserName());
		ps.setString(2, userservice.getPassWord());
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			userservice.setUid(rs.getInt(1));
			userservice.setName(rs.getString(2));
			userHome(userservice.getUid(),userservice.getName());
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
				}
				}catch(InputMismatchException e) {
					System.out.println("Invalid option! Please Select a Valid Option.");
				}
		}
		}
	}
	
	
	public void userHome(int uid,String name) throws SQLException {
		//create object for Scanner Class
		Scanner scanner=new Scanner(System.in);
		System.out.println("________________________________________________________________________________________________________________");
		while(true) {
		System.out.println("---------------------------");
		System.out.println("|      ____________       |");
		System.out.println("|                         |");
		System.out.println("|       User  Home        |");
		System.out.println("|          Page           |");
		System.out.println("|      ____________       |");
		System.out.println("|                         |");
		System.out.println("---------------------------");
		System.out.println("Dear "+name);
		System.out.println("---------------------------");
		System.out.println("|1.View Books             |");
		System.out.println("|2.Your Books             |");
		System.out.println("|3.Logout                 |");
		System.out.println("---------------------------");	
		try{//try block starts	
			System.out.print("Enter a Option:");
		    int option = scanner.nextInt();
		 	 if(option ==1) {
		 		 BookService bookservice = new BookService();
		 		 bookservice.userViewBooks(uid,name);
		 		 break;
			 }
			 else if(option ==2) {
				 	userBooks(uid,name);
	             break;
			 }
			 else if(option ==3) {
				 OtherServices otherServices = new OtherServices();
				 otherServices.indexMethod();
				  break;
			 }
			 else {
				System.out.println("Invalid option!!! Please Select a Valid Option."); 
			 }
		}//try block ends
		catch(InputMismatchException e) {
			System.out.println("Invalid option! Please Select a Valid Option.");
			scanner.nextLine();
		}
	}
		scanner.close();
  }
	
	public void userBooks(int uid,String name) throws SQLException{
		//createa object for scanner class
		Scanner scanner = new Scanner(System.in);
		LanguageService languageservice = new LanguageService();
		AuthorService authorervice =new AuthorService();
		final String query="select book.bookname,book.languageid,book.authorid,booksduelist.duedate from book inner join booksduelist on book.bookid=booksduelist.bookid where booksduelist.userid=?";
	    Connection con = ConnectionClass.getConnectionMethod();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, uid);
		ResultSet rs = ps.executeQuery();
		    System.out.println("--------------Your Books---------------");
		while(rs.next()) {
			System.out.println("---------------------------------------");
			System.out.println("Bookname:"+rs.getString(1));
			System.out.println("Language:"+languageservice.getLanguageById(rs.getInt(2)));
			System.out.println("Author  :"+authorervice.getAuthorById(rs.getInt(3)));
			System.out.println("DueDate :"+rs.getString(4));
			System.out.println("---------------------------------------");
		}
		    System.out.println("---------------------------------------");

		System.out.println("Press any key to exit.");
		scanner.next();
		userHome(uid,name);
		scanner.close();
		
	}
	
	public void userDetails(int uid,String name) throws SQLException {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		final String selectQuery = "select age,gender,address,phoneno,district,state,pincode from user where uid = ?";
		Connection con = ConnectionClass.getConnectionMethod();
		PreparedStatement ps1 = con.prepareStatement(selectQuery);
		ps1.setInt(1, uid);
		ResultSet rs = ps1.executeQuery();
		rs.next();
		try {
		if(rs.getInt(1)==0 && rs.getString(2)==null && rs.getString(3)==null && rs.getInt(4)==0 && rs.getString(5)==null && rs.getString(6)==null && rs.getInt(7)==0) {
			System.out.println("Enter Your Personal Informations to collect a Book.");
			System.out.println("---------------------------------------------------");
			System.out.println("Enter a Age:");
			int age = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter a Gender:");
			String gender = scanner.next();
			scanner.nextLine();
			System.out.println("Enter a Address:");
			String address = scanner.nextLine();
			System.out.println("Enter a PhoneNo:");
			String phoneNo = scanner.next();
			scanner.nextLine();
			System.out.println("Enter a District:");
			String district = scanner.nextLine();
			System.out.println("Enter a State:");
			String state = scanner.nextLine();
			System.out.println("Enter a Pincode:");
			int pincode = scanner.nextInt();
			UserService userservice = new UserService(uid,name,age,gender,address,phoneNo,district,state,pincode);
			final String insertQuery="update user set name=?,age=?,gender=?,address=?,phoneno=?,district=?,state=?,pincode=? where uid =?";
			PreparedStatement ps2 = con.prepareStatement(insertQuery);
			ps2.setString(1, userservice.getName());
			ps2.setInt(2, userservice.getAge());
			ps2.setString(3, userservice.getGender());
			ps2.setString(4, userservice.getAddress());
			ps2.setString(5, userservice.getPhoneNo());
			ps2.setString(6, userservice.getDistrict());
			ps2.setString(7, userservice.getState());
			ps2.setInt(8,userservice.getPinCode());
			ps2.setInt(9, userservice.getUid());
			ps2.executeUpdate();
			}
		}catch(InputMismatchException e) {
			System.out.println("Invalid input. Please enter a Valid Input.");
		}
	}
	
	
	
}
