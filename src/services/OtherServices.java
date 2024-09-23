package services;
import java.sql.SQLException;
import java.util.*;
public class OtherServices {
	   //Object creation for UserServices class:
	   UserService userservice = new UserService();
	   //Object creation for AdminServices class:
	   AdminService adminservice = new AdminService();
	   
	  //Index Method starts     #1
	public void indexMethod() throws SQLException{
		
		while(true) {
			//create object for Scanner Class
			Scanner scanner=new Scanner(System.in);
		System.out.println("---------------------------");
		System.out.println("|                         |");
		System.out.println("|       WELCOME TO        |");
		System.out.println("|    LIBRARY MANAGEMENT   |");
		System.out.println("|         SYSTEM          |");
		System.out.println("|      ____________       |");
		System.out.println("|                         |");
		System.out.println("|    1.Admin   2.User     |");
		System.out.println("|                         |");
		System.out.println("|         3.Exit          |");
		System.out.println("|                         |");
		System.out.println("---------------------------");
		try{//try block starts	
			System.out.print("Enter a Option:");
		    int option = scanner.nextInt();
		  
		 	 if(option ==1) {
		 		 adminIndexMethod();
				 break;
			 }
			 else if(option ==2) {
	             userIndexMethod();
	             break;
			 }
			 else if(option ==3) {
				 System.out.println("---------------------------");
				 System.out.println("        Thank You!         ");
			   	 System.out.println("---------------------------");
					scanner.close();
				 break;
			 }
			 else {
				System.out.println("Invalid option!!! Please Select a Valid Option."); 
			 }
		    
		}//try block ends
		catch(InputMismatchException e) {
			System.out.println("Invalid option! Please Select a Valid Option.");
		}

	}//loop end
  }//Index method ends

   
//	userIndex Method starts    #2
	public void userIndexMethod() throws  SQLException{
		
		 //Loop Starts
		 while(true) {
			//create object for Scanner Class
			Scanner scanner=new Scanner(System.in);
		System.out.println("________________________________________________________________________________________________________________");
		System.out.println("---------------------------");
		System.out.println("|                         |");
		System.out.println("|       WELCOME TO        |");
		System.out.println("|    LIBRARY MANAGEMENT   |");
		System.out.println("|         SYSTEM          |");
		System.out.println("|                         |");
		System.out.println("|        User Page        |");
		System.out.println("|      ____________       |");
		System.out.println("|                         |");
		System.out.println("|   1.Signup   2.Signin   |");
		System.out.println("|                         |");
		System.out.println("|         3.exit          |");
		System.out.println("|                         |");
		System.out.println("---------------------------");
				try{
				System.out.print("Enter a Option:");
				int option = scanner.nextInt();
				 	if(option ==1) {
                         userservice.userRegister();
                         break;
                     }
					 else if(option ==2) {
						 userservice.userLogin();
						 break;
					 }
					 else if(option ==3) {
						indexMethod();
						 scanner.close();
						break;
					 }
					 else {
						System.out.println("Invalid option!!! Please Select a Valid Option."); 
					 }
				}//try block ends
				catch(InputMismatchException e) {
					System.out.println("Invalid option! Please Select a Valid Option.");
				}	
	  } //Loop Ends
  } //UserIndex Method Ends
	
	//AdminIndex Method starts     #2
	public void adminIndexMethod() throws SQLException {
		 //Loop Starts
		 while(true) {
				//create object for Scanner Class
				Scanner scanner=new Scanner(System.in);
		System.out.println("________________________________________________________________________________________________________________");
		System.out.println("---------------------------");
		System.out.println("|                         |");
		System.out.println("|       WELCOME TO        |");
		System.out.println("|    LIBRARY MANAGEMENT   |");
		System.out.println("|         SYSTEM          |");
		System.out.println("|                         |");
		System.out.println("|       Admin Page        |");
		System.out.println("|      ____________       |");
		System.out.println("|                         |");
		System.out.println("|   1.Signin   2.Exit     |");
		System.out.println("|                         |");
		System.out.println("---------------------------");
				try{
				System.out.print("Enter a Option:");
				int option = scanner.nextInt();
				 	if(option == 1) {
                        adminservice.adminLogin();
                        break;
                    }
					 else if(option == 2) {
					     indexMethod();
						 scanner.close();
						 break;
					 }
					 else {
						System.out.println("Invalid option!!! Please Select a Valid Option."); 
					 }
				}//try block ends
				catch(InputMismatchException e) {
					System.out.println("Invalid option! Please Select a Valid Option.");
				}	
//				scanner.close();
	  } //Loop Ends
	}
	//AdminIndex Method Ends
	
}
