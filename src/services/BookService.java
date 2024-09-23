package services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import customExceptions.AuthorAlreadyExistException;
import customExceptions.BookNotAvailableException;
import customExceptions.LanguageAlreadyExistException;
import entities.Author;
import entities.Book;
public class BookService extends Book {
		//Constructor
	    public BookService() {
	    }
		//Constructor
		public BookService(int bookId, String bookName, int languageId, int authorId, String keyWords, float rating) {
		super(bookId, bookName, languageId, authorId, keyWords,rating);
	    }
		//Constructor
		public BookService(String bookName, int languageId, int authorId, String keyWords) {
		super(bookName, languageId, authorId, keyWords);
		}
		
		//addBook method Starts
		public void addBook(int aid,String name) throws SQLException {
			//create object for Scanner Class
			Scanner scanner=new Scanner(System.in);
			System.out.println("________________________________________________________________________________________________________________");
			while(true) {//Loop starts
				System.out.println("---------------------------");
				System.out.println("|      ____________       |");
				System.out.println("|                         |");
				System.out.println("|       Add   book        |");
				System.out.println("|          Page           |");
				System.out.println("|      ____________       |");
				System.out.println("|                         |");
				System.out.println("|   1.Start     2.Exit    |");
				System.out.println("|                         |");
				System.out.println("---------------------------");
				try {
					
					System.out.println("Enter a Option:");
					int option = scanner.nextInt();
					scanner.nextLine();
					if(option==1) {//if block starts
					System.out.println("Enter a book name:");
					String bookName=scanner.nextLine();
					System.out.println("Enter a Language name:");
					String languageName=scanner.nextLine();
					int languageId=LanguageAlreadyExistException.getLanguageByName(languageName);
					if(languageId==0) {
						scanner.close();
						throw new LanguageAlreadyExistException();
					}
					System.out.println("Enter a Author Name:");
					String authorName=scanner.nextLine();
					int authorId=AuthorAlreadyExistException.getAuthorByName(authorName);
					if(authorId==0) {
						scanner.close();
						throw new AuthorAlreadyExistException();
					}
					System.out.println("Enter a Keywords:");
					String keyWords=scanner.nextLine();
					//creating object for BookService Class
					BookService bookservice = new BookService(bookName,languageId,authorId,keyWords);
					if(bookservice.getAuthorId()!=0 && bookservice.getLanguageId()!=0) {
						Connection con=ConnectionClass.getConnectionMethod();
						String query="insert into book (bookname,languageid,authorid,keywords) values(?,?,?,?)";
					    PreparedStatement ps = con.prepareStatement(query);
					    ps.setString(1,bookservice.getBookName());
					    ps.setInt(2,bookservice.getLanguageId());
					    ps.setInt(3,bookservice.getAuthorId());
					    ps.setString(4,bookservice.getKeyWords());
					    int result=ps.executeUpdate();
					    if(result>0) {
					    	System.out.println("Book is Added Successfully");
					    }else {
					    	System.out.println("Book is not Added. Please try again.");
					    }
					}
					else {
						scanner.close();
						throw new AuthorAlreadyExistException();
					}
					
				    }//if block ends
					else if(option==2) {
						AdminService adminService = new AdminService();
						final int adminId=aid;
						final String adminName=name;
						adminService.adminHome(adminId,adminName);
						break;
					}//else-if block ends
					else {
						System.out.println("Invalid option!!! Please Select a Valid Option."); 
//						scanner.close();
					}//else block ends
//					scanner.close();
				}catch (LanguageAlreadyExistException e) {
                    //System.out.println(e);
					System.out.println("Sorry Admin,this Language is Not Available in Our Database.");
					System.out.println("Please First Add a Language then,Try Again.");
				}catch (AuthorAlreadyExistException e) {
                    //System.out.println(e);
					System.out.println("Sorry Admin,this Author is Not Available in Our Database.");
					System.out.println("Please First Add a Author then,Try Again.");
				}catch(InputMismatchException e) {
					System.out.println("Invalid option! Please Select a Valid Option.");
					scanner.next();
				}
			}//Loop ends
			scanner.close();
		}//addBook method ends
		
		//getAllBooks method starts
		public ArrayList<BookService> getAllBooks() throws SQLException {
			ArrayList<BookService> books = new ArrayList<BookService>();
			final String query1="select * from book";
			Connection con1 = ConnectionClass.getConnectionMethod();
			PreparedStatement ps1 = con1.prepareStatement(query1);
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()) {
				final String query2="select avg(rating) from ratings where bookid=?";
				Connection con2 = ConnectionClass.getConnectionMethod();
				PreparedStatement ps2 = con2.prepareStatement(query2);
				ps2.setInt(1, rs1.getInt(1));
                ResultSet rs2 = ps2.executeQuery();
                rs2.next();
				BookService  bookservice = new BookService(rs1.getInt(1),rs1.getString(2),rs1.getInt(3),rs1.getInt(4),rs1.getString(5),rs2.getInt(1));
				books.add(bookservice);
			}
			return books;
		}
		//getAllBooks method ends

		
		//ViewBooks method starts
		public void adminViewBooks(int aid,String name) throws SQLException{
			//ArrayList Object
			ArrayList<BookService> books = new ArrayList<BookService>();
			//object creation for AuthorService,LanguageService,AdminServices Classes
			AdminService adminservice = new AdminService();
			AuthorService authorservice = new AuthorService();
			LanguageService languageservice = new LanguageService();
			//Scanner Class Object
			Scanner scanner = new Scanner(System.in);
			books=getAllBooks();
			System.out.println("________________________________________________________________________________________________________________");
			System.out.println("------------------------------------");
			System.out.println("|           _____________          |");
			System.out.println("|                                  |");
			System.out.println("|            View  books           |");
			System.out.println("|               Page               |");
			System.out.println("|           _____________          |");
			System.out.println("|                                  |");
			System.out.println("|                                  |");
			System.out.println("------------------------------------");
					for(Book book:books) {	
					System.out.println("------------------------------------");
					System.out.println("Book Id      : "+book.getBookId());
					System.out.println("Book Name    : "+book.getBookName());
					System.out.println("Language Id  : "+book.getLanguageId());
					System.out.println("Language Name: "+languageservice.getLanguageById(book.getLanguageId()));
					System.out.println("Author Id    : "+book.getAuthorId());
					System.out.println("Author Name  : "+authorservice.getAuthorById(book.getAuthorId()));
					System.out.println("Rating       : "+book.getRating()+" out of 5");
					System.out.println("KeyWords     : "+book.getKeyWords());
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
								final int adminId=aid;
								final String adminName=name;
								updateBook(adminId,adminName);
								break;
							}
							else if(option == 2) {
								final int adminId=aid;
								final String adminName=name;
								deleteBook(adminId,adminName);
								break;
							}
							else if(option == 3) {
								final int adminId=aid;
							    final String adminName=name;
								adminservice.adminHome(adminId, adminName);	
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
		}//View Book method ends
		
		
		public void userViewBooks(int uid,String name) throws SQLException {
			ArrayList<BookService> books = new ArrayList<BookService>();
			//object creation for AuthorService,LanguageService,UserService Classes
			UserService userservice = new UserService();
			AuthorService authorservice = new AuthorService();
			LanguageService languageservice = new LanguageService();
			//Scanner Class Object
			Scanner scanner = new Scanner(System.in);
			books=getAllBooks();
			System.out.println("________________________________________________________________________________________________________________");
			System.out.println("------------------------------------");
			System.out.println("|           _____________          |");
			System.out.println("|                                  |");
			System.out.println("|            View  books           |");
			System.out.println("|               Page               |");
			System.out.println("|           _____________          |");
			System.out.println("|                                  |");
			System.out.println("|                                  |");
			System.out.println("------------------------------------");
					for(Book book:books) {
					System.out.println("------------------------------------");
					System.out.println("Book Id      : "+book.getBookId());
					System.out.println("Book Name    : "+book.getBookName());
					System.out.println("Language Name: "+languageservice.getLanguageById(book.getLanguageId()));
					System.out.println("Author Name  : "+authorservice.getAuthorById(book.getAuthorId()));
					System.out.println("Rating       : "+book.getRating()+" out of 5");
					System.out.println("------------------------------------");
					}
					while(true) {
						try {
							System.out.println("----------------------------------");
							System.out.println("|                                |");
							System.out.println("|   1.GiveRate    2.GetBook      |");
							System.out.println("|                                |");
							System.out.println("|            3.Exit              |");
							System.out.println("|                                |");
							System.out.println("----------------------------------");
							System.out.println("Enter a Option:");
							int option = scanner.nextInt();
							if(option ==1) {
								RatingService ratingservice = new RatingService();
								ratingservice.rating(uid,name);
								break;
							}
							else if(option ==2) {
								userGetBook(uid,name);
								break;
							}
							else if (option==3) {
								userservice.userHome(uid, name);
								break;
							}else {
								System.out.println("Invalid option!!! Please Select a Valid Option."); 
							}
						}catch(InputMismatchException e) {
							System.out.println("Invalid Option! Please Select a Valid Option.");
							scanner.next();
						}
					}
				
			scanner.close();
		}
		
		
		public void userGetBook(int uid, String name) throws SQLException{
			//Scanner Class Object
			Scanner scanner = new Scanner(System.in);
			while(true) {
			try {
			System.out.println("Which Book You Want? Enter a any above bookId:");			
			int bookId=BookNotAvailableException.bookAvailableOrNot(scanner.nextInt());
			if(bookId==0) {
				throw new BookNotAvailableException();
			}
			
			UserService userservice = new UserService();
			userservice.userDetails(uid,name);
			
			System.out.println("Enter a Date to submit(YYYY-MM-DD):");
			String date = scanner.next();
		
			BookDueListService bookduelistservice = new BookDueListService();
			if(bookduelistservice.confirmGetBook(uid,bookId,date)) {
				userViewBooks(uid,name);
				break;
			}
			} catch (SQLException e) {
				System.out.println(e);
					userViewBooks(uid,name);
					break;
				
			} catch (BookNotAvailableException e) {
				System.out.println("This book is not available.");
					userViewBooks(uid,name);
					break;
				
			}catch(InputMismatchException e) {
				System.out.println("Invalid Input. please Enter a Valid Input.");
					userViewBooks(uid,name);
					break;
			}
			}
			scanner.close();
		}
		
		
		public void updateBook(int aid,String name) throws SQLException{
			//ArrayList Object
			ArrayList<BookService> books = new ArrayList<BookService>();
			//object creation for AuthorService,LanguageService Classes
			AuthorService authorservice = new AuthorService();
			LanguageService languageservice = new LanguageService();
			//Scanner Class Object
			Scanner scanner = new Scanner(System.in);
			// call connection Establishment function
			Connection con = ConnectionClass.getConnectionMethod();
			System.out.println("________________________________________________________________________________________________________________");
			while(true) {
				books=getAllBooks();
			    System.out.println("------------<All Books>--------------");
			for(Book book:books) {//Display Books using Enhanced For Loop
				System.out.println("------------------------------------");
				System.out.println("Book Id      : "+book.getBookId());
				System.out.println("Book Name    : "+book.getBookName());
				System.out.println("Language Id  : "+book.getLanguageId());
				System.out.println("Language Name: "+languageservice.getLanguageById(book.getLanguageId()));
				System.out.println("Author Id    : "+book.getAuthorId());
				System.out.println("Author Name  : "+authorservice.getAuthorById(book.getAuthorId()));
				System.out.println("KeyWords     : "+book.getKeyWords());
				System.out.println("Rating       : "+book.getRating()+" out of 5");
				System.out.println("------------------------------------");
			}
			//update code
				try {
					System.out.println("Enter any One above BookId:");
					int bookId = scanner.nextInt();
					System.out.println("---------------------------");
					System.out.println("|1.Book Name              |");
					System.out.println("|2.Author Id              |");
					System.out.println("|3.Language Id            |");
					System.out.println("|4.KeyWords               |");
					System.out.println("|5.Exit                   |");
					System.out.println("---------------------------");	
					System.out.println("Enter Any One above Option to Perform Update.");
					System.out.println("Enter 5 to Exit");
					int option = scanner.nextInt();
					scanner.nextLine();
					if(option == 1){
						System.out.println("Enter a New BookName:");
						String bookName= scanner.nextLine();
						final String query="update book set bookname=? where bookid=?";
						PreparedStatement ps =con.prepareStatement(query);
						ps.setString(1,bookName);
						ps.setInt(2, bookId);
						int result =ps.executeUpdate();
						if(result>0) {
							System.out.println("BookName Updated");
							adminViewBooks(aid,name);
							break;
						}else {
							System.out.println("BookName Not Updated.Please check a BookId.");
						}
					}
					else if(option == 2) {
						ArrayList<AuthorService> authors = new ArrayList<AuthorService>();
						authors=authorservice.getAllAuthors();
						for(Author author:authors) {
							System.out.println("------------------------------------");
							System.out.println("Author Id  : "+author.getAuthorId());
							System.out.println("Author Name: "+author.getAuthorName());
							System.out.println("------------------------------------");
						}
						System.out.println("Enter any One above AuthorId:");
						int authorId=scanner.nextInt();
						final String query="update book set authorid=? where bookid=?";
						PreparedStatement ps =con.prepareStatement(query);
						ps.setInt(1, authorId);
						ps.setInt(2, bookId);
						int result=ps.executeUpdate();
						if(result>0) {
							System.out.println("AuthorId Updated");
							adminViewBooks(aid,name);
							break;
						}else {
							System.out.println("AuthorId Not Updated. Please check a BookId and AuthorId.");
						}
					}
					else if(option ==3) {
						ArrayList<LanguageService> languages = new ArrayList<LanguageService>();
						languages = languageservice.getAllLanguages();
						for(LanguageService language: languages) {
							System.out.println("------------------------------------");
							System.out.println("Language Id  : "+language.getLanguageId());
							System.out.println("Language Name: "+language.getLanguageName());
							System.out.println("------------------------------------");
						}
						System.out.println("Enter any One above LanguageId:");
						int languageId=scanner.nextInt();
						final String query ="update book set languageid=? where bookid=?";
						PreparedStatement ps = con.prepareStatement(query);
						ps.setInt(1, languageId);
						ps.setInt(2, bookId);
						int result=ps.executeUpdate();
						if(result>0) {
							System.out.println("LanguageId Updated");
							adminViewBooks(aid,name);
							break;
							
						}else {
							System.out.println("LanguageId Not Updated. Please check a BookId and LanguageId.");
						}
					}
					else if(option ==4) {
						System.out.println("Enter a New KeyWords:");
						String keyWords= scanner.nextLine();
						final String query="update book set keywords=? where bookid=?";
						PreparedStatement ps =con.prepareStatement(query);
						ps.setString(1,keyWords);
						ps.setInt(2, bookId);
						int result =ps.executeUpdate();
						if(result>0) {
							System.out.println("Keywords Updated");
							adminViewBooks(aid,name);
							break;
							
						}else {
							System.out.println("Keywords Not Updated. Please check a BookId.");
						}
					}
					else if(option ==5) {
						adminViewBooks(aid,name);
						scanner.close();
						break;
					}
                    else {
						System.out.println("Invalid option!!! Please Select a Valid Option."); 
					}
				}catch(InputMismatchException e) {
					System.out.println("Invalid option! Please Select a Valid Option.");
					scanner.next();
				}catch(SQLException e) {
					System.out.println("Update failed. please check if your input Values are Correct or Not.");
				}
			}
		}
		
		
		
		public void deleteBook(int aid,String name) throws SQLException {
			//ArrayList Object
			ArrayList<BookService> books = new ArrayList<BookService>();
			//object creation for AuthorService,LanguageService Classes
			AuthorService authorservice = new AuthorService();
			LanguageService languageservice = new LanguageService();
			//Scanner Class Object
			Scanner scanner = new Scanner(System.in);
			// call connection Establishment function
			Connection con = ConnectionClass.getConnectionMethod();
			System.out.println("________________________________________________________________________________________________________________");
			while(true) {//while loop starts
			books=getAllBooks();
		    System.out.println("------------<All Books>--------------");
		    for(Book book:books) {//Display Books using Enhanced For Loop
			System.out.println("------------------------------------");
			System.out.println("Book Id      : "+book.getBookId());
			System.out.println("Book Name    : "+book.getBookName());
			System.out.println("Language Id  : "+book.getLanguageId());
			System.out.println("Language Name: "+languageservice.getLanguageById(book.getLanguageId()));
			System.out.println("Author Id    : "+book.getAuthorId());
			System.out.println("Author Name  : "+authorservice.getAuthorById(book.getAuthorId()));
			System.out.println("Rating       : "+book.getRating()+" out of 5");
			System.out.println("KeyWords     : "+book.getKeyWords());
			System.out.println("------------------------------------");
		    }
		    System.out.println("Enter a Any one Above Book Id: ");
		    System.out.println("Enter 0 to Exit:");
		    try {
		    int option = scanner.nextInt();
		    if(option==0) {
		    	adminViewBooks(aid, name);
		    	break;
		    }
		    else {
		    	final String query1="delete from ratings where bookid=?";
		    	PreparedStatement ps1 = con.prepareStatement(query1);
		    	ps1.setInt(1, option);
		    	ps1.executeUpdate();
		    	final String query2="delete from book where bookid=?";
		    	PreparedStatement ps2 = con.prepareStatement(query2);
		    	ps2.setInt(1, option);
		    	if(ps2.executeUpdate()>0) {
		    		System.out.println("Book is Deleted.");
		    		adminViewBooks(aid,name);
					break;
		    	}
		    	else {
		    		System.out.println("Book is not Deleted. Please Check Your BookId.");
		    	}
		    }
		    }catch(InputMismatchException e) {
				System.out.println("Invalid option! Please Select a Valid Option.");
				scanner.next();
			}catch(SQLException e) {
				System.out.println("Book Deletion failed. please check if your input Values are Correct or Not.");
			}
			}// while loop end
			scanner.close();
		}
		
		
}
