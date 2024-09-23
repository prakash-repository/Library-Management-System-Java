package services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import customExceptions.AuthorAlreadyExistException;
import entities.Author;
public class AuthorService extends Author{
	    //Constructor
	    public AuthorService() {
		super();
	    }
		//Constructor
	    public AuthorService(String authorName) {
		super(authorName);
		}
		//Constructor
		public AuthorService(int authorId, String authorName) {
		super(authorId, authorName);
	    }

		
		//addAuthor method starts
		public void addAuthor(int aid, String name) throws SQLException {
			//create object for Scanner Class
			Scanner scanner=new Scanner(System.in);
			// creating a object for AuthorService class
			AuthorService authorservice = new AuthorService();
			// Call Connection Establishment function
			Connection con = ConnectionClass.getConnectionMethod();

			while(true) {
				System.out.println("________________________________________________________________________________________________________________");
				System.out.println("---------------------------");
				System.out.println("|      ____________       |");
				System.out.println("|                         |");
				System.out.println("|       Add  Author       |");
				System.out.println("|          Page           |");
				System.out.println("|      ____________       |");
				System.out.println("|                         |");
				System.out.println("|   1.Start     2.Exit    |");
				System.out.println("|                         |");
				System.out.println("---------------------------");
				try {
					//create object for Scanner Class
//					Scanner scanner=new Scanner(System.in);
					System.out.println("Enter a Option:");
					int option=scanner.nextInt();
					scanner.nextLine();
					if(option==1) {
						System.out.println("Enter Author Name:");
						authorservice.setAuthorName(scanner.nextLine());
						authorservice.setAuthorId(AuthorAlreadyExistException.getAuthorByName(authorservice.getAuthorName()));
						if(authorservice.getAuthorId()==0) {
							String query="insert into author(authorname) values(?)";
							PreparedStatement ps = con.prepareStatement(query);
							ps.setString(1,authorservice.getAuthorName());
							if(ps.executeUpdate()>0) {
								System.out.println("The Author '"+authorservice.getAuthorName()+"' is Added.");
							}
						}else {
							throw new AuthorAlreadyExistException();
						}
					}
					else if(option==2) {
						AdminService adminservice= new AdminService();
						adminservice.adminHome(aid,name);
						break;
					}
					else {
						System.out.println("Invalid option!!! Please Select a Valid Option."); 
					}
//					scanner.close();
				}catch(AuthorAlreadyExistException e) {
					System.out.println("The Author is Already Exist.");
				}catch(InputMismatchException e) {
					System.out.println("Invalid option! Please Select a Valid Option.");
					scanner.next();
				}
			}
			scanner.close();
		}//addAuthor method ends
		
		
		public ArrayList<AuthorService> getAllAuthors() throws SQLException{
			//ArrayList Object
			ArrayList<AuthorService> authors = new ArrayList<AuthorService>();
			final String query="select * from author";
			Connection con = ConnectionClass.getConnectionMethod();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				//object creation for AuthorService Class
				AuthorService authorservice = new AuthorService(rs.getInt(1),rs.getString(2));				
				authors.add(authorservice);
			}
			return authors;
		}

		
		public void viewAuthors(int aid,String name) throws SQLException {
			//ArrayList Object
			ArrayList<AuthorService> authors = new ArrayList<AuthorService>();
			//Scanner Class Object
			Scanner scanner = new Scanner(System.in);
			// Object for AdminService Class
			AdminService adminservice = new AdminService();
			System.out.println("________________________________________________________________________________________________________________");
			System.out.println("------------------------------------");
			System.out.println("|           _____________          |");
			System.out.println("|                                  |");
			System.out.println("|           View  Authors          |");
			System.out.println("|               Page               |");
			System.out.println("|           _____________          |");
			System.out.println("|                                  |");
			System.out.println("|                                  |");
			System.out.println("------------------------------------");
			authors =getAllAuthors();
					for(Author author:authors) {
					System.out.println("------------------------------------");
					System.out.println("Author Id      : "+author.getAuthorId());
					System.out.println("Author Name    : "+author.getAuthorName());
					System.out.println("------------------------------------");
				}	
					while(true) {
						try {
							System.out.println("----------------------------------");
							System.out.println("|                                |");
							System.out.println("|    1.Update      2.Delete      |");
							System.out.println("|                                |");
							System.out.println("|            3.Exit              |");
							System.out.println("|                                |");
							System.out.println("----------------------------------");
							System.out.println("Enter a Option:");
							int option = scanner.nextInt();
							if(option == 1) {
								updateAuthor(aid,name);
								break;
							}
							else if(option == 2) {
								deleteAuthor(aid,name);
								break;
							}
							else if(option == 3) {
								adminservice.adminHome(aid, name);	
								break;
							}
							else {
								System.out.println("Invalid option!!! Please Select a Valid Option."); 
							}
						}catch(InputMismatchException e) {
							System.out.println("Invalid option! Please Select a Valid Option.");
							scanner.next();
						}
					}
			scanner.close();
		}
		
		
		public String getAuthorById(int authorId) throws SQLException {
			final String query="select authorname from author where authorid=?";
			Connection con = ConnectionClass.getConnectionMethod();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1,authorId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
			return null;
		}
		
		
		
		public void updateAuthor(int aid,String name) throws SQLException {
			//ArrayList Object
			ArrayList<AuthorService> authors = new ArrayList<AuthorService>();
			//Scanner Class Object
			Scanner scanner = new Scanner(System.in);
			System.out.println("________________________________________________________________________________________________________________");
			while(true) {
				authors=getAllAuthors();
			    System.out.println("------------<All Authors->--------------");
				for(Author author:authors) {//Display Authors using Enhanced For Loop
					System.out.println("------------------------------------");
					System.out.println("Author Id      : "+author.getAuthorId());
					System.out.println("Author Name    : "+author.getAuthorName());
					System.out.println("------------------------------------");
				}
				//update code
				try {
					System.out.println("Enter any One above AuthorId:");
					int authorId = scanner.nextInt();
					System.out.println("---------------------------");
					System.out.println("|1.Change AuthorName      |");
					System.out.println("|2.Exit                   |");
					System.out.println("---------------------------");
					System.out.println("Enter a Option:");
					int option = scanner.nextInt();
					if(option ==1) {
						System.out.println("Enter a new Author Name:");
						String authorName = scanner.nextLine();
						final String query="update author set authorname=? where authorid=?";
						Connection con = ConnectionClass.getConnectionMethod();
						PreparedStatement ps = con.prepareStatement(query);
						ps.setString(1, authorName);
						ps.setInt(2, authorId);
						if(ps.executeUpdate()>0){
							System.out.println("AuthorName Updated.");
						}
						else {
							System.out.println("AuthorName not updated. please check a AuthorId.");
							viewAuthors(aid,name);
							scanner.close(); 
							break;
						}
					}
					else if(option ==2) {
						viewAuthors(aid,name);
						break;
					}
				}catch(InputMismatchException e) {
					System.out.println("Invalid option! Please Select a Valid Option.");
					scanner.next();
				}catch(SQLException e) {
					System.out.println("Update failed. please check if your input Values are Correct or Not.");
				}
			}
		 }
		
		
		
		
        public void deleteAuthor(int aid,String name) throws SQLException {
        	//ArrayList Object
			ArrayList<AuthorService> authors = new ArrayList<AuthorService>();
			//Scanner Class Object
			Scanner scanner = new Scanner(System.in);
			// call connection Establishment function
						Connection con = ConnectionClass.getConnectionMethod();
						System.out.println("________________________________________________________________________________________________________________");
						while(true) {//while loop starts
						authors=getAllAuthors();
					    System.out.println("------------<All Authors>--------------");
					     for(Author author:authors) {//Display Authors using Enhanced For Loop
						 System.out.println("--------------------------------------");
						 System.out.println("Author Id      : "+author.getAuthorId());
						 System.out.println("Author Name    : "+author.getAuthorName());
						 System.out.println("--------------------------------------");
					     }
					     System.out.println("Enter a Any one Above Author Id: ");
						 System.out.println("Enter 0 to Exit:");
						 try {
							    int option = scanner.nextInt();
							    if(option==0) {
							    	viewAuthors(aid,name);
							    	break;
							    }
							    else {
							    	final String query="delete from Author where authorid=?";
							    	PreparedStatement ps = con.prepareStatement(query);
							    	ps.setInt(1, option);
							    	int result=ps.executeUpdate();
							    	if(result>0) {
							    		System.out.println("Author is Deleted.");
							    		viewAuthors(aid,name);
							    		scanner.close();
										break;
							    	}
							    	else {
							    		System.out.println("Author is not Deleted. Please Check Your AuthorId.");
							    	}
							    }
						 }catch(InputMismatchException e) {
								System.out.println("Invalid option! Please Select a Valid Option.");
								scanner.next();
						 }catch(SQLException e) {
							 	System.out.println(e);
								System.out.println("Author Deletion failed. Please First Delete This Author Books and then try again.");
						 }
					    }
		}
		
}
