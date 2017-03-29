package alarMe;

import java.sql.SQLException;

public class AlarMeProgram extends LoginProcess{
	
	
	public AlarMeProgram(){
		super(username, user_password, driver);
	}
	
	
    public static void main(String[] args) throws InterruptedException, SQLException {
	   	
    	AlarMeProgram program = new AlarMeProgram();
	   	program.setConnection();
	   	program.init();
	   	
	   	if(program.checkForNewUser() == true){
			System.out.println("Ny bruker er lagt til: ");
			System.out.println("Brukernavn = " + username);
			System.out.println("Passord = " + user_password);
	   		System.out.println(program.newUser());

	   		Blackboard black = new Blackboard();
	   		black.setAssignmentsB();
	   		black.addAssignmentsBToDatabase();
	   		Thread.sleep(5000);
	   		
	   		ItsLearning assI = new ItsLearning();
	   		assI.setAssignmentsI();
	   		assI.addAssignmentsIToDatabase();
	   		Thread.sleep(5000);
	   		
	   		Exams exams = new Exams();
	   		exams.setExams();
	   		exams.addExamsToDatabase();
	   		Thread.sleep(5000);
	   		
	   		driver.close();
	   		
	   	}	   	

    }
  
}

