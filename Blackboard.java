package alarMe;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Blackboard extends LoginProcess {
	
	protected ArrayList<String> assignmentsB = new ArrayList<String>();
	protected ArrayList<String> coursecodeB = new ArrayList<String>();
	protected ArrayList<String> datesB = new ArrayList<String>();
	protected ArrayList<String> assignments = new ArrayList<String>();
	protected ArrayList<String> assignments1 = new ArrayList<String>();
	protected ArrayList<String> assignments2 = new ArrayList<String>();
	protected ArrayList<String> assignments3 = new ArrayList<String>();
	
	
	public Blackboard(){
		super(username, user_password, driver);
	}
	
	
	public ArrayList<String> getAssignmentsB(){
		return assignmentsB;
	}
	
	public ArrayList<String> getCoursecodeB(){
		return coursecodeB;
	}
	
	public ArrayList<String> getDatesB(){
		return datesB;
	}
	
	
	public void setAssignmentsB(){
		
		driver.get("https://ntnu.blackboard.com/webapps/portal/execute/tabs/tabAction?tab_tab_group_id=_70_1");
	    driver.findElement(By.className("loginPrimary")).click();
	    try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	    //Choose NTNU as the institution
	    try {
			chooseNTNU();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	    //Fill in username and password to log in
	    try {
			login();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	    //klikke inn på "varsler"
	    driver.findElement(By.linkText("Varsler")).click();
	    try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    driver.findElement(By.id("headerTextheader::1-dueView::1-dueView_4")).click();
	    driver.findElement(By.id("headerTextheader::1-dueView::1-dueView_3")).click();
	    driver.findElement(By.id("headerTextheader::1-dueView::1-dueView_2")).click();

	    
	    //hent alle ovinger
	    List<WebElement> allAssignments = driver.findElements(By.className("itemGroups"));
	    for (WebElement element : allAssignments) {
	    	String assig = element.getText();
	    	if(assig.length() >= 2){
	    		assignments.add(assig);
	    	}
	    }
		       
	    for(String element1 : assignments){
	    	String assignment = element1.replace("\n", " ");
			assignment = assignment.replaceAll("[,-]", "");
			assignment = assignment.replace("(2017 VÅR)", "");
			assignment = assignment.replace("  ", " ");
			assignment = assignment.replace(" Leveringsfrist", "");
			String[] items = assignment.split(".17");
			List<String> assignments3 = Arrays.asList(items);
		   
		  	                	
	       for(String element2 : assignments3){
	        	List<String> dates = Arrays.asList(element2.split("  "));
	        	assignments1.addAll(dates);
	        }
	    } 	        	
	        for(int i = 0; i < assignments1.size(); i ++){
	    		if (i % 2 != 0){
	    			datesB.add(assignments1.get(i));
	    		}else{
	    			assignments2.add(assignments1.get(i));
	    		}
	    	}
	        	
	        for(String element3 : assignments2){
	        	List<String> stringList = Arrays.asList(element3.split(" "));
	        	for(String e1 : stringList){
	        		if(e1.length() == 7){
	           			if(e1.matches("^[A-Z]{3}\\d{4}")){
	           				coursecodeB.add(e1);
	           				List<String> findass = Arrays.asList(element3.split(e1));
	           				String first = findass.get(0);
	           				first.replaceAll("  ", "");
	           				first.replaceAll(" ", "");
	           				assignmentsB.add(first);
	            				
	           			}
	           		}
	        	}
	        

	}
	        
	}
	
    //Code to add BlackBoard assignments to Database
    public void addAssignmentsBToDatabase() throws SQLException {
    	   
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
     	    setConnection();
     	    for (int i = 0; i < assignmentsB.size(); i++){
     	    	PreparedStatement p = (PreparedStatement) connection.prepareStatement("INSERT INTO Assignment(course_code, assignment_name, assignment_date, student_id) VALUES(?,?,?,?)");
     	    	p.setString(1, coursecodeB.get(i));
     	    	p.setString(2, assignmentsB.get(i));
     	    	p.setString(3, datesB.get(i));
     	    	p.setInt(4, student_id);
     	    	p.executeUpdate();
     	    }
     	        
      	    }catch(Exception e){
    			System.out.println( "error:" + e);
    	       
    	   }
    	}



}
	
