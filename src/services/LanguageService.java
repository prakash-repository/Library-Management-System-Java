package services;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import customExceptions.LanguageAlreadyExistException;
import entities.Language;
public class LanguageService extends Language{
			//Constructor
	        public LanguageService() {
			super();
		    }
			//Constructor
		    public LanguageService(String languageName) {
			super(languageName);
			}
			//Constructor
			public LanguageService(int languageId, String languageName) {
			super(languageId, languageName);
		    }
			
			
	
	public void addLanguage(int aid, String name) throws SQLException {
		// creating a object for AuthorService class
		LanguageService languageservice = new LanguageService();
		// Call Connection Establishment function
		Connection con = ConnectionClass.getConnectionMethod();
		
		while(true) {
			System.out.println("---------------------------");
			System.out.println("|      ____________       |");
			System.out.println("|                         |");
			System.out.println("|       Add Language      |");
			System.out.println("|          Page           |");
			System.out.println("|      ____________       |");
			System.out.println("|                         |");
			System.out.println("|   1.Start     2.Exit    |");
			System.out.println("|                         |");
			System.out.println("---------------------------");
			try {
				//create object for Scanner Class
				Scanner scanner=new Scanner(System.in);
				System.out.println("Enter a Option:");
				int option=scanner.nextInt();
				scanner.nextLine();
				if(option==1) {
					System.out.println("Enter Language Name:");
					languageservice.setLanguageName(scanner.nextLine());
					languageservice.setLanguageId(LanguageAlreadyExistException.getLanguageByName(languageservice.getLanguageName()));
					if(languageservice.getLanguageId()==0) {
						String query="insert into language(languagename) values(?)";
						PreparedStatement ps = con.prepareStatement(query);
						ps.setString(1,languageservice.getLanguageName());
						if(ps.executeUpdate()>0) {
							System.out.println("The Language '"+languageservice.getLanguageName()+"' is Added.");
						}
					}else {
						scanner.close();
						throw new LanguageAlreadyExistException();
					}
				}
				else if(option==2) {
					AdminService adminservice= new AdminService();
					adminservice.adminHome(aid, name);
					break;
				}
				else {
					System.out.println("Invalid option!!! Please Select a Valid Option."); 
				}
				scanner.close();
			}catch(LanguageAlreadyExistException e) {
				System.out.println("The Language is Already Exist.");
			}catch(InputMismatchException e) {
				System.out.println("Invalid option! Please Select a Valid Option.");
			}
		}
	}
	
	public ArrayList<LanguageService> getAllLanguages() throws SQLException{
		//ArrayList Object
		ArrayList<LanguageService> languages = new ArrayList<LanguageService>();
		final String query="select * from language";
		Connection con = ConnectionClass.getConnectionMethod();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			//object creation for AuthorService Class
			LanguageService languageservice = new LanguageService(rs.getInt(1),rs.getString(2));				
			languages.add(languageservice);
		}
		return languages;
	}
	
	public void viewLanguages(int aid,String name) throws SQLException {
		//ArrayList Object
		ArrayList<LanguageService> languages = new ArrayList<LanguageService>();
		//Scanner Class Object
		Scanner scanner = new Scanner(System.in);
		// Object for AdminService Class
		AdminService adminservice = new AdminService();
		System.out.println("------------------------------------");
		System.out.println("|           _____________          |");
		System.out.println("|                                  |");
		System.out.println("|          View  Languages         |");
		System.out.println("|              Page                |");
		System.out.println("|           _____________          |");
		System.out.println("|                                  |");
		System.out.println("|                                  |");
		System.out.println("------------------------------------");
		languages =getAllLanguages();
				for(Language language:languages) {
				System.out.println("------------------------------------");
				System.out.println("Language Id      : "+language.getLanguageId());
				System.out.println("Language Name    : "+language.getLanguageName());
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
							updateLanguage(adminId,adminName);
							break;
						}
						else if(option == 2) {
							final int adminId=aid;
							final String adminName=name;
							deleteLanguage(adminId,adminName);
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
	}
	
	public void updateLanguage(int aid, String name) throws SQLException {
		//ArrayList Object
		ArrayList<LanguageService> languages = new ArrayList<LanguageService>();
		//Scanner Class Object
		Scanner scanner = new Scanner(System.in);
		System.out.println("________________________________________________________________________________________________________________");
		while(true) {
			languages=getAllLanguages();
		    System.out.println("------------<All Languages->--------------");
			for(Language language:languages) {//Display Languages using Enhanced For Loop
				System.out.println("------------------------------------");
				System.out.println("Language Id      : "+language.getLanguageId());
				System.out.println("Language Name    : "+language.getLanguageName());
				System.out.println("------------------------------------");
			}
			//update code
			try {
				System.out.println("Enter any One above LanguageId:");
				int languageId = scanner.nextInt();
				System.out.println("---------------------------");
				System.out.println("|1.Change LanguageName    |");
				System.out.println("|2.Exit                   |");
				System.out.println("---------------------------");
				System.out.println("Enter a Option:");
				int option = scanner.nextInt();
				if(option ==1) {
					System.out.println("Enter a new Language Name:");
					String languageName = scanner.nextLine();
					final String query="update Language set languagename=? where languageid=?";
					Connection con = ConnectionClass.getConnectionMethod();
					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1, languageName);
					ps.setInt(2, languageId);
					if(ps.executeUpdate()>0){
						System.out.println("LanguageName Updated.");
					}
					else {
						System.out.println("LanguageName not updated. please check a languageId.");
						viewLanguages(aid,name);
						scanner.close();
						break;
					}
				}
				else if(option ==2) {
					viewLanguages(aid,name);
					break;
				}
			}catch(InputMismatchException e) {
				System.out.println("Invalid Input! Please enter a Valid Input.");
				scanner.next();
			}catch(SQLException e) {
				System.out.println("Update failed. please check if your input Values are Correct or Not.");
			}
		}
	}
	
	
    public void deleteLanguage(int aid, String name) throws SQLException {
    	//ArrayList Object
		ArrayList<LanguageService> languages = new ArrayList<LanguageService>();
		//Scanner Class Object
		Scanner scanner = new Scanner(System.in);
		// call connection Establishment function
					Connection con = ConnectionClass.getConnectionMethod();
					System.out.println("________________________________________________________________________________________________________________");
					while(true) {//while loop starts
					languages=getAllLanguages();
				    System.out.println("------------<All Authors>--------------");
				     for(Language language:languages) {//Display Language using Enhanced For Loop
					 System.out.println("--------------------------------------");
					 System.out.println("Language Id      : "+language.getLanguageId());
					 System.out.println("Language Name    : "+language.getLanguageName());
					 System.out.println("--------------------------------------");
				     }
				     System.out.println("Enter a Any one Above Language Id: ");
					 System.out.println("Enter 0 to Exit:");
					 try {
						    int option = scanner.nextInt();
						    if(option==0) {
						    	viewLanguages(aid,name);
						    	break;
						    }
						    else {
						    	final String query="delete from Language where languageid=?";
						    	PreparedStatement ps = con.prepareStatement(query);
						    	ps.setInt(1, option);
						    	int result=ps.executeUpdate();
						    	if(result>0) {
						    		System.out.println("language is Deleted.");
						    		viewLanguages(aid,name);
						    		scanner.close();
									break;
						    	}
						    	else {
						    		System.out.println("language is not Deleted. Please Check Your LanguageId.");
						    	}
						    }
					 }catch(InputMismatchException e) {
							System.out.println("Invalid input! Please Select a Valid Input.");
							scanner.next();
					 }catch(SQLException e) {
						 	System.out.println(e);
							System.out.println("Language Deletion failed. Please First Delete This Language Books and then try again.");
					 }
				    }
	}
	
	public String getLanguageById(int languageId) throws SQLException {
		final String query="select languagename from language where languageid=?";
		Connection con = ConnectionClass.getConnectionMethod();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1,languageId);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return rs.getString(1);
		}
		return null;
	}
}
