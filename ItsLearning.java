package alarMe;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ItsLearning extends LoginProcess{
	
	
	protected ArrayList<String> assignmentsI = new ArrayList<String>();
	protected ArrayList<String> coursecodeI = new ArrayList<String>();
	protected ArrayList<String> datesI = new ArrayList<String>();
	protected ArrayList<String> dateAndCourse = new ArrayList<String>();
    
    //konstruktor
    public ItsLearning(){
    	super(username, user_password, driver);
    }

    //hent assignments
    public ArrayList<String> getAssignmentsI() {
        return assignmentsI;
    }
    
    public ArrayList<String> getCoursecodeI(){
    	return coursecodeI;
    }
    
    public ArrayList<String> getDatesI(){
    	return datesI;
    }
       
        
    public void setAssignmentsI() throws InterruptedException{
    	
    	driver.get("http://www.ilearn.sexy"); //loads the webpage
    	Thread.sleep(5000);
    	
    	//login();
    	
    	//Thread.sleep(5000);
    	
    	driver.switchTo().frame(driver.findElement(By.name("mainmenu")));
    	//Fetch the assignments and dates from itsLearning
    	List <WebElement> active_assignments = driver.findElements(By.cssSelector("a > .h-va-baseline"));
    	List<WebElement> dates1 = driver.findElements(By.cssSelector("li > .itsl-widget-extrainfo"));
    	
    	
    	for (WebElement a : active_assignments){
    		String assignmentString = a.getText();
    		this.assignmentsI.add(assignmentString);

    	}
    			
    	for (WebElement date : dates1) {
    		//int numberOfAss = active_assignments.size();
    		String dateString = date.getText();
    		dateString = dateString.replaceAll(",", "");
    		dateString = dateString.replaceAll("i ", "");
    		dateAndCourse.add(dateString);
    
    	}
    	for (int i = active_assignments.size() + 1; i < dateAndCourse.size(); i++){
    		if ((i % 2) == 0){
    			String course = dateAndCourse.get(i);
    			String [] course1 = course.split(" ", 2);
    			String courseC = course1 [0];
    			this.coursecodeI.add(courseC);
    			
       		}
    		else{
    			this.datesI.add(dateAndCourse.get(i));
    		}

    	}
    	
    }
    
    //Code to add Itslearning assignments to Database
    public void addAssignmentsIToDatabase() throws SQLException {
        	   
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
     	    setConnection();
     	    for (int i = 0; i < assignmentsI.size(); i++){
     	    	PreparedStatement p = (PreparedStatement) connection.prepareStatement("INSERT INTO Assignment(course_code, assignment_name, assignment_date, student_id) VALUES(?,?,?,?)");
     	    	p.setString(1, coursecodeI.get(i));
     	    	p.setString(2, assignmentsI.get(i));
     	    	p.setString(3, datesI.get(i));
     	    	p.setInt(4, student_id);
     	    	p.executeUpdate();
     	    }
     	        
      	    }catch(Exception e){
    			System.out.println( "error:" + e);
    	       
    	   }
    	}
    
    

}
